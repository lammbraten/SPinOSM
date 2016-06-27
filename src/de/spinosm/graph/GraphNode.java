package de.spinosm.graph;

import java.util.LinkedList;



public abstract class GraphNode implements RouteableNode {

	private LinkedList<RouteableEdge> edges;
	private String id;
	private int distance;
	
	
	public GraphNode(String id, int distance){
		this.setId(id);
		this.setDistance(distance);
	}
	
	public GraphNode(String id){
		this.setId(id);
		this.setDistance(Integer.MAX_VALUE);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
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
}
