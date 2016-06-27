package de.spinosm.graph;

public interface RouteableEdge {
	public int getCost();
	public void setCost(int cost);
	public RouteableNode getStart();
	public void setStart(RouteableNode start);
	public RouteableNode getEnd();
	public void setEnd(RouteableNode end);
	public RouteableNode getOtherKnotThan(RouteableNode that);
}
