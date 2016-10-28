package pucrs.myflight.modelo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import pucrs.myflight.gui.JanelaConsulta;

public class App {

	public static void main(String[] args) {
		
		//System.out.println("Total cias: "+CiaAerea.getTotalCias());
		//CiaAerea gol = new CiaAerea("G3", "Gol Linhas Aéreas SA");
		//System.out.println("Total cias: "+CiaAerea.getTotalCias());
		//CiaAerea latam = new CiaAerea("JJ", "LATAM Linhas Aéreas");
		//System.out.println("Total cias: "+CiaAerea.getTotalCias());		
		
		/*
		//Geo locPoa = new Geo(-29.9939, -51.1711);
		Aeroporto poa = new Aeroporto("POA", "Salgado Filho Intl Apt",
				new Geo(-29.9939, -51.1711));
		Aeroporto gru = new Aeroporto("GRU", "São Paulo Guarulhos Intl Apt",
				new Geo(-23.4356, -46.4731));
		Aeroporto mia = new Aeroporto("MIA", "Miami International Apt",
				new Geo(25.7933,-80.2906));
		
		double dist = Geo.distancia(poa.getLocal(),
					gru.getLocal());
		System.out.println("Distância POA-GRU: "+dist);
		
		double dist2 = poa.getLocal().distancia(gru.getLocal());
		System.out.println("Distância POA-GRU: "+dist2);
		
		GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
		gerAero.adicionar(poa);
		gerAero.adicionar(gru);
		gerAero.adicionar(mia);
		
		GerenciadorAeronaves gerAvioes = new GerenciadorAeronaves();
		gerAvioes.adicionar(new Aeronave("73G", "Boeing 737-700", 126));
		gerAvioes.adicionar(new Aeronave("733", "Boeing 737-300", 140));
		gerAvioes.adicionar(new Aeronave("380", "Airbus Industrie A380", 644));
		//gerAvioes.ordenarAeronaves();
		gerAvioes.ordenarDescricao();
		//gerAvioes.ordenarCodigo();
		
		Aeronave a1 = gerAvioes.buscarCodigo("733");
		Aeronave a2 = gerAvioes.buscarCodigo("73G");
		System.out.println("CompareTo: "+a2.compareTo(a1));
		
		GerenciadorRotas gerRotas = new GerenciadorRotas();		
		gerRotas.adicionar(new Rota(gol, gru,
			poa, gerAvioes.buscarCodigo("733")));
		gerRotas.adicionar(new Rota(latam, gru,
				mia, gerAvioes.buscarCodigo("380")));
		gerRotas.adicionar(new Rota(gol, poa,
				gru, gerAvioes.buscarCodigo("73G")));		
		
		System.out.println();
		// Descomente uma das linhas abaixo para ordenar as ROTAS
		//gerRotas.ordenarCia();
		//gerRotas.ordenarOrigem();
		//gerRotas.ordenaOrigemCia();
		ArrayList<Rota> rotas = gerRotas.listarTodas();
		for(Rota r: rotas)
			System.out.println(r.getCia().getNome()+"-"
					+r.getOrigem().getNome()+" -> "
					+r.getDestino().getNome());
		GerenciadorVoos gerVoos = new GerenciadorVoos();
		System.out.println();
		
		// Vôo com duração de 1h30min GRU->POA
		LocalDateTime datahora1 = LocalDateTime.of(2016,
				8, 18, 8, 30);
		Duration duracao1 = Duration.ofMinutes(90);
		
		// Rota: GRU -> POA
		Rota rota1 = rotas.get(0);
		// Rota: GRU -> MIA
		Rota rota2 = rotas.get(1);
		// Rota: POA -> GRU
		Rota rota3 = rotas.get(2);
		
		// Não é mais possível criar objetos Voo (classe abstrata)
		//Voo voo1 = new Voo(rota1,datahora1, duracao1);
		
		VooDireto voo1 = new VooDireto(datahora1, rota1);
		VooEscalas voo2 = new VooEscalas(LocalDateTime.of(2016,9,11,8,0));
		voo2.adicionarRota(rota2);
		voo2.adicionarRota(rota3);
		
		gerVoos.adicionar(voo1);
		gerVoos.adicionar(voo2);
		
		ArrayList<Voo> listaVoos = gerVoos.listarTodos();
		for(Voo v: listaVoos) {			
			System.out.println("Voo: "+v);
			System.out.println(" dur: "+v.getDuracao().toMinutes());
			if(v instanceof VooEscalas) {
				VooEscalas v2 = (VooEscalas) v;
				// Exemplo: acessa lista de rotas do objeto v
				System.out.println(v2.getRotas());
			}
		}
		
		// Teste: procurar o vôo
		ArrayList<Voo> meusVoos = gerVoos.buscarData(LocalDate.of(2016, 8, 18));
		for(Voo v: meusVoos) {
			System.out.println(v.getRota().getOrigem().getNome());
			System.out.println(v.getRota().getDestino().getNome());
			System.out.println(v.getDatahora());
		}
		*/
		
		GerenciadorCias gerCias = new GerenciadorCias();
		//gerCias.adicionar(gol);
		//gerCias.adicionar(latam);
		/**/
		try {
			gerCias.carregaDados();
		} catch (IOException e) {
			System.out.println("Impossível ler airlines.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}/**/
		
		try {
			gerCias.gravaJSON();
		} catch (IOException e) {
			System.out.println("Impossível grava airlines.json!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
		
		/*
		try {
			gerCias.carregaSerial();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Impossível ler airlines.ser!");
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
		}*/
		
		GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
		GerenciadorRotas gerRotas = new GerenciadorRotas();
		
		// Teste GUI: abre janela
		/**/
		JanelaConsulta janela = new JanelaConsulta();
		janela.setGerAeroportos(gerAero);
		janela.setGerRotas(gerRotas);
		janela.setVisible(true);
		/**/
	}
}
