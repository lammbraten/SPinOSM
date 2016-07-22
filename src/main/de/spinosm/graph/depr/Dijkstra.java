package de.spinosm.graph.depr;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

public class Dijkstra<V, E> implements ShortestPath{

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		// TODO Auto-generated method stub
		return null;
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

/*	private Graph graph; 
	
	private TreeSet<RouteableNode> calculatedNodes;
	private TreeSet<RouteableNode> openNodes;
	
	public Dijkstra(Graph<V, E> g) {
		this.graph = g;
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		TreeSet<RouteableNode> S = new TreeSet<RouteableNode>();
		TreeSet<RouteableNode> Q = new TreeSet<RouteableNode>();
		Q.add(graph..getNode(start.getId()));
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
						if(v.getDistance()  > (u.getDistance() + e.getCost())){
							v.setDistance(u.getDistance() + e.getCost());
							pi.set(pi.indexOf(v), u);
						}
					}else{
						Q.add(v);
						v.setDistance(u.getDistance() + e.getCost());
						pi.set(pi.indexOf(v), u);
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
	
	*/


}
