package de.spinosm.graph.pattern;

import java.io.Serializable;
import java.util.Comparator;

import de.spinosm.graph.RouteableVertex;

public class DistanceHeuristicComparator implements Comparator<RouteableVertex>, Serializable {
	private static final long serialVersionUID = -6405437387322559473L;

	@Override
	public int compare(RouteableVertex v1, RouteableVertex v2) {
		if(v1 == null || v2 == null )
			return 0;
		if(v1.equals(v2))
			return 0;
		if(v1.getDistance() + v1.getHeuristic() > v2.getDistance() + v2.getHeuristic()) 
			return 1;
		if(v1.getDistance() + v1.getHeuristic() < v2.getDistance() + v2.getHeuristic()) 
			return -1;
		return 0;
	}

}
