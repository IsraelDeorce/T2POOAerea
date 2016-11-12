package pucrs.myflight.gui;

import java.awt.Color;
import java.util.ArrayList;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.myflight.modelo.Geo;

public class Tracado {
	
	private ArrayList<GeoPosition> pontos;
	private Color cor;
	
	public Tracado() {
		pontos = new ArrayList<>();
	}
	
	public void clear() {
		pontos.clear();
	}
	
	public int size() {
		return pontos.size();
	}
	
	public void addPonto(Geo pos) {
		pontos.add(pos);
	}
	
	public ArrayList<GeoPosition> getPontos() {
		return pontos;
	}
	
	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public Color getCor() { 
		return cor;
	}
}
