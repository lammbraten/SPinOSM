package de.spinosm.graph;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import de.westnordost.osmapi.map.data.LatLon;

public interface RouteableNode extends Iterator<RouteableNode> ,  Comparable<RouteableNode>, Serializable{
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
	public LatLon getPosition();
	public boolean isEdgesLoaded();
	public boolean equals(Object other);
	public int compareTo(RouteableNode other);
}
