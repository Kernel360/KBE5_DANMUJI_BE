package back2basics.moduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "back2basics.moduleapi",
    "back2basics.moduleservice",
    "back2basics.modulecommon"
})
public class ModuleApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ModuleApiApplication.class, args);
  }

}
