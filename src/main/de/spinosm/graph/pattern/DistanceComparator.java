package de.spinosm.graph.pattern;

import java.io.Serializable;
import java.util.Comparator;

import de.spinosm.graph.RouteableVertex;

public class DistanceComparator implements Comparator<RouteableVertex>, Serializable{
	private static final long serialVersionUID = 3202907756735881493L;

	@Override
	public int compare(RouteableVertex v1, RouteableVertex v2) {
		return v1.compareTo(v2);
	}

}
