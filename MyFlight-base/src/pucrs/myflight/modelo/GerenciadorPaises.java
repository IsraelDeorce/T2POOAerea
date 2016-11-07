
package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorPaises {
	
	private Map<String,Pais> paises;
	
	public GerenciadorPaises(){
		paises = new HashMap<String,Pais>(); 
	}
	
	public void carregaDados() throws IOException {
		Path path2 = Paths.get("countries.dat");
		try (BufferedReader br = Files.newBufferedReader(path2, Charset.forName("utf8"))) {
			String linha = br.readLine();			
			while ((linha = br.readLine()) != null) {
				Scanner scan = new Scanner(linha).useDelimiter(";");
				String codigo, nome;
				codigo = scan.next();
				nome = scan.next();
				Pais aux = new Pais(codigo, nome);
				paises.put(aux.getCodigo(), aux);				
			}
		}
		System.out.println("Total de países carregados na memória: " + paises.size());
	}
	
	public void gravaSerial() throws IOException {
		Path arq = Paths.get("countriesHM.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(paises);
		}		
	}
	
	public Pais getPais(String key){
		return paises.get(key);
	}
	
	


}
