package ydrive.app_ydrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AppYdriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppYdriveApplication.class, args);
	}

}
