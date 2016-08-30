package de.spinosm.graph.weights;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;

public class CrowFliesTimeHeuristic extends CrowFliesHeuristic {

	private static final long serialVersionUID = 3301264470174567440L;
	private double maxSpeed;
	
	public CrowFliesTimeHeuristic(RouteableVertex endVertex, float weight, double avgSpeed) {
		super(endVertex, weight);
		this.maxSpeed = avgSpeed;
	}

	@Override
	public double heuristicForVertex(RouteableVertex v) {
		return (Common.asTheCrowFlies(reference.getPosition(), v.getPosition()) /maxSpeed) * weight;
	}
}
