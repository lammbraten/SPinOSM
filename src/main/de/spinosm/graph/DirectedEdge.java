package de.spinosm.graph;

public abstract class DirectedEdge implements RouteableEdge {

	private GraphNode startNode;
	private GraphNode endNode;
	private double cost;
	
	DirectedEdge(GraphNode start, GraphNode end, double cost2){
		this.startNode = start;
		this.endNode = end;
		this.cost = cost2;
	}
	
	@Override
	public double getCost() {
		return cost;
	}
	
	@Override
	public void setCost(double cost) {
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
	
	@Override
	public void replace(RouteableNode oldNode, RouteableNode newNode){
		if(isStartKnot(oldNode))
			this.setStart(newNode);
		else if(isEndKnot(oldNode))
			this.setEnd(newNode);
		else
			throw new IllegalArgumentException("The Knot " + oldNode + " isn't in this Graph");
	}
	
	@Override
	public String toString(){
		return "s: " + this.getStart() + "; e: " + this.getEnd() + "; c: " + this.getCost();
	}

	private boolean isStartKnot(RouteableNode that) {
		if(that.hasSameId(startNode))
			return true;
		return false;
	}

	private boolean isEndKnot(RouteableNode that) {
		if(that.hasSameId(endNode))
			return true;
		return false;
	}	
}
