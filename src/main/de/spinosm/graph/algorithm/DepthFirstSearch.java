package de.spinosm.graph.algorithm;

import java.util.PriorityQueue;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;

public class DepthFirstSearch {

	private long maxDepth;
	private StreetGraph g;
	private PriorityQueue<StreetJunction> Q;

	
	public DepthFirstSearch(StreetGraph g, long id, long maxDepth){
		this.g = g;
		int depth = 0;
		setMaxDepth(maxDepth);
		Q = new PriorityQueue<StreetJunction>();		
		StreetJunction s = g.getNode(id);
		searchDepthFirst(s, depth);
		
		Q.add(s);
	}


	/**
	 * Rekursiv
	 * @param u
	 * @param depth
	 */
	private void searchDepthFirst(StreetJunction u, int depth){
		u = mark(u);
		
		if(maxDepth <= depth)
			return;
		
		for(StreetEdge e : g.getEdgesForNode(u))
			if(!e.getEnd().isEdgesLoaded())
				searchDepthFirst(e.getEnd(), depth+1);
	}
	
	private void searchDephtFirst(){
		while(!Q.isEmpty()){
			if(Q.peek().getId() == endVertex.getId())
				return buildShortestPathTo(endVertex);
			checkNextVertex();
		}
	}
	
	private StreetJunction mark(StreetJunction u){
		//EdgesLoaded is like isVisible
		if(!u.isEdgesLoaded())
			for(StreetEdge e : 	g.getEdgesForNode(u))
				g.addEdge(e);				
		return u;
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

