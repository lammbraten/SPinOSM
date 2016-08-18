package de.spinosm.graph.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.pattern.IdComparator;
import de.spinosm.graph.weights.DefaultHeuristic;
import de.spinosm.graph.weights.Heuristic;

public class AStar implements ShortestPath {
	private StreetGraph graph; 
	private TreeSet<StreetVertex> S;
	private TreeSet<StreetVertex> Q;
	private TreeMap<StreetVertex, StreetVertex> pi;
	private StreetVertex startVertex;
	private StreetVertex endVertex;	
	private Heuristic heuristic;	
	
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic){
		this.graph = streetgraph;
		this.heuristic = heuristic;
		S = new TreeSet<StreetVertex>(new IdComparator());
		Q = new TreeSet<StreetVertex>();		
		pi = new TreeMap<StreetVertex, StreetVertex>(new IdComparator());
	}
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic, TreeSet<StreetVertex> S, TreeSet<StreetVertex> Q) {
		this.heuristic = heuristic;
		this.graph = streetgraph;
		this.S = S;
		this.Q = Q;
	}

	@Override
	public List<StreetVertex> getShortestPath(StreetVertex start, StreetVertex end) {
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
		StreetVertex u = Q.first();
		System.out.println(u.getId() + ": " + u.getDistance());
		
		Q.remove(u);
		S.add(u);
		
		if(!u.isEdgesLoaded())
			for(StreetEdge e : 	graph.getEdgesForVertex(u, StreetGraph.DEFAULT_DIRECTION))
				graph.addEdge(e);				
		
		for(StreetEdge e : graph.getEdgesForVertex(u, StreetGraph.DEFAULT_DIRECTION)){
			StreetVertex v = e.getOtherKnotThan(u);
			
			if(!S.contains(v)){
				if(Q.contains(v)){
					//v = getVertexFrom(v, Q); Nicht nötig, da in StreetGraph schon richtig verlinkt wird.
						
					if(v.getDistance()  > (u.getDistance() + e.getWeight()  + heuristicForVertex(u))){
						Q.remove(v);
						v.setDistance(u.getDistance() + e.getWeight()   + heuristicForVertex(v));
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

	List<StreetVertex> buildShortestPathTo(StreetVertex endVertex) {
		StreetVertex v = pi.get(endVertex);
		LinkedList<StreetVertex> returnValue = new LinkedList<StreetVertex>();	
		returnValue.add(v);
		//System.out.println(pi);
		while(v.getId() != startVertex.getId()){
			//System.out.println(v.getId());
			v = pi.get(v);
			returnValue.add(v);
		}
		
		return returnValue;
	}

	
	void init(StreetVertex start){
		if(heuristic == null)
			heuristic = new DefaultHeuristic(endVertex);	
		else
			heuristic.setReferenceVertex(endVertex);
		startVertex = start;
		startVertex.setDistance(heuristicForVertex(startVertex));
		graph.getEdgesForVertex(startVertex, StreetGraph.DEFAULT_DIRECTION);
		Q.add(graph.getVertex(startVertex.getId()));

	}


	private double heuristicForVertex(StreetVertex v) {			
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

	@Override
	public List<StreetVertex> getBorderVertecies() {
		return  new ArrayList<StreetVertex>(Q);
	}

	@Override
	public List<StreetVertex> getFinishedVertecies() {
		return new ArrayList<StreetVertex>(S);
	}
}
