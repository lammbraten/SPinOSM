package de.spinosm.common;

import java.util.LinkedList;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
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
	
	public static double pythagoras(double lat1, double lat2, double lon1, double lon2) {
		return Math.sqrt(
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
		for(StreetJunction sj : sg.vertexSet()){
			System.out.println(sj);
			for(StreetEdge se :sg.getEdgesForNode(sj,StreetGraph.DEFAULT_DIRECTION, false)){
				System.out.println("\t" + se);
			}
		}
	}
}
