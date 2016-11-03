package pucrs.myflight.modelo;

import java.io.Serializable;

public class Aeronave implements Serializable {
		
	private static final long serialVersionUID = 7957040885809928366L;
	private String codigo;
	private String descricao;
	private int capacidade;
	private static int totalAeronaves = 0; 
	
	public Aeronave(String codigo, String descricao, int capacidade) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.capacidade = capacidade;
		totalAeronaves++;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public int getCapacidade() {
		return capacidade;
	}

	
}
