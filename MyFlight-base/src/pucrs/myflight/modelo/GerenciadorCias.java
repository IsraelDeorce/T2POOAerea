package pucrs.myflight.modelo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

public class GerenciadorCias {
	private ArrayList<CiaAerea> empresas;

	public GerenciadorCias() {
		empresas = new ArrayList<>();
	}

	public void adicionar(CiaAerea cia) {
		empresas.add(cia);
	}

	public void carregaDados() throws IOException {
		Path path2 = Paths.get("airlines.dat");
		try (BufferedReader br = Files.newBufferedReader(path2, Charset.defaultCharset())) {
			String linha = br.readLine();
			System.out.println("Cabe�alho: " + linha);
			while ((linha = br.readLine()) != null) {
				Scanner sc = new Scanner(linha).useDelimiter(";"); // separador
																	// é ;
				String codigo, nome;
				codigo = sc.next();
				nome = sc.next();
				CiaAerea nova = new CiaAerea(codigo, nome);
				empresas.add(nova);
				// System.out.println(codigo + " - " + nome);
			}
		}
		System.out.println("Total empresas: " + empresas.size());
	}
	
	public void gravaSerial() throws IOException {
		Path arq1 = Paths.get("airlines.ser");
		try (ObjectOutputStream oarq = new ObjectOutputStream(Files.newOutputStream(arq1))) {
		  oarq.writeObject(empresas);
		}		
	}
	
	public void carregaSerial() throws IOException, ClassNotFoundException {
		Path arq1 = Paths.get("airlines.ser");
		try (ObjectInputStream iarq = new ObjectInputStream(Files.newInputStream(arq1))) {
		  empresas = (ArrayList<CiaAerea>) iarq.readObject();
		}
		System.out.println("Total empresas: " + empresas.size());
	}
	
	public void gravaJSON() throws IOException {
	    try (JsonWriter writer = new JsonWriter(new BufferedOutputStream(new FileOutputStream("airlines.json")))) {
		  writer.write(empresas);		  
	    }
	}
	

	public ArrayList<CiaAerea> listarTodas() {
		// ArrayList<CiaAerea> nova = new ArrayList<>();
		// for(CiaAerea cia: empresas)
		// nova.add(cia);
		// return nova;
		return new ArrayList<CiaAerea>(empresas);
	}

	public CiaAerea buscarCodigo(String codigo) {
		for (CiaAerea c : empresas) {
			if (codigo.equals(c.getCodigo()))
				return c;
		}
		return null; // não achamos!
	}

	public CiaAerea buscarNome(String nome) {
		for (CiaAerea c : empresas) {
			if (nome.equals(c.getNome()))
				return c;
		}
		return null; // não achamos!
	}
}
