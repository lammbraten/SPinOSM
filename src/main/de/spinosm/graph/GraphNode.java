package de.spinosm.graph;

import java.util.LinkedList;
import java.util.TreeSet;



public abstract class GraphNode implements RouteableNode, Comparable {

	private LinkedList<RouteableEdge> edges;
	private long id;
	private double distance;
	
	
	public GraphNode(long id, double distance){
		this.setId(id);
		this.setDistance(distance);
	}
	
	public GraphNode(long id){
		this.setId(id);
		this.setDistance(Integer.MAX_VALUE);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public void setDistance(double distance) {
		if(distance < 0)
			throw new IllegalArgumentException("Distance must be positive!");
		this.distance = distance;
	}

	@Override
	public boolean hasNext() {
		if(!edges.isEmpty())
			return true;
		return false;
	}

	@Override
	public RouteableNode next() {
		return edges.iterator().next().getOtherKnotThan(this);
	}

	@Override
	public LinkedList<RouteableEdge> getEdges() {
		return edges;
	}

	@Override
	public void removeEdge(RouteableEdge toRemove) {
		edges.removeIf(e -> e.equals(toRemove));
	}

	@Override
	public void addEdge(RouteableEdge e) {
		edges.add(e);
	}

	@Override
	public void setEdges(LinkedList<RouteableEdge> edges) {
		this.edges = edges;
		
	}

	@Override
	public void getOutDegree() {
		this.edges.size();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this == other)
			return true;
		if(this.getClass().equals(other.getClass())){
			GraphNode otherGraphNode = (GraphNode) other;
			if(this.id == otherGraphNode.id &&
					this.edges.equals(otherGraphNode.edges) &&
					this.distance == otherGraphNode.distance)
				return true;
		}
		return false;
	}
	
	
	@Override
	public boolean hasSameId(RouteableNode n) {
		return (this.getId() == n.getId());
	}

	@Override
	public String toString() {
		return "" + this.getId();
	}
	
	
	@Override
	public int compareTo(Object other) {
		if(other == null )
			return 1;
		if(this.getClass().equals(other.getClass())){
			GraphNode otherGraphNode = (GraphNode) other;
			return (int) (this.id - otherGraphNode.id);
		}
		return 1;
	}
}
