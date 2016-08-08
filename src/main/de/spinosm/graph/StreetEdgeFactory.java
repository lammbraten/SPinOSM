package de.spinosm.graph;

import org.jgrapht.EdgeFactory;

public class StreetEdgeFactory implements EdgeFactory<StreetJunction, StreetEdge> {

	@Override
	public StreetEdge createEdge(StreetJunction source, StreetJunction target) {
		return new StreetEdge(source, target, Double.MAX_VALUE);
	}

}
