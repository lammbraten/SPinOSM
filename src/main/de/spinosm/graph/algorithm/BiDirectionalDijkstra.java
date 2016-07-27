package de.spinosm.graph.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.depr.ShortestPath;
import de.spinosm.graph.pattern.IdComparator;

public class BiDirectionalDijkstra implements ShortestPath{
	private StreetGraph graph; 
	private Dijkstra d1;
	private Dijkstra d2;
	private static RouteableNode startVertex;
	private static RouteableNode endVertex;	
	
	
	public BiDirectionalDijkstra(StreetGraph streetgraph) {
		this.graph = streetgraph;
		
		this.d1 = new Dijkstra(graph);
		this.d2 = new Dijkstra(graph);
		
	}

	@Override
	public List<RouteableNode> getShortestPath(RouteableNode start, RouteableNode end) {
		startVertex = start;
		endVertex = end;

		d1.getQ().add(graph.getNode(startVertex.getId()));
		d1.getQ().first().setDistance(0);
		
		d2.getQ().add(graph.getNode(endVertex.getId()));
		d2.getQ().first().setDistance(0);
		
		while(intersectionOf(d1.getS(),d2.getS()).isEmpty() && (!d1.getQ().isEmpty() || !d2.getQ().isEmpty())){
			d1.checkNextVertex();
			d2.checkNextVertex();
		}
			
		return (List<RouteableNode>) intersectionOf(d1.getS(),d2.getS());
	}

	@Override
	public StreetGraph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGraph(StreetGraph g) {
		// TODO Auto-generated method stub
		
	}

	private TreeSet<RouteableNode> intersectionOf(TreeSet<RouteableNode> treeSet, TreeSet<RouteableNode> treeSet2){
		TreeSet<RouteableNode> intersection = new TreeSet<RouteableNode>(treeSet);
		intersection.retainAll(treeSet2);
		return intersection;
	}
}
