package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class GerenciadorAeronaves {	
	private Map<String, Aeronave> aeronaves;
	
	public GerenciadorAeronaves() {
		aeronaves = new TreeMap<String, Aeronave>();
	}
	
	public void carregaDados() throws IOException {
		Path path = Paths.get("equipment.dat");
		try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
			String linha = br.readLine();
			System.out.println("CabeÁalho: " + linha);
			while ((linha = br.readLine()) != null) {
				Scanner scan = new Scanner(linha).useDelimiter(";");
				String codigo, descricao;
				int capacidade = 0; //Observe que a inicializaÁ„o com 0 pode causar erros.
				codigo = scan.next();
				descricao = scan.next();
				try{
					capacidade = Integer.parseInt(scan.next());
										
				}catch (NumberFormatException e) {
				    e.printStackTrace();
				}							
				Aeronave aux = new Aeronave(codigo, descricao, capacidade);
				aeronaves.put(aux.getCodigo(), aux);			
			}					
		}
		System.out.println("Total Aeronaves: " + aeronaves.size());
	}
	/*
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
		return null; // n√£o achamos!
	}
	*/
}
