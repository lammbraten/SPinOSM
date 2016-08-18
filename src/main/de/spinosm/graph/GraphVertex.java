package de.spinosm.graph;

import org.jgrapht.util.FibonacciHeapNode;

import de.westnordost.osmapi.map.data.LatLon;



public abstract class GraphVertex implements RouteableVertex {

	private static final long serialVersionUID = 1307414385237284029L;
	private long id;
	private double distance;
	private SerializableLatLon position; //needed for Heuristic
	
	public static int DISTANCE_INIT_VALUE = Integer.MAX_VALUE;
	

	public GraphVertex(long id, LatLon latLon, double distance){
		this.setId(id);
		this.setDistance(distance);
		this.position = new SerializableLatLon(latLon);
	}
	
	public GraphVertex(long id, LatLon latLon){
		this.setId(id);
		this.setDistance(DISTANCE_INIT_VALUE);
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

	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this == other)
			return true;
		if(other instanceof GraphVertex){
			GraphVertex otherGraphNode = (GraphVertex) other;
			if(this.id == otherGraphNode.id)
				return true;
		}
		return false;
	}
	
	@Override
	public boolean hasSameId(RouteableVertex n) {
		return (this.getId() == n.getId());
	}

	@Override
	public String toString() {
		return "" + this.getId();
	}
	
	@Override
	public int compareTo(RouteableVertex other) {
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
