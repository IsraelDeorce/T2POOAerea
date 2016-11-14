package pucrs.myflight.modelo;

import java.io.IOException;
import pucrs.myflight.gui.JanelaConsulta;

public class App {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
						
		GerenciadorPaises gerCountries = new GerenciadorPaises();

		try{ 
			gerCountries.carregaDados();
		}
		catch (IOException e) {
		System.out.println("Impossível ler countries.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}
				
		GerenciadorAeronaves gerAircrafts = new GerenciadorAeronaves();
		try{
			gerAircrafts.carregaDados();
		}
		catch (IOException e){
			System.out.println("Impossível ler equipment.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
						
		GerenciadorAeroportos gerAirports = new GerenciadorAeroportos();
		try{ 
			gerAirports.carregaDados(gerCountries.enviaHM());
		}
		catch (IOException | ClassNotFoundException e) {
		System.out.println("Impossível ler airports.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}
		
		GerenciadorCias gerCias = new GerenciadorCias();
		try {
			gerCias.carregaDados();
		} 
		catch (IOException e) {
			System.out.println("Impossível ler airlines.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
		
		GerenciadorRotas gerRoutes = new GerenciadorRotas();
		try {
			gerRoutes.carregaDados(gerCias.enviaHM(), gerAirports.enviaHM(), gerAircrafts.enviaHM());
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("Impossível ler routes.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
		
		// Teste GUI: abre janela
		
		JanelaConsulta janela = new JanelaConsulta();
		janela.setGerAeroportos(gerAirports);
		
		janela.setGerRotas(gerRoutes);
		janela.setVisible(true);
	}
}
	
	


