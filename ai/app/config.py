from dotenv import load_dotenv
import os

# .env 파일 로드
load_dotenv()

API_KEY = os.getenv("API_KEY")
HOST = os.getenv("HOST")
PORT = int(os.getenv("PORT"))