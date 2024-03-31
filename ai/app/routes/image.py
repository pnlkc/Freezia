from fastapi import APIRouter, HTTPException
from common import client

image_router = APIRouter()

@image_router.get("")
def generate_image(
    recipeName: str,
    ingredients: str,
    recipeTypes: str,
):
    try:
        prompt = f"""
        음식 사진를 만들어 줘.
        음식 : {recipeName}
        식재료 : {ingredients}
        음식 유형 : {recipeTypes}
        
        식재료를 알려주는 이유는 음식이라는 피사체에 반영하고 싶기 때문입니다.
        그러니까 절대로 식재료를 음식과 별개로 따로 나열하지 마십시오.
        식재료는 원래 형태대로 음식에 담기지 않고 조리된 상태로 음식에 들어갑니다.
        식재료는 원래 크기대로 음식에 담기지 않고 갓난 아기가 먹을 수 있을 정도로 매우 작은 크기로 들어갑니다.
        음식만 있고 식재료는 보이지 않는 이미지.
        
        음식은 원목 소재의 식탁 위의 정가운데에 놓여있습니다.
        누군가가 그린 그림이 아닌 음식 사진 전문 사진가가 실제로 찍은 듯한 이미지.
        당장 입에 넣고 싶을 정도로 맛있어 보이는 사진.
        
        식욕을 자극하는 색감.
        음식에서 윤기가 납니다.
        따뜻한 음식이라면 김이 모락모락 나는 것을 표현해줘.
        
        음식을 비스듬히 내려다보는 구도.
        음식 전체가 사진 안에 포함되어 있는 구도.
        
        Create an image so lip-smackingly impressive that it instantly triggers an appetite.
        Give warm-touch to the food by showing the steam wafting up from it, suggesting it's freshly cooked and hot.
        """

        response = client.images.generate(
            prompt=prompt,
            model="dall-e-3",
            n=1,
            quality="standard",   # standard or hd
            response_format="b64_json",  # url or b64_json
            size="1024x1024",   # dall-e-3는 1024x1024, 1792x1024, 1024x1792만 가능
            style="natural"     # vivid or natural
        )

        return response.data[0].b64_json

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
