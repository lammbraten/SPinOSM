package de.spinosm.graph.pattern;

import java.util.Comparator;

import de.spinosm.graph.RouteableVertex;

public class DistanceComparator implements Comparator<RouteableVertex> {

	@Override
	public int compare(RouteableVertex v1, RouteableVertex v2) {
		return v1.compareTo(v2);
	}

}
