package pucrs.myflight.modelo;

import java.util.ArrayList;

public class GerenciadorCias {
	private ArrayList<CiaAerea> empresas;
	
	public GerenciadorCias() {
		empresas = new ArrayList<>();
	}
	
	public void adicionar(CiaAerea cia) {
		empresas.add(cia);	
	}
	
	public ArrayList<CiaAerea> listarTodas() {
		//ArrayList<CiaAerea> nova = new ArrayList<>();
		//for(CiaAerea cia: empresas)
		//	nova.add(cia);
		//return nova;
		return new ArrayList<CiaAerea>(empresas);
	}
	
	public CiaAerea buscarCodigo(String codigo) {
		for(CiaAerea c : empresas) {
			if(codigo.equals(c.getCodigo()))
				return c;					
		}
		return null; // não achamos!
	}
	
	public CiaAerea buscarNome(String nome) {
		for(CiaAerea c : empresas) {
			if(nome.equals(c.getNome()))
				return c;					
		}
		return null; // não achamos!
	}
}
