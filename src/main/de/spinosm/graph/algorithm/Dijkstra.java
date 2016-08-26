package de.spinosm.graph.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.pattern.DistanceComparator;
import de.spinosm.graph.pattern.IdComparator;

public class Dijkstra extends ObservableShortestPath{
	protected StreetGraph graph; 
	protected HashSet<RouteableVertex> visitedVertecies;
	protected PriorityQueue<RouteableVertex> toVisitVertecies;
	protected TreeMap<RouteableVertex, RouteableVertex> shortestPathMap;
	protected RouteableVertex startVertex;
	protected RouteableVertex endVertex;
	protected int direction;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this(streetgraph, StreetGraph.DEFAULT_DIRECTION);
	}
	
	public Dijkstra(StreetGraph streetgraph, int direction){
		this.graph = streetgraph;
		this.direction = direction;
		visitedVertecies = new HashSet<RouteableVertex>();
		toVisitVertecies = new PriorityQueue<RouteableVertex>(new DistanceComparator());		
		shortestPathMap = new TreeMap<RouteableVertex, RouteableVertex>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, HashSet<RouteableVertex> visitedVertecies, PriorityQueue<RouteableVertex> toVisitVertecies) {
		this.graph = streetgraph;
		this.visitedVertecies = visitedVertecies;
		this.toVisitVertecies = toVisitVertecies;
	}

	@Override
	public List<RouteableVertex> getShortestPath(RouteableVertex start, RouteableVertex end) {
		endVertex = end;		
		init(start);

		return iterateThrougGraph();
	}

	/**
	 * @return
	 */
	protected List<RouteableVertex> iterateThrougGraph() {
		while(!toVisitVertecies.isEmpty()){
			if(isEndVertexFound())
				return buildShortestPathTo(endVertex);
			checkNextVertex();
		}
		return null;
	}
	
	void checkNextVertex() {
		RouteableVertex u = toVisitVertecies.poll();
		visitedVertecies.add(u);
		setChanged();
		notifyObservers(u);		
		
		for(StreetEdge e : graph.edgesOf(u, direction)){
			RouteableVertex v = e.getOtherKnotThan(u);
			if(!visitedVertecies.contains(v)){
				try {				
					if(toVisitVertecies.contains(v)){					
						decraeseValueIfLower(u, v, e.getWeight());
					}else{
						insertNewValue(u, v, e.getWeight());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private boolean isEndVertexFound() {
		return toVisitVertecies.peek().getId() == endVertex.getId();
	}
	
	protected void decraeseValueIfLower(RouteableVertex u, RouteableVertex v, double weight) throws Exception {
		RouteableVertex alreadyFoundV = shortestPathMap.ceilingKey(v);
		if(!alreadyFoundV.equals(v) || alreadyFoundV == null || u == null)
			throw new Exception("Something went wrong");
		if(alreadyFoundV.getDistance() > (u.getDistance() + weight)){			
			alreadyFoundV.setDistance(u.getDistance() + weight);	
			if(shortestPathMap.put(alreadyFoundV, u) == null)
				throw new Exception("Something went wrong");
		}
	}

	protected void insertNewValue(RouteableVertex u, RouteableVertex v, double weight) throws Exception {
		v.setDistance(u.getDistance() + weight);						
		toVisitVertecies.add(v);
		if(shortestPathMap.put(v, u) != null)
			throw new Exception("Something went wrong");
	}

	protected void loadEdges(StreetVertex u) {
		for(StreetEdge e : 	graph.edgesOf(u, direction))
			graph.addEdge(e,u);
	}

	List<RouteableVertex> buildShortestPathTo(RouteableVertex endVertex2) {
		//writeToLogFile(shortestPathMap.descendingMap());
		RouteableVertex v = shortestPathMap.get(endVertex2);
		LinkedList<RouteableVertex> returnValue = new LinkedList<RouteableVertex>();	
		
		returnValue.add(v);
		while(v.getId() != startVertex.getId()){
			v = shortestPathMap.get(v);
			if(v == null)
				break;
			returnValue.add(v);
		}
		
		return returnValue;
	}

	void init(RouteableVertex start){
		startVertex = start;
		startVertex.setDistance(0);
		graph.edgesOf(startVertex, direction);
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
	public List<RouteableVertex> getBorderVertecies() {
		return  new ArrayList<RouteableVertex>(toVisitVertecies);
	}

	@Override
	public List<RouteableVertex> getFinishedVertecies() {
		return new ArrayList<RouteableVertex>(visitedVertecies);
	}
	
	@Override
	public HashSet<RouteableVertex> getVisitedVertecies() {
		return visitedVertecies;
	}

	@Override
	public void setVisitedVertecies(HashSet<RouteableVertex> visitedVertecies) {
		this.visitedVertecies = visitedVertecies;
	}

	
	public RouteableVertex getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(RouteableVertex startVertex) {
		this.startVertex = startVertex;
	}

	public RouteableVertex getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(RouteableVertex endVertex) {
		this.endVertex = endVertex;
	}
}
