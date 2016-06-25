package de.spinosm.graph;

public abstract class DirectedEdge implements Edge {

	private Knot startNode;
	private Knot endNode;
	private int cost;
	
	DirectedEdge(Knot start, Knot end, int cost){
		this.startNode = start;
		this.endNode = end;
		this.cost = cost;
	}
	
	@Override
	public int getCost() {
		return cost;
	}
	
	@Override
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	@Override
	public Knot getStart() {
		return startNode;
	}
	
	@Override
	public void setStart(Knot start) {
		this.startNode = start;		
	}

	@Override
	public Knot getEnd() {
		return endNode;
	}
	
	@Override
	public void setEnd(Knot end) {
		this.endNode = end;
		
	}
	
	@Override
	public Knot getOtherKnotThan(Knot that) {
		if(isStartKnot(that))
			return endNode;
		else if(isEndKnot(that))
			return startNode;
		else
			throw new IllegalArgumentException("The Knot " + that + " isn't in this Graph");
	}

	private boolean isStartKnot(Knot that) {
		if(that.equals(startNode))
			return true;
		return false;
	}

	private boolean isEndKnot(Knot that) {
		if(that.equals(endNode))
			return true;
		return false;
	}
}
