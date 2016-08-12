package de.spinosm.graph.algorithm;

import java.util.PriorityQueue;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.pattern.HashComparator;
import de.spinosm.graph.pattern.IdComparator;

public class DepthFirstSearch {

	private long maxDepth;
	private StreetGraph g;
	private TreeSet<StreetJunction> Q;
	private TreeSet<StreetJunction> S;

	
	public DepthFirstSearch(StreetGraph g, long id, long maxDepth){
		this.g = g;
		int depth = 0;
		setMaxDepth(maxDepth);
		S = new TreeSet<StreetJunction>(new HashComparator());
		Q = new TreeSet<StreetJunction>(new HashComparator());		
		StreetJunction s = g.getNode(id);
		mark(s);
		searchDephtFirst();
		
		Q.add(s);
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
			StreetJunction sj = Q.pollFirst();
			S.add(sj);			
			mark(sj);
			depth++;
			System.out.println(sj);

		}
	}
	
	private StreetJunction mark(StreetJunction u){
		//EdgesLoaded is like isVisible
		if(!u.isEdgesLoaded())
			for(StreetEdge e : 	g.getEdgesForNode(u)){
				g.addEdge(e);		
				StreetJunction v = e.getEnd();
				if(!S.contains(v));
					Q.add(v);
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

