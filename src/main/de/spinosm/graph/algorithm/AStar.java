package de.spinosm.graph.algorithm;

import java.util.List;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.weights.DefaultHeuristic;
import de.spinosm.graph.weights.Heuristic;

public class AStar extends Dijkstra {
	private Heuristic heuristic;	
	
	public AStar(StreetGraph streetgraph){
		this(streetgraph, null);
	}
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic){
		super(streetgraph);
		this.heuristic = heuristic;	
	}
	
	@Override
	public List<StreetVertex> getShortestPath(StreetVertex start, StreetVertex end) {
		endVertex = end;		
		init(start);

		return iterateThrougGraph();
	}

	/**
	 * @param pi
	 * @param u
	 */
	void checkNextVertex() {
		StreetVertex u = toVisitVertecies.poll();
		visitedVertecies.add(u);
		setChanged();
		notifyObservers(u);		
		
		if(!u.isEdgesLoaded())
			loadEdges(u);				
		
		for(StreetEdge e : graph.getEdgesForVertex(u, direction)){
			StreetVertex v = e.getOtherKnotThan(u);
			if(!visitedVertecies.contains(v)){
				if(toVisitVertecies.contains(v)){					
					if(v.getDistance() > (u.getDistance() + e.getWeight() ))
						decraeseValue(u, v, e.getWeight() + heuristicForVertex(v));
				}else{
					insertNewValue(u, v, e.getWeight() + heuristicForVertex(v));
				}
			}
		}
	}


	
	void init(StreetVertex start){
		if(heuristic == null)
			heuristic = new DefaultHeuristic(endVertex);	
		else
			heuristic.setReferenceVertex(endVertex);
		startVertex = start;
		startVertex.setDistance(heuristicForVertex(startVertex));
		graph.getEdgesForVertex(startVertex, StreetGraph.DEFAULT_DIRECTION);
		toVisitVertecies.add(graph.getVertex(startVertex.getId()));

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
}
