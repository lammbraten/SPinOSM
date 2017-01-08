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
import de.spinosm.graph.pattern.DistanceComparator;
import de.spinosm.graph.pattern.IdComparator;

public class Dijkstra extends ObservableShortestPath {
	private static final long serialVersionUID = 8742913011912127748L;
	protected StreetGraph graph; 
	protected HashSet<RouteableVertex> visitedVertices;
	protected PriorityQueue<RouteableVertex> toVisitVertices;
	protected RouteableVertex startVertex;
	protected RouteableVertex endVertex;
	protected int direction;
	private TreeMap<RouteableVertex, RouteableVertex> shortestPathMap;
	private LinkedList<RouteableVertex> calculatetdPath;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this(streetgraph, StreetGraph.DEFAULT_DIRECTION);
	}
	
	public Dijkstra(StreetGraph streetgraph, int direction){
		this.graph = streetgraph;
		this.direction = direction;
		visitedVertices = new HashSet<RouteableVertex>();
		toVisitVertices = new PriorityQueue<RouteableVertex>(new DistanceComparator());		
		shortestPathMap = new TreeMap<RouteableVertex, RouteableVertex>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, HashSet<RouteableVertex> visitedVertecies, PriorityQueue<RouteableVertex> toVisitVertecies) {
		this.graph = streetgraph;
		this.visitedVertices = visitedVertecies;
		this.toVisitVertices = toVisitVertecies;
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
	List<RouteableVertex> iterateThrougGraph() {
		while(!toVisitVertices.isEmpty()){
			if(isEndVertexFound())
				return buildShortestPathTo(endVertex);
			checkNextVertex();
		}
		return null;
	}
	
	RouteableVertex checkNextVertex() {
		RouteableVertex u = toVisitVertices.poll();
		visitedVertices.add(u);
		setChanged();
		notifyObservers(u);		
		
		for(StreetEdge e : graph.edgesOf(u, direction)){
			RouteableVertex v = e.getOtherVertexThan(u);
			if(!visitedVertices.contains(v)){
				try {				
					if(toVisitVertices.contains(v)){					
						decraeseValueIfLower(u, v, e.getWeight());
					}else{
						insertNewValue(u, v, e.getWeight());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return u;
	}
	
	void decraeseValueIfLower(RouteableVertex u, RouteableVertex v, double weight) throws Exception {
		RouteableVertex alreadyFoundV = shortestPathMap.ceilingKey(v);
		if(!alreadyFoundV.equals(v) || alreadyFoundV == null || u == null)
			throw new Exception("Something went wrong");
		if(alreadyFoundV.getDistance() > (u.getDistance() + weight)){			
			alreadyFoundV.setDistance(u.getDistance() + weight);	
			if(shortestPathMap.put(alreadyFoundV, u) == null)
				throw new Exception("Something went wrong");
		}
	}

	void insertNewValue(RouteableVertex u, RouteableVertex v, double weight) throws Exception {
		v.setDistance(u.getDistance() + weight);						
		toVisitVertices.add(v);
		if(shortestPathMap.put(v, u) != null)
			throw new Exception("Something went wrong");
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
		calculatetdPath = returnValue;
		return returnValue;
	}

	void init(RouteableVertex start){
		startVertex = start;
		startVertex.setDistance(0);
		graph.edgesOf(startVertex, direction);
		toVisitVertices.add(graph.getVertex(startVertex.getId()));
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
	public List<RouteableVertex> getBorderVertices() {
		return  new ArrayList<RouteableVertex>(toVisitVertices);
	}

	@Override
	public List<RouteableVertex> getFinishedVertices() {
		return new ArrayList<RouteableVertex>(visitedVertices);
	}
	
	@Override
	public HashSet<RouteableVertex> getVisitedVertices() {
		return visitedVertices;
	}

	@Override
	public void setVisitedVertices(HashSet<RouteableVertex> visitedVertices) {
		this.visitedVertices = visitedVertices;
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

	@Override
	public List<RouteableVertex> getCalculatedShortestPath() {
		return calculatetdPath;
	}

	private boolean isEndVertexFound() {
		return toVisitVertices.peek().getId() == endVertex.getId();
	}
}
