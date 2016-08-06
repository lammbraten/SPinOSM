package de.spinosm.graph.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.pattern.IdComparator;

public class AStar implements ShortestPath {
	private StreetGraph graph; 
	private TreeSet<RouteableNode> S;
	private TreeSet<RouteableNode> Q;
	private TreeMap<RouteableNode, RouteableNode> pi;
	private RouteableNode startVertex;
	private RouteableNode endVertex;	
	
	public AStar(StreetGraph streetgraph){
		this.graph = streetgraph;
		S = new TreeSet<RouteableNode>(new IdComparator());
		Q = new TreeSet<RouteableNode>();		
		pi = new TreeMap<RouteableNode, RouteableNode>(new IdComparator());
	}
	
	public AStar(StreetGraph streetgraph, TreeSet<RouteableNode> S, TreeSet<RouteableNode> Q) {
		this.graph = streetgraph;
		this.S = S;
		this.Q = Q;
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		endVertex = end;		
		init(start);

			
		while(!Q.isEmpty()){
			if(Q.first().getId() == endVertex.getId())
				return buildShortestPathTo(endVertex);
			checkNextVertex();
		}
		return null;
	}

	/**
	 * @param pi
	 * @param u
	 */
	void checkNextVertex() {
		RouteableNode u = Q.first();
		System.out.println(u.getId() + ": " + u.getDistance());
		
		Q.remove(u);
		S.add(u);
		
		if(!u.isEdgesLoaded()){
			RouteableNode loaded = graph.getNode(u.getId());
			if(loaded != null)
				u.setEdges(loaded.getEdges());
		}
		
		for(RouteableEdge e : u.getEdges()){
			RouteableNode v = e.getOtherKnotThan(u);
			
			if(!S.contains(v)){
				if(Q.contains(v)){
					//v = getVertexFrom(v, Q); Nicht nötig, da in StreetGraph schon richtig verlinkt wird.
						
					if(v.getDistance()  > (u.getDistance() + e.getWeight() + heuristicForVertex(v))){
						Q.remove(v);
						v.setDistance(u.getDistance() + e.getWeight()  + heuristicForVertex(v));
						Q.add(v);
						pi.put(v, u);
						System.out.println("--" + v.getId() + " now: " + v.getDistance());

					}
				}else{
					v.setDistance(u.getDistance() + e.getWeight() + heuristicForVertex(v));						
					Q.add(v);
					pi.put(v, u);
					System.out.println("-" + v.getId() + " yet: " + v.getDistance() );
				}
			}
		}
	}

	List<RouteableNode> buildShortestPathTo(RouteableNode endVertex) {
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

	
	void init(RouteableNode start){
		startVertex = start;
		startVertex.setDistance(heuristicForVertex(startVertex));
		Q.add(graph.getNode(startVertex.getId()));
	}


	private double heuristicForVertex(RouteableNode v) {			
		return Common.asTheCrowFlies(endVertex.getPosition(), v.getPosition()) / (75 *5) ; //(Angenommene Durchschnittsgeschwindigkeit * Gewichtung) 
	}

	@Override
	public StreetGraph getGraph() {
		return this.graph;
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
		
	}
}
