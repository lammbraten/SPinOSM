package de.spinosm.graph.weights;

import java.util.LinkedList;

import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public class Unweighted implements WeightFunction {

	@Override
	public double calcCosts(LinkedList<LatLon> nodes, Way way) {
		return 1;
	}

}
