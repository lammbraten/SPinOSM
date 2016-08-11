package de.spinosm.graph.weights;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableNode;

public class SimpleCrowFliesTimeHeuristic implements Heuristic {

	private RouteableNode reference;
	private int avgSpeed;
	private float weight;
	
	public SimpleCrowFliesTimeHeuristic(RouteableNode endVertex, int avgSpeed, float weight) {
		this.reference = endVertex;
		this.avgSpeed = avgSpeed;
		this.weight = weight;
	}

	@Override
	public double heuristicForVertex(RouteableNode v) {
		return Common.asTheCrowFlies(reference.getPosition(), v.getPosition()) /(avgSpeed * weight);
	}

	@Override
	public void setReferenceVertex(RouteableNode v) {
		this.reference = v;
	}

}
