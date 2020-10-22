package caiyunc.file.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"caiyunc.file.api", "caiyunc.file.service"})
@MapperScan("caiyunc.file.db")
public class CaiYuncApplication {


    public static void main(String[] args) {
        SpringApplication.run(CaiYuncApplication.class, args);
    }

}
