package pucrs.myflight.modelo;

import org.jxmapviewer.viewer.GeoPosition;

public class Aeroporto{	
	
	private String codigo;
	private String nome;
	private GeoPosition loc;
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
	
	public GeoPosition getLocal() {
		return loc;
	}
	
	public Pais getPais(){
		return pais;
	}

	@Override
	public String toString() {
		return nome + " - " + codigo;
	}
	
	
}
