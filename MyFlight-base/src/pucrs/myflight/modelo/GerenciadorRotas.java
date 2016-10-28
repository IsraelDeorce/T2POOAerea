package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Comparator;

public class GerenciadorRotas {

	private ArrayList<Rota> rotas;
	
	public GerenciadorRotas() {
		rotas = new ArrayList<>();
	}
	
	public void adicionar(Rota r) {
		rotas.add(r);	
	}
	
	public void ordenarCia() {
		rotas.sort( (Rota r1, Rota r2)
				-> r1.getCia().getNome().compareTo(r2.getCia().getNome()));
	}
	
	public void ordenarOrigem() {
		rotas.sort( (Rota r1, Rota r2)
				-> r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome()));
	}
	
	public void ordenarOrigemCia() {
		rotas.sort( (Rota r1, Rota r2) ->
		{
			int res = r1.getOrigem().getNome().compareTo(r2.getOrigem().getNome());
			if(res != 0)
				return res;
			// Desempata pelo nome da cia.
			return r1.getCia().getNome().compareTo(r2.getCia().getNome());
		});
		// ou:
		rotas.sort( Comparator.comparing((Rota r) -> r.getOrigem().getNome())
				.thenComparing(r -> r.getCia().getNome()));
	}
	
	public ArrayList<Rota> listarTodas() {
		return new ArrayList<Rota>(rotas);
	}
	
	public ArrayList<Rota> buscarOrigem(Aeroporto origem) {
		ArrayList<Rota> lista = new ArrayList<>();
		for(Rota r : rotas) {
			if(origem.getCodigo().equals(r.getOrigem().getCodigo()))
				lista.add(r);					
		}
		return lista;
	}
}
