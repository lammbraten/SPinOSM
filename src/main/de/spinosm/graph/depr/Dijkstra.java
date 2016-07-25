package de.spinosm.graph.depr;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.pattern.DistanceComparator;

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
		LinkedList<RouteableNode> S = new LinkedList<RouteableNode>();
		TreeSet<RouteableNode> Q = new TreeSet<RouteableNode>(/*new DistanceComparator()*/);
		Q.add(graph.getNode(start.getId()));
		Q.first().setDistance(0);
		
		LinkedList<RouteableNode> pi = new LinkedList<RouteableNode>();
		
		while(!Q.isEmpty()){
			
			//RouteableNode u = Q.pollLast();
			RouteableNode u = Q.first();
			
			System.out.println(u.getId() + ": " + u.getDistance());
			
			Q.remove(u);
			S.add(u);
			
			if(!u.isEdgesLoaded())
				u.setEdges(graph.getNode(u.getId()).getEdges());
			
			for(RouteableEdge e : u.getEdges()){
				RouteableNode v = e.getOtherKnotThan(u);
				
				//System.out.println(v.getId() +" : " + end.getId());
				if(v.getId() == end.getId())
					return pi;
				
				
				if(!S.contains(v)){
					if(Q.contains(v)){
						//v = getVertexFrom(v, Q); Nicht nörig, dain StreetGraph schon richtig verlinkt wird.
							
						if(v.getDistance()  > (u.getDistance() + e.getWeight())){
							Q.remove(v);
							v.setDistance(u.getDistance() + e.getWeight());
							Q.add(v);
							int vI = pi.indexOf(v);
							if(vI != -1)
								pi.set(vI, u);
							else
								pi.add(u);
						}
					}else{
						v.setDistance(u.getDistance() + e.getWeight());						
						Q.add(v);
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

	private RouteableNode getVertexFrom(RouteableNode ref, TreeSet<RouteableNode> q) {
		for(RouteableNode vertex : q)
			if(ref.equals(vertex))
				return vertex;				
		return null;
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
