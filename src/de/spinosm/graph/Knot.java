package de.spinosm.graph;

import java.util.Iterator;

public interface Knot extends Iterator<Knot> {
	public Edge getEdges();
	public Edge getCheapestEdge();
	public void removeEdge(Edge e);
	public void addEdge();
	

}
