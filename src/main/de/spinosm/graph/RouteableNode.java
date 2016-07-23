package de.spinosm.graph;

import java.util.Iterator;
import java.util.LinkedList;

public interface RouteableNode extends Iterator<RouteableNode> {
	public LinkedList<RouteableEdge> getEdges();
	public void setEdges(LinkedList<RouteableEdge> edges);
	//public RouteableEdge getCheapestEdge();
	public void removeEdge(RouteableEdge e);
	public void addEdge(RouteableEdge e);
	public void getOutDegree();
	public long getId();
	boolean hasSameId(RouteableNode n);
	public void setDistance(double distance);
	public double getDistance();
	public boolean isEdgesLoaded();
	public boolean equals(Object other);
}
