package de.spinosm.graph.algorithm;

import java.util.List;

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;

public interface ShortestPath {

	public List<StreetVertex> getShortestPath(StreetVertex start, StreetVertex end);
	public StreetGraph getGraph();
	void setGraph(StreetGraph g);
}
