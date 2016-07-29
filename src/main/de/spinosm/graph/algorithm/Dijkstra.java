package de.spinosm.graph.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.pattern.IdComparator;

public class Dijkstra implements ShortestPath{
	private StreetGraph graph; 
	private TreeSet<RouteableNode> S;
	private PriorityQueue<RouteableNode> Q;
	private TreeMap<RouteableNode, RouteableNode> pi;
	private RouteableNode startVertex;
	private RouteableNode endVertex;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		S = new TreeSet<RouteableNode>(new IdComparator());
		Q = new PriorityQueue<RouteableNode>();		
		pi = new TreeMap<RouteableNode, RouteableNode>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, TreeSet<RouteableNode> S, PriorityQueue<RouteableNode> Q) {
		this.graph = streetgraph;
		this.S = S;
		this.Q = Q;
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		init(start);
		endVertex = end;
			
		while(!Q.isEmpty()){
			if(Q.peek().getId() == endVertex.getId())
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
		RouteableNode u = Q.poll();
		System.out.println(u.getId() + ": " + u.getDistance());
		
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
					if(v.getDistance()  > (u.getDistance() + e.getWeight())){
						Q.remove(v);
						v.setDistance(u.getDistance() + e.getWeight());
						Q.add(v);
						pi.put(v, u);
						System.out.println("--" + v.getId() + " now: " + v.getDistance());

					}
				}else{
					v.setDistance(u.getDistance() + e.getWeight());						
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
		startVertex.setDistance(0);
		Q.add(graph.getNode(startVertex.getId()));
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
		
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}

	public TreeSet<RouteableNode> getS() {
		return S;
	}

	public void setS(TreeSet<RouteableNode> s) {
		S = s;
	}

	public PriorityQueue<RouteableNode> getQ() {
		return Q;
	}

	public void setQ(PriorityQueue<RouteableNode> q) {
		Q = q;
	}

	public RouteableNode getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(RouteableNode startVertex) {
		this.startVertex = startVertex;
	}

	public RouteableNode getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(RouteableNode endVertex) {
		this.endVertex = endVertex;
	}
	



}
