package de.spinosm.graph.algorithm;

import java.util.Observable;
import java.util.PriorityQueue;
import java.util.TreeSet;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.pattern.IdComparator;

public class DepthFirstSearch extends Observable {

	private long maxDepth;
	private StreetGraph graph;
	private TreeSet<StreetVertex> toVisitVertecie;
	private PriorityQueue<StreetVertex>  vistitedVertecies;

	
	public DepthFirstSearch(StreetGraph g, long id, long maxDepth){
		this.graph = g;
		setMaxDepth(maxDepth);
		vistitedVertecies = new PriorityQueue<StreetVertex>(new IdComparator());
		toVisitVertecie = new TreeSet<StreetVertex>(new IdComparator());		
		StreetVertex s = g.getVertex(id);
		mark(s);
	}
	
	public void searchDephtFirst(){
		long depth = 0;
		while(!toVisitVertecie.isEmpty() && maxDepth > depth){
			StreetVertex u = toVisitVertecie.pollFirst();
			
			setChanged();
			notifyObservers(u);		
			
			if(!u.isEdgesLoaded())
				loadEdges(u);	
			
			vistitedVertecies.add(u);			
			mark(u);
			depth++;
			}
	}
	
	private void mark(StreetVertex u){
		for(StreetEdge e : graph.getEdgesForVertex(u, 1)){
			StreetVertex v = e.getOtherKnotThan(u);
			if(!vistitedVertecies.contains(v)){
				toVisitVertecie.add(v);
			}
		}
	}
	
	private void loadEdges(StreetVertex u) {
		for(StreetEdge e : 	graph.getEdgesForVertex(u, 1))
			graph.addEdge(e);
	}
	
	private void setMaxDepth(long maxDepth) {
		if(maxDepth < 0)
			this.maxDepth = Long.MAX_VALUE;
		else
			this.maxDepth = maxDepth;
	}
}

