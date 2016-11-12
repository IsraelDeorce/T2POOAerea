
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
	private String label = " ";

	/**
	 * @param color a cor
	 * @param coord a localização
	 */
	public MyWaypoint(Color color, String codAero, GeoPosition coord)
	{
		super(coord);
		this.color = color;
		this.label = codAero;
	}
	
	
	public MyWaypoint(Color color, GeoPosition coord)
	{
		super(coord);
		this.color = color;		
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
