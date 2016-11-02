package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorAeroportos {

	private List<Aeroporto> aeroportos;	
	
	public GerenciadorAeroportos() {
		aeroportos = new ArrayList<Aeroporto>();		
	}
	
	public void carregaDados() throws IOException {
		Path path = Paths.get("airports.dat");
		try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("utf8"))) {
			String linha = br.readLine();				
			while ((linha = br.readLine()) != null){
				Scanner scan = new Scanner(linha).useDelimiter(";");				
				String codigo, nome, pais;
				Double longitude=null, latitude = null; //Adicionado valor null para inicializar Aeroporto aux
				codigo = scan.next();
				try{
					latitude = Double.parseDouble(scan.next());					
				} catch (NumberFormatException e) {
				    e.printStackTrace();
				}
				try{
					longitude = Double.parseDouble(scan.next());					
				} catch (NumberFormatException e) {
				    e.printStackTrace();
				}
				nome = scan.next();
				pais = scan.next();
				
				Aeroporto aux = new Aeroporto(codigo, nome, new Geo(latitude,longitude), pais);
				aeroportos.add(aux);				
			}
			
		System.out.println("Total de aeroporto: " + aeroportos.size());		
		}
	}
		
	public ArrayList<String> buscarCodigo(String codigo) {
		
		return aeroportosPais.get(codigo);
		 
	}
}
