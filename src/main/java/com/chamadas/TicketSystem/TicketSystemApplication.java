package com.chamadas.TicketSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//Classe principal que inicia a aplicação Spring Boot para o sistema de chamados.
//Ativa o suporte a agendamentos no sistema por meio de `@EnableScheduling`.
public class TicketSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketSystemApplication.class, args);
	}

}
