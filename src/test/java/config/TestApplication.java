package config;

import com.dudaMeneses.odata.jpa.ODataJpaExecutorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
        repositoryBaseClass = ODataJpaExecutorImpl.class,
        basePackages = "config"
)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
