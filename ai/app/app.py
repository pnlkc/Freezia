from fastapi import FastAPI, HTTPException, Depends
from models.threads import ThreadAssistantIdResponse, ThreadAssistantResponse, ThreadRequest, ThreadAssistantIdRequest
from models.prompts import PromptRequest
from routes.health import health_router
from routes.category import category_router
from prompt_config import CONVERSATION_JSON_FORMAT
from common import client

import config
import uvicorn
import json
import time


app = FastAPI()
app.include_router(health_router,  prefix="/health")
app.include_router(category_router,  prefix="/category")

# FastAPI에서는 dependency를 사용하여 반복적인 로직을 중앙에서 처리할 수 있음
# Access Token을 확인하는 dependency를 생성
def get_accessToken():
    accessToken = "DUMMY ACCESS TOKEN"
    if not accessToken:
        raise HTTPException(status_code=401, detail="Unauthorized")
    return accessToken


@app.delete('/thread-assistant', response_model=ThreadAssistantResponse)
def delete_thread_and_assistant(
    threadId: str,
    assistantId: str,
    # accessToken: str = Depends(get_accessToken),
):
    try:
        thread_response = client.beta.threads.delete(threadId)
        assistant_response = client.beta.assistants.delete(assistantId)
        return {"threadResult": thread_response, "assistantResult": assistant_response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.get('/thread-assistant', response_model=ThreadAssistantIdResponse)
def get_thread_and_assistant(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str,
    accessToken: str = Depends(get_accessToken),
):
    try:
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
                recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                """ + CONVERSATION_JSON_FORMAT
        
        # Assistant 생성
        assistant = client.beta.assistants.create(
            name="S005 Manager",
            instructions=instruction,
            tools=[{"type": "code_interpreter"}],
            model="gpt-4-turbo-preview"
        )

        # Thread 생성
        thread = client.beta.threads.create()
        return {"threadId": thread.id, "assistantId": assistant.id}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post('/prompt')
def prompt(
    request_data: PromptRequest,
    accessToken: str = Depends(get_accessToken)):
    try:
        
        thread_id = request_data.threadId
        assistant_id = request_data.assistantId
        input_prompt = request_data.prompt

        client.beta.threads.messages.create(
            thread_id=thread_id,
            role="user",
            content=input_prompt
        )

        # run을 실행해야 create했던 message들이 API를 통해 chat gpt에게 질문
        run = client.beta.threads.runs.create(
        thread_id=thread_id,
        assistant_id=assistant_id,
        )

        # 2초 마다 한번씩 API 응답이 return 되었는지 확인
        while True:
            run_check = client.beta.threads.runs.retrieve(
                thread_id=thread_id,
                run_id=run.id
            )
            if run_check.status not in ['queued','in_progress']:
                break
            else:
                time.sleep(2)

        response = client.beta.threads.messages.list(thread_id=thread_id).data[0].content[0].text.value
        return  json.loads(response)
    
    except Exception as e:
        print(e)
        raise HTTPException(status_code=500, detail=str(e))


if __name__ == "__main__":
    uvicorn.run(app, host=config.HOST, port=config.PORT)