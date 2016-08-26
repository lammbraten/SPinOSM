package de.spinosm.graph.algorithm;

import java.util.Observable;
import java.util.PriorityQueue;
import java.util.TreeSet;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.pattern.IdComparator;

public class DepthFirstSearch extends Observable {

	private long maxDepth;
	private StreetGraph graph;
	private TreeSet<RouteableVertex> toVisitVertecie;
	private PriorityQueue<RouteableVertex>  vistitedVertecies;

	
	public DepthFirstSearch(StreetGraph g, long id, long maxDepth){
		this.graph = g;
		setMaxDepth(maxDepth);
		vistitedVertecies = new PriorityQueue<RouteableVertex>(new IdComparator());
		toVisitVertecie = new TreeSet<RouteableVertex>(new IdComparator());		
		RouteableVertex s = g.getVertex(id);
		mark(s);
	}
	
	public void searchDephtFirst(){
		long depth = 0;
		while(!toVisitVertecie.isEmpty() && maxDepth > depth){
			RouteableVertex u = toVisitVertecie.pollFirst();
			
			setChanged();
			notifyObservers(u);		
			
			if(!u.isEdgesLoaded())
				loadEdges(u);	
			
			vistitedVertecies.add(u);			
			mark(u);
			depth++;
			}
	}
	
	private void mark(RouteableVertex s){
		for(StreetEdge e : graph.edgesOf(s, 1)){
			RouteableVertex v = e.getOtherKnotThan(s);
			if(!vistitedVertecies.contains(v)){
				toVisitVertecie.add(v);
			}
		}
	}
	
	private void loadEdges(RouteableVertex u) {
		for(StreetEdge e : 	graph.edgesOf(u, 1))
			graph.addEdge(e,u);
	}
	
	private void setMaxDepth(long maxDepth) {
		if(maxDepth < 0)
			this.maxDepth = Long.MAX_VALUE;
		else
			this.maxDepth = maxDepth;
	}
}

