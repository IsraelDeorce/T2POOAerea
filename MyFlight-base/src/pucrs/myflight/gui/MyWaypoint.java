
package pucrs.myflight.gui;

import java.awt.Color;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Um waypoint que tem uma cor e um texto associados
 * @author Martin Steiger, Marcelo Cohen
 */
public class MyWaypoint extends DefaultWaypoint
{
	private final Color color;
	private String label = "";

	/**
	 * @param color a cor
	 * @param coord a localização
	 */
	public MyWaypoint(Color color, String label, GeoPosition coord){
		super(coord);
		this.color = color;
		this.label = label;
	}
	
	public MyWaypoint(GeoPosition coord){
		super(coord);
		color = Color.BLACK;		
	}
	
	/**
	 * @returns a cor do waypoint
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * @returns o texto do waypoint
	 */
	public String getLabel() {
		return label;
	}
	
}
