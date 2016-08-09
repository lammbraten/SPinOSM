package de.spinosm.graph.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.pattern.IdComparator;

public class Dijkstra implements ShortestPath{
	private StreetGraph graph; 
	private TreeSet<StreetJunction> S;
	private PriorityQueue<StreetJunction> Q;
	private TreeMap<StreetJunction, StreetJunction> pi;
	private StreetJunction startVertex;
	private StreetJunction endVertex;	
	
	public Dijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		S = new TreeSet<StreetJunction>(new IdComparator());
		Q = new PriorityQueue<StreetJunction>();		
		pi = new TreeMap<StreetJunction, StreetJunction>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, TreeSet<StreetJunction> S, PriorityQueue<StreetJunction> Q) {
		this.graph = streetgraph;
		this.S = S;
		this.Q = Q;
	}

	@Override
	public List<StreetJunction> getShortestPath(StreetJunction start, StreetJunction end) {
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
		StreetJunction u = Q.poll();
		System.out.println(u.getId() + ": " + u.getDistance());
		
		S.add(u);
		
		if(!u.isEdgesLoaded())
			for(StreetEdge e : 	graph.getEdgesForNode(u))
				graph.addEdge(e);				
		
		for(StreetEdge e : graph.getEdgesForNode(u)){
			StreetJunction v = e.getOtherKnotThan(u);
			
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
		startVertex = start;
		startVertex.setDistance(0);
		graph.getEdgesForNode(startVertex);
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

	public TreeSet<StreetJunction> getS() {
		return S;
	}

	public void setS(TreeSet<StreetJunction> s) {
		S = s;
	}

	public PriorityQueue<StreetJunction> getQ() {
		return Q;
	}

	public void setQ(PriorityQueue<StreetJunction> q) {
		Q = q;
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
