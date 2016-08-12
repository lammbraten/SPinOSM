package de.spinosm.graph.weights;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;

public class SimpleCrowFliesTimeHeuristic implements Heuristic {

	private RouteableVertex reference;
	private int avgSpeed;
	private float weight;
	
	public SimpleCrowFliesTimeHeuristic(RouteableVertex endVertex, int avgSpeed, float weight) {
		this.reference = endVertex;
		this.avgSpeed = avgSpeed;
		this.weight = weight;
	}

	@Override
	public double heuristicForVertex(RouteableVertex v) {
		return Common.asTheCrowFlies(reference.getPosition(), v.getPosition()) /(avgSpeed * weight);
	}

	@Override
	public void setReferenceVertex(RouteableVertex v) {
		this.reference = v;
	}

}
