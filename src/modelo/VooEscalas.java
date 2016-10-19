package pucrs.myflight.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VooEscalas extends Voo {

	private ArrayList<Rota> rotas;
	
	public VooEscalas(LocalDateTime dataHora) {		
		super(dataHora); // chama o construtor de Voo		//
		rotas = new ArrayList<>();
	}
	
	public void adicionarRota(Rota r) {
		// Adiciona a rota na lista
		rotas.add(r);
	}
	
	public ArrayList<Rota> getRotas() {
		return (ArrayList<Rota>) rotas.clone();
	}
	
	@Override
    public String toString() {
       //return getStatus() + " " + getDatahora()
       //+ "("+getDuracao()+"): " + getRota() + ", "+rotaFinal;
		return super.toString();//+", "+rotaFinal;
    }

	@Override
	public Rota getRota() {
		// Retornar 1a. rota da lista
		return null;
	}

	@Override
	public Duration getDuracao() {
		// Calcular a duração total (soma das durações)
		double total = 0;
		double horas, dist;
		for(Rota r: rotas) {
			dist = r.getOrigem().getLocal().distancia(r.getDestino().getLocal());
			horas = dist/805 + 0.5;
			total += horas;
		}
		return Duration.ofMinutes((long) (total*60));	
	}
}
