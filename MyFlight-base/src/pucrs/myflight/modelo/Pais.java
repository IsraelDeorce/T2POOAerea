package pucrs.myflight.modelo;

public class Pais {
	private String nome;
	private int codigo;
	
	public Pais(String nome, int codigo){
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public int getCodigo() {
		return codigo;
	}
	
}
