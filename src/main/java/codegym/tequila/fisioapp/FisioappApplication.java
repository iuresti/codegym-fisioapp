package codegym.tequila.fisioapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FisioappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FisioappApplication.class, args);
	}

}
