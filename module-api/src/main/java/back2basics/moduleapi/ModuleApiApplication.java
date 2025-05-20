package back2basics.moduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "back2basics.moduleapi",
    "back2basics.moduleinfra",
    "back2basics.moduleservice",
    "back2basics.modulecommon"
})
@EntityScan("back2basics.moduledomain")
@EnableJpaRepositories("back2basics.moduledomain")
public class ModuleApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ModuleApiApplication.class, args);
  }

}
