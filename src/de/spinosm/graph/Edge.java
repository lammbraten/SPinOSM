package de.spinosm.graph;

public interface Edge {
	public int getCost();
	public void setCost(int cost);
	public Knot getStart();
	public void setStart(Knot start);
	public Knot getEnd();
	public void setEnd(Knot end);
	public Knot getOtherKnotThan(Knot that);
}
