package pucrs.myflight.modelo;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class GerenciadorCias {
	private HashMap<String, CiaAerea> empresasHM;

	public GerenciadorCias() {
		empresasHM = new HashMap<String, CiaAerea>();
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
				empresasHM.put(aux.getCodigo(), aux);			}
		}		
	}
	
	public HashMap<String, CiaAerea> enviaHM(){
		return empresasHM;
	}
}
