package de.spinosm.graph;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StreetGraph extends SimpleDirectedWeightedGraph<StreetVertex, StreetEdge> implements Serializable, WeightedGraph<StreetVertex, StreetEdge>{

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
	
	public void setStreetJunctions(TreeSet<StreetVertex> vertecies) {
		if(vertecies != null){
			super.removeAllVertices(vertecies);
			for(StreetVertex v : vertecies)
				super.addVertex(v);
		}else{
			throw new IllegalArgumentException("vertecies is null");
		}
	}
	
	public Set<StreetVertex> getStreetVertecies() {
		return super.vertexSet();	
	}
	

	public StreetVertex getVertex(long id){
		StreetVertex returnValue = checkBufferedVerteciesForId(id);
		if(returnValue != null)
			return returnValue;
		
		return getStreetVertexFromDataProvider(id);
	}

	private StreetVertex getStreetVertexFromDataProvider(long id) {
		StreetVertex returnValue = null;
		try {
			returnValue = this.dataprovider.getStreetJunction(id);
			this.integrateNewNodeToGraph(returnValue);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnValue;
	}

	private StreetVertex checkBufferedVerteciesForId(long id) {
		for(StreetVertex n : super.vertexSet())
			if(n.getId() == id && n.isEdgesLoaded())
				return n;
		return null;
	}
	
	private void integrateNewNodeToGraph(StreetVertex newNode){
		//Prüfe ob benachbarte knoten schon im graph. wenn ja verlinke sie
//		linkWithAlredyKnownNodes(newNode);
		super.addVertex(newNode);
	}
	
	private void linkWithAlredyKnownNodes(StreetVertex newNode){
		for(StreetEdge edge : getEdgesForVertex(newNode, DEFAULT_DIRECTION)){
			StreetVertex other = (StreetVertex) edge.getOtherKnotThan(newNode);
			if(super.containsVertex( other))
					linkEdgeWithAlreedyKnownVertex(edge, other);
				
		}
	}

	private void linkEdgeWithAlreedyKnownVertex(StreetEdge edge, StreetVertex toReplace){
		for(StreetVertex node : super.vertexSet()){
			if(node.hasSameId(toReplace)){
				edge.replace(toReplace, node);
			}
		}
	}

	@Override
	public StreetEdge addEdge(StreetVertex sourceVertex, StreetVertex targetVertex) {
		try {
			return super.addEdge(sourceVertex, targetVertex);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public boolean addEdge(StreetVertex sourceVertex, StreetVertex targetVertex, StreetEdge e) {
		return super.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public boolean containsVertex(StreetVertex vertex) {
		if(super.containsVertex(vertex))
			return true;
		return false;
	}
	
	public Set<StreetEdge> getEdgesForVertex(StreetVertex sv, int direction) {
		Set<StreetEdge>  returnValue = checkBufferedEdgeForId(sv, direction);
		if(!returnValue.isEmpty())
			return returnValue;
		else{
			loadEdgesFormDataprovider(sv);
			return checkBufferedEdgeForId(sv, direction); //Had to that because, Loading From dataprovider with direction makes no sense
		}
	}
	

	public Set<StreetEdge> getEdgesForVertex(StreetVertex sv, int direction, boolean download) {
		if(download)
			return getEdgesForVertex(sv, direction);
		return checkBufferedEdgeForId(sv, direction);
	}


	private Set<StreetEdge> loadEdgesFormDataprovider(StreetVertex sv) {
		Set<StreetEdge> edges =	dataprovider.getStreetEdgesForNode(sv);
		for(StreetEdge e: edges)
			addEdge(e);
		sv.setEdgesLoaded(true);		
		return edges;
	}

	private Set<StreetEdge> checkBufferedEdgeForId(StreetVertex sv, int direction) {
		Set<StreetEdge> edges = new HashSet<StreetEdge>();
		try {
			edges = super.edgesOf(sv);
		} catch (Exception e) {}
		if(direction > 0)
			return outgoingEdgesOf(sv, edges);
		else
			return incomingEdgesOf(sv, edges);
		
	}

	private Set<StreetEdge> incomingEdgesOf(StreetVertex sv, Set<StreetEdge> edges) {
		Set<StreetEdge> incomingEdges = new HashSet<StreetEdge>();
		for(StreetEdge e: edges)
			if(e.getEnd().getId() == sv.getId())
				incomingEdges.add(e);
		return incomingEdges;
	}

	private Set<StreetEdge> outgoingEdgesOf(StreetVertex sv, Set<StreetEdge> edges) {
		Set<StreetEdge> outgoingEdges = new HashSet<StreetEdge>();
		for(StreetEdge e: edges)
			if(e.getStart().getId() == sv.getId())
				outgoingEdges.add(e);
		return outgoingEdges;
	}

	public void addEdge(StreetEdge e) {
		if(super.containsVertex(e.getEnd()))
			linkEdgeWithAlreedyKnownVertex(e, e.getEnd());
		else 
			super.addVertex(e.getEnd());
		StreetEdge se = addEdge(e.getStart(), e.getEnd());
		if(se != null)
			se.setWeight(e.getWeight());
	}

	@SuppressWarnings("Deprecated")
	@Override
	public Set<StreetEdge> edgesOf(StreetVertex v){
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
