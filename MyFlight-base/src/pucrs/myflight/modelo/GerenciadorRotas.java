package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GerenciadorRotas {

	private List<Rota> rotas;
	
	
	public GerenciadorRotas() {
		rotas = new ArrayList<Rota>();		
	}
	
	public void carregaDados() throws IOException{
		Path path = Paths.get("routes.dat");
		try(BufferedReader br = Files.newBufferedReader(path, Charset.forName("utf8"))){
		String linha = br.readLine();
		linha = br.readLine();
		while((linha = br.readLine())!=null){
			Scanner scan1 = new Scanner(linha).useDelimiter(";");
			String cia = scan1.next();
			String origem = scan1.next();
			String destino = scan1.next();
			String codeshare = scan1.next();
			String paradas = scan1.next();
			String avioes = scan1.next();
			Scanner scan2 = new Scanner(avioes);
			
			while(scan2.hasNext()){
				
			}
			
			
			
			
			
			
		}
		
				
			
		}
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
