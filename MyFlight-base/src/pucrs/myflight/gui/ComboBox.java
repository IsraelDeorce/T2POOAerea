package pucrs.myflight.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import pucrs.myflight.modelo.GerenciadorCias;

	
public class ComboBox extends JPanel implements ActionListener {
	    

	    public ComboBox() {
	    
	    	super(new BorderLayout());
	    	GerenciadorCias cias = new GerenciadorCias();
	    	try {
				cias.carregaDados();
			} 
	    	catch (IOException e) {
				System.out.println("Impossível ler airlines.dat!");
				System.out.println("Msg: "+e);
				System.exit(1);
			}
	        String[] empresas = new String[cias.enviaAL().size()];	        
	        for(int i = 0;i<empresas.length;i++){
	        	empresas[i] = cias.enviaAL().get(i).toString();
	        }
	    	
	        //Create the combo box, select the item at index 4.
	        //Indices start at 0, so 4 specifies the pig.
	        JComboBox comboEmpresas = new JComboBox(empresas);
	        comboEmpresas.addActionListener(this);
	        JButton botao = new JButton("Ok");
	        botao.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {					
					comboEmpresas.hidePopup();
				}
			});

	        //Lay out the demo.
	        add(comboEmpresas, BorderLayout.PAGE_START);
	        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	        add(comboEmpresas, BorderLayout.CENTER);
	        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	        add(botao, BorderLayout.PAGE_END);
	    }

	    /** Listens to the combo box. */
	    public void actionPerformed(ActionEvent e) {
	        JComboBox cb = (JComboBox)e.getSource();
	        String empresa = (String)cb.getSelectedItem();	        
	        
	    }
	    

	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("Selecione a empresa");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Create and set up the content pane.
	        JComponent newContentPane = new ComboBox();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	    public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
}
	    
	


