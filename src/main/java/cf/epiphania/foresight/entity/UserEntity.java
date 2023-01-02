package cf.epiphania.foresight.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

@TableName("users")
public class UserEntity {

    @TableId(value="uid", type=IdType.AUTO)
    private Integer uid;
    private String username;
    private String email;
    private String password;
    private Timestamp joinedSince;

    public Integer getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getJoinedSince() {
        return joinedSince;
    }

    public void setJoinedSince(Timestamp joinedSince) {
        this.joinedSince = joinedSince;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid=" + uid +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", joinedSince=" + joinedSince +
                '}';
    }

}
