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
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import pucrs.myflight.gui.GerenciadorMapa;

public class GerenciadorRotas {

	private List<Rota> rotas;
	private GerenciadorAeroportos geraero;		
	
	public GerenciadorRotas() throws ClassNotFoundException, IOException{
		rotas = new ArrayList<Rota>();
		geraero = new GerenciadorAeroportos();
		geraero.carregaDados();
	}
	
	public void gravaSerial() throws IOException{
		Path arq = Paths.get("routes.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(rotas);
		}		
	}		
	
	public void carregaDados() throws IOException, ClassNotFoundException{
		HashMap<String, CiaAerea> empresas;
		Path arq = Paths.get("airlinesHM.ser");
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))){
			empresas = (HashMap<String, CiaAerea>) outArq.readObject();
		}
		HashMap<String, Aeronave> aeronaves;
		arq = Paths.get("equipmentHM.ser");
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))){
			aeronaves = (HashMap<String, Aeronave>) outArq.readObject();
		}
		/*HashMap<String, Aeroporto> aeroportos;		
		arq = Paths.get("airportsHM.ser");
		try (ObjectInputStream outArq = new ObjectInputStream(Files.newInputStream(arq))){
			aeroportos = (HashMap<String, Aeroporto>) outArq.readObject();	
		}
		*/
		
		arq = Paths.get("routes.dat");
		try(BufferedReader br = Files.newBufferedReader(arq, Charset.forName("utf8"))){
		String linha = br.readLine();
		linha = br.readLine();		
		while((linha = br.readLine())!=null){	
			Rota aux;
			Scanner scan1 = new Scanner(linha).useDelimiter(";");
			String cia = scan1.next();
			//System.out.println("CIA : " + cia);
			String origem = scan1.next();
			//System.out.println("OR: " + origem);
			String destino = scan1.next();
			//System.out.println("DEST: " + destino);
			String codeshare = scan1.next();
			//System.out.println("COD: " + codeshare);
			String paradas = scan1.next();
			//System.out.println("PAR: " + paradas);
			if(scan1.hasNext()){
				String equip = scan1.next();
				//System.out.println("EQUIP: " + equip);			 
				Scanner scan2 = new Scanner(equip);
				equip = scan2.next();
				aux = new Rota(empresas.get(cia), geraero.buscarCod(origem), geraero.buscarCod(destino), aeronaves.get(equip));
			}
			else
				aux = new Rota(empresas.get(cia), geraero.buscarCod(origem), geraero.buscarCod(destino));
			rotas.add(aux);
		}
		}
	}
}
		
			
			
						
			