package de.spinosm.graph.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.pattern.IdComparator;

public class Dijkstra extends ObservableShortestPath{
	private StreetGraph graph; 
	private TreeSet<StreetJunction> visitedVertecies;
	private PriorityQueue<StreetJunction> toVisitVertecies;
	private TreeMap<StreetJunction, StreetJunction> shortestPathMap;
	private StreetJunction startVertex;
	private StreetJunction endVertex;
	private int direction;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this(streetgraph, StreetGraph.DEFAULT_DIRECTION);
	}
	
	public Dijkstra(StreetGraph streetgraph, int direction){
		this.graph = streetgraph;
		this.direction = direction;
		visitedVertecies = new TreeSet<StreetJunction>(new IdComparator());
		toVisitVertecies = new PriorityQueue<StreetJunction>();		
		shortestPathMap = new TreeMap<StreetJunction, StreetJunction>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, TreeSet<StreetJunction> visitedVertecies, PriorityQueue<StreetJunction> toVisitVertecies) {
		this.graph = streetgraph;
		this.visitedVertecies = visitedVertecies;
		this.toVisitVertecies = toVisitVertecies;
	}

	@Override
	public List<StreetJunction> getShortestPath(StreetJunction start, StreetJunction end) {
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
		StreetJunction u = toVisitVertecies.poll();
		visitedVertecies.add(u);
		setChanged();
		notifyObservers(u);		
		
		if(!u.isEdgesLoaded())
			loadEdges(u);				
		
		for(StreetEdge e : graph.getEdgesForNode(u, direction)){
			StreetJunction v = e.getOtherKnotThan(u);
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
	
	private void decraeseValue(StreetJunction u, StreetEdge e, StreetJunction v) {
		toVisitVertecies.remove(v);
		insertNewValue(u, e, v);
	}

	private void insertNewValue(StreetJunction u, StreetEdge e, StreetJunction v) {
		v.setDistance(u.getDistance() + e.getWeight());						
		toVisitVertecies.add(v);
		shortestPathMap.put(v, u);
	}

	private void loadEdges(StreetJunction u) {
		for(StreetEdge e : 	graph.getEdgesForNode(u, direction))
			graph.addEdge(e);
	}

	List<StreetJunction> buildShortestPathTo(StreetJunction endVertex) {
		StreetJunction v = shortestPathMap.get(endVertex);
		LinkedList<StreetJunction> returnValue = new LinkedList<StreetJunction>();	
		returnValue.add(v);
		while(v.getId() != startVertex.getId()){
			v = shortestPathMap.get(v);
			returnValue.add(v);
		}
		
		return returnValue;
	}

	void init(StreetJunction start){
		startVertex = start;
		startVertex.setDistance(0);
		graph.getEdgesForNode(startVertex, direction);
		toVisitVertecies.add(graph.getNode(startVertex.getId()));
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
		
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}

	public TreeSet<StreetJunction> getS() {
		return visitedVertecies;
	}

	public void setS(TreeSet<StreetJunction> s) {
		visitedVertecies = s;
	}

	public PriorityQueue<StreetJunction> getQ() {
		return toVisitVertecies;
	}

	public void setQ(PriorityQueue<StreetJunction> q) {
		toVisitVertecies = q;
	}

	public StreetJunction getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(StreetJunction startVertex) {
		this.startVertex = startVertex;
	}

	public StreetJunction getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(StreetJunction endVertex) {
		this.endVertex = endVertex;
	}
	



}
