package com.chamadas.TicketSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//Representa os horários de atendimento, indicando dias da semana e intervalos de horas.
public class HorarioAtendimento {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String diaSemana; // Ex: SEGUNDA, TERÇA
	    private String horaInicio; // Ex: 08:00
	    private String horaFim;    // Ex: 18:00


}
