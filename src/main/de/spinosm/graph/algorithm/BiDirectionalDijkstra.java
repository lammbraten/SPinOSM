package de.spinosm.graph.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;

public class BiDirectionalDijkstra extends ObservableShortestPath{
	private static final long serialVersionUID = 78753746760491437L;
	private static final int REVERSE_DIRECTION = -1;
	private static final int STRAIGHT_DIRECTION = 1;
	private StreetGraph graph; 
	private Dijkstra reverseDijkstra;
	private Dijkstra straightDijkstra;
	private List<RouteableVertex> calculatetdPath;
	private static RouteableVertex startVertex;
	private static RouteableVertex endVertex;	
	
	
	public BiDirectionalDijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		
		this.reverseDijkstra = new Dijkstra(graph,REVERSE_DIRECTION);
		this.straightDijkstra = new Dijkstra(graph,STRAIGHT_DIRECTION);
	}

	@Override
	public List<RouteableVertex> getShortestPath(RouteableVertex start, RouteableVertex end) {
		startVertex = start;
		endVertex = end;
		
		reverseDijkstra.init(startVertex);
		straightDijkstra.init(endVertex);
		while(!reverseDijkstra.getBorderVertices().isEmpty() || !straightDijkstra.getBorderVertices().isEmpty()){
			if(straightDijkstra.getVisitedVertices().contains(reverseDijkstra.checkNextVertex()))
				break;
			if(reverseDijkstra.getVisitedVertices().contains(straightDijkstra.checkNextVertex()))
				break;
			
		}
			
		return buildShortestPath();
	}
	
	@Override
	public StreetGraph getGraph() {
		return graph;
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;	
		reverseDijkstra.setGraph(g);
		straightDijkstra.setGraph(g);
	}

	@Override
	public List<RouteableVertex> getBorderVertices() {
		ArrayList<RouteableVertex> returnList = new ArrayList<RouteableVertex>(straightDijkstra.getBorderVertices()); 
		returnList.addAll(reverseDijkstra.getBorderVertices());
		return returnList;
	}

	@Override
	public List<RouteableVertex> getFinishedVertices() {
		ArrayList<RouteableVertex> returnList = new ArrayList<RouteableVertex>(straightDijkstra.getFinishedVertices()); 
		returnList.addAll(reverseDijkstra.getFinishedVertices());
		return returnList;
	}

	@Override
	public HashSet<RouteableVertex> getVisitedVertices() {
		return null;
	}

	@Override
	public void setVisitedVertices(HashSet<RouteableVertex> visitedVertecies) {
	}

	@Override
	public List<RouteableVertex> getCalculatedShortestPath() {
		return calculatetdPath;
	}

	private TreeSet<RouteableVertex> intersectionOf(HashSet<RouteableVertex> hashSet, HashSet<RouteableVertex> hashSet2){
		TreeSet<RouteableVertex> intersection = new TreeSet<RouteableVertex>(hashSet);
		intersection.retainAll(hashSet2);
		return intersection;
	}

	private List<RouteableVertex> buildShortestPath() {
		RouteableVertex jointValue = getMiddleVertex();
		List<RouteableVertex> shortestSub1PathReverse = reverseDijkstra.buildShortestPathTo(jointValue);	
		List<RouteableVertex> shortestSub2Path = straightDijkstra.buildShortestPathTo(jointValue);
	    ListIterator<RouteableVertex> listIterator = shortestSub1PathReverse.listIterator();
		List<RouteableVertex>  shortestPath = new LinkedList<RouteableVertex>();
		
	    //vorwaerts
	    while(listIterator.hasNext())
	    	listIterator.next();
	    //zurueck
		while(listIterator.hasPrevious())
			shortestPath.add(listIterator.previous());	
		shortestPath.add(jointValue);		
		shortestPath.addAll(shortestSub2Path);
	
		calculatetdPath = shortestPath;
		return shortestPath;
	}

	private RouteableVertex getMiddleVertex() {
		TreeSet<RouteableVertex> intersectionSet = intersectionOf(reverseDijkstra.getVisitedVertices(),straightDijkstra.getVisitedVertices());
		RouteableVertex lowestDistance = new StreetVertex();
		
		for(RouteableVertex sv : intersectionSet)
			if(sv.getDistance() < lowestDistance.getDistance())
				lowestDistance = sv;
		
		return lowestDistance;
	}
}
