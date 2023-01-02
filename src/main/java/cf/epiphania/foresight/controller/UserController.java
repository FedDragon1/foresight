package cf.epiphania.foresight.controller;

import cf.epiphania.foresight.entity.UserEntity;
import cf.epiphania.foresight.req.UserLoginReq;
import cf.epiphania.foresight.req.UserSaveReq;
import cf.epiphania.foresight.resp.CommonResp;
import cf.epiphania.foresight.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public CommonResp<UserLoginReq> login(@RequestBody UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginReq> commonResp = new CommonResp<>();
        UserLoginReq userLoginReq = userService.login(req);
        commonResp.setContent(userLoginReq);
        return commonResp;
    }

    @PostMapping("/register")
    public CommonResp<UserEntity> register(@RequestBody UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserEntity> commonResp = new CommonResp<>();
        userService.register(req, commonResp);
        return commonResp;
    }

}
