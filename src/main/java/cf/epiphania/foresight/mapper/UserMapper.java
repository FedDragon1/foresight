package cf.epiphania.foresight.mapper;

import cf.epiphania.foresight.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    @Update("CREATE TABLE uid#{uid} (event_id INT PRIMARY KEY AUTO_INCREMENT, start_time DATETIME NOT NULL, end_time DATETIME, event VARCHAR(30) NOT NULL, status ENUM('incomplete', 'in_progress', 'over_due', 'completed') NOT NULL)")
    void createEventTable(int uid);

}
