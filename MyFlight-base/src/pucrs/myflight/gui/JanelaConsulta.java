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
        painelLateral.add(btnNewButton);
        
        this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    
    // Um exemplo de como fazer a comunicação do programa principal para cá:

    public void setGerAeroportos(GerenciadorAeroportos ger) {
    	this.gerAero = ger;
    }
    
    public void setGerRotas(GerenciadorRotas ger) {
    	this.gerRotas = ger;
    }

    private void consulta(ActionEvent evt) {
    	
        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        
        Aeroporto poa = gerAero.buscarCodigo("POA");
        Aeroporto gru = gerAero.buscarCodigo("GRU"); 
        
        Geo locPoa = poa.getLocal();
        Geo locGru = gru.getLocal();
                       
        lstPoints.add(new MyWaypoint(Color.BLUE, poa.getNome(), locPoa));
        lstPoints.add(new MyWaypoint(Color.RED, gru.getNome(), locGru));

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        // Exemplo: criando um traçado
        Tracado tr = new Tracado();
        // Adicionando as mesmas localizações de antes
        tr.addPonto(locPoa);
        tr.addPonto(locGru);
        tr.setCor(Color.RED);
        // E adicionando o traçado...
        gerenciador.addTracado(tr);
               
        this.repaint();

    }
    
    private class EventosMouse extends MouseAdapter
    {
    	private int lastButton = -1;    	
    	
    	@Override
    	public void mousePressed(MouseEvent e) {
    		JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
    		GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
//    		System.out.println(loc.getLatitude()+", "+loc.getLongitude());
    		lastButton = e.getButton();
    		// Botão 3: seleciona localização
    		if(lastButton==MouseEvent.BUTTON3) {  			
    			gerenciador.setPosicao(loc);
    			//gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    			gerenciador.getMapKit().repaint();    			
    		}
    	}    
    } 	
}
