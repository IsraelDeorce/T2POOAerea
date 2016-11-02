package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class GerenciadorAeroportos {

	private Map<String, Aeroporto> aeroportosCodigo;
	private Map<String, Aeroporto> aeroportosPais;
	
	public GerenciadorAeroportos() {
		aeroportosCodigo = new TreeMap<String, Aeroporto>();
		aeroportosPais = new TreeMap<String, Aeroporto>();		
	}
	
	public void carregaDados() throws IOException {
		Path path = Paths.get("airports.dat");
		try (BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset())) {
			String linha = br.readLine();			
			while ((linha = br.readLine()) != null) {
				Scanner scan = new Scanner(linha).useDelimiter(";");
				String codigo, nome, pais;
				Double longitude, latitude;
				codigo = scan.next();
				latitude = scan.nextDouble();
				longitude = scan.nextDouble();
				nome = scan.next();
				pais =scan.next();
				Aeroporto aux = new Aeroporto(codigo, nome, new Geo(latitude,longitude), pais);
				aeroportosCodigo.put(aux.getCodigo(), aux);
				aeroportosPais.put(aux.getPais(), aux);
		}
			
		System.out.println("Total de aeroporto: " + aeroportosCodigo.size());
		}
	}
		
	public Aeroporto buscarCodigo(String codigo) {
		if(aeroportosCodigo.get(codigo)!=null)
			return aeroportosCodigo.get(codigo);
		return null; 
	}
}
