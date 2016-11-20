package pucrs.myflight.modelo;

public class Rota{
	
	private CiaAerea cia;
	private Aeroporto origem;
	private Aeroporto destino;
	private Aeronave aeronave;
	private Double distancia;
	
	public Rota (CiaAerea cia, Aeroporto origem, Aeroporto destino, Aeronave aeronave){
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		this.aeronave = aeronave;
		distancia = Geo.distancia(this.origem.getLocal(), this.destino.getLocal());
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
