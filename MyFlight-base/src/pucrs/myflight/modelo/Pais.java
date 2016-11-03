package pucrs.myflight.modelo;

import java.io.Serializable;

public class Pais implements Serializable{
		
	private static final long serialVersionUID = 4902285437172393371L;
	private String codigo;
	private String nome;
	
	
	public Pais(String codigo, String nome){
		this.codigo = codigo;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public String getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return nome + " [" + codigo + "]\n";
	}
	
	
}
