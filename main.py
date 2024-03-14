# python -m uvicorn main:app --reload --host 0.0.0.0 --port 8000 로 서버시작
# 1. 통신필요할때 8000번 포트 열어줘야함
# 2. https://url.kr/web_tools/ip/ 여기서 내 ip주소 확인해서 주소전달

from fastapi import FastAPI, HTTPException  # fastapi
from pydantic import BaseModel  # basemodel
from pymongo import MongoClient  # pymongo


class UserInfo(BaseModel):
    id: str
    name: str
    birth: str
    email: str


# client, user_col = connect_dbms() // 이런식으로 사용하면됨
def connect_dbms():
    try:
        dbms_client = MongoClient('localhost', 27017)
        db = dbms_client.HearUR
        db_user_col = db.UserInfo
        return dbms_client, db_user_col
    except Exception as e:
        raise HTTPException(status_code=500, detail="DB 연결 실패")


# FastAPI 앱 생성
app = FastAPI()


@app.get("/")
async def root():
    return {"message": "200(OK) 원할하게 접속 성공함."}


# /userinfo 엔드포인트 정의 // 회원가입후 dbms에 회원정보 등록하는 로직
@app.post("/userinfo")
async def save_user_info(user_info: UserInfo):
    try:
        client, user_col = connect_dbms()
        user_col.insert_one(user_info.dict())
        client.close()
        return {"message": "successful insert user_info"}
    except HTTPException as e:
        raise e
    except Exception as e:
        raise HTTPException(status_code=500, detail="서버 오류 발생")


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
