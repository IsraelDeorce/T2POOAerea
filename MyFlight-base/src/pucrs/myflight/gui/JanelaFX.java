package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;
import pucrs.myflight.modelo.TreeOfRotas;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();
	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAeroportos;
	private GerenciadorAeronaves gerAeronaves;
	private GerenciadorRotas gerRotas;
	private GerenciadorPaises gerPaises;
	private GerenciadorMapa gerenciador;
	private TreeOfRotas arvoreRotas;
	private EventosMouse mouse;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		setup();
		
		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		createSwingContent(mapkit);

		BorderPane pane = new BorderPane();			
		GridPane leftPane = new GridPane();
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10,5,10,5));
		
		Button exibeAeros = new Button("Mostrar todos aeroportos ");
		exibeAeros.setOnAction(e -> {gerenciador.clear(); exibeAeros();});
		
		Button aeroPais = new Button("Mostrar aeroportos do país");
		aeroPais.setOnAction(e -> {gerenciador.clear(); consulta1();});
		
		
		Slider dist = new Slider(0, 20_000, 0);
		dist.setShowTickMarks(true);
		dist.setShowTickLabels(true);
		dist.setMajorTickUnit(5_000);
		dist.setMinorTickCount(2_500);
		dist.setBlockIncrement(1_250);
		
		Button rotasDist = new Button("Mostrar rotas por distância");
		rotasDist.setOnAction(e-> { gerenciador.clear(); consulta2(dist.getValue());});
		
		Button rotasLigacoes = new Button("Mostrar ligações");
		rotasLigacoes.setOnAction(e -> {Aeroporto selecionado = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
										arvoreRotas = new TreeOfRotas(selecionado);
			  						 	consulta3(selecionado, 1);});
		ComboBox ciaSelect = new ComboBox();
		ciaSelect.getItems().addAll(gerCias.enviaAL());	
		
		Button rotasCia = new Button("Mostrar rotas por Cia");
		rotasCia.setOnAction(e-> consulta4(ciaSelect));
		
		leftPane.add(exibeAeros, 0,0);
		leftPane.add(aeroPais, 0,1);
		leftPane.add(dist, 0,2);
		leftPane.add(rotasDist, 0,3);
		leftPane.add(rotasLigacoes, 0,4);
		leftPane.add(rotasCia, 0,5);
		leftPane.add(ciaSelect, 0,6);
		
		
		pane.setCenter(mapkit);
		pane.setLeft(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}
	
    // Inicializando os dados aqui...
    private void setup() throws ClassNotFoundException, IOException {

    	gerPaises = new GerenciadorPaises();

		try{ 
			gerPaises.carregaDados();
		}
		catch (IOException e) {
		System.out.println("Impossível ler countries.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}
				
		gerAeronaves = new GerenciadorAeronaves();
		try{
			gerAeronaves.carregaDados();
		}
		catch (IOException e){
			System.out.println("Impossível ler equipment.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}
						
		gerAeroportos = new GerenciadorAeroportos();
		try{ 
			gerAeroportos.carregaDados(gerPaises.enviaHM());
		}
		catch (IOException | ClassNotFoundException e) {
		System.out.println("Impossível ler airports.dat!");
		System.out.println("Msg: "+e);
		System.exit(1);
		}
		
		gerCias = new GerenciadorCias();
		try {
			gerCias.carregaDados();
		} 
		catch (IOException e) {
			System.out.println("Impossível ler airlines.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
		
		gerRotas = new GerenciadorRotas();
		try {
			gerRotas.carregaDados(gerCias.enviaHM(), gerAeroportos.enviaHM(), gerAeronaves.enviaHM());
		}
		catch (IOException | ClassNotFoundException e) {
			System.out.println("Impossível ler routes.dat!");
			System.out.println("Msg: "+e);
			System.exit(1);
		}		
	}
  
    
    public void exibeAeros() {   	
		gerenciador.clear();
		List<MyWaypoint> lstPoints = new ArrayList<>();
		List<Aeroporto> aeroportos = gerAeroportos.enviaAL();
		for(Aeroporto a : aeroportos)
			lstPoints.add(new MyWaypoint(Color.RED,a.getNome(), a.getLocal()));
		gerenciador.setPontos(lstPoints);
	}
    
    
    public void consulta1(){
    	gerenciador.clear();
    	List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
    	Aeroporto aeroSelecionado = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
    	String codPais = aeroSelecionado.getPais().getCodigo();
    	List<Aeroporto> lista = gerAeroportos.buscarPais(codPais);
    	for(Aeroporto a: lista)
    		lstPoints.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(lstPoints);    	
    }
    
    
    public void consulta2(double maxKm){
    	gerenciador.clear();
    	Tracado tr = new Tracado();
    	Aeroporto aeroSelec = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
    	ArrayList<Rota> rotas = gerRotas.buscarOrigem(aeroSelec.getCodigo());
    	for(Rota r: rotas){
    		if(Geo.distancia(aeroSelec.getLocal(), r.getDestino().getLocal())<=maxKm){
            	tr.addPonto(r.getOrigem().getLocal());
            	tr.addPonto(r.getDestino().getLocal()); 
            	gerenciador.addTracado(tr);  
    		}
    	}
    }
    
    public void consulta3(Aeroporto origem, int ligacao){	
    	gerenciador.clear();
    	if(ligacao<3){
    		Tracado tr = new Tracado();
    		switch(ligacao){
    		case 1:
    			tr.setCor(Color.YELLOW);
    		case 2:
    			tr.setCor(Color.ORANGE);
    		}
    		String codAeroporto = origem.getCodigo();
    		ArrayList<Rota> rotas = gerRotas.buscarOrigem(codAeroporto);
    		/*List<Rota> semLoops = rotas.stream()
    				.filter(r -> !(arvoreRotas.contains(r.getDestino())))
    				.collect(Collectors.toList());
    		for(Rota r: semLoops){
    			tr.addPonto(r.getOrigem().getLocal());
    			tr.addPonto(r.getDestino().getLocal());
    			gerenciador.addTracado(tr);
    			this.repaint();
    		}    							   */
    		
    		rotas.stream()
    		.filter(r -> !(arvoreRotas.contains(r.getDestino())))
    		.forEach(r -> adicionaTR(r));
    		for(int i = 0;i<rotas.size();i++)
    			consulta3(rotas.get(i).getDestino(),ligacao+1);
    		ligacao++;
    	}
    	else    	
    		return;    		
    }
    
    public void consulta4(ComboBox ciaSelect){
    	gerenciador.clear();
    	String cia = (String) ciaSelect.getValue();
    	
    	ArrayList<Rota> rotas = gerRotas.buscarCia(cia);    	
        for(Rota r : rotas){        
        	Tracado tr = new Tracado();        	
        	tr.addPonto(r.getOrigem().getLocal());
        	tr.addPonto(r.getDestino().getLocal());        	
        	gerenciador.addTracado(tr);        	
        }        
    }
    
    private void adicionaTR(Rota r){
    	Tracado tr = new Tracado();
    	tr.addPonto(r.getOrigem().getLocal());
		tr.addPonto(r.getDestino().getLocal());
		gerenciador.addTracado(tr);		
    }
    
	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(gerenciador.getMapKit());
			}
		});
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// BotÃ£o 3: seleciona localizaÃ§Ã£o
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				// gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				gerenciador.getMapKit().repaint();
			}
		}
	}

	public static void main(String[] args) {
		launch(args);			
	}

}
