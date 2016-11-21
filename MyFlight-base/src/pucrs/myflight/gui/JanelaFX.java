package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.Aeronave;
import pucrs.myflight.modelo.CiaAerea;
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
	private EventosMouse mouse;
	private Aeroporto aeroSelecionado;
	
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
		
		Label clearLB = new Label("Limpar tela");
		Button clearBT = new Button("Limpar");
		clearBT.setOnAction(e -> {
								  gerenciador.clear();
								  gerenciador.getMapKit().repaint();
							});
		
		Label exibeAerosLB = new Label("Exibir todos os aeroportos");
		Button exibeAerosBT = new Button("Exibir");
		exibeAerosBT.setOnAction(e -> {
			 gerenciador.clear(); 
			 exibeAeros();
			 gerenciador.getMapKit().repaint();
			 });
		
		Label aeroPaisLB = new Label("Exibir aeroportos do país");
		Button aeroPaisBT = new Button("Exibir");
		aeroPaisBT.setOnAction(e -> {
			   gerenciador.clear(); 
			   consulta1();
			   gerenciador.getMapKit().repaint();
			   });
		
		Label distLB = new Label("Exibir rotas até determinado raio");		
		Slider distSli = new Slider(0, 20_000, 0);
		
		distSli.setShowTickMarks(true);
		distSli.setShowTickLabels(true);
		distSli.setMajorTickUnit(5_000);		
		distSli.setBlockIncrement(1_000);
		distSli.setMinWidth(50);		
		
		Button distBT = new Button("Exibir");
		distBT.setOnAction(e-> { 
			gerenciador.clear(); 
			consulta2(distSli.getValue());
			gerenciador.getMapKit().repaint();
		  });
		
		Label rotasLigacoesLB = new Label("Exibir 3 ligações a partir de um aeroporto de origem");
		Button rotasLigacoesBT = new Button("Exibir");
		rotasLigacoesBT.setOnAction(e -> {
			gerenciador.clear();										
			Aeroporto selecionado = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
			Set<Aeroporto> origem = new HashSet<Aeroporto>();
			origem.add(selecionado);
			Set<Aeroporto> visitados = new HashSet<Aeroporto>();
			visitados.add(selecionado);
			consulta3(origem, visitados, 0);
			gerenciador.getMapKit().repaint();
			});
		
		Label rotasCiaLB = new Label("Exibir todos as rotas de uma Cia");
		Button rotasCiaBT = new Button("Exibir");
		ComboBox ciaSelect = new ComboBox();
		ciaSelect.getItems().addAll(gerCias.enviaAL());
		rotasCiaBT.setOnAction(e-> {
			gerenciador.clear();			
			consulta4(ciaSelect);			
			gerenciador.getMapKit().repaint();
			});
		
		Label caminhoLB = new Label("Buscar caminho entre 2 aeroportos");
		Label origemLB = new Label("Origem");
		TextField origemTF = new TextField();
		origemTF.setPromptText("Código da origem");
		Label destinoLB = new Label("Destino");
		TextField destinoTF = new TextField();
		destinoTF.setPromptText("Código do destino");
		Label invalido = new Label("Códigos informados inválidos!");
		invalido.setVisible(false);
		Button caminhoBT = new Button("Buscar");
		Button clearCodBT = new Button("Limpar campos");
		
		caminhoBT.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				gerenciador.clear();
				Set<Aeroporto> origens = new HashSet<Aeroporto>();
				ArrayList<Aeroporto> origem = new ArrayList<Aeroporto>();
				ArrayList<Aeroporto> destino = new ArrayList<Aeroporto>();
				if((gerAeroportos.validaCodigo(origemTF.getText()) && gerAeroportos.validaCodigo(destinoTF.getText()))){ 
					origem.add(gerAeroportos.buscarCod(origemTF.getText()));
					destino.add(gerAeroportos.buscarCod(destinoTF.getText()));
					invalido.setVisible(false);
					origens.addAll(origem);
					TreeOfRotas arvore = new TreeOfRotas(origem.get(0));
					consulta5(origem.get(0), destino.get(0), origens, arvore);
					gerenciador.getMapKit().repaint();
				}
				else
					invalido.setVisible(true);
			}
		});
		
		clearCodBT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				origemTF.clear();
				destinoTF.clear();
				invalido.setVisible(false);
			}
		});

		GridPane geral = grid();
		geral.setHgap(50);
		geral.add(clearLB, 0, 0);
		geral.add(clearBT, 0, 1);
		geral.add(exibeAerosLB, 1, 0);
		geral.add(exibeAerosBT, 1, 1);
		
		HBox origemHB = new HBox();
		origemHB.getChildren().addAll(origemLB, origemTF);
		origemHB.setSpacing(10);
		
		HBox destinoHB = new HBox();
		destinoHB.getChildren().addAll(destinoLB, destinoTF);
		destinoHB.setSpacing(10);
		
		GridPane cons5 = grid();
		cons5.setHgap(50);
		cons5.add(caminhoBT, 0, 0);
		cons5.add(clearCodBT, 0, 1);
		
				
		//leftPane.setGridLinesVisible(true);
		leftPane.add(geral, 0, 0);
		leftPane.add(new Separator(), 0, 1);
		leftPane.add(aeroPaisLB, 0, 2);
		leftPane.add(aeroPaisBT, 0, 3);
		leftPane.add(new Separator(), 0, 4);
		leftPane.add(distLB, 0, 5);
		leftPane.add(distSli, 0, 6);		
		leftPane.add(distBT, 0, 7);
		leftPane.add(new Separator(), 0, 8);
		leftPane.add(rotasLigacoesLB, 0, 9);
		leftPane.add(rotasLigacoesBT, 0, 10);
		leftPane.add(new Separator(), 0, 11);
		leftPane.add(rotasCiaLB, 0, 12);		
		leftPane.add(ciaSelect, 0, 13);
		leftPane.add(rotasCiaBT, 0, 14);
		leftPane.add(new Separator(), 0, 15);
		leftPane.add(caminhoLB, 0, 16);
		leftPane.add(origemHB, 0, 17);
		leftPane.add(destinoHB, 0, 18);
		leftPane.add(invalido, 0, 19);
		leftPane.add(cons5, 0, 20);
		
		pane.setCenter(mapkit);		
		pane.setLeft(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();
	}
	
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
    	String codPais = aeroSelecionado.getPais().getCodigo();
    	Set<Aeroporto> lista = gerAeroportos.buscarPais(codPais);
    	for(Aeroporto a: lista)
    		pontos.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(pontos);    	
    }
    
    public void consulta2(double maxKm){    	
    	GeoPosition aeroSelecionadoPos = aeroSelecionado.getLocal();
    	Set<Rota> rotas = gerRotas.buscarOrigem(aeroSelecionado.getCodigo());
    	Set<MyWaypoint> pontos = new HashSet<MyWaypoint>();
    	pontos.add(new MyWaypoint(aeroSelecionadoPos));
    	for(Rota r: rotas){
    		if(Geo.distancia(aeroSelecionadoPos, r.getDestino().getLocal())<=maxKm){
    			Tracado tr = new Tracado();  
    			GeoPosition aeroDestinoPos = r.getDestino().getLocal(); 
    			tr.addPonto(aeroSelecionadoPos);
            	tr.addPonto(aeroDestinoPos); 
            	gerenciador.addTracado(tr);
            	pontos.add(new MyWaypoint(aeroDestinoPos));
    		}
    	}
    	tableRotas(rotas);
    	gerenciador.setPontos(pontos);    		
    }
    
    
    public void consulta3(Set<Aeroporto> origens, Set<Aeroporto> visitados, int ligacao){	
    	
    	if(ligacao<3){
    		
    		Set<Rota> rotas;
    		Set<Aeroporto> destinos = new HashSet<Aeroporto>();
    		
    		for(Aeroporto a: origens){
    			rotas = gerRotas.buscarOrigem(a.getCodigo());
    			Tracado tr = new Tracado();
    			if(ligacao==1)
    				tr.setCor(Color.ORANGE);
    			if(ligacao==2)
    				tr.setCor(Color.MAGENTA);
    			GeoPosition origem = a.getLocal();    			    	   	
    			rotas.stream()
    			.filter(r -> !visitados.contains(r.getDestino()))
    			.forEach(r -> {
    				tr.addPonto(origem); 
    				tr.addPonto(r.getDestino().getLocal()); 
    				gerenciador.addTracado(tr);    		
    				destinos.add(r.getDestino());    							
    			});     	   	
    		}
    		visitados.addAll(destinos);
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
    
    public void consulta5(Aeroporto origem, Aeroporto destino, Set<Aeroporto> origens, TreeOfRotas arvore){


    	if(origens.size()>0){

    		Set<Rota> rotas;
    		Set<Aeroporto> destinos = new HashSet<Aeroporto>();
    		//formando a árvore
    		for(Aeroporto a: origens){    			
    			rotas = gerRotas.buscarOrigem(a.getCodigo());			
    			rotas.stream()
    			.filter(r -> !arvore.contains(r.getDestino()))
    			.forEach(r -> {
    				arvore.add(r.getDestino(), r.getOrigem());
    				destinos.add(r.getDestino());    							
    			});
    		}
    		consulta5(origem, destino, destinos, arvore);
    	}
    		
    	else{
    		if(arvore.contains(destino)){ //verificando se o destino está na árvore    			
    			TreeOfRotas.Node aux = arvore.searchNodeRef(destino, arvore.getRoot());
    			ArrayList<Aeroporto> longWay = new ArrayList<Aeroporto>();
    			longWay = areWeThereYet(aux, longWay); //formando o caminho

    			Set<Rota> rotas = new HashSet<Rota>();
    			for(int i=0,j=i+1;j<longWay.size();i++,j++){
    				rotas.add(gerRotas.buscarOrigemDestino(longWay.get(i).getCodigo(), longWay.get(j).getCodigo()));    				
    			}    			

    			Set<MyWaypoint> pontos = new HashSet<MyWaypoint>();    			    	
    			rotas.stream()
    			.forEach(r -> {             			
    				Tracado tr = new Tracado();
    				GeoPosition inicio = r.getOrigem().getLocal();
    				GeoPosition fim = r.getDestino().getLocal();
    				pontos.add(new MyWaypoint(inicio));
    				pontos.add(new MyWaypoint(fim));
    				tr.addPonto(inicio);
    				tr.addPonto(fim);
    				gerenciador.setPontos(pontos);
    				gerenciador.addTracado(tr);
    			});

    			tableCaminho(longWay);    			
    		}
    	}
    }
    		    		
    private void tableCaminho(ArrayList<Aeroporto> longWay){

    	TableView<Aeroporto> caminhosTB = new TableView<Aeroporto>();
    	TableColumn nomeCol = new TableColumn("Nome");
    	TableColumn codigoCol = new TableColumn("Código");
    	ObservableList<Aeroporto> caminhos = FXCollections.observableArrayList(longWay); 
    	nomeCol.setCellValueFactory(new PropertyValueFactory<Rota,String>("nome"));
    	codigoCol.setCellValueFactory(new PropertyValueFactory<Rota,String>("codigo"));
    	caminhosTB.setItems(caminhos);
    	caminhosTB.getColumns().addAll(nomeCol, codigoCol);    		
    	ScrollPane scPane = new ScrollPane();
    	scPane.setContent(caminhosTB);
    	scPane.setFitToHeight(true);
    	scPane.setFitToWidth(true);
    	Scene scene = new Scene(scPane);
    	Stage janela = new Stage();
    	janela.setTitle("Rotas entre " + longWay.get(longWay.size()-1) + " e " + longWay.get(0));
    	janela.setMinHeight(500);
    	janela.setMinWidth(950);
    	janela.setScene(scene);
    	janela.setResizable(true);
    	janela.show();            
    }
	
    private ArrayList<Aeroporto> areWeThereYet(TreeOfRotas.Node aux, ArrayList<Aeroporto> way){

    	way.add(aux.getElement());    	
    	if(aux.father!=null){
    		aux=aux.father;
    		areWeThereYet(aux,way);
    	}
    	Collections.reverse(way);
    	return way;
    }
    
    public void consulta4(ComboBox ciaSelect){
    	Set<MyWaypoint> aeroportos = new HashSet<MyWaypoint>();    	
    	CiaAerea ciaSelecionada= (CiaAerea)ciaSelect.getValue();
    	String nomeCia = ciaSelecionada.getNome();
    	Set<Rota> rotas = gerRotas.buscarCia(ciaSelecionada.getCodigo());   	
    	rotas.stream()
    	.forEach(r -> {             			
    		GeoPosition origem = r.getOrigem().getLocal();
    		GeoPosition destino = r.getDestino().getLocal();
    		aeroportos.add(new MyWaypoint(origem));
    		aeroportos.add(new MyWaypoint(destino));
    		Tracado tr = new Tracado();    	
    		tr.addPonto(origem);
    		tr.addPonto(destino);
    		gerenciador.addTracado(tr);               			
    	});        
    	gerenciador.setPontos(aeroportos);
    	tableRotas(rotas, nomeCia);
    }
    
    private void tableRotas(Set<Rota> rotas, String cia){

    	TableView<Rota> rotasCiaTB = new TableView<Rota>();
    	TableColumn origemCol = new TableColumn("Origem");
    	TableColumn destinoCol = new TableColumn("Destino");
    	TableColumn aeronaveCol = new TableColumn("Aeronave");        
    	TableColumn distanciaCol = new TableColumn("Distância (em km)");
    	ObservableList<Rota> rotasCia = FXCollections.observableArrayList(rotas); 
    	origemCol.setCellValueFactory(new PropertyValueFactory<Rota,Aeroporto>("Origem"));
    	destinoCol.setCellValueFactory(new PropertyValueFactory<Rota,Aeroporto>("Destino"));
    	aeronaveCol.setCellValueFactory(new PropertyValueFactory<Rota,Aeronave>("Aeronave"));
    	distanciaCol.setCellValueFactory(new PropertyValueFactory<Rota,Double>("Distancia"));
    	rotasCiaTB.setItems(rotasCia);
    	rotasCiaTB.getColumns().addAll(origemCol,destinoCol,aeronaveCol,distanciaCol);

    	ScrollPane scPane = new ScrollPane();
    	scPane.setContent(rotasCiaTB);
    	scPane.setFitToHeight(true);
    	scPane.setFitToWidth(true);
    	Scene scene = new Scene(scPane);
    	Stage janela = new Stage();
    	janela.setTitle("Rotas da companhia " + cia);
    	janela.setMinHeight(500);
    	janela.setMinWidth(950);
    	janela.setScene(scene);
    	janela.setResizable(true);
    	janela.show();        
    }
    
    private void tableRotas(Set<Rota> rotas){

    	TableView<Rota> rotasCiaTB = new TableView<Rota>();
    	TableColumn origemCol = new TableColumn("Origem");
    	TableColumn destinoCol = new TableColumn("Destino");
    	TableColumn aeronaveCol = new TableColumn("Aeronave");        
    	TableColumn distanciaCol = new TableColumn("Distância (em km)");
    	ObservableList<Rota> rotasCia = FXCollections.observableArrayList(rotas); 
    	origemCol.setCellValueFactory(new PropertyValueFactory<Rota,Aeroporto>("Origem"));
    	destinoCol.setCellValueFactory(new PropertyValueFactory<Rota,Aeroporto>("Destino"));
    	aeronaveCol.setCellValueFactory(new PropertyValueFactory<Rota,Aeronave>("Aeronave"));
    	distanciaCol.setCellValueFactory(new PropertyValueFactory<Rota,Double>("Distancia"));
    	rotasCiaTB.setItems(rotasCia);
    	rotasCiaTB.getColumns().addAll(origemCol,destinoCol,aeronaveCol,distanciaCol);

    	ScrollPane scPane = new ScrollPane();
    	scPane.setContent(rotasCiaTB);
    	scPane.setFitToHeight(true);
    	scPane.setFitToWidth(true);
    	Scene scene = new Scene(scPane);
    	Stage janela = new Stage();
    	janela.setMinHeight(500);
    	janela.setMinWidth(950);
    	janela.setTitle("Rotas possíveis");
    	janela.setScene(scene);
    	janela.setResizable(true);
    	janela.show();        
    }
    private Aeroporto aeroSelecionado(){
    	return aeroSelecionado = gerAeroportos.buscarAeroProximo(gerenciador.getPosicao());
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
			lastButton = e.getButton();
			// BotÃ£o 3: seleciona localizaÃ§Ã£o
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				aeroSelecionado = aeroSelecionado();
				gerenciador.getMapKit().repaint();
			}
		}
	}
	
	private GridPane grid(){
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(10,5,10,5));				
		return pane;
	}

	public static void main(String[] args) {
		launch(args);			
	}
}
