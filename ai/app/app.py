from fastapi import FastAPI, HTTPException, Depends
from routes.root import root_router

import config
import uvicorn


app = FastAPI()
app.include_router(root_router,  prefix="/api/ai")



if __name__ == "__main__":
    uvicorn.run(app, host=config.HOST, port=config.PORT)