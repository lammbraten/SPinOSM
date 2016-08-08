package de.spinosm.graph.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.pattern.IdComparator;

public class AStar implements ShortestPath {
	private StreetGraph graph; 
	private TreeSet<StreetJunction> S;
	private TreeSet<StreetJunction> Q;
	private TreeMap<StreetJunction, StreetJunction> pi;
	private StreetJunction startVertex;
	private StreetJunction endVertex;	
	private Heuristic heuristic;	
	
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic){
		this.graph = streetgraph;
		this.heuristic = heuristic;
		S = new TreeSet<StreetJunction>(new IdComparator());
		Q = new TreeSet<StreetJunction>();		
		pi = new TreeMap<StreetJunction, StreetJunction>(new IdComparator());
	}
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic, TreeSet<StreetJunction> S, TreeSet<StreetJunction> Q) {
		this.heuristic = heuristic;
		this.graph = streetgraph;
		this.S = S;
		this.Q = Q;
	}

	@Override
	public List<StreetJunction> getShortestPath(StreetJunction start, StreetJunction end) {
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
		StreetJunction u = Q.first();
		System.out.println(u.getId() + ": " + u.getDistance());
		
		Q.remove(u);
		S.add(u);
		
		if(!u.isEdgesLoaded())
			for(StreetEdge e : 	graph.getEdgesForNode(u))
				graph.addEdge(e);				
		
		for(StreetEdge e : graph.getEdgesForNode(u)){
			StreetJunction v = e.getOtherKnotThan(u);
			
			if(!S.contains(v)){
				if(Q.contains(v)){
					//v = getVertexFrom(v, Q); Nicht nötig, da in StreetGraph schon richtig verlinkt wird.
						
					if(v.getDistance()  > (u.getDistance() + e.getWeight() /*+ heuristicForVertex(v)*/)){
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

	List<StreetJunction> buildShortestPathTo(StreetJunction endVertex) {
		StreetJunction v = pi.get(endVertex);
		LinkedList<StreetJunction> returnValue = new LinkedList<StreetJunction>();	
		returnValue.add(v);
		//System.out.println(pi);
		while(v.getId() != startVertex.getId()){
			//System.out.println(v.getId());
			v = pi.get(v);
			returnValue.add(v);
		}
		
		return returnValue;
	}

	
	void init(StreetJunction start){
		if(heuristic == null)
			heuristic = new DefaultHeuristic(endVertex);	
		else
			heuristic.setReferenceVertex(endVertex);
		startVertex = start;
		startVertex.setDistance(heuristicForVertex(startVertex));
		graph.getEdgesForNode(startVertex);
		Q.add(graph.getNode(startVertex.getId()));

	}


	private double heuristicForVertex(StreetJunction v) {			
		//return Common.asTheCrowFlies(endVertex.getPosition(), v.getPosition()) /(50 * 2) ; //(Angenommene Durchschnittsgeschwindigkeit * Gewichtung) 
		return this.heuristic.heuristicForVertex(v);
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
