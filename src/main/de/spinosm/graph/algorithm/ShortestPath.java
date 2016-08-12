package de.spinosm.graph.algorithm;

import java.util.List;

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;

public interface ShortestPath  {
	public List<StreetJunction> getShortestPath(StreetJunction start, StreetJunction end);
	public StreetGraph getGraph();
	void setGraph(StreetGraph g);
}
