package de.spinosm.graph;

public abstract class DirectedEdge implements Edge {

	private GraphNode startNode;
	private GraphNode endNode;
	
	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Knot getStart() {
		return startNode;
	}

	@Override
	public Knot getEnd() {
		return endNode;
	}

	@Override
	public Knot getOtherKnotThan(Knot that) {
		// TODO Auto-generated method stub
		return null;
	}

}
