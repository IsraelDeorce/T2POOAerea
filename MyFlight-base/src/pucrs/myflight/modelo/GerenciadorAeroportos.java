package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GerenciadorAeroportos {

	private List<Aeroporto> aeroportos;	
	private TreeMap<String, Pais> paises;
	
	public GerenciadorAeroportos() {
		aeroportos = new ArrayList<Aeroporto>();		
		try{
			carregaSerial();			
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("Impossível ler countries.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		
	}
	
	public void gravaSerial() throws IOException {
		Path arq = Paths.get("airports.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(aeroportos);
		}		
	}
	
	public void carregaSerial() throws IOException, ClassNotFoundException {
		Path arq = Paths.get("countries.ser");
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))) {
		  paises = (TreeMap<String, Pais>) outArq.readObject();
		}
	}
	
	public void carregaDados() throws IOException {
		Path path = Paths.get("airports.dat");
		try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("utf8"))) {
			String linha = br.readLine();				
			while ((linha = br.readLine()) != null){
				Scanner scan = new Scanner(linha).useDelimiter(";");				
				String codigo, nome, codPais;
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
				codPais = scan.next();
				
				Pais pais = paises.get(codPais);				
				
				aeroportos.add(new Aeroporto(codigo, nome, new Geo(latitude,longitude), pais));				
			}
			
		System.out.println("Total de aeroporto: " + aeroportos.size());		
		}
	}
		
	public List<Aeroporto> buscarPais(Pais pais) {
		List<Aeroporto> aeroPais = aeroportos.stream()
				  .filter(a -> a.getPais().getCodigo().equals(pais.getCodigo()))
				  .collect(Collectors.toList()); 
		return aeroPais;		
	}
	
	public TreeMap<String,Aeroporto> mapaCodigos(){
		TreeMap<String,Aeroporto> aux = new TreeMap<String, Aeroporto>();
		for(Aeroporto a : aeroportos)
			aux.put(a.getCodigo(),a);
		return aux;
	}	
}
