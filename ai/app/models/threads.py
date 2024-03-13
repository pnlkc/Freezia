from pydantic import BaseModel

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
