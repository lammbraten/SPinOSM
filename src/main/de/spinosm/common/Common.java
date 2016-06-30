package de.spinosm.common;

import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public class Common {
	
	public static int EARTHRADIUS = 6371;
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
	

	public static boolean getSpeedLimits(Way way, Vehicle vehicle) {
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
				return false;
			}
		}
		return false;
	}

	public static int calcCost(StreetJunction startNode, StreetJunction endNode, Way way) {
		// TODO Auto-generated method stub
		return -1;
	}
	
	/**
	 * Calculates the Distance betwenn
	 * @param node1
	 * @param node2
	 * @return distance in Kilomezers
	 */
	public static double calcDistance(LatLon node1 , LatLon node2){
		double a = 0.5 - Math.cos((node2.getLatitude() - node1.getLatitude()) * Math.PI)/2 + 
				Math.cos(node1.getLatitude() * Math.PI) * Math.cos(node2.getLatitude() * Math.PI) * 
				(1 - Math.cos((node2.getLongitude() - node1.getLongitude()) * Math.PI))/2;

		return EARTHDIAMETER * Math.asin(Math.sqrt(a)); 
	}
		
}
