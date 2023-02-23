package cf.epiphania.foresight;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
@MapperScan({"com.gitee.sunchenbin.mybatis.actable.dao", "cf.epiphania.foresight.mapper"})
public class ForesightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForesightApplication.class, args);
    }

}
