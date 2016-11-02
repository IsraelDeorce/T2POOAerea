package pucrs.myflight.modelo;

public class Rota {
	private CiaAerea cia;
	private String origem;
	private String destino;
	private Aeronave aeronave;
	
	public Rota (CiaAerea cia, String origem, String destino, Aeronave aeronave){
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		this.aeronave = aeronave;		
	}	
	
	@Override
	public String toString()
	{
		return cia.getCodigo()+": "
				+origem + " -> " + destino
				+" [" +aeronave.getCodigo()+ "]";
	}
	
	public CiaAerea getCia() {
		return cia;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public String getOrigem() {
		return origem;
	}
	
	public Aeronave getAeronave() {
		return aeronave;
	}
}
