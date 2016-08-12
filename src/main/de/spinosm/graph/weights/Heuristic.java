package de.spinosm.graph.weights;

import de.spinosm.graph.RouteableVertex;

public interface Heuristic {
	public double heuristicForVertex(RouteableVertex v);
	public void setReferenceVertex(RouteableVertex v);
}
