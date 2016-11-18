/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.myflight.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;
import pucrs.myflight.modelo.TreeOfRotas;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 *
 * @author Marcelo Cohen
 */
public class JanelaConsulta extends javax.swing.JFrame {

	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorCias gerCias;
	private TreeOfRotas arvoreRotas; 
	
	
    private GerenciadorMapa gerenciador;
    private EventosMouse mouse;
    
    private JPanel painelMapa;
    private JPanel painelLateral;    

    /**
     * Creates new form JanelaConsulta
     */
    public JanelaConsulta() {
    	super();
    	GeoPosition poa = new GeoPosition(-30.05, -51.18);
        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
        mouse = new EventosMouse();        		
        gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
        gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);       

        painelMapa = new JPanel();
        painelMapa.setLayout(new BorderLayout());
        painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);
        
        
        JLabel current = new JLabel();
		JSlider slider = new JSlider();
		slider.setMaximum(21000);
		slider.setMinimum(0);
		slider.setMajorTickSpacing(21000/4);
		slider.setMinorTickSpacing(1000);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                current.setText(
                        String.valueOf((int) slider.getValue()));

            }

		
        });
        
	
		

		
        getContentPane().add(painelMapa, BorderLayout.SOUTH);
        
        painelLateral = new JPanel();
        getContentPane().add(painelLateral, BorderLayout.WEST);        
          
        //Checkbox exibePaises = new Checkbox("Exibir todos os países");
        JButton exibeAeros = new JButton("Exibir todos aeroportos");
        exibeAeros.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		exibeAeros();
        	}
        });    
        
        JButton c1 = new JButton("Consulta 1");
        c1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		consulta1();
        	}
        });      
        
        JButton c2 = new JButton("Consulta 2");
        c2.addActionListener(new ActionListener(){
        		public void actionPerformed(ActionEvent e){
        			double dist = slider.getValue();
        			consulta2(dist);
        			        			
        		
        }});
        
        JButton c3 = new JButton("Consulta 3");
        c3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gerenciador.clear();
        		Aeroporto origem = gerAero.buscarAeroProximo(gerenciador.getPosicao());
        		arvoreRotas = new TreeOfRotas(origem);
        		consulta3(origem, 0);        		
        	}
        });
        
        JButton c4 = new JButton("Consulta 4");
        c4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		JFrame frame = new JFrame("Selecione a empresa");    	            	        
    	        JComponent combo = new ComboBox();
    	        combo.setOpaque(true);
    	        frame.setContentPane(combo);    	        
    	        frame.pack();
    	        frame.setVisible(true);
        	}
        });
        
        painelLateral.add(exibeAeros);
        painelLateral.add(c1);
        painelLateral.add(c2);
        painelLateral.add(c3);
        painelLateral.add(c4);
        painelLateral.add(slider);
        painelLateral.add(current);
        
        this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setGerAeroportos(GerenciadorAeroportos ger) {
    	this.gerAero = ger;
    }
    
    public void setGerRotas(GerenciadorRotas ger) {
    	this.gerRotas = ger;
    }
    
    public void setGerCias(GerenciadorCias ger) {
    	this.gerCias = ger;
    }    
    
    
    public void consulta1(){
    	gerenciador.clear();
    	this.repaint();
    	List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
    	Aeroporto aeroSelecionado = gerAero.buscarAeroProximo(gerenciador.getPosicao());
    	String codPais = aeroSelecionado.getPais().getCodigo();
    	List<Aeroporto> lista = gerAero.buscarPais(codPais);
    	for(Aeroporto a: lista)
    		lstPoints.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(lstPoints);
    	this.repaint();  	
    }

    public void consulta2(double maxKm){
    	gerenciador.clear();
    	this.repaint();
    	Tracado tr = new Tracado();
    	Aeroporto aeroSelec = gerAero.buscarAeroProximo(gerenciador.getPosicao());
    	ArrayList<Rota> rotas = gerRotas.buscarOrigem(aeroSelec.getCodigo());
    	for(Rota r: rotas){
    		if(Geo.distancia(aeroSelec.getLocal(), r.getDestino().getLocal())<=maxKm){
            	tr.addPonto(r.getOrigem().getLocal());
            	tr.addPonto(r.getDestino().getLocal()); 
            	gerenciador.addTracado(tr);  
    		}
    		
    		
    	}
    	 this.repaint();
		
    }
    
    /*Selecionar um aeroporto no mapa e mostrar todas as rotas que comecem por ele, considerando 1, 2 ou 3 ligações a
	partir dele */
    public void consulta3(Aeroporto origem, int ligacao){	
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
    
    private void adicionaTR(Rota r){
    	Tracado tr = new Tracado();
    	tr.addPonto(r.getOrigem().getLocal());
		tr.addPonto(r.getDestino().getLocal());
		gerenciador.addTracado(tr);
		this.repaint();
    }
    

    
    public void consulta4(String cia){
    	gerenciador.clear();
    	this.repaint();
    	ArrayList<Rota> rotas = gerRotas.buscarCia(cia);
    	//List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
        for(Rota r : rotas){
        	/* Cria um MyWayPoint no Aeroporto de origem para mostrar a distância e a aeronave
        	  Solução que pode melhorar == Label? 
        	 String label = "Distância " + String.valueOf(Geo.distancia(r.getOrigem().getLocal(), r.getDestino().getLocal()))
        					+ "km " 
        					+ "Aeronave " + r.getAeronave().toString();
        	lstPoints.add(new MyWaypoint(Color.BLACK,label, r.getOrigem().getLocal()));
        	gerenciador.setPontos(lstPoints);*/
        	Tracado tr = new Tracado();        	
        	tr.addPonto(r.getOrigem().getLocal());
        	tr.addPonto(r.getDestino().getLocal());        	
        	gerenciador.addTracado(tr);        	
        }
        this.repaint();
    }
    
    public void exibeAeros() {    	
    	gerenciador.clear();
    	this.repaint();
    	List<MyWaypoint> lstPoints = new ArrayList<>();
        List<Aeroporto> aeroportos = gerAero.enviaAL();
        for(Aeroporto a : aeroportos)
        	lstPoints.add(new MyWaypoint(Color.RED,a.getNome(), a.getLocal()));
       	gerenciador.setPontos(lstPoints);       	
       	this.repaint();
    }
    
    private class EventosMouse extends MouseAdapter{
    	private int lastButton = -1;    	
    	
    	@Override
    	public void mousePressed(MouseEvent e) {
    		JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
    		GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());    		
    		//System.out.println(loc.getLatitude()+", "+loc.getLongitude());
    		lastButton = e.getButton();
    		// Botão 3: seleciona localização8
    		if(lastButton==MouseEvent.BUTTON3) {  			
    			gerenciador.setPosicao(loc);
    			//gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    			gerenciador.getMapKit().repaint();
    		}
    		/*Captura a localização do clique com o botão esquerdo do mouse e dispara a consulta 1 automaticamente.
    		 * Desabilitado. Consulta funcionando com seleção no botão direito e clique no botão "Consulta 1".
    		  
    		 else if(lastButton==MouseEvent.BUTTON1){
    			gerenciador.setPosicao(loc);
    			consulta1();
    		}
    		*/
    	}    
    }
    
    private class ComboBox extends JPanel{
	    

	    public ComboBox() {
	    
	    	super(new BorderLayout());
	    	String[] empresas = new String[gerCias.enviaAL().size()];	    	
	        for(int i =0;i<empresas.length;i++)
	        	empresas[i] = gerCias.enviaAL().get(i).toString();
	        JComboBox comboEmpresas = new JComboBox(empresas);
	        comboEmpresas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox)e.getSource();
			        String selecao = (String)cb.getSelectedItem();
			        Scanner scan = new Scanner(selecao).useDelimiter("-");
			        String cia = scan.next();
			        consulta4(cia);
				}
			});
	        add(comboEmpresas, BorderLayout.PAGE_START);
	        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	        add(comboEmpresas, BorderLayout.CENTER);
	    }
    }
    
    
    
}