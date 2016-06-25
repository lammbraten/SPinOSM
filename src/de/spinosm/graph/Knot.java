package de.spinosm.graph;

import java.util.Iterator;
import java.util.LinkedList;

public interface Knot extends Iterator<Knot> {
	public LinkedList<Edge> getEdges();
	public void setEdges(LinkedList<Edge> edges);
	public Edge getCheapestEdge();
	public void removeEdge(Edge e);
	void addEdge(Edge e);
	public void getOutDegree();
}
