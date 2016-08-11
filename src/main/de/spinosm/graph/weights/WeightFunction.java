package de.spinosm.graph.weights;

import java.util.LinkedList;

import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Way;

public interface WeightFunction {
	public double calcCosts(LinkedList<LatLon> nodes, Way way);
}
