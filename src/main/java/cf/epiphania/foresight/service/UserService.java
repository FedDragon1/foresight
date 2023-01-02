package cf.epiphania.foresight.service;

import cf.epiphania.foresight.entity.UserEntity;
import cf.epiphania.foresight.req.UserLoginReq;
import cf.epiphania.foresight.req.UserSaveReq;
import cf.epiphania.foresight.resp.CommonResp;

public interface UserService {

    void register(UserSaveReq req, CommonResp<UserEntity> resp);

    UserLoginReq login(UserLoginReq req);

}
