package cf.epiphania.foresight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cf.epiphania.foresight.mapper")
public class ForesightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForesightApplication.class, args);
    }

}
