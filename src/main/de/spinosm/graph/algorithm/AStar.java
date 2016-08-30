package de.spinosm.graph.algorithm;

import java.util.List;
import java.util.PriorityQueue;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.pattern.DistanceHeuristicComparator;
import de.spinosm.graph.weights.DefaultHeuristic;
import de.spinosm.graph.weights.Heuristic;

public class AStar extends Dijkstra {
	private static final long serialVersionUID = -8285289477827305700L;
	private Heuristic heuristic;	
	
	public AStar(StreetGraph streetgraph){
		this(streetgraph, null);
	}
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic){
		super(streetgraph);
		this.heuristic = heuristic;	
		toVisitVertecies = new PriorityQueue<RouteableVertex>(new DistanceHeuristicComparator());
	}
	
	@Override
	public List<RouteableVertex> getShortestPath(RouteableVertex start, RouteableVertex end) {
		endVertex = end;		
		this.init(start);

		return iterateThrougGraph();
	}

	void checkNextVertex() {
		RouteableVertex u = toVisitVertecies.poll();
		visitedVertecies.add(u);
		setChanged();
		notifyObservers(u);		
		
		for(StreetEdge e : graph.edgesOf(u, direction)){
			RouteableVertex v = e.getOtherVertexThan(u);
			if(!visitedVertecies.contains(v)){
				try {	
					if(toVisitVertecies.contains(v)){
						decraeseValueIfLower(u, v, e.getWeight());
					}else{
						v.setHeuristic(heuristicForVertex(v));
						insertNewValue(u, v, e.getWeight());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	void init(RouteableVertex start){
		if(heuristic == null)
			heuristic = new DefaultHeuristic(endVertex);	
		else
			heuristic.setReferenceVertex(endVertex);
		startVertex = start;
		startVertex.setDistance(0);
		startVertex.setHeuristic(heuristicForVertex(startVertex));
		graph.edgesOf(startVertex, StreetGraph.DEFAULT_DIRECTION);
		toVisitVertecies.add(graph.getVertex(startVertex.getId()));

	}


	private double heuristicForVertex(RouteableVertex startVertex) {			
		//return Common.asTheCrowFlies(endVertex.getPosition(), v.getPosition()) /(50 * 2) ; //(Angenommene Durchschnittsgeschwindigkeit * Gewichtung) 
		return this.heuristic.heuristicForVertex(startVertex);
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
