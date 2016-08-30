package de.spinosm.graph.weights;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;

public class PythagoreanTimeHeuristic extends PythagoreanDistanceHeuristic {

	private static final long serialVersionUID = -6474669603007268977L;
	private double maxSpeed;
	
	public PythagoreanTimeHeuristic(RouteableVertex endVertex, float weight, double maxSpeed) {
		super(endVertex, weight);
		this.maxSpeed = maxSpeed;
	}
	
	@Override
	public double heuristicForVertex(RouteableVertex v) {
		return (Common.PythagoreanDistance(reference.getPosition(), v.getPosition()) /maxSpeed) * weight;
	}
}
