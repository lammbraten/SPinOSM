package de.spinosm.graph.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.pattern.DistanceComparator;
import de.spinosm.graph.pattern.IdComparator;

public class Dijkstra extends ObservableShortestPath{
	private StreetGraph graph; 
	private TreeSet<StreetVertex> visitedVertecies;
	private PriorityQueue<StreetVertex> toVisitVertecies;
	private TreeMap<StreetVertex, StreetVertex> shortestPathMap;
	private StreetVertex startVertex;
	private StreetVertex endVertex;
	private int direction;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this(streetgraph, StreetGraph.DEFAULT_DIRECTION);
	}
	
	public Dijkstra(StreetGraph streetgraph, int direction){
		this.graph = streetgraph;
		this.direction = direction;
		visitedVertecies = new TreeSet<StreetVertex>(new IdComparator());
		toVisitVertecies = new PriorityQueue<StreetVertex>(new DistanceComparator());		
		shortestPathMap = new TreeMap<StreetVertex, StreetVertex>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, TreeSet<StreetVertex> visitedVertecies, PriorityQueue<StreetVertex> toVisitVertecies) {
		this.graph = streetgraph;
		this.visitedVertecies = visitedVertecies;
		this.toVisitVertecies = toVisitVertecies;
	}

	@Override
	public List<StreetVertex> getShortestPath(StreetVertex start, StreetVertex end) {
		init(start);
		endVertex = end;
			
		while(!toVisitVertecies.isEmpty()){
			if(isEndVertexFound())
				return buildShortestPathTo(endVertex);
			checkNextVertex();
		}
		return null;
	}
	
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
					if(v.getDistance() > (u.getDistance() + e.getWeight()))
						decraeseValue(u, e, v);
				}else{
					insertNewValue(u, e, v);
				}
			}
		}
	}
	
	private boolean isEndVertexFound() {
		return toVisitVertecies.peek().getId() == endVertex.getId();
	}
	
	private void decraeseValue(StreetVertex u, StreetEdge e, StreetVertex v) {
		toVisitVertecies.remove(v);
		insertNewValue(u, e, v);
	}

	private void insertNewValue(StreetVertex u, StreetEdge e, StreetVertex v) {
		v.setDistance(u.getDistance() + e.getWeight());						
		toVisitVertecies.offer(v);
		shortestPathMap.put(v, u);
	}

	private void loadEdges(StreetVertex u) {
		for(StreetEdge e : 	graph.getEdgesForVertex(u, direction))
			graph.addEdge(e);
	}

	List<StreetVertex> buildShortestPathTo(StreetVertex endVertex) {
		StreetVertex v = shortestPathMap.get(endVertex);
		LinkedList<StreetVertex> returnValue = new LinkedList<StreetVertex>();	
		returnValue.add(v);
		while(v.getId() != startVertex.getId()){
			v = shortestPathMap.get(v);
			returnValue.add(v);
		}
		
		return returnValue;
	}

	void init(StreetVertex start){
		startVertex = start;
		startVertex.setDistance(0);
		graph.getEdgesForVertex(startVertex, direction);
		toVisitVertecies.add(graph.getVertex(startVertex.getId()));
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
		
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}
	
	@Override
	public List<StreetVertex> getBorderVertecies() {
		return  new ArrayList<StreetVertex>(toVisitVertecies);
	}

	@Override
	public List<StreetVertex> getFinishedVertecies() {
		return new ArrayList<StreetVertex>(visitedVertecies);
	}
	
	public StreetVertex getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(StreetVertex startVertex) {
		this.startVertex = startVertex;
	}

	public StreetVertex getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(StreetVertex endVertex) {
		this.endVertex = endVertex;
	}
}
