package pucrs.myflight.modelo;

import org.jxmapviewer.viewer.GeoPosition;

// Geo herda de GeoPosition, que é a classe usada internamente
// pelo desenhador do mapa (não faz sentido recriar tudo novamente...)
public class Geo extends GeoPosition{
	
	public Geo(double latitude, double longitude) {
		super(latitude, longitude);		
	}
	
	// Metodo para calcular a distancia entre
	// ESTA localizacao e o outra informada
	public double distancia(GeoPosition outra) {
		GeoPosition obj = new GeoPosition(getLatitude(), getLongitude());
		return distancia(obj, outra);
		//return distancia(this, outra);
	}
	
	// Metodo de classe (static) para calcular
	// distancias entre dois objetos Geo informados
	public static double distancia(GeoPosition geoPosition, GeoPosition pos) {
		double lat1 = Math.toRadians(geoPosition.getLatitude());
		double lat2 = Math.toRadians(pos.getLatitude());
		double lon1 = Math.toRadians(geoPosition.getLongitude());
		double lon2 = Math.toRadians(pos.getLongitude());
		
		double diflat = (lat1-lat2)/2;
		double diflon = (lon1-lon2)/2;
		
		double d = Math.pow(Math.sin(diflat),2)+
				   Math.pow(Math.sin(diflon),2)*
				   Math.cos(lat1) * Math.cos(lat2);
		
		d = 2 * 6371 * Math.asin(Math.sqrt(d));
				
		return d;	
	}
	
}
