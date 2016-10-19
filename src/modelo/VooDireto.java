package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class VooDireto extends Voo {
	
	private Rota rota;

	public VooDireto(LocalDateTime datahora, Rota rota) {
		super(datahora);
		this.rota = rota;
	}

	@Override
	public Rota getRota() {
		return rota;
	}

	@Override
	public Duration getDuracao() {
		// Calcular duração em função da distância
		double dist = rota.getOrigem().getLocal().distancia(rota.getDestino().getLocal());
		double horas = dist/805 + 0.5;		
		return Duration.ofMinutes((long) (horas*60));
	}

}
