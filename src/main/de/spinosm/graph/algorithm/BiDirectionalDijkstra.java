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
	private static final int REVERSE_DIRECTION = -1;
	private static final int STRAIGHT_DIRECTION = 1;
	private StreetGraph graph; 
	private Dijkstra reverseDijkstra;
	private Dijkstra straightDijkstra;
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
		
		while(!hasSomeSameElement(reverseDijkstra.getVisitedVertecies(),straightDijkstra.getVisitedVertecies()) && (!reverseDijkstra.getBorderVertecies().isEmpty() || !straightDijkstra.getBorderVertecies().isEmpty())){
			reverseDijkstra.checkNextVertex();
			straightDijkstra.checkNextVertex();
		}
			
		return buildShortestPath();
	}

	
	private boolean hasSomeSameElement(HashSet<RouteableVertex> visitedVertecies, HashSet<RouteableVertex> visitedVertecies2) {
		for(RouteableVertex rv : visitedVertecies)
			if(visitedVertecies2.contains(rv))
				return true;
		return false;
	}

	private List<RouteableVertex> buildShortestPath() {
		RouteableVertex jointValue = getMiddleVertex();
		List<RouteableVertex> shortestSub1PathReverse = reverseDijkstra.buildShortestPathTo(jointValue);	
		List<RouteableVertex> shortestSub2Path = straightDijkstra.buildShortestPathTo(jointValue);
	    ListIterator<RouteableVertex> listIterator = shortestSub1PathReverse.listIterator();
		List<RouteableVertex>  shortestPath = new LinkedList<RouteableVertex>();
		
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

	/**
	 * @return
	 */
	public RouteableVertex getMiddleVertex() {
		TreeSet<RouteableVertex> intersectionSet = intersectionOf(reverseDijkstra.getVisitedVertecies(),straightDijkstra.getVisitedVertecies());
		RouteableVertex lowestDistance = new StreetVertex();
		
		for(RouteableVertex sv : intersectionSet)
			if(sv.getDistance() < lowestDistance.getDistance())
				lowestDistance = sv;
		
		return lowestDistance;
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;	
	}

	private TreeSet<RouteableVertex> intersectionOf(HashSet<RouteableVertex> hashSet, HashSet<RouteableVertex> hashSet2){
		//TODO; Make This more effictive
		TreeSet<RouteableVertex> intersection = new TreeSet<RouteableVertex>(hashSet);
		intersection.retainAll(hashSet2);
		return intersection;
	}
	
	@Override
	public List<RouteableVertex> getBorderVertecies() {
		ArrayList<RouteableVertex> returnList = new ArrayList<RouteableVertex>(straightDijkstra.getBorderVertecies()); 
		returnList.addAll(reverseDijkstra.getBorderVertecies());
		return returnList;
	}

	@Override
	public List<RouteableVertex> getFinishedVertecies() {
		ArrayList<RouteableVertex> returnList = new ArrayList<RouteableVertex>(straightDijkstra.getFinishedVertecies()); 
		returnList.addAll(reverseDijkstra.getFinishedVertecies());
		return returnList;
	}

	@Override
	public HashSet<RouteableVertex> getVisitedVertecies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisitedVertecies(HashSet<RouteableVertex> visitedVertecies) {
		// TODO Auto-generated method stub
		
	}
}
