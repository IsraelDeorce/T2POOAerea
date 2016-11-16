package pucrs.myflight.modelo;

import java.io.Serializable;

public class Rota{
	
	private CiaAerea cia;
	private Aeroporto origem;
	private Aeroporto destino;
	private Aeronave aeronave;
	
	public Rota (CiaAerea cia, Aeroporto origem, Aeroporto destino, Aeronave aeronave){
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		this.aeronave = aeronave;		
	}
	
	public Rota (CiaAerea cia, Aeroporto origem, Aeroporto destino){
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;		
	}	
	
	@Override
	public String toString()
	{
		return cia.getCodigo() + ": "
				+ origem.getCodigo() + " -> " + destino.getCodigo()
				+ " [" +aeronave.getCodigo()+ "]";
	}
	
	public CiaAerea getCia() {
		return cia;
	}
	
	public Aeroporto getDestino() {
		return destino;
	}
	
	public Aeroporto getOrigem() {
		return origem;
	}
	
	public Aeronave getAeronave() {
		return aeronave;
	}
}
