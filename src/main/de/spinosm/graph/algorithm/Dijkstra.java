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
	protected StreetGraph graph; 
	protected TreeSet<StreetVertex> visitedVertecies;
	protected PriorityQueue<StreetVertex> toVisitVertecies;
	protected TreeMap<StreetVertex, StreetVertex> shortestPathMap;
	protected StreetVertex startVertex;
	protected StreetVertex endVertex;
	protected int direction;	
	
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
		endVertex = end;		
		init(start);

			
		return iterateThrougGraph();
	}

	/**
	 * @return
	 */
	protected List<StreetVertex> iterateThrougGraph() {
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
						decraeseValue(u, v, e.getWeight());
				}else{
					insertNewValue(u, v, e.getWeight());
				}
			}
		}
	}
	
	private boolean isEndVertexFound() {
		return toVisitVertecies.peek().getId() == endVertex.getId();
	}
	
	protected void decraeseValue(StreetVertex u, StreetVertex v, double weight) {
		toVisitVertecies.remove(v);
		insertNewValue(u, v, weight);
	}

	protected void insertNewValue(StreetVertex u, StreetVertex v, double weight) {
		v.setDistance(u.getDistance() + weight);						
		toVisitVertecies.offer(v);
		shortestPathMap.put(v, u);
	}

	protected void loadEdges(StreetVertex u) {
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
