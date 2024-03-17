from fastapi import HTTPException, APIRouter
from pydantic import BaseModel
from pymongo import MongoClient

# 사용자 정보 모델
class UserInfo(BaseModel):
    id: str
    name: str
    email: str


# 엔드포인트 관련 클래스
class UserEndpoints:
    router = APIRouter()

    @router.post("/setuser")  # /setuser 엔드포인트 정의 // 회원가입후 dbms에 회원정보 등록하는 로직
    async def save_user_info(self, user_info: UserInfo):
        try:
            user_col.insert_one(user_info.dict())
            return {"message": "successful insert user_info"}
        except Exception as e:
            raise HTTPException(status_code=500, detail="서버 오류 발생")

    @router.post("/getuser")  # /getuser 엔드포인트 정의 // 로그인 시 userinfo return
    async def get_user_info(self, uid: str):
        try:
            user_info = user_col.find_one({"id": uid})
            if user_info:
                return user_info
            else:
                raise HTTPException(status_code=404, detail="사용자를 찾을 수 없습니다.")
        except Exception as e:
            raise HTTPException(status_code=500, detail="서버 오류 발생")

    @router.post("/moduser")  # /moduser 엔드포인트 정의 // 회원정보 수정시 userinfo get
    async def mod_user_info(self, user_info: UserInfo):
        try:
            # 사용자 정보를 수정하는 로직 추가
            existing_user = user_col.find_one({"id": user_info.id})
            if existing_user:
                user_col.replace_one({"id": user_info.id}, user_info.dict())
                return {"message": "사용자 정보 수정 완료"}
            else:
                raise HTTPException(status_code=404, detail="사용자를 찾을 수 없습니다.")
        except Exception as e:
            raise HTTPException(status_code=500, detail="서버 오류 발생")


# 엔드포인트 관련 클래스 인스턴스 생성
user_endpoints = UserEndpoints()

# 엔드포인트 라우터 등록
router = user_endpoints.router
