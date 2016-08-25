package de.spinosm.graph.algorithm;

import java.util.List;
import java.util.TreeSet;

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;

public interface ShortestPath {
	public List<StreetVertex> getShortestPath(StreetVertex start, StreetVertex end);
	public StreetGraph getGraph();
	public void setGraph(StreetGraph g);
	public List<StreetVertex> getBorderVertecies();
	public List<StreetVertex> getFinishedVertecies();
	TreeSet<StreetVertex> getVisitedVertecies();
	void setVisitedVertecies(TreeSet<StreetVertex> visitedVertecies);
	
}
