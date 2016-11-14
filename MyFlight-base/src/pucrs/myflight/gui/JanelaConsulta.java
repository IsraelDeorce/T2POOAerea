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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;

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
	
    private GerenciadorMapa gerenciador;
    private EventosMouse mouse;
    
    private JPanel painelMapa;
    private JPanel painelLateral;    

    /**
     * Creates new form JanelaConsulta
     */
    public JanelaConsulta() {
    	super();    	
        //initComponents();
    	GeoPosition poa = new GeoPosition(-30.05, -51.18);
        gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
        mouse = new EventosMouse();        		
        gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
        gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);       

        painelMapa = new JPanel();
        painelMapa.setLayout(new BorderLayout());
        painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);
                
        getContentPane().add(painelMapa, BorderLayout.CENTER);
        
        painelLateral = new JPanel();
        getContentPane().add(painelLateral, BorderLayout.WEST);        
        
        JButton exibePaises = new JButton("Exibir todos os países");
        exibePaises.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		mostraPaises(e);
        	}
        });
        
        JButton c1 = new JButton("Consulta 1");
        c1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		consulta1();
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
        
        painelLateral.add(exibePaises);
        painelLateral.add(c1);
        painelLateral.add(c4);
        
        
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
    	List<Aeroporto> lista = gerAero.buscarPais(gerAero.buscarAeroProximo(gerenciador.getPosicao()).getPais().getCodigo());
    	for(Aeroporto a: lista)
    		lstPoints.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(lstPoints);
    	this.repaint();  	
    }    
    
    public void consulta4(String cia){
    	gerenciador.clear();
    	this.repaint();
    	ArrayList<Rota> rotas = gerRotas.buscarCia(cia);
    	//List<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
        for(Rota r : rotas){
        	/* Cria um MyWayPoint no Aeroporto de origem para mostrar a distância e a aeronave
        	 * Solução que pode melhorar == Label? 
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
    
    public void mostraPaises(ActionEvent evt) {    	
    	gerenciador.clear();
    	this.repaint();
    	List<MyWaypoint> lstPoints = new ArrayList<>();
        List<Aeroporto> aeroportos = gerAero.enviaAL();
        for(Aeroporto a : aeroportos)
        	lstPoints.add(new MyWaypoint(Color.RED,a.getNome(), a.getLocal()));
       	gerenciador.setPontos(lstPoints);       	
       	this.repaint();
    }
             
        
    /*
        Exemplo: criando um traçado       
        Tracado tr = new Tracado();
        Adicionando as mesmas localizações de antes
        tr.addPonto(locPoa);
        tr.addPonto(locGru);
        tr.setCor(Color.RED);
        E adicionando o traçado...
        gerenciador.addTracado(tr);
               
        this.repaint();
    }
    */
    
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
	    	String [] empresas = {"9K", "9M", "9N", "9R"};
	    	
	    	
	    	//String[] empresas = new String[ize-1];	    	
	        
	    	/*
	    	for(int i = 0;i<empresas.length;i++){
	        	empresas[i] = gerCias.enviaAL().get(i).toString();
	        }
	        */	    	
	        JComboBox comboEmpresas = new JComboBox(empresas);
	        comboEmpresas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox)e.getSource();
			        String empresa = (String)cb.getSelectedItem();
			        
			        consulta4(empresa);
				}
			});
	        add(comboEmpresas, BorderLayout.PAGE_START);
	        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	        add(comboEmpresas, BorderLayout.CENTER);
	        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	        
	    }
    }
    
}