package pucrs.myflight.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class GerenciadorVoos {
	private ArrayList<Voo> voos;
	
	public GerenciadorVoos() {
		voos = new ArrayList<>();
	}
	
	public void adicionar(Voo v) {
		voos.add(v);	
	}
	
	public ArrayList<Voo> listarTodos() {
		return new ArrayList<Voo>(voos);
	}
	
	public ArrayList<Voo> buscarData(LocalDate data) {
		ArrayList<Voo> lista = new ArrayList<>();
		for(Voo v : voos) {
			if(v.getDatahora().toLocalDate().equals(data))
				lista.add(v);							
		}
		return lista; // não achamos!
	}
}
