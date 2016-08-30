package de.spinosm.graph.weights;

import java.util.LinkedList;

import de.spinosm.common.Common;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public class CrowFliesDistanceWeight implements WeightFunction {

	@Override
	public double calcCosts(LinkedList<LatLon> nodes, Way way) {
		double distance = 0.0;
		for(int i = 0; i < nodes.size()-1; i++)
				distance += calcDistance(nodes.get(i), nodes.get(i+1));		
		return distance;
	}

	protected double calcDistance(LatLon node1, LatLon node2) {
	    return Common.asTheCrowFlies(node1, node2);
	}
}
