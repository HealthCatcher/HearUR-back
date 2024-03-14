from pymongo import MongoClient  # pymongo 임포트
from pydantic import BaseModel  # basemodel


def connect_db():
    dbms_client = MongoClient('localhost', 27017)
    db = client.HearUR
    db_user_col = db.UserInfo
    return dbms_client, db_user_col


class UserInfo(BaseModel):
    id: str
    name: str
    birth: str
    email: str


user_info = UserInfo(id="103", name="byh", birth="20000819", email="test")
client, user_col = connect_db()

print(user_info)
print(user_info.json())

user_col.insert_one(user_info.dict())

# find 배우기!
# all_data = user_col.find()
# for data in all_data:
# print(data)
