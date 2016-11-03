package pucrs.myflight.modelo;

import java.io.Serializable;

public class Aeroporto implements Serializable {
	
	private static final long serialVersionUID = -4664156660074006249L;
	private String codigo;
	private String nome;
	private Geo loc;
	private Pais pais;
	
	public Aeroporto(String codigo, String nome, Geo loc, Pais pais) {
		this.codigo = codigo;
		this.nome = nome;
		this.loc = loc;
		this.pais = pais;
	}
	
	public String getCodigo(){ 
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Geo getLocal() {
		return loc;
	}
	
	public Pais getPais(){
		return pais;
	}

	@Override
	public String toString() {
		return nome + " [" + codigo + "] Localização " + loc + " " + pais;
	}
	
	
}
