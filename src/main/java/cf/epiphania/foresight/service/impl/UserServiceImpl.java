package cf.epiphania.foresight.service.impl;

import cf.epiphania.foresight.entity.UserEntity;
import cf.epiphania.foresight.mapper.UserMapper;
import cf.epiphania.foresight.req.UserLoginReq;
import cf.epiphania.foresight.req.UserSaveReq;
import cf.epiphania.foresight.resp.CommonResp;
import cf.epiphania.foresight.service.UserService;
import cf.epiphania.foresight.util.CopyUtil;
import cf.epiphania.foresight.util.EmailOTP;
import cf.epiphania.foresight.util.aspect.WebInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.hash.Hashing;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Value("${email.from}")
    private String email;

    @Value("${email.password}")
    private String password;

    @Override
    @WebInfo
    public UserLoginReq login(UserLoginReq req) {
        UserEntity userEntity = selectByEmail(req.getEmail());
        if (ObjectUtils.isEmpty(userEntity)) {
            // email does not exist in database
            return null;
        }
        if (userEntity.getPassword().equals(req.getPassword())) {
            // passes
            req = CopyUtil.copy(userEntity, UserLoginReq.class);
            return req;
        }
        return null;
    }

    @Override
    @WebInfo
    public void register(UserSaveReq req, CommonResp<UserEntity> resp) {
        UserEntity user = CopyUtil.copy(req, UserEntity.class);
        UserEntity userEntity = selectByEmail(req.getEmail());
        if (ObjectUtils.isEmpty(userEntity)) {
            //send an otp to the given email
            try {
                String otp = EmailOTP.sendOTP(email, user.getEmail(), user.getUsername(), password);
                resp.setMessage(Hashing.sha256().hashString(otp, StandardCharsets.UTF_8).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setJoinedSince(new Timestamp(System.currentTimeMillis()));
            userMapper.insert(user);
            UserEntity insertedUser = selectByEmail(req.getEmail());
            resp.setContent(insertedUser);  // return new created user entity
            userMapper.createEventTable(insertedUser.getUid());
        } else {  // related account exists with the email
            resp.setMessage("An account exists with this email");
            resp.setSuccess(false);
        }
    }

    public UserEntity selectByEmail(String email) {
        QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.lambda().eq(UserEntity::getEmail, email);
        List<UserEntity> userEntities = userMapper.selectList(userEntityQueryWrapper);
        if (CollectionUtils.isEmpty(userEntities)) {
            return null;
        }
        return userEntities.get(0);
    }
}
