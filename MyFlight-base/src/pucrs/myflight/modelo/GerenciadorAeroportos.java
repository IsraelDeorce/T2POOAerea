package pucrs.myflight.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.jxmapviewer.viewer.GeoPosition;

public class GerenciadorAeroportos{

	private List<Aeroporto> aeroportosAL;
	private HashMap<String, Aeroporto> aeroportosHM;
	
	public GerenciadorAeroportos() {
		aeroportosAL = new ArrayList<Aeroporto>();
		aeroportosHM = new HashMap<String, Aeroporto>();
	}
		
	public void carregaDados(HashMap<String, Pais> paises) throws IOException, ClassNotFoundException {
		Path arq = Paths.get("airports.dat");		
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
			for(Aeroporto a :aeroportosAL)
				aeroportosHM.put(a.getCodigo(), a);
			}
	}
	
	public HashMap<String, Aeroporto> enviaHM(){
		return aeroportosHM;
	}
	
	public List<Aeroporto> enviaAL(){
		return aeroportosAL;
	}

	public Set<Aeroporto> buscarPais(String pais) {
		Set<Aeroporto> aeroPais = aeroportosAL.stream()
				  .filter(a -> a.getPais().getCodigo().equals(pais))
				  .collect(Collectors.toSet()); 
		return aeroPais;		
	}
	
	public Aeroporto buscarCod(String cod){
		return aeroportosHM.get(cod);
	}
	 /*Método que localiza um aeroporto no raio de 15km de onde o usuário clicou no mapa
	 	Paramêtro é o Geo clicado
	 	Retorno é o Aeroporto 	  
	*/
	public Aeroporto buscarAeroProximo(GeoPosition pos){
		Aeroporto aero = aeroportosAL.stream()
				.filter(a -> Geo.distancia(a.getLocal(), pos)<=15)
				.findAny().get();		
		return aero;
	}
		
	public GeoPosition getGeo(String cod){
		return aeroportosHM.get(cod).getLocal();
	}
	
	
	
}			
			
	

