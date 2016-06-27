package de.spinosm.graph;

public abstract class DirectedEdge implements RouteableEdge {

	private GraphNode startNode;
	private GraphNode endNode;
	private int cost;
	
	DirectedEdge(GraphNode start, GraphNode end, int cost){
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
	public RouteableNode getStart() {
		return startNode;
	}
	
	@Override
	public void setStart(RouteableNode start) {
		this.startNode = (GraphNode) start;		
	}

	@Override
	public RouteableNode getEnd() {
		return endNode;
	}
	
	@Override
	public void setEnd(RouteableNode end) {
		this.endNode = (GraphNode) end;
		
	}
	
	@Override
	public RouteableNode getOtherKnotThan(RouteableNode that) {
		if(isStartKnot(that))
			return endNode;
		else if(isEndKnot(that))
			return startNode;
		else
			throw new IllegalArgumentException("The Knot " + that + " isn't in this Graph");
	}

	private boolean isStartKnot(RouteableNode that) {
		if(that.equals(startNode))
			return true;
		return false;
	}

	private boolean isEndKnot(RouteableNode that) {
		if(that.equals(endNode))
			return true;
		return false;
	}
}
