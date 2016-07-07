package de.spinosm.graph;

public interface RouteableEdge {
	public double getCost();
	public void setCost(double cost);
	public RouteableNode getStart();
	public void setStart(RouteableNode start);
	public RouteableNode getEnd();
	public void setEnd(RouteableNode end);
	public RouteableNode getOtherKnotThan(RouteableNode that);
}
