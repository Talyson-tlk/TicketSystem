package com.chamadas.TicketSystem.views;

//Classe utilitária para definição de projeções de visualização com Jackson.
//Projeções são usadas para controlar quais campos das entidades serão serializados em JSON.
//A classe define uma projeção básica as SLAs.
//Foi utilizada para evitar o looping de dados associados 

public class ViewsSLA {
	 public static class SLAOnly {}
	}
