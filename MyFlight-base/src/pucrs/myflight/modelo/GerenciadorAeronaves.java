package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class GerenciadorAeronaves {	
	private HashMap<String, Aeronave> aeronavesHM;
	
	public GerenciadorAeronaves() {
		aeronavesHM = new HashMap<String, Aeronave>();
	}
	
	public void carregaDados() throws IOException {
		Path path = Paths.get("equipment.dat");
		try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("utf8"))) {
			String linha = br.readLine();			
			while ((linha = br.readLine()) != null) {
				Scanner scan = new Scanner(linha).useDelimiter(";");
				String codigo, descricao;
				int capacidade = 0; //Observe que a inicialização com 0 pode causar erros.
				codigo = scan.next();
				descricao = scan.next();
				try{
					capacidade = Integer.parseInt(scan.next());
										
				}catch (NumberFormatException e) {
				    e.printStackTrace();
				}							
				Aeronave aux = new Aeronave(codigo, descricao, capacidade);
				aeronavesHM.put(aux.getCodigo(), aux);			
			}					
		}	
	}
	
	public HashMap<String, Aeronave> enviaHM(){
		return aeronavesHM;
	}
}
