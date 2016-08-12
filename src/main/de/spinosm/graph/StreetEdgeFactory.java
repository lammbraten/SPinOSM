package de.spinosm.graph;

import java.io.Serializable;

import org.jgrapht.EdgeFactory;

public class StreetEdgeFactory implements EdgeFactory<StreetVertex, StreetEdge>, Serializable {
	private static final long serialVersionUID = 7841119678860432702L;

	@Override
	public StreetEdge createEdge(StreetVertex source, StreetVertex target) {
		return new StreetEdge(source, target, Double.MAX_VALUE);
	}

}
