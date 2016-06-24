package de.spinosm.graph;

public interface Edge {
	public int getCost();
	public Knot getStart();
	public Knot getEnd();
	public Knot getOtherKnotThan(Knot that);
}
