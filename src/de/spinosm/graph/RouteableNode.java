package de.spinosm.graph;

import java.util.Iterator;
import java.util.LinkedList;

public interface RouteableNode extends Iterator<RouteableNode> {
	public LinkedList<RouteableEdge> getEdges();
	public void setEdges(LinkedList<RouteableEdge> edges);
	public RouteableEdge getCheapestEdge();
	public void removeEdge(RouteableEdge e);
	void addEdge(RouteableEdge e);
	public void getOutDegree();
}
