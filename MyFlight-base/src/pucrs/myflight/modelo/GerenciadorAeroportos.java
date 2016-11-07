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
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GerenciadorAeroportos{

	private List<Aeroporto> aeroportosAL;
	private HashMap<String, Aeroporto> aeroportosHM;
	
	public GerenciadorAeroportos() {
		aeroportosAL = new ArrayList<Aeroporto>();
		aeroportosHM = new HashMap<String, Aeroporto>();
		
	}
	
	public void gravaSerial() throws IOException {
		Path arq = Paths.get("airportsAL.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(aeroportosAL);
		}
		arq = Paths.get("airportsHM.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
			  outArq.writeObject(aeroportosHM);
		}
	}
	
	public void carregaDados() throws IOException, ClassNotFoundException {
		HashMap<String, Pais> paises;
		Path arq = Paths.get("countriesHM.ser");
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))){
			paises = (HashMap<String, Pais>) outArq.readObject();
		}
		arq = Paths.get("airports.dat");		
		try (BufferedReader br = Files.newBufferedReader(arq, Charset.forName("utf8"))) {
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
				Geo geo = new Geo(latitude,longitude);
				Aeroporto aero = new Aeroporto(codigo, nome, geo, paises.get(codPais));
				aeroportosAL.add(aero);								
			}
			for(Aeroporto a : aeroportosAL){
				if(aeroportosHM.containsKey(a.getCodigo()))
					System.out.println("Chave duplicada: " + a.getCodigo());
				else aeroportosHM.put(a.getCodigo(),a);
			}
		}
	}
		
		

	public List<Aeroporto> buscarPais(String pais) {
		List<Aeroporto> aeroPais = aeroportosAL.stream()
				  .filter(a -> a.getPais().getCodigo().equals(pais))
				  .collect(Collectors.toList()); 
		return aeroPais;		
	}
	
	public Aeroporto buscarCod(String cod){
		return aeroportosHM.get(cod);
	}
	
	public Aeroporto buscarProximo(Geo geo){
		List<Aeroporto> aero = aeroportosAL.stream()
				.filter(a -> Geo.distancia(a.getLocal(), geo) >=5)
				.collect(Collectors.toList());
		return aero.get(0);
	}
	
	public Geo getGeo(String cod){
		return aeroportosHM.get(cod).getLocal();
	}
		
}			
			
	

