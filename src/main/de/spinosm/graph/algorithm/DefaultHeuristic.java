package de.spinosm.graph.algorithm;

import de.spinosm.graph.RouteableNode;

public class DefaultHeuristic extends SimpleCrowFliesTimeHeuristic {
	private static int ESTIMATED_AVERAGE_SPEED = 25;
	private static float WEIGHTING =  4;
	
	public DefaultHeuristic(RouteableNode endVertex) {
		super(endVertex, ESTIMATED_AVERAGE_SPEED, WEIGHTING);
	}
}
