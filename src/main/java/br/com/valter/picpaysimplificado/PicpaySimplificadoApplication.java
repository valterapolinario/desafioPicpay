package br.com.valter.picpaysimplificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PicpaySimplificadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpaySimplificadoApplication.class, args);
	}

}
