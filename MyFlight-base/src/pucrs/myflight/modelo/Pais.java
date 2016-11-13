package pucrs.myflight.modelo;

public class Pais{
	
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
