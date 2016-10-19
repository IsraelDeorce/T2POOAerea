package pucrs.myflight.modelo;

import java.util.ArrayList;

public class GerenciadorAeroportos {

	private ArrayList<Aeroporto> aeroportos;
	
	public GerenciadorAeroportos() {
		aeroportos = new ArrayList<>();
	}
	
	public void adicionar(Aeroporto a) {
		aeroportos.add(a);	
	}
	
	public ArrayList<Aeroporto> listarTodos() {
		return new ArrayList<Aeroporto>(aeroportos);
	}
	
	public Aeroporto buscarCodigo(String codigo) {
		for(Aeroporto a : aeroportos) {
			if(codigo.equals(a.getCodigo()))
				return a;					
		}
		return null; // não achamos!
	}
}
