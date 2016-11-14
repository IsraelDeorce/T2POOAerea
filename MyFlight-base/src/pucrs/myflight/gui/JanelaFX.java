package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pucrs.myflight.gui.GerenciadorMapa;
import pucrs.myflight.gui.MyWaypoint;
import pucrs.myflight.gui.Tracado;
import pucrs.myflight.modelo.Aeroporto;
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
	private GerenciadorAeroportos gerAirports;
	private GerenciadorRotas gerRoutes;

	private GerenciadorMapa gerenciador;

	private EventosMouse mouse;
	
	public static void main(String[] args) {
		Launch(args);			
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
		GridPane leftPane = new GridPane();
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10,10,10,10));
		Button btnConsulta = new Button("AAAA");
		leftPane.add(btnConsulta, 0,0);
		leftPane.add(new Button("BBBB"), 0,1);
		leftPane.add(new Button("CCCC"), 0,2);
		leftPane.add(new Button("DDDD"), 0,3);
		btnConsulta.setOnAction(e -> {
			consulta();
		});
		
		pane.setCenter(mapkit);
		pane.setLeft(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}
	
    private void setup() {

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
	}
    
    private void consulta1(){
    	gerenciador.clear();
    	this.repaint();
    	List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
    	List<Aeroporto> lista = gerAero.buscarPais(gerAero.buscarAeroProximo(gerenciador.getPosicao()).getPais().getCodigo());
    	for(Aeroporto a: lista)
    		lstPoints.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(lstPoints);
    	this.repaint();  	
    }    
    
    private void consulta4(String cia){
    	gerenciador.clear();
    	this.repaint();
    	List<Rota> rotas = gerRotas.buscarCia(cia);
        for(Rota r : rotas){        	
        	Tracado tr = new Tracado(Geo.distancia(r.getOrigem().getLocal(), r.getDestino().getLocal()));
        	tr.addPonto(r.getOrigem().getLocal());
        	tr.addPonto(r.getDestino().getLocal());        	
        	gerenciador.addTracado(tr);
        }
        this.repaint();
    }
    
    private void mostraPaises(ActionEvent evt) {    	
    	gerenciador.clear();
    	this.repaint();
    	List<MyWaypoint> lstPoints = new ArrayList<>();
        List<Aeroporto> aeroportos = gerAero.enviaAL();
        for(Aeroporto a : aeroportos)
        	lstPoints.add(new MyWaypoint(Color.RED,a.getNome(), a.getLocal()));
       	gerenciador.setPontos(lstPoints);       	
       	this.repaint();
    }
    
	/*private void consulta() {
		// TODO Auto-generated method stub
		// Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        
        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        // Exemplo: criando um traÃ§ado
        Tracado tr = new Tracado();
        // Adicionando as mesmas localizaÃ§Ãµes de antes
        tr.addPonto(locPoa);
        tr.addPonto(locGru);
        tr.setCor(Color.RED);
        // E adicionando o traÃ§ado...
        gerenciador.addTracado(tr);
        gerenciador.getMapKit().repaint();
	}
	*/

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
			lastButton = e.getButton();			
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				// gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				gerenciador.getMapKit().repaint();
			/*Captura a localização do clique com o botão esquerdo do mouse e dispara a consulta 1 automaticamente.
	    	* Desabilitado. Consulta funcionando com seleção no botão direito e clique no botão "Consulta 1".
	    	else if(lastButton==MouseEvent.BUTTON1){
	    		gerenciador.setPosicao(loc);
	    		consulta1();
	    		}
	    		*/
			}
		}
	}

	

}