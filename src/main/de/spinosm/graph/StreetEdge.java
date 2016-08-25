package de.spinosm.graph;

public class StreetEdge extends DirectedEdge {
	private static final long serialVersionUID = -6282043762165569240L;

	public StreetEdge(RouteableVertex source, RouteableVertex target, double cost) {
		super(source, target, cost);
	}
}
