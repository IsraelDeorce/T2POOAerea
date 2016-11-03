package pucrs.myflight.modelo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import pucrs.myflight.gui.JanelaConsulta;

public class App {

	public static void main(String[] args) {
						
		GerenciadorPaises gerCountries = new GerenciadorPaises();
		try{ 
			gerCountries.carregaDados();
		}
		catch (IOException e) {
		System.out.println("Impossível ler countries.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}
		try {
			gerCountries.gravaSerial();
			System.out.println("Gravei countries.ser!");
		} catch (IOException e) {
			System.out.println("Impossível gravar coountries.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);			
		}
		
		GerenciadorAeronaves gerAircrafts = new GerenciadorAeronaves();
		try{
			gerAircrafts.carregaDados();
		}catch (IOException e){
			System.out.println("Impossível ler equipment.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		try {
			gerAircrafts.gravaSerial();
			System.out.println("Gravei equipment.ser!");
		} catch (IOException e) {
			System.out.println("Impossível gravar equipment.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);			
		}		
		
		GerenciadorAeroportos gerAirports = new GerenciadorAeroportos();
		try{ 
			gerAirports.carregaDados();
		}
		catch (IOException e) {
		System.out.println("Impossível ler airports.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}
		try {
			gerAirports.gravaSerial();
			System.out.println("Gravei airports.ser!");
		} catch (IOException e) {
			System.out.println("Impossível gravar airports.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);			
		}
		
		GerenciadorCias gerCias = new GerenciadorCias();
		try {
			gerCias.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossível ler airlines.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
		try {
			gerCias.gravaSerial();
			System.out.println("Gravei airlines.ser!");
		} catch (IOException e) {
			System.out.println("Impossível gravar airlines.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);			
		}
		
		GerenciadorRotas gerRoutes = new GerenciadorRotas();
		try {
			gerRoutes.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossível ler routes.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
		try {
			gerRoutes.gravaSerial();
			System.out.println("Gravei routes.ser!");
		} catch (IOException e) {
			System.out.println("Impossível gravar routes.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);			
		}	
		
				
		// Teste GUI: abre janela
		/*
		JanelaConsulta janela = new JanelaConsulta();
		janela.setGerAeroportos(gerAero);
		
		janela.setGerRotas(gerRotas);
		janela.setVisible(true);

		*/
	}
	
	
	
	
}
