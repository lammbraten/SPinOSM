package de.spinosm.graph.algorithm;

import java.util.Observable;
import java.util.PriorityQueue;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.pattern.HashComparator;
import de.spinosm.graph.pattern.IdComparator;

public class DepthFirstSearch extends Observable {

	private long maxDepth;
	private StreetGraph graph;
	private TreeSet<StreetJunction> Q;
	private PriorityQueue<StreetJunction>  S;

	
	public DepthFirstSearch(StreetGraph g, long id, long maxDepth){
		this.graph = g;
		int depth = 0;
		setMaxDepth(maxDepth);
		S = new PriorityQueue<StreetJunction>(new IdComparator());
		Q = new TreeSet<StreetJunction>(new IdComparator());		
		StreetJunction s = g.getNode(id);
		mark(s);
		searchDephtFirst();
		
		//Q.add(s);
	}


	/**
	 * Rekursiv
	 * @param u
	 * @param depth
	 */
	/*private void searchDepthFirst(StreetJunction u, int depth){
		u = mark(u);
		
		if(maxDepth <= depth)
			return;
		
		for(StreetEdge e : g.getEdgesForNode(u))
			if(!e.getEnd().isEdgesLoaded())
				searchDepthFirst(e.getEnd(), depth+1);
	}*/
	
	private void searchDephtFirst(){
		long depth = 0;
		while(!Q.isEmpty() && maxDepth > depth){
			StreetJunction u = Q.pollFirst();
			
			setChanged();
			notifyObservers(u);		
			
			if(!u.isEdgesLoaded())
				loadEdges(u);	
			
			S.add(u);			
			mark(u);
			depth++;
			}
	}
	
	private void mark(StreetJunction u){
		//EdgesLoaded is like isVisible
		for(StreetEdge e : graph.getEdgesForNode(u, 1)){
			StreetJunction v = e.getOtherKnotThan(u);
			if(!S.contains(v)){
				Q.add(v);
			}
		}
	}
	
	
	private void loadEdges(StreetJunction u) {
		for(StreetEdge e : 	graph.getEdgesForNode(u, 1))
			graph.addEdge(e);
	}
	
	/**
	 * @param maxDepth
	 */
	private void setMaxDepth(long maxDepth) {
		if(maxDepth < 0)
			this.maxDepth = Long.MAX_VALUE;
		else
			this.maxDepth = maxDepth;
	}
}

