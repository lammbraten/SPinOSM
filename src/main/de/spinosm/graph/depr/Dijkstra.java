package de.spinosm.graph.depr;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;

import org.jgrapht.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

public class Dijkstra<V, E> implements ShortestPath{
	private StreetGraph graph; 
	
	private TreeSet<RouteableNode> calculatedNodes;
	private TreeSet<RouteableNode> openNodes;
	
	public Dijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		TreeSet<RouteableNode> S = new TreeSet<RouteableNode>();
		TreeSet<RouteableNode> Q = new TreeSet<RouteableNode>();
		Q.add(graph.getNode(start.getId()));
		Q.first().setDistance(0);
		
		LinkedList<RouteableNode> pi = new LinkedList<RouteableNode>();
		
		while(!Q.isEmpty()){
			RouteableNode u = Q.first();
			Q.remove(u);
			S.add(u);
			
			if(!u.isEdgesLoaded())
				u.setEdges(graph.getNode(u.getId()).getEdges());
			
			for(RouteableEdge e : u.getEdges()){
				RouteableNode v = e.getOtherKnotThan(u);
				if(!S.contains(v)){
					if(Q.contains(v)){
						if(v.getDistance()  > (u.getDistance() + e.getWeight())){
							v.setDistance(u.getDistance() + + e.getWeight());
							int vI = pi.indexOf(v);
							if(vI != -1)
								pi.set(vI, u);
							else
								pi.add(u);
						}
					}else{
						Q.add(v);
						v.setDistance(u.getDistance() + + e.getWeight());
						int vI = pi.indexOf(v);
						if(vI != -1)
							pi.set(vI, u);
						else
							pi.add(u);
					}
				}
			}
		}
		return pi;
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
		
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}
	



}
