package de.spinosm.graph;

public class StreetEdge extends DirectedEdge {
	private static final long serialVersionUID = -6282043762165569240L;

	public StreetEdge(StreetVertex start, StreetVertex end, double cost) {
		super(start, end, cost);
	}
}
