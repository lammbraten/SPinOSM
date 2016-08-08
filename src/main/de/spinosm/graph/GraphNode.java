package de.spinosm.graph;

import java.util.LinkedList;

import de.westnordost.osmapi.map.data.LatLon;



public abstract class GraphNode implements RouteableNode {


	private static final long serialVersionUID = 1307414385237284029L;
//	private LinkedList<RouteableEdge> edges;
	private long id;
	private double distance;
	private SerializableLatLon position; //needed for Heuristic
	
	
	public GraphNode(long id, LatLon latLon, double distance){
		this.setId(id);
		this.setDistance(distance);
		this.position = new SerializableLatLon(latLon);
	}
	
	public GraphNode(long id, LatLon latLon){
		this.setId(id);
		this.setDistance(Integer.MAX_VALUE);
		this.position = new SerializableLatLon(latLon);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	@Override
	public SerializableLatLon getPosition() {
		return position;
	}

	public void setPosition(LatLon position) {
		this.position = new SerializableLatLon(position);
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

	/*
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
	}*/
	
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this == other)
			return true;
		if(other instanceof GraphNode){
			GraphNode otherGraphNode = (GraphNode) other;
			if(this.id == otherGraphNode.id)
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
	public int compareTo(RouteableNode other) {
		if(other == null )
			return 0;
		if(this.equals(other))
			return 0;
		if(this.getDistance() > other.getDistance()) 
			return 1;
		if(this.getDistance() < other.getDistance()) 
			return -1;
		return 0;
	}
}
