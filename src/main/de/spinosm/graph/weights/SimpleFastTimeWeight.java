package de.spinosm.graph.weights;

import java.util.LinkedList;

import de.spinosm.common.Common;
import de.spinosm.common.OsmHighwayValues;
import de.spinosm.common.Vehicle;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public class SimpleFastTimeWeight implements WeightFunction {

	@Override
	public double calcCosts(LinkedList<LatLon> nodes, Way way) {
		double distance = 0.0;
		for(int i = 0; i < nodes.size()-1; i++)
				distance += calcDistance(nodes.get(i), nodes.get(i+1));		
		double speed = getSpeedLimits(way, Vehicle.CAR);
		return distance /  speed;
	}

	private double calcDistance(LatLon node1, LatLon node2) {
	    double lat1 = Common.toRad(node1.getLatitude());
	    double lat2 = Common.toRad(node2.getLatitude());
	    double lon1 = Common.toRad(node1.getLongitude());
	    double lon2 = Common.toRad(node2.getLongitude());

	    return Common.EARTHRADIUS * Common.pythagoras(lat1, lat2, lon1, lon2);
	}

	private double getSpeedLimits(Way way, Vehicle vehicle) {
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

}
