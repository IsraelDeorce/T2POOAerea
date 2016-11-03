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
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class GerenciadorRotas {

	private List<Rota> rotas;
	private TreeMap<String,CiaAerea> companhias;
	private TreeMap<String,Aeroporto> aeroportos;	
	private TreeMap<String,Aeronave> aeronaves;	
	
	public GerenciadorRotas(){
		rotas = new ArrayList<Rota>();
		try{
			carregaSerialCias();
			carregaSerialAeroportos();
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("Impossível ler arquivo!");
			System.out.println("Msg: "+e);
			System.exit(1);
			}
	}
	
	public void gravaSerial() throws IOException{
		Path arq = Paths.get("routes.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(rotas);
		}		
	}
	
	public void carregaSerialCias() throws IOException, ClassNotFoundException {
			Path arq = Paths.get("airlines.ser");		
			try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))) {
				companhias = (TreeMap<String, CiaAerea>) outArq.readObject();
		}
	}
	
	public void carregaSerialAeroportos() throws IOException, ClassNotFoundException {
		Path arq = Paths.get("airports.ser");		
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))) {
			aeroportos = (TreeMap<String, Aeroporto>) outArq.readObject();
		}
	}
	
	public void carregaSerialAeronaves() throws IOException, ClassNotFoundException {
		Path arq = Paths.get("equipment.ser");		
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))) {
			aeronaves = (TreeMap<String, Aeronave>) outArq.readObject();
		}
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
			ArrayList<String> lista = new ArrayList<String>();
			while(scan2.hasNext())
				lista.add(scan2.next());
			for(String aircraft : lista){
				rotas.add(new Rota(
						companhias.get(cia), 
						aeroportos.get(origem), 
						aeroportos.get(destino), 
						aeronaves.get(aircraft)));
				System.out.println("Adicionei ");
			}
		}
		}
	}
	
}
