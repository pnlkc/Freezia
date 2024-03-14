from dotenv import load_dotenv
import os

# .env 파일 로드
load_dotenv()

API_KEY = os.getenv("API_KEY")
HOST = os.getenv("HOST")
PORT = int(os.getenv("PORT"))

CONVERSATION_JSON_FORMAT = """
                이 때, 레시피는 전부 1인분 기준으로 재료 수량 정보를 제공해주도록 해.

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
                                    unit : '단위 종류',  // 조금, 약간 같은 애매한 단위를 제시하지 말고 명확한 단위를 제시해줘. ex) g, kg, 한줌, 1T, 1스푼
                                }
                            ],
                            cookTime : '총 요리에 필요한 시간',
                            carlorie : '요리의 칼로리',
                            servings : '요리 제공량' // 몇 인분인지,
                            recipeType : '요리 유형', // 이 예시 내에서만 반환 - 한식, 양식, 중식, 일식, 밑반찬, 면요리, 볶음요리, 찜요리, 국물요리, 유통기한 임박 중 하나로 리턴해줘.
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

RECIPE_JSON_FORMAT = """
이 때, 레시피는 전부 1인분 기준으로 재료 수량 정보를 제공해주도록 해.

{
    reply : "냉장고에 있는 스팸, 마요네즈, 김치를 사용해 레시피를 생성했습니다.",
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
                    unit : '단위 종류', // 조금, 약간 같은 애매한 단위를 제시하지 말고 명확한 단위를 제시해줘. ex) g, kg, 한줌, 1T, 1스푼
                }
            ],
            cookTime : '총 요리에 필요한 시간',
            carlorie : '요리의 칼로리',
            servings : '요리 제공량' // 몇 인분인지,
            recipeType : '요리 유형', // 이 예시 내에서만 반환 - 한식, 양식, 중식, 일식, 밑반찬, 면요리, 볶음요리, 찜요리, 국물요리, 유통기한 임박 중 하나로 리턴해줘.
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