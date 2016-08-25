package de.spinosm.graph;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StreetGraph extends SimpleDirectedWeightedGraph<RouteableVertex, StreetEdge> implements Serializable, WeightedGraph<RouteableVertex, StreetEdge>{

	private static final long serialVersionUID = -67998995199008728L;
	transient private DataProvider dataprovider;
	public static int DEFAULT_DIRECTION = 1;

	public StreetGraph(DataProvider dataprovider){
		super(new StreetEdgeFactory());
		if(dataprovider == null)
			this.dataprovider = new DefaultDataProvider();
		else
			this.dataprovider = dataprovider;
	}
	
	public void setStreetJunctions(TreeSet<RouteableVertex> vertecies) {
		if(vertecies != null){
			super.removeAllVertices(vertecies);
			for(RouteableVertex v : vertecies)
				super.addVertex(v);
		}else{
			throw new IllegalArgumentException("vertecies is null");
		}
	}
	
	public Set<RouteableVertex> getStreetVertecies() {
		return super.vertexSet();	
	}
	

	public RouteableVertex getVertex(long id){
		RouteableVertex returnValue = checkBufferedVerteciesForId(id);
		if(returnValue != null)
			return returnValue;
		
		return getStreetVertexFromDataProvider(id);
	}

	private RouteableVertex getStreetVertexFromDataProvider(long id) {
		RouteableVertex returnValue = null;
		try {
			returnValue = this.dataprovider.getStreetJunction(id);
			this.integrateNewNodeToGraph(returnValue);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnValue;
	}

	private RouteableVertex checkBufferedVerteciesForId(long id) {
		for(RouteableVertex n : super.vertexSet())
			if(n.getId() == id && n.isEdgesLoaded())
				return n;
		return null;
	}
	
	private void integrateNewNodeToGraph(RouteableVertex newNode){
		//Prüfe ob benachbarte knoten schon im graph. wenn ja verlinke sie
//		linkWithAlredyKnownNodes(newNode);
		super.addVertex(newNode);
	}
	
	/*
	 private void linkWithAlredyKnownNodes(StreetVertex newNode){
		for(StreetEdge edge : getEdgesForVertex(newNode, DEFAULT_DIRECTION)){
			StreetVertex other = (StreetVertex) edge.getOtherKnotThan(newNode);
			//if(super.containsVertex( other))
					//linkEdgeWithAlreedyKnownVertex(edge, other);
				
		}
	}

	private void linkEdgeWithAlreedyKnownVertex(StreetEdge edge, StreetVertex toReplace){
		for(StreetVertex node : super.vertexSet().){
			if(node.hasSameId(toReplace)){
				edge.replace(toReplace, node);
			}
		}
	}*/

	@Override
	public StreetEdge addEdge(RouteableVertex sourceVertex, RouteableVertex targetVertex) {
		try {
			return super.addEdge(sourceVertex, targetVertex);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public boolean addEdge(RouteableVertex sourceVertex, RouteableVertex targetVertex, StreetEdge e) {
		return super.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public boolean containsVertex(RouteableVertex vertex) {
		if(super.containsVertex(vertex))
			return true;
		return false;
	}
	
	public Set<StreetEdge> getEdgesForVertex(RouteableVertex startVertex, int direction) {
		if(startVertex.isEdgesLoaded())
			return checkBufferedEdgeForId(startVertex, direction);
		else{
			loadEdgesFormDataprovider(startVertex);
			return checkBufferedEdgeForId(startVertex, direction); //Had to that because, Loading From dataprovider with direction makes no sense
		}
	}
	
	private Set<StreetEdge> loadEdgesFormDataprovider(RouteableVertex startVertex) {
		Set<StreetEdge> edges =	dataprovider.getStreetEdgesForVertex(startVertex);
		for(StreetEdge e: edges)
			addEdge(e, startVertex);
		startVertex.setEdgesLoaded(true);		
		return edges;
	}

	private Set<StreetEdge> checkBufferedEdgeForId(RouteableVertex startVertex, int direction) {
		Set<StreetEdge> edges = new HashSet<StreetEdge>();
		try {
			edges = super.edgesOf(startVertex);
		} catch (Exception e) {}
		if(direction > 0)
			return outgoingEdgesOf(startVertex, edges);
		else
			return incomingEdgesOf(startVertex, edges);
		
	}

	private Set<StreetEdge> incomingEdgesOf(RouteableVertex sv, Set<StreetEdge> edges) {
		Set<StreetEdge> incomingEdges = new HashSet<StreetEdge>();
		for(StreetEdge e: edges)
			if(e.getEnd().getId() == sv.getId())
				incomingEdges.add(e);
		return incomingEdges;
	}

	private Set<StreetEdge> outgoingEdgesOf(RouteableVertex sv, Set<StreetEdge> edges) {
		Set<StreetEdge> outgoingEdges = new HashSet<StreetEdge>();
		for(StreetEdge e: edges)
			if(e.getStart().getId() == sv.getId())
				outgoingEdges.add(e);
		return outgoingEdges;
	}

	public void addEdge(StreetEdge e, RouteableVertex sv) {
		super.addVertex(e.getOtherKnotThan(sv));
		StreetEdge se = addEdge(e.getStart(), e.getEnd());
		if(se != null)
			se.setWeight(e.getWeight());
	}

	@Override
	public Set<StreetEdge> edgesOf(RouteableVertex v){
		try {
			throw new Exception("Don't use me. use getEdgesForNode(sj)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DataProvider getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(DataProvider dataprovider) {
		this.dataprovider = dataprovider;
	}

}
