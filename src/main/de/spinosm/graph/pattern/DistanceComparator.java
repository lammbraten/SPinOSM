package de.spinosm.graph.pattern;

import java.util.Comparator;

import de.spinosm.graph.RouteableNode;

public class DistanceComparator implements Comparator<RouteableNode> {

	@Override
	public int compare(RouteableNode v1, RouteableNode v2) {
		return v1.compareTo(v2);
	}

}
