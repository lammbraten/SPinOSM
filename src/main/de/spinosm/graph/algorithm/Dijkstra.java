package de.spinosm.graph.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.depr.ShortestPath;
import de.spinosm.graph.pattern.DistanceComparator;
import de.spinosm.graph.pattern.IdComparator;

import org.jgrapht.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

public class Dijkstra<V, E> implements ShortestPath{
	private StreetGraph graph; 
	
	private TreeSet<RouteableNode> calculatedNodes;
	private TreeSet<RouteableNode> openNodes;


	private static RouteableNode startVertex;
	private static RouteableNode endVertex;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		startVertex = start;
		endVertex = end;
		LinkedList<RouteableNode> S = new LinkedList<RouteableNode>();
		TreeSet<RouteableNode> Q = new TreeSet<RouteableNode>(/*new DistanceComparator()*/);
		Q.add(graph.getNode(startVertex.getId()));
		Q.first().setDistance(0);
		
		TreeMap<RouteableNode, RouteableNode> pi = new TreeMap<RouteableNode, RouteableNode>(new IdComparator());
		
		while(!Q.isEmpty()){
			RouteableNode u = Q.first();
			
			System.out.println(u.getId() + ": " + u.getDistance());
			
			Q.remove(u);
			S.add(u);
			
			if(!u.isEdgesLoaded())
				u.setEdges(graph.getNode(u.getId()).getEdges());
			
			for(RouteableEdge e : u.getEdges()){
				RouteableNode v = e.getOtherKnotThan(u);
				
				//System.out.println(v.getId() +" : " + end.getId());
				if(v.getId() == end.getId()){
					pi.put(v, u);
					return buildLinkedList(pi);
				}
				
				
				if(!S.contains(v)){
					if(Q.contains(v)){
						//v = getVertexFrom(v, Q); Nicht nörig, da in StreetGraph schon richtig verlinkt wird.
							
						if(v.getDistance()  > (u.getDistance() + e.getWeight())){
							Q.remove(v);
							v.setDistance(u.getDistance() + e.getWeight());
							Q.add(v);
							pi.put(v, u);

						}
					}else{
						v.setDistance(u.getDistance() + e.getWeight());						
						Q.add(v);
						pi.put(v, u);
					}
				}
			}
		}
		return null;
	}

	private List<RouteableNode> buildLinkedList(TreeMap<RouteableNode, RouteableNode> pi) {
		RouteableNode v = pi.get(endVertex);
		LinkedList<RouteableNode> returnValue = new LinkedList<RouteableNode>();	
		returnValue.add(v);
		//System.out.println(pi);
		while(v.getId() != startVertex.getId()){
			//System.out.println(v.getId());
			v = pi.get(v);
			returnValue.add(v);
		}
		
		return returnValue;
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
