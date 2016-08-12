package de.spinosm.graph;


import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.graph.weights.WeightFunction;
import de.spinosm.graph.weights.DefaultCostFunction;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StreetGraph extends SimpleDirectedWeightedGraph<StreetJunction, StreetEdge> implements Serializable, WeightedGraph<StreetJunction, StreetEdge>{

	private static final long serialVersionUID = -67998995199008728L;
	transient private DataProvider dataprovider;
	public static int DEFAULT_DIRECTION = 1;

	
	/*public StreetGraph(DataProvider dataprovider){
		this(dataprovider, null);
	}*/
	
	public StreetGraph(DataProvider dataprovider){
		super(new StreetEdgeFactory());
		if(dataprovider == null)
			this.dataprovider = new DefaultDataProvider();
		else
			this.dataprovider = dataprovider;
	}
	
	/*public StreetGraph(DataProvider dataprovider, TreeSet<StreetJunction> nodes){
		super(new StreetEdgeFactory());
		this.dataprovider = dataprovider;;
		for(StreetJunction v : nodes)
			super.addVertex(v);
	}*/

	public void setStreetJunctions(TreeSet<StreetJunction> junctions) {
		if(junctions != null){
			super.removeAllVertices(junctions);
			for(StreetJunction v : junctions)
				super.addVertex(v);
		}else{
			throw new IllegalArgumentException("junctions is null");
		}
	}
	
	public Set<StreetJunction> getStreetJunctions() {
		return super.vertexSet();	
	}
	

	public StreetJunction getNode(long id){
		StreetJunction returnValue = checkBufferedNodesForId(id);
		if(returnValue != null)
			return returnValue;
		
		return getStreetJunctionFromDataProvider(id);
	}

	 /** @param id
	 * @return
	 */
	private StreetJunction getStreetJunctionFromDataProvider(long id) {
		StreetJunction returnValue = null;
		try {
			returnValue = this.dataprovider.getStreetJunction(id);
			this.integrateNewNodeToGraph(returnValue);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnValue;
	}

	/**
	 * @param id
	 */
	private StreetJunction checkBufferedNodesForId(long id) {
		for(StreetJunction n : super.vertexSet())
			if(n.getId() == id && n.isEdgesLoaded())
				return n;
		return null;
	}
	
	private void integrateNewNodeToGraph(StreetJunction newNode){
		//Prüfe ob benachbarte knoten schon im graph. wenn ja verlinke sie
//		linkWithAlredyKnownNodes(newNode);
		super.addVertex(newNode);
	}
	
	private void linkWithAlredyKnownNodes(StreetJunction newNode){
		for(StreetEdge edge : getEdgesForNode(newNode, DEFAULT_DIRECTION)){
			StreetJunction other = (StreetJunction) edge.getOtherKnotThan(newNode);
			if(super.containsVertex( other))
					linkEdgeWithAlreedyKnownNode(edge, other);
				
		}
	}

	private void linkEdgeWithAlreedyKnownNode(StreetEdge edge, StreetJunction toReplace){
		for(StreetJunction node : super.vertexSet()){
			if(node.hasSameId(toReplace)){
				edge.replace(toReplace, node);
			}
		}
	}

	@Override
	public StreetEdge addEdge(StreetJunction sourceVertex, StreetJunction targetVertex) {
		try {
			return super.addEdge(sourceVertex, targetVertex);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public boolean addEdge(StreetJunction sourceVertex, StreetJunction targetVertex, StreetEdge e) {
		return super.addEdge(sourceVertex, targetVertex, e);
	}


	/*
	@Override
	public boolean containsEdge(StreetEdge e) {
		try {
			if(super.containsEdge(e))
				return true;
			throw new Exception("Not implemented yet!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean containsEdge(StreetJunction sourceVertex, StreetJunction targetVertex) {
		try {
			if (super.containsEdge(sourceVertex, targetVertex))
				return true;
			throw new Exception("Not implemented yet!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/

	@Override
	public boolean containsVertex(StreetJunction vertex) {
		if(super.containsVertex(vertex))
			return true;
		return false;
	}
	
	public StreetGraph getGraph(){
		return this;
	}

	public Set<StreetEdge> getEdgesForNode(StreetJunction sj, int direction) {
		Set<StreetEdge>  returnValue = checkBufferedEdgeForId(sj, direction);
		if(!returnValue.isEmpty())
			return returnValue;
		else{
			loadEdgesFormDataprovider(sj);
			return checkBufferedEdgeForId(sj, direction); //Had to that because, Loading From dataprovider with direction makes no sense
		}
	}
	

	public Set<StreetEdge> getEdgesForNode(StreetJunction sj, int direction, boolean download) {
		if(download)
			return getEdgesForNode(sj, direction);
		return checkBufferedEdgeForId(sj, direction);
	}


	private Set<StreetEdge> loadEdgesFormDataprovider(StreetJunction sj) {
		Set<StreetEdge> edges =	dataprovider.getStreetEdgesForNode(sj);
		for(StreetEdge e: edges)
			addEdge(e);
		sj.setEdgesLoaded(true);		
		return edges;
	}

	private Set<StreetEdge> checkBufferedEdgeForId(StreetJunction sj, int direction) {
		Set<StreetEdge> edges = new HashSet<StreetEdge>();
		try {
			edges = super.edgesOf(sj);
		} catch (Exception e) {}
		if(direction > 0)
			return outgoingEdgesOf(sj, edges);
		else
			return incomingEdgesOf(sj, edges);
		
	}

	private Set<StreetEdge> incomingEdgesOf(StreetJunction sj, Set<StreetEdge> edges) {
		Set<StreetEdge> incomingEdges = new HashSet<StreetEdge>();
		for(StreetEdge e: edges)
			if(e.getEnd().getId() == sj.getId())
				incomingEdges.add(e);
		return incomingEdges;
	}

	private Set<StreetEdge> outgoingEdgesOf(StreetJunction sj, Set<StreetEdge> edges) {
		Set<StreetEdge> outgoingEdges = new HashSet<StreetEdge>();
		for(StreetEdge e: edges)
			if(e.getStart().getId() == sj.getId())
				outgoingEdges.add(e);
		return outgoingEdges;
	}

	public void addEdge(StreetEdge e) {
		super.addVertex(e.getEnd());
		StreetEdge se = addEdge(e.getStart(), e.getEnd());
		//StreetEdge se = getEdge(e.getStart(), e.getStart());
		if(se != null)
			se.setWeight(e.getWeight());
	}

	@SuppressWarnings("Deprecated")
	@Override
	public Set<StreetEdge> edgesOf(StreetJunction v){
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
