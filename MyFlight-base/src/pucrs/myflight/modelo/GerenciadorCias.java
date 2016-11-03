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
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

public class GerenciadorCias {
	private Map<String, CiaAerea> empresas;

	public GerenciadorCias() {
		empresas = new TreeMap<String, CiaAerea>();
	}

	public void carregaDados() throws IOException {
		Path path2 = Paths.get("airlines.dat");
		try (BufferedReader br = Files.newBufferedReader(path2, Charset.forName("utf8"))) {
			String linha = br.readLine();
			while ((linha = br.readLine()) != null) {
				Scanner sc = new Scanner(linha).useDelimiter(";");
				String codigo, nome;
				codigo = sc.next();
				nome = sc.next();
				CiaAerea aux = new CiaAerea(codigo, nome);
				empresas.put(aux.getCodigo(), aux);			}
		}
		System.out.println("Total empresas: " + empresas.size());
	}
	
	public void gravaSerial() throws IOException {
		Path arq = Paths.get("airlines.ser");
		try (ObjectOutputStream outArq = new ObjectOutputStream(Files.newOutputStream(arq))) {
		  outArq.writeObject(empresas);
		}		
	}
}
