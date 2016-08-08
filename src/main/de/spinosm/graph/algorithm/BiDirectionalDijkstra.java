package de.spinosm.graph.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;

public class BiDirectionalDijkstra implements ShortestPath{
	private StreetGraph graph; 
	private Dijkstra d1;
	private Dijkstra d2;
	private static StreetJunction startVertex;
	private static StreetJunction endVertex;	
	
	
	public BiDirectionalDijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		
		this.d1 = new Dijkstra(graph);
		this.d2 = new Dijkstra(graph);
		
	}

	@Override
	public List<StreetJunction> getShortestPath(StreetJunction start, StreetJunction end) {
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

	private List<StreetJunction> buildShortestPath() {
		StreetJunction jointValue = intersectionOf(d1.getS(),d2.getS()).first();
		List<StreetJunction> shortestSub1PathReverse = d1.buildShortestPathTo(jointValue);	
		List<StreetJunction> shortestSub2Path = d2.buildShortestPathTo(jointValue);
	    ListIterator<StreetJunction> listIterator = shortestSub1PathReverse.listIterator();
		List<StreetJunction>  shortestPath = new LinkedList<StreetJunction>();
		

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

	private TreeSet<StreetJunction> intersectionOf(TreeSet<StreetJunction> treeSet, TreeSet<StreetJunction> treeSet2){
		TreeSet<StreetJunction> intersection = new TreeSet<StreetJunction>(treeSet);
		intersection.retainAll(treeSet2);
		return intersection;
	}
}
