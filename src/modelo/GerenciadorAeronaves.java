package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GerenciadorAeronaves {

	private ArrayList<Aeronave> aeronaves;
	
	public GerenciadorAeronaves() {
		aeronaves = new ArrayList<>();
	}
	
	public void adicionar(Aeronave av) {
		aeronaves.add(av);	
	}
	
	public void ordenarAeronaves() {
		Collections.sort(aeronaves);
	}
	
	public void ordenarDescricao() {
//		aeronaves.sort( (Aeronave a1, Aeronave a2)
//				-> a1.getDescricao().compareTo(a2.getDescricao()) );
		//aeronaves.sort(Comparator.comparing(a -> a.getDescricao()));
		aeronaves.sort(Comparator.comparing(Aeronave::getDescricao));
	}
	
	public void ordenarCodigo() {
//		aeronaves.sort( (Aeronave a1, Aeronave a2)
//				-> a1.getCodigo().compareTo(a2.getCodigo()));
//		aeronaves.sort(Comparator.comparing(a -> a.getCodigo()));
		aeronaves.sort(Comparator.comparing(Aeronave::getCodigo));
	}
	
	public ArrayList<Aeronave> listarTodas() {
		return new ArrayList<Aeronave>(aeronaves);
	}
	
	public Aeronave buscarCodigo(String codigo) {
		for(Aeronave av : aeronaves) {
			if(codigo.equals(av.getCodigo()))
				return av;					
		}
		return null; // não achamos!
	}
}
