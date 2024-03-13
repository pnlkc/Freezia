from fastapi import FastAPI, HTTPException, Depends
from models.threads import ThreadAssistantIdResponse, ThreadAssistantResponse
from models.prompts import PromptRequest
from openai import OpenAI
import uvicorn
import json
import time
import os
from dotenv import load_dotenv

# .env 파일 로드
load_dotenv()

API_KEY = os.getenv("API_KEY")
HOST = os.getenv("HOST")
PORT = int(os.getenv("PORT"))

client = OpenAI(api_key=API_KEY)
ingredients_info = "베이컨, 토마토, 계란, 대파, 마늘"
allergies_info = "복숭아, 갑각류"
hate_info = "토마토, 오이, 가지"

instruction = f"""
        너는 사용자 맞춤 레시피 추천 전문가야. 

        사용자의 정보는 다음과 같아.
        사용자의 냉장고 재고 정보: {ingredients_info}
        사용자의 알레르기 정보: {allergies_info} 
        사용자의 기피 식재료 정보: {hate_info}

        사용자가 레시피 추천 요청을 하면 무조건 이 재고 내에서 만들 수 있는 요리로만 추천해주도록 해.
        가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
        사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.

        레시피와 관련 없는 질문에는 절대 대답하지마.

        모든 대답은 아래 JSON 형태로 반환해줘.
        reply는 네가 하는 대답이야.
        recommendList는 네가 하는 대답을 보고 사용자가 어떤 질문을 하면 적합할 지 추천해주는 질문이야.
        recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
        """ + """
        {
            reply : "냉장고에 있는 스팸, 마요네즈, 김치를 사용해 레시피를 생성했습니다.",
            recommendList : [
                "가장 최근에 만든 요리와 비슷한 재료를 사용하는 레시피를 알려줘",
                "간식으로 먹고 싶은데 칼로리를 절반으로 줄인 레시피를 알려줘",
                "덮밥말고 볶음밥 레시피로 알려줘"
            ],
            recipeList : [
                {
                    name : '스팸마요김치덮밥',
                    ingredientList : [
                        {
                            name : '재료명',
                            amounts : '재료의 양',
                            unit : '단위 종류',
                        }
                    ],
                    seasoningList : [
                        {
                            name : '조미료명',
                            amounts : '조미료의 양',
                            unit : '단위 종류',
                        }
                    ],
                    cookTime : '총 요리에 필요한 시간',
                    carlorie : '요리의 칼로리',
                    servings : '요리 제공량' // 몇 인분인지,
                    recipeType : '중식, 면요리, 유통기한 임박',
                    recipeSteps: [
                        {
                            type: '단계 유형', // ex: '끓이기', '재료손질', '굽기', '볶기'
                            description : '해당 단계 설명',
                            name : '해당 단계 이름',
                            duration : '해당 단계 진행 시간, 타이머에 사용',
                            tip : '해당 단계에 도움이 되는 요리 팁',
                            timer : '해당 단계에서 걸리는 시간'
                        },
                    ]
                },
            ]
        }
    """


app = FastAPI()

# FastAPI에서는 dependency를 사용하여 반복적인 로직을 중앙에서 처리할 수 있음
# Access Token을 확인하는 dependency를 생성
def get_access_token():
    access_token = "DUMMY ACCESS TOKEN"
    if not access_token:
        raise HTTPException(status_code=401, detail="Unauthorized")
    return access_token


@app.delete('/thread-assistant', response_model=ThreadAssistantResponse)
def delete_thread_and_assistant(
    access_token: str = Depends(get_access_token),
    thread_id: str = None,
    assistant_id: str = None
):
    try:
        # 성공적으로 삭제되었을 경우
        assistant_response = client.beta.assistants.delete(assistant_id)
        thread_response = client.beta.threads.delete(thread_id)
        return {"threadResult": thread_response, "assistantResult": assistant_response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get('/thread-assistant', response_model=ThreadAssistantIdResponse)
def get_thread_and_assistant(
    access_token: str = Depends(get_access_token)
):
    try:
        # 성공적으로 생성되었을 경우
        
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
    access_token: str = Depends(get_access_token)):
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
    uvicorn.run(app, host=HOST, port=PORT)