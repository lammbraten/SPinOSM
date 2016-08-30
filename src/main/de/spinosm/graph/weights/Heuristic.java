package de.spinosm.graph.weights;

import java.io.Serializable;

import de.spinosm.graph.RouteableVertex;

public interface Heuristic extends Serializable {
	public double heuristicForVertex(RouteableVertex v);
	public void setReferenceVertex(RouteableVertex v);
}
