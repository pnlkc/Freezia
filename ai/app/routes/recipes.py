from fastapi import APIRouter, HTTPException
import time, json, config
from openai import OpenAI

recipe_router = APIRouter()
client = OpenAI(api_key=config.API_KEY)

@recipe_router.get("/stress")
def stress_recipe(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str
):
        try:
            instruction = f"""
                    너는 스트레스 지수가 높은 사용자의 맞춤 레시피 추천 전문가야. 

                    사용자의 정보는 다음과 같아.
                    사용자의 냉장고 재고 정보: {ingredients}
                    사용자의 지병 정보: {diseases} 
                    사용자의 기피 식재료 정보: {dislikeIngredients}

                    무조건 스트레스 지수가 높은 사람이 먹으면 좋은 음식을 딱 한가지만 추천해주도록 해.
                    사용자가 레시피 추천 요청을 하면 무조건 이 재고 내에서 만들 수 있는 요리로만 추천해주도록 해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                    레시피와 관련 없는 질문에는 절대 대답하지마.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 스트레스 해소에 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                    """ + config.RECIPE_JSON_FORMAT            
            
            return get_recipe(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))


@recipe_router.get("/blood-oxygen")
def blood_oxygen_recipe(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str
):
        try:
            instruction = f"""
                    너는 혈중 산소 농도가 낮은 사용자의 맞춤 레시피 추천 전문가야. 

                    사용자의 정보는 다음과 같아.
                    사용자의 냉장고 재고 정보: {ingredients}
                    사용자의 지병 정보: {diseases} 
                    사용자의 기피 식재료 정보: {dislikeIngredients}

                    무조건 혈중 산소 농도가 낮은 사람이 먹으면 좋은 음식을 딱 한가지만 추천해주도록 해.
                    사용자가 레시피 추천 요청을 하면 무조건 이 재고 내에서 만들 수 있는 요리로만 추천해주도록 해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                    레시피와 관련 없는 질문에는 절대 대답하지마.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 혈중산소 농도 수치가 낮은 사람에게 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                    """ + config.RECIPE_JSON_FORMAT            
            
            return get_recipe(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))


@recipe_router.get("/sleep")
def sleep_recipe(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str
):
        try:
            instruction = f"""
                    너는 수면 시간이 부족한 사용자의 맞춤 레시피 추천 전문가야. 

                    사용자의 정보는 다음과 같아.
                    사용자의 냉장고 재고 정보: {ingredients}
                    사용자의 지병 정보: {diseases} 
                    사용자의 기피 식재료 정보: {dislikeIngredients}

                    무조건 수면 시간이 부족한 사람이 먹으면 좋은 음식을 딱 한가지만 추천해주도록 해.
                    사용자가 레시피 추천 요청을 하면 무조건 이 재고 내에서 만들 수 있는 요리로만 추천해주도록 해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                    레시피와 관련 없는 질문에는 절대 대답하지마.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 수면 부족에 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                    """ + config.RECIPE_JSON_FORMAT            
            
            return get_recipe(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))


def get_recipe(instruction):
    assistant = client.beta.assistants.create(
            name="S005 Manager",
            instructions=instruction,
            tools=[{"type": "code_interpreter"}],
            model="gpt-4-turbo-preview"
        )

    # Thread 생성
    thread = client.beta.threads.create()

    client.beta.threads.messages.create(
    thread_id=thread.id,
    role="user",
    content="식사 메뉴 추천해줘."
        )

    # run을 실행해야 create했던 message들이 API를 통해 chat gpt에게 질문
    run = client.beta.threads.runs.create(
    thread_id=thread.id,
    assistant_id=assistant.id,
    )

    # 2초 마다 한번씩 API 응답이 return 되었는지 확인
    while True:
        run_check = client.beta.threads.runs.retrieve(
            thread_id=thread.id,
            run_id=run.id
        )
        if run_check.status not in ['queued','in_progress']:
            break
        else:
            time.sleep(2)

    response = client.beta.threads.messages.list(thread_id=thread.id).data[0].content[0].text.value
    delete_connect(client, thread.id, assistant.id)

    return json.loads(response)


def delete_connect(client, thread_id, assistant_id):
    client.beta.threads.delete(thread_id)
    client.beta.assistants.delete(assistant_id)