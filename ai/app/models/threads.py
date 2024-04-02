from pydantic import BaseModel


class ThreadRequest(BaseModel):
    ingredients: str
    diseases: str
    dislikeIngredients: str

    class Config:
        json_schema_extra = {
            "example": {
                "ingredients" : "베이컨, 토마토, 계란, 대파, 마늘",
                "diseases" : "당뇨, 고혈압",
                "dislikeIngredients" : "토마토, 오이, 가지"
                }
        }


class ThreadAssistantIdRequest(BaseModel):
    threadId: str
    assistantId: str

    class Config:
        json_schema_extra = {
            "example": {
                "threadId": "thread_sRT1we1w421",
                "assistantId": "asst_sRT1we1w421",
            
            }
        }

class ThreadAssistantResponse(BaseModel):
    threadResult: str
    assistantResult: str


class ThreadAssistantIdResponse(BaseModel):
    threadId: str
    assistantId: str

    class Config:
        json_schema_extra = {
            "example": {
                "threadId": "thread_sRT1we1w421",
                "assistantId": "asst_sRT1we1w421",
            
            }
        }
