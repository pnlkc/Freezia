from fastapi import HTTPException, APIRouter
from sse_starlette import EventSourceResponse
from models.threads import ThreadAssistantIdResponse, ThreadAssistantResponse
from routes.health import health_router
from routes.category import category_router
from routes.image import image_router
from prompt_config import CONVERSATION_JSON_FORMAT
from common import client
import json
import asyncio

root_router = APIRouter()

root_router.include_router(health_router,  prefix="/health")
root_router.include_router(category_router,  prefix="/category")
root_router.include_router(image_router, prefix="/image")


@root_router.delete('/thread-assistant', response_model=ThreadAssistantResponse)
def delete_thread_and_assistant(
    threadId: str,
    assistantId: str
):
    try:
        thread_response = client.beta.threads.delete(threadId)
        assistant_response = client.beta.assistants.delete(assistantId)
        return {"threadResult": thread_response, "assistantResult": assistant_response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@root_router.get('/thread', response_model=ThreadAssistantIdResponse)
def get_thread_and_assistant():
    try:
        # Thread 생성
        thread = client.beta.threads.create()
        return json.loads('{ "threadId" : "' + thread.id + '"}')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@root_router.get("/prompt")
async def recipe_stream(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str,
    prompt: str,
    threadId: str
):
    return EventSourceResponse(get_recipe_stream(ingredients, diseases, dislikeIngredients, prompt, threadId))


async def get_recipe_stream(ingredients, diseases, dislikeIngredients, prompt, threadId):
    instruction = f"""
                너는 사용자 맞춤 레시피 추천 전문가야. 

                사용자의 정보는 다음과 같아.
                사용자의 냉장고 재고 정보: {ingredients}
                사용자의 지병 정보: {diseases}
                사용자의 기피 식재료 정보: {dislikeIngredients}

                사용자가 레시피 추천 요청을 하면 무조건 이 재고 내에서 만들 수 있는 요리로만 추천해주도록 해.
                냉장고 재고 정보를 고려해서 레시피를 추천하고, 냉장보관하지 않는 쌀이나 튀김가루, 각종 조미료 같은 것들은 이미 있다고 가정하고 추천해.
                가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                레시피와 관련 없는 질문에는 절대 대답하지마.

                모든 대답은 아래 JSON 형태로 반환해줘.
                reply는 네가 하는 대답이야.
                recommendList는 네가 하는 대답을 보고 사용자가 어떤 질문을 하면 적합할 지 추천해주는 질문이야.
                recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.
                JSON의 key는 꼭 ""로 감싸줘야해. \n
                """ + CONVERSATION_JSON_FORMAT

    assistant = client.beta.assistants.create(
            name="S005 Manager",
            instructions=instruction,
            tools=[{"type": "code_interpreter"}],
            model="gpt-4-turbo-preview"
        )

    # Thread 생성
    thread = client.beta.threads.retrieve(thread_id=threadId)
    # thread = client.beta.threads.create()

    try:
        message = client.beta.threads.messages.create(
            thread_id=thread.id,
            role="user",
            content=prompt
        )

        stream = client.beta.threads.runs.create(
            thread_id=thread.id,
            assistant_id=assistant.id,
            stream=True)
        
        for event in stream:
            # 이벤트 데이터가 "thread.message.delta"인 경우에 대해서만 처리
            if event.data.object == "thread.message.delta":
                for content in event.data.delta.content:
                    # text인 경우에만 클라이언트에 전송
                    if content.type == 'text':
                        yield content.text.value
                        await asyncio.sleep(0.05)
        client.beta.assistants.delete(assistant.id)
    except Exception as e:
        print(e)
