from fastapi import APIRouter, HTTPException
from common import client

image_router = APIRouter()


@image_router.get("/")
def generate_image(
    recipe_name: str,
    # ingredients: str
):
    try:
        prompt = f"""
        맛있게 만들어진 {recipe_name} 사진을 만들어 줘.
        
        다음 조건을 반드시 만족해야 해!
        - 화목한 가정의 따뜻한 분위기가 나야 해.
        
        음식과 관련 없는 질문에는 절대 대답하지마.
        """

        response = client.images.generate(
            prompt=prompt,
            model="dall-e-3",
            n=1,
            quality="standard",   # standard or hd
            response_format="url",  # url or b64_json
            size="1024x1024",   # dall-e-3는 1024x1024, 1792x1024, 1024x1792만 가능
            style="natural"     # vivid or natural
        )

        print(response.data[0].url)
        print(response.data[0].revised_prompt)

        return response.data[0].url

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
