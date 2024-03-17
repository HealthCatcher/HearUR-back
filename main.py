"""
프로젝트 시작 : 2024-03
"""
# python -m uvicorn main:app --reload --host 0.0.0.0 --port 8000 로 서버시작
# 1. 통신필요할때 8000번 포트 열어줘야함
# 2. https://url.kr/web_tools/ip/ 여기서 내 ip주소 확인해서 주소전달

from fastapi import FastAPI, HTTPException  # fastapi
from pydantic import BaseModel  # basemodel
from pymongo import MongoClient  # pymongo


class UserInfo(BaseModel):  # 회원 정보 BaseModel
    id: str
    name: str
    birth: str
    email: str


def connect_dbms():  # client, user_col = connect_dbms()
    try:
        dbms_client = MongoClient('localhost', 27017)
        db = dbms_client.HearUR
        db_user_col = db.UserInfo
        return dbms_client, db_user_col
    except Exception as e:
        raise HTTPException(status_code=500, detail="DB 연결 실패")


app = FastAPI()  # FastAPI 앱 생성
client, user_col = connect_dbms()  # db 연동


@app.get("/")  # root로 접속 확인
async def root():
    return {"message": "200(OK) 원할하게 접속 성공함."}


@app.post("/setuser")  # /setuser 엔드포인트 정의 // 회원가입후 dbms에 회원정보 등록하는 로직
async def save_user_info(user_info: UserInfo):
    try:
        user_col.insert_one(user_info.dict())
        client.close()
        return {"message": "successful insert user_info"}
    except HTTPException as e:
        raise e
    except Exception as e:
        raise HTTPException(status_code=500, detail="서버 오류 발생")


@app.post("/getuser")  # /getuser 엔드포인트 정의 // 로그인 시 userinfo return
async def get_user_info(uid: str):
    try:
        user_info = user_col.find_one({"id": uid})
        if user_info:
            return user_info
        else:
            raise HTTPException(status_code=404, detail="사용자를 찾을 수 없습니다.")
    except HTTPException as e:
        raise e
    except Exception as e:
        raise HTTPException(status_code=500, detail="서버 오류 발생")


@app.post("/moduser")  # /moduser 엔드포인트 정의 // 회원정보 수정시 userinfo get
async def mod_user_info(user_info: UserInfo):
    try:
        # 사용자 정보를 수정하는 로직 추가
        existing_user = user_col.find_one({"id": user_info.id})
        if existing_user:
            user_col.replace_one({"id": user_info.id}, user_info.dict())
            return {"message": "사용자 정보 수정 완료"}
        else:
            raise HTTPException(status_code=404, detail="사용자를 찾을 수 없습니다.")
    except HTTPException as e:
        raise e
    except Exception as e:
        raise HTTPException(status_code=500, detail="서버 오류 발생")


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
