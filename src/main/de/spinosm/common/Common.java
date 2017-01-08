package de.spinosm.common;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public class Common {
	
	public static int EARTHRADIUS = 6371000; //Mittlerer Radius in m
	public static int EARTHDIAMETER = 2*EARTHRADIUS;
	
	public static boolean wayIsUseable(Way way, Vehicle vehicle) {
		if(way.getTags().containsKey("highway")){
			switch(vehicle){
			case CAR:
				return OsmHighwayValues.isRouateableForCars(way);	
			case TRUCK:
				return OsmHighwayValues.isRouateableForTrucks(way);
			case PEDESTRIAN:
				return OsmHighwayValues.isRouateableForPedestrians(way);
			case BICYCLE:
				return OsmHighwayValues.isRouateableForBicycles(way);
			default:
				return false;
			}
		}
		return false;
	}
	
	public static double getSpeedLimits(Way way, Vehicle vehicle) {
		if(way.getTags().containsKey("highway")){
			switch(vehicle){
			case CAR:
				return OsmHighwayValues.getCarSpeedLimits(way);	
			case TRUCK:
				return OsmHighwayValues.getTruckSpeedLimits(way);
			case PEDESTRIAN:
				return OsmHighwayValues.getPedestrianSpeeds(way);
			case BICYCLE:
				return OsmHighwayValues.getBycicleSpeeds(way);
			default:
				throw new IllegalArgumentException("Vehicle unknown!");
			}
		}
		throw new IllegalArgumentException("Not a Highway!");
	}
	
	public static double PythagoreanDistance(LatLon node1, LatLon node2){
	    double lat1 = Common.toRad(node1.getLatitude());
	    double lat2 = Common.toRad(node2.getLatitude());
	    double lon1 = Common.toRad(node1.getLongitude());
	    double lon2 = Common.toRad(node2.getLongitude());
	    
	    return EARTHRADIUS * pythagoras(lat1, lat2, lon1, lon2);

	}
	
	public static double pythagoras(double lat1, double lat2, double lon1, double lon2) {
		return  Math.sqrt(
				Math.pow((lat1-lat2),2) +
				Math.pow((lon1-lon2),2)
				);
	}


	/**
	 * Calculates the Distance between two given Coordinates as the Crow flies. (de: Luftlinie)
	 * @param node1
	 * @param node2
	 * @return distance in Kilometers
	 */
	public static double asTheCrowFlies(LatLon node1 , LatLon node2){
	    double lat1 = toRad(node1.getLatitude());
	    double lat2 = toRad(node2.getLatitude());
	    double lon1 = toRad(node1.getLongitude());
	    double lon2 = toRad(node2.getLongitude());

	    return asTheCrowFlies(lat1, lat2 , lon1, lon2);
	}
	
	public static double asTheCrowFlies(double lat1, double lat2 , double lon1, double lon2){
	    return EARTHRADIUS *  haversine(lat1, lat2, lon1, lon2);
	}
	
	/**
	 * Calculates the distance between two Points on a Sphere, using the haversine-formula. 
	 * @param lat1 in Rad
	 * @param lat2 in Rad
	 * @param lon1 in Rad
	 * @param lon2 in Rad
	 * @return distance in Rad;
	 */
	private static double haversine(double lat1, double lat2, double lon1, double lon2){
	    double deltaLat = lat2 - lat1;
	    double deltaLon = lon2 - lon1;
		return 2*Math.asin(
	    		Math.sqrt(
	    				Math.pow(Math.sin((deltaLat)/2),2)+
	    				Math.cos(lat1)*Math.cos(lat2)*
	    				Math.pow(Math.sin((deltaLon)/2),2)
	    		));
	}
	
	public static double toRad(double karth){
		return karth * Math.PI / 180;
	}
	
	public static void writeStreetGraph(StreetGraph sg){
		for(RouteableVertex sj : sg.vertexSet()){
			System.out.println(sj);
			for(StreetEdge se :sg.edgesOf(sj, 1)){
				System.out.println("\t" + se);
			}
		}
	}
}
