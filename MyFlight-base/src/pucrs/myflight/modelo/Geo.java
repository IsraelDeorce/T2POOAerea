package pucrs.myflight.modelo;

import java.io.Serializable;

import org.jxmapviewer.viewer.GeoPosition;

// Geo herda de GeoPosition, que é a classe usada internamente
// pelo desenhador do mapa (não faz sentido recriar tudo novamente...)
public class Geo extends GeoPosition implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Geo(double latitude, double longitude) {
		super(latitude, longitude);		
	}
	
	// Metodo para calcular a distancia entre
	// ESTA localizacao e o outra informada
	public double distancia(Geo outra) {
		Geo obj = new Geo(getLatitude(), getLongitude());
		return distancia(obj, outra);
		//return distancia(this, outra);
	}
	
	// Metodo de classe (static) para calcular
	// distancias entre dois objetos Geo informados
	public static double distancia(Geo geo1, Geo geo2) {
		double lat1 = Math.toRadians(geo1.getLatitude());
		double lat2 = Math.toRadians(geo2.getLatitude());
		double lon1 = Math.toRadians(geo1.getLongitude());
		double lon2 = Math.toRadians(geo2.getLongitude());
		
		double diflat = (lat1-lat2)/2;
		double diflon = (lon1-lon2)/2;
		
		double d = Math.pow(Math.sin(diflat),2)+
				   Math.pow(Math.sin(diflon),2)*
				   Math.cos(lat1) * Math.cos(lat2);
		
		d = 2 * 6371 * Math.asin(Math.sqrt(d));
				
		return d;	
	}	
}
