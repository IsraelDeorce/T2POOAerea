package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GerenciadorRotas {

	private List<Rota> rotas;
	
	
	public GerenciadorRotas() {
		rotas = new ArrayList<Rota>();		
	}
	
	public ArrayList<Rota> buscarOrigem(String origem) {
		ArrayList<Rota> lista = new ArrayList<>();	
				for(Rota r : rotas){
					if(r.getOrigem().equals(origem))
							lista.add(r);
				}
		return lista;
	}
	
}
