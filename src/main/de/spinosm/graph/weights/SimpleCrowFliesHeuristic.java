package de.spinosm.graph.weights;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;

public class SimpleCrowFliesHeuristic implements Heuristic{

	private RouteableVertex reference;
	private float weight;
	
	public SimpleCrowFliesHeuristic(RouteableVertex endVertex, float weight) {
		this.reference = endVertex;
		this.weight = weight;
	}

	@Override
	public double heuristicForVertex(RouteableVertex v) {
		return Common.asTheCrowFlies(reference.getPosition(), v.getPosition()) *weight;
	}

	@Override
	public void setReferenceVertex(RouteableVertex v) {
		this.reference = v;
	}
	
}
