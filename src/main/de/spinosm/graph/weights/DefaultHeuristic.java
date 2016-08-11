package de.spinosm.graph.weights;

import de.spinosm.graph.RouteableNode;

public class DefaultHeuristic extends SimpleCrowFliesTimeHeuristic {
	private static int ESTIMATED_AVERAGE_SPEED = 1;
	private static float WEIGHTING =  1;
	
	public DefaultHeuristic(RouteableNode endVertex) {
		super(endVertex, ESTIMATED_AVERAGE_SPEED, WEIGHTING);
	}
}
