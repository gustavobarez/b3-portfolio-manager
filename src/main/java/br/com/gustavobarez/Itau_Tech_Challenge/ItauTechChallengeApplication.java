package br.com.gustavobarez.Itau_Tech_Challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ItauTechChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItauTechChallengeApplication.class, args);
	}

}
