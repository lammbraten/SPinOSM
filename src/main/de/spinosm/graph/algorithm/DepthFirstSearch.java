package de.spinosm.graph.algorithm;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;

public class DepthFirstSearch {

	private long maxDepth;
	private StreetGraph g;
	
	public DepthFirstSearch(StreetGraph g, long id, long maxDepth){
		this.g = g;
		int depth = 0;
		setMaxDepth(maxDepth);
	
		RouteableNode s = g.getNode(id);
		searchDepthFirst(s, depth);
	}


	private void searchDepthFirst(RouteableNode u, int depth){
		u = mark(u);
		
		if(maxDepth <= depth)
			return;
		
		for(RouteableEdge e : u.getEdges())
			if(!e.getEnd().isEdgesLoaded())
				searchDepthFirst(e.getEnd(), depth+1);
	}
	
	private RouteableNode mark(RouteableNode u){
		//EdgesLoaded is like is Visible
		if(!u.isEdgesLoaded()){ 
			RouteableNode loaded = g.getNode(u.getId());
			if(loaded != null)
				u.setEdges(loaded.getEdges());
		}
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

