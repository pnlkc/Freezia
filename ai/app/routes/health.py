from fastapi import APIRouter, HTTPException
from common import send_request
from prompt_config import RECIPE_JSON_FORMAT

health_router = APIRouter()

@health_router.get("/stress")
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
                    냉장고 재고 정보를 고려해서 레시피를 추천하고, 냉장보관하지 않는 쌀이나 튀김가루, 각종 조미료 같은 것들은 이미 있다고 가정하고 추천해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                    레시피와 관련 없는 질문에는 절대 대답하지마.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 스트레스 해소에 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                    """ + RECIPE_JSON_FORMAT            
            
            return send_request(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))


@health_router.get("/blood-oxygen")
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
                    냉장고 재고 정보를 고려해서 레시피를 추천하고, 냉장보관하지 않는 쌀이나 튀김가루, 각종 조미료 같은 것들은 이미 있다고 가정하고 추천해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                    레시피와 관련 없는 질문에는 절대 대답하지마.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 혈중산소 농도 수치가 낮은 사람에게 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                    """ + RECIPE_JSON_FORMAT            
            
            return send_request(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))


@health_router.get("/sleep")
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
                    냉장고 재고 정보를 고려해서 레시피를 추천하고, 냉장보관하지 않는 쌀이나 튀김가루, 각종 조미료 같은 것들은 이미 있다고 가정하고 추천해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.
                    레시피와 관련 없는 질문에는 절대 대답하지마.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 수면 부족에 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘.\n
                    """ + RECIPE_JSON_FORMAT            
            
            return send_request(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
