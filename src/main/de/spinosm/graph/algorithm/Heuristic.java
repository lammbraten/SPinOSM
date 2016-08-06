package de.spinosm.graph.algorithm;

import de.spinosm.graph.RouteableNode;

public interface Heuristic {
	public double heuristicForVertex(RouteableNode v);
	public void setReferenceVertex(RouteableNode v);
}
