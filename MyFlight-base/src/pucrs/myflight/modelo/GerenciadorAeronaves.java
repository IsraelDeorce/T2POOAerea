package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorAeronaves {	
	private Map<String, Aeronave> aeronaves;
	
	public GerenciadorAeronaves() {
		aeronaves = new HashMap<String, Aeronave>();
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
				aeronaves.put(aux.getCodigo(), aux);			
			}					
		}
		System.out.println("Total Aeronaves: " + aeronaves.size());
	}
	
	
	public void gravaSerial() throws IOException {
		Path arq = Paths.get("equipmentHM.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(aeronaves);
		}		
	}	
}
