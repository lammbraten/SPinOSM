package de.spinosm.graph;


import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StreetGraph extends SimpleDirectedWeightedGraph<StreetJunction, StreetEdge> implements Serializable{


	private static final long serialVersionUID = -67998995199008728L;
	
	transient private DataProvider dataprovider;
	//private TreeSet<StreetJunction> nodes;
	
	public StreetGraph(DataProvider dataprovider){
		super(StreetEdge.class);
		this.dataprovider = dataprovider;
	}
	
	public StreetGraph(DataProvider dataprovider, TreeSet<StreetJunction> nodes){
		super(StreetEdge.class);
		this.dataprovider = dataprovider;;
		for(StreetJunction v : nodes)
			super.addVertex(v);
	}
	
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
	

	public RouteableNode getNode(long id){
		RouteableNode returnValue = checkBufferedNodesForId(id);
		if(returnValue != null)
			return returnValue;
		
		return getStreetJunctionFromDataProvider(id);
	}

	/**
	 * @param id
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
	private RouteableNode checkBufferedNodesForId(long id) {
		for(RouteableNode n : super.vertexSet())
			if(n.getId() == id && n.isEdgesLoaded())
				return n;
		return null;
	}
	
	private void integrateNewNodeToGraph(StreetJunction newNode){
		//Prüfe ob benachbarte knoten schon im graph. wenn ja verlinke sie
		linkWithAlredyKnownNodes(newNode);
		super.addVertex(newNode);
	}
	
	private void linkWithAlredyKnownNodes(StreetJunction newNode){
		for(RouteableEdge edge : newNode.getEdges()){
			StreetJunction other = (StreetJunction) edge.getOtherKnotThan(newNode);
			if(super.containsVertex( other))
					linkEdgeWithAlreedyKnownNode(edge, other);
				
		}
	}

	private void linkEdgeWithAlreedyKnownNode(RouteableEdge edge, RouteableNode toReplace){
		for(RouteableNode node : super.vertexSet()){
			if(node.hasSameId(toReplace)){
				edge.replace(toReplace, node);
			}
		}
	}

	@Override
	public StreetEdge addEdge(StreetJunction sourceVertex, StreetJunction targetVertex) {
		return super.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(StreetJunction sourceVertex, StreetJunction targetVertex, StreetEdge e) {
		return super.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public boolean addVertex(StreetJunction v) {
		return super.addVertex(v);
	}

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
			if(super.containsEdge(sourceVertex, targetVertex))
				return true;
			throw new Exception("Not implemented yet!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean containsVertex(StreetJunction vertex) {
		if(super.containsVertex(vertex))
			return true;
		return false;
	}



}
