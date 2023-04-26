package com.projeto.iprt2.iprt2.model;

public enum Cargo {

	PASTOR("Pastor"),
	DIACONO("Diacono"),
	EVANGELISTA("Evangelista"),
	MISSIONARIA("Missionaria"),
	PRESBITERO("Presbitero"),
	COOPERADOR("Cooperador");
	
	
	private String nome;
	private String valor;
	
	private Cargo (String nome) {
		this.nome = nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor = this.name();
	}
}
