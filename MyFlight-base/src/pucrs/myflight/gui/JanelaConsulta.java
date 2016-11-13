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

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorRotas;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Marcelo Cohen
 */
public class JanelaConsulta extends javax.swing.JFrame {

	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	
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
        
        JButton btnNewButton = new JButton("Consulta");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consulta(e);
        	}
        });
        
        JButton c1 = new JButton("Consulta 1");
        c1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		consulta1(e);
        	}
        });
        
        painelLateral.add(btnNewButton);
        painelLateral.add(c1);
        
        
        this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setGerAeroportos(GerenciadorAeroportos ger) {
    	this.gerAero = ger;
    }
    
    public void setGerRotas(GerenciadorRotas ger) {
    	this.gerRotas = ger;
    }    
    
    public void consulta1(ActionEvent evt){
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	List<Aeroporto> lista = gerAero.buscarPais(gerAero.buscarAeroProximo(gerenciador.getPosicao()).getPais().getCodigo());
    	for(Aeroporto a: lista)
    		lstPoints.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));
    	gerenciador.setPontos(lstPoints);
    	this.repaint();    	
    }
    
    public void consulta(ActionEvent evt) {    	

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:        

        List<Aeroporto> aeroportos = gerAero.enviaAL();
        
        Tracado tr = new Tracado();
        for(Aeroporto a : aeroportos)
        	lstPoints.add(new MyWaypoint(Color.RED,a.getNome(), a.getLocal()));  
        	//lstPoints.add(new MyWaypoint(Color.BLUE, a.getNome(), a.getLocal()));      	
        
       	gerenciador.setPontos(lstPoints);
       	
       	this.repaint();
        
        /*	
        lstPoints.add(new MyWaypoint(Color.BLUE, poa.getNome(), pos));
        lstPoints.add(new MyWaypoint(Color.RED, gru.getNome(), locGru));
        

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        
    
        // Exemplo: criando um tra�ado       
        Tracado tr = new Tracado();
        // Adicionando as mesmas localiza��es de antes
        tr.addPonto(locPoa);
        tr.addPonto(locGru);
        tr.setCor(Color.RED);
        // E adicionando o tra�ado...
        gerenciador.addTracado(tr);
               
        this.repaint();
      
    */
    
    }
    
    private class EventosMouse extends MouseAdapter
    {
    	private int lastButton = -1;    	
    	
    	@Override
    	public void mousePressed(MouseEvent e) {
    		JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
    		GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
    		System.out.println(loc.getLatitude()+", "+loc.getLongitude());
    		lastButton = e.getButton();
    		// Bot�o 3: seleciona localiza��o
    		if(lastButton==MouseEvent.BUTTON3) {  			
    			gerenciador.setPosicao(loc);
    			//gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    			gerenciador.getMapKit().repaint();    			
    		}
    	}    
    } 	
}