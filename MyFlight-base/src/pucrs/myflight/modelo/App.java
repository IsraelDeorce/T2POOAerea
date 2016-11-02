package pucrs.myflight.modelo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import pucrs.myflight.gui.JanelaConsulta;

public class App {

	public static void main(String[] args) {
						
		/*
		try {
			gerCias.carregaSerial();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ImpossÃ­vel ler airlines.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
				
		try {
			gerCias.gravaSerial();
			System.out.println("Gravei airlines.ser!");
		} catch (IOException e) {
			System.out.println("ImpossÃ­vel gravar airlines.ser!");
			System.out.println("Msg: "+e);
			System.exit(1);			
		}*/
		
		GerenciadorPaises gerPaises = new GerenciadorPaises();
		try{ 
			gerPaises.carregaDados();
		}
		catch (IOException e) {
		System.out.println("Impossível ler countries.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}	
		
		GerenciadorAeronaves gerAeron = new GerenciadorAeronaves();
		try{
			gerAeron.carregaDados();
		}catch (IOException e){
			System.out.println("Impossível ler equipment.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);			

		}
		
		GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
		try{ 
			gerAero.carregaDados();
		}
		catch (IOException e) {
		System.out.println("Impossível ler airports.dat!");
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
		/*
		try {
			gerCias.gravaJSON();
		} catch (IOException e) {
			System.out.println("Impossível gravar airlines.json!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		*/
		
		GerenciadorRotas gerRotas = new GerenciadorRotas();
		
		
		
		
				
		// Teste GUI: abre janela
		/*
		JanelaConsulta janela = new JanelaConsulta();
		janela.setGerAeroportos(gerAero);
		
		janela.setGerRotas(gerRotas);
		janela.setVisible(true);

		*/

		
		
		
	}
}
