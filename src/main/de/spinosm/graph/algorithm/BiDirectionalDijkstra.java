package de.spinosm.graph.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;

public class BiDirectionalDijkstra extends ObservableShortestPath{
	private static final int REVERSE_DIRECTION = -1;
	private static final int STRAIGHT_DIRECTION = 1;
	private StreetGraph graph; 
	private Dijkstra reverseDijkstra;
	private Dijkstra straightDijkstra;
	private static StreetVertex startVertex;
	private static StreetVertex endVertex;	
	
	
	public BiDirectionalDijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		
		this.reverseDijkstra = new Dijkstra(graph,REVERSE_DIRECTION);
		this.straightDijkstra = new Dijkstra(graph,STRAIGHT_DIRECTION);
		
	}

	@Override
	public List<StreetVertex> getShortestPath(StreetVertex start, StreetVertex end) {
		startVertex = start;
		endVertex = end;
		
		reverseDijkstra.init(startVertex);
		straightDijkstra.init(endVertex);
		
		while(intersectionOf(reverseDijkstra.getFinishedVertecies(),straightDijkstra.getFinishedVertecies()).isEmpty() && (!reverseDijkstra.getBorderVertecies().isEmpty() || !straightDijkstra.getBorderVertecies().isEmpty())){
			reverseDijkstra.checkNextVertex();
			straightDijkstra.checkNextVertex();
		}
			
		return buildShortestPath();
	}

	private List<StreetVertex> buildShortestPath() {
		StreetVertex jointValue = intersectionOf(reverseDijkstra.getFinishedVertecies(),straightDijkstra.getFinishedVertecies()).first();
		List<StreetVertex> shortestSub1PathReverse = reverseDijkstra.buildShortestPathTo(jointValue);	
		List<StreetVertex> shortestSub2Path = straightDijkstra.buildShortestPathTo(jointValue);
	    ListIterator<StreetVertex> listIterator = shortestSub1PathReverse.listIterator();
		List<StreetVertex>  shortestPath = new LinkedList<StreetVertex>();
		
	    //vorwärts
	    while(listIterator.hasNext())
	    	listIterator.next();
	    //zurück
		while(listIterator.hasPrevious())
			shortestPath.add(listIterator.previous());	
		shortestPath.add(jointValue);		
		shortestPath.addAll(shortestSub2Path);

		return shortestPath;
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;	
	}

	private TreeSet<StreetVertex> intersectionOf(List<StreetVertex> list, List<StreetVertex> list2){
		TreeSet<StreetVertex> intersection = new TreeSet<StreetVertex>(list);
		intersection.retainAll(list2);
		return intersection;
	}
	
	@Override
	public List<StreetVertex> getBorderVertecies() {
		ArrayList<StreetVertex> returnList = new ArrayList<StreetVertex>(straightDijkstra.getBorderVertecies()); 
		returnList.addAll(reverseDijkstra.getBorderVertecies());
		return returnList;
	}

	@Override
	public List<StreetVertex> getFinishedVertecies() {
		ArrayList<StreetVertex> returnList = new ArrayList<StreetVertex>(straightDijkstra.getFinishedVertecies()); 
		returnList.addAll(reverseDijkstra.getFinishedVertecies());
		return returnList;
	}
}
