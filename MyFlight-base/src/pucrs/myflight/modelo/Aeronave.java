package pucrs.myflight.modelo;

public class Aeronave{
		
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
	
	@Override
	public String toString(){
		return codigo + " - " + descricao;   
	}

	
}
