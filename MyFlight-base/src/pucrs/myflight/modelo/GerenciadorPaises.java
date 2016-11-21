
package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class GerenciadorPaises {
	
	private HashMap<String,Pais> paisesHM;
	
	public GerenciadorPaises(){
		paisesHM = new HashMap<String,Pais>(); 
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
				paisesHM.put(aux.getCodigo(), aux);				
			}
		}	
	}
	
	public HashMap<String, Pais> enviaHM(){
		return paisesHM;
	}
	
	public Pais getPais(String key){
		return paisesHM.get(key.toUpperCase());
	}
	
	


}
