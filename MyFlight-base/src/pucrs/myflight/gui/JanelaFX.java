package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();
	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAeroportos;
	private GerenciadorAeronaves gerAeronaves;
	private GerenciadorRotas gerRotas;
	private GerenciadorPaises gerPaises;
	private GerenciadorMapa gerenciador;	
	private EventosMouse mouse;
	
	private GridPane grid(){
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(10,5,10,5));				
		return pane;
	}

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
		GridPane leftPane = grid();		
		
		Button exibeAeros = new Button("Mostrar todos aeroportos ");
		exibeAeros.setOnAction(e -> {
			 gerenciador.clear(); 
			 exibeAeros();
			 gerenciador.getMapKit().repaint();
			 });
		
		Button aeroPais = new Button("Mostrar aeroportos do país");
		aeroPais.setOnAction(e -> {
			   gerenciador.clear(); 
			   consulta1();
			   gerenciador.getMapKit().repaint();
			   });
		
		Label distLab = new Label("Pesquisar rota por distância");
		Label raio = new Label ("Distância selecionada: 0.0km");
		Slider distSli = new Slider(0, 20_000, 0);
		
		distSli.setShowTickMarks(true);
		distSli.setShowTickLabels(true);
		distSli.setMajorTickUnit(5_000);		
		distSli.setBlockIncrement(1_000);
		distSli.setMinWidth(280);		
		distSli.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
	    
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean changing){
				if (wasChanging || !changing) {	                    
					raio.setText("Distância selecionada: " + String.valueOf(Math.round(distSli.getValue())) + "km");
				}				
			}
		}
		);
				
		Button rotasDist = new Button("Pesquisar");
		rotasDist.setOnAction(e-> { 
			gerenciador.clear(); 
			consulta2(distSli.getValue());
			gerenciador.getMapKit().repaint();
		  });
		
		Button rotasLigacoes = new Button("Mostrar ligações");
		rotasLigacoes.setOnAction(e -> {
			gerenciador.clear();										
			Aeroporto selecionado = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
			Set<Aeroporto> origem = new HashSet<Aeroporto>();
			origem.add(selecionado);
			Set<Aeroporto> visitados = new HashSet<Aeroporto>();
			visitados.add(selecionado);
			consulta3(origem, visitados, 0);
			gerenciador.getMapKit().repaint();
			});
		
		Button rotasCia = new Button("Mostrar rotas por Cia");
		ComboBox ciaSelect = new ComboBox();
		ciaSelect.getItems().addAll(gerCias.enviaAL());
		rotasCia.setOnAction(e-> {
			gerenciador.clear(); 
			consulta4(ciaSelect);
			gerenciador.getMapKit().repaint();
			});
		
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
		Set<MyWaypoint> pontos = new HashSet<>();
		List<Aeroporto> aeroportos = gerAeroportos.enviaAL();
		for(Aeroporto a : aeroportos)
			pontos.add(new MyWaypoint(Color.RED,a.getNome(), a.getLocal()));
		gerenciador.setPontos(pontos);
	}
    
    
    public void consulta1(){
    	gerenciador.clear();
    	Set<MyWaypoint> pontos = new HashSet<MyWaypoint>();
    	Aeroporto aeroSelecionado = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
    	String codPais = aeroSelecionado.getPais().getCodigo();
    	Set<Aeroporto> lista = gerAeroportos.buscarPais(codPais);
    	for(Aeroporto a: lista)
    		pontos.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(pontos);    	
    }
    
    
    public void consulta2(double maxKm){    	
    	Tracado tr = new Tracado();
    	Aeroporto aeroSelec = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
    	Set<Rota> rotas = gerRotas.buscarOrigem(aeroSelec.getCodigo());
    	for(Rota r: rotas){
    		if(Geo.distancia(aeroSelec.getLocal(), r.getDestino().getLocal())<=maxKm){
            	tr.addPonto(r.getOrigem().getLocal());
            	tr.addPonto(r.getDestino().getLocal()); 
            	gerenciador.addTracado(tr);  
    		}
    	}
    }
    
    public void consulta3(Set<Aeroporto> origens, Set<Aeroporto> visitados, int ligacao){	
    	
    	if(ligacao<3){
    		Set<Rota> rotas;
    		Set<Aeroporto> destinos = new HashSet<Aeroporto>();
    		Tracado tr = new Tracado();
    		if(ligacao==1)
    			tr.setCor(Color.ORANGE);
    		if(ligacao==2)
    			tr.setCor(Color.MAGENTA);
    		
    		for(Aeroporto a: origens){
    			rotas = gerRotas.buscarOrigem(a.getCodigo());
    			GeoPosition origem = a.getLocal();    			    	   	
    			rotas.stream()
    			.filter(r -> !visitados.contains(r.getDestino()))
    			.forEach(r -> {
    							tr.addPonto(origem); 
    							tr.addPonto(r.getDestino().getLocal()); 
    							gerenciador.addTracado(tr); 
    							visitados.add(r.getDestino());
    							destinos.add(r.getDestino());
    							});     	   	
    		}
    		ligacao++;
    		consulta3(destinos, visitados, ligacao);
    	}
    	else{
    		Set<MyWaypoint> pontos = new HashSet<MyWaypoint>();
    		for(Aeroporto a : visitados)
    			pontos.add(new MyWaypoint(a.getLocal()));
    		gerenciador.setPontos(pontos);    			
    	}
    }   	
    
    public void consulta4(ComboBox ciaSelect){    	
    	Set<MyWaypoint> aeroportos = new HashSet<MyWaypoint>();
    	Tracado tr = new Tracado();    	
    	CiaAerea ciaSelecionada= (CiaAerea)ciaSelect.getValue();
    	Set<Rota> rotas = gerRotas.buscarCia(ciaSelecionada.getCodigo());   	
       	rotas.stream()
        .forEach(r -> {             			
        				GeoPosition origem = r.getOrigem().getLocal();
        				GeoPosition destino = r.getDestino().getLocal();
        				aeroportos.add(new MyWaypoint(origem));
        				aeroportos.add(new MyWaypoint(destino));
               			tr.addPonto(origem);
               			tr.addPonto(destino);
               			gerenciador.addTracado(tr);
        			   });        
		gerenciador.setPontos(aeroportos);    			
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
