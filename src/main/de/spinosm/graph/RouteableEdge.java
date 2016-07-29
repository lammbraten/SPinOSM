package de.spinosm.graph;

import java.io.Serializable;

public interface RouteableEdge extends Cloneable, Serializable {
	public double getWeight();
	public void setWeight(double cost);
	public RouteableNode getStart();
	public void setStart(RouteableNode start);
	public RouteableNode getEnd();
	public void setEnd(RouteableNode end);
	public RouteableNode getOtherKnotThan(RouteableNode that);
	public void replace(RouteableNode oldNode, RouteableNode newNode);
}
