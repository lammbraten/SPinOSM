package de.spinosm.graph.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.depr.ShortestPath;
import de.spinosm.graph.pattern.IdComparator;

public class BiDirectionalDijkstra implements ShortestPath{
	private StreetGraph graph; 
	private Dijkstra d1;
	private Dijkstra d2;
	private static RouteableNode startVertex;
	private static RouteableNode endVertex;	
	
	
	public BiDirectionalDijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		
		this.d1 = new Dijkstra(graph);
		this.d2 = new Dijkstra(graph);
		
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		startVertex = start;
		endVertex = end;
		
		d1.init(startVertex);
		d2.init(endVertex);
		
		while(intersectionOf(d1.getS(),d2.getS()).isEmpty() && (!d1.getQ().isEmpty() || !d2.getQ().isEmpty())){
			d1.checkNextVertex();
			d2.checkNextVertex();
		}
			
		return buildShortestPath();
	}

	private List<RouteableNode> buildShortestPath() {
		RouteableNode jointValue = intersectionOf(d1.getS(),d2.getS()).first();
		List<RouteableNode> shortestSub1PathReverse = d1.buildShortestPathTo(jointValue);	
		List<RouteableNode> shortestSub2Path = d2.buildShortestPathTo(jointValue);
	    ListIterator<RouteableNode> listIterator = shortestSub1PathReverse.listIterator();
		List<RouteableNode>  shortestPath = new LinkedList<RouteableNode>();
		

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGraph(StreetGraph g) {
		// TODO Auto-generated method stub
		
	}

	private TreeSet<RouteableNode> intersectionOf(TreeSet<RouteableNode> treeSet, TreeSet<RouteableNode> treeSet2){
		TreeSet<RouteableNode> intersection = new TreeSet<RouteableNode>(treeSet);
		intersection.retainAll(treeSet2);
		return intersection;
	}
}
