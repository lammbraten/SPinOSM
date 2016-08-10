package de.spinosm.graph;

import java.io.Serializable;

import org.jgrapht.EdgeFactory;

public class StreetEdgeFactory implements EdgeFactory<StreetJunction, StreetEdge>, Serializable {
	private static final long serialVersionUID = 7841119678860432702L;

	@Override
	public StreetEdge createEdge(StreetJunction source, StreetJunction target) {
		return new StreetEdge(source, target, Double.MAX_VALUE);
	}

}
