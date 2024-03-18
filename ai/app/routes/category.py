from fastapi import APIRouter, HTTPException
from common import send_request
import config

category_router = APIRouter()

define = {
     "중식" : config.CHINA,
     "양식" : config.RESTAURANT,
     "한식" : config.KOREA,
     "일식" : config.JAPAN
}

answer = {
     "중식" : config.CHINA_ANSWER_JSON,
     "양식" : config.RESTAURANT_ANSWER_JSON,
     "한식" : config.KOREA_ANSWER_JSON,
     "일식" : config.JAPAN_ANSWER_JSON
}

@category_router.get("/list")
def category_recipe_list(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str,
    recipe_types: str
):
        try:

            if recipe_types == "유통기한 임박":
                recipe_types = ""

            instruction = f"""
                    너는 사용자 개인 맞춤 {recipe_types} 레시피 추천 전문가야. 

                    {recipe_types}의 정의는 다음과 같아.
                    {recipe_types}:
                    {define[recipe_types]}

                    사용자의 정보는 다음과 같아.
                    사용자의 냉장고 재고 정보: {ingredients}
                    사용자의 지병 정보: {diseases} 
                    사용자의 기피 식재료 정보: {dislikeIngredients}

                    냉장고 재고 정보를 고려해서 레시피를 추천하고, 냉장보관하지 않는 쌀이나 튀김가루, 각종 조미료 같은 것들은 이미 있다고 가정하고 추천해.
                    {answer[recipe_types]}

                """    
            
            recipe_candidates =  send_request(instruction)

            result = {
                "recipeList" : []
            }

            for name in recipe_candidates:
                if recipe_candidates[name] > 80:
                    result["recipeList"].append(name)
            
            return result

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))

@category_router.get("/recipe")
def category_recipe(
    ingredients: str,
    diseases: str,
    dislikeIngredients: str,
    name: str
):
        try:
            instruction = f"""
                    너는 사용자의 맞춤 {name} 레시피 추천 전문가야. 

                    사용자의 정보는 다음과 같아.
                    사용자의 냉장고 재고 정보: {ingredients}
                    사용자의 지병 정보: {diseases} 
                    사용자의 기피 식재료 정보: {dislikeIngredients}
                   
                    사용자가 레시피 추천 요청을 하면 무조건 이 재료 내에서 만들 수 있는 {name} 레시피를 추천해주도록 해.
                    냉장고 재고 정보를 고려해서 레시피를 추천하고, 냉장보관하지 않는 쌀이나 튀김가루, 각종 조미료 같은 것들은 이미 있다고 가정하고 추천해.
                    가지고 있는 재료를 전부 사용하지 않고 일부만 사용한 레시피를 추천해도 돼.
                    사용자가 재고에 없는 재료를 요청할 경우에만 해당 재료를 포함해서 레시피를 추천해주도록 해.

                    모든 대답은 아래 JSON 형태로 반환해줘.
                    reply는 네가 하는 대답이야. 레시피의 어떤 요소가 스트레스 해소에 적합한지 꼭 명시하도록 해.
                    recipeList는 네가 추천해주는 레시피의 세부 정보를 저 recipeList 안에 있는 JSON 형태로 변환해서 반환해줘. 
                    """ + config.RECIPE_JSON_FORMAT            
            
            return send_request(instruction)

        except Exception as e:
            raise HTTPException(status_code=500, detail=str(e))
