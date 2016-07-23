package de.spinosm.graph;


import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StreetGraph extends SimpleDirectedWeightedGraph<RouteableNode, StreetEdge>{


	private static final long serialVersionUID = -67998995199008728L;
	
	private DataProvider dataprovider;
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
	
	public Set<RouteableNode> getStreetJunctions() {
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
		try {
			StreetJunction returnValue = this.dataprovider.getStreetJunction(id);
			this.integrateNewNodeToGraph(returnValue);
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param id
	 */
	private RouteableNode checkBufferedNodesForId(long id) {
		for(RouteableNode n : super.vertexSet())
			if(n.getId() == id)
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
			RouteableNode other = edge.getOtherKnotThan(newNode);
			if(super.containsVertex(other))
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
	public StreetEdge addEdge(RouteableNode sourceVertex, RouteableNode targetVertex) {
		return super.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(RouteableNode sourceVertex, RouteableNode targetVertex, StreetEdge e) {
		return super.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public boolean addVertex(RouteableNode v) {
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
	public boolean containsEdge(RouteableNode sourceVertex, RouteableNode targetVertex) {
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
	public boolean containsVertex(RouteableNode vertex) {
		if(super.containsVertex(vertex))
			return true;
		/*if(dataprovider.getStreetJunction(vertex.getId()) != null)
			return true;*/
		return false;
	}

	@Override
	public Set<StreetEdge> edgeSet() {
		return super.edgeSet();
	}

	@Override
	public Set<StreetEdge> edgesOf(RouteableNode vertex) {
		return super.edgesOf(vertex);
	}

	@Override
	public Set<StreetEdge> getAllEdges(RouteableNode sourceVertex, RouteableNode targetVertex) {
		return super.getAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public StreetEdge getEdge(RouteableNode sourceVertex, RouteableNode targetVertex) {
		return super.getEdge(sourceVertex, targetVertex);
	}

	@Override
	public EdgeFactory<RouteableNode, StreetEdge> getEdgeFactory() {
		return super.getEdgeFactory();
	}

	@Override
	public RouteableNode getEdgeSource(StreetEdge e) {
		return super.getEdgeSource(e);
	}

	@Override
	public RouteableNode getEdgeTarget(StreetEdge e) {
		return super.getEdgeTarget(e);
	}

	@Override
	public double getEdgeWeight(StreetEdge e) {
		return super.getEdgeWeight(e);
	}

	@Override
	public boolean removeAllEdges(Collection<? extends StreetEdge> edges) {
		return super.removeAllEdges(edges);
	}

	@Override
	public Set<StreetEdge> removeAllEdges(RouteableNode sourceVertex, RouteableNode targetVertex) {
		return super.removeAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeAllVertices(Collection<? extends RouteableNode> vertices) {
		return super.removeAllVertices(vertices);
	}

	@Override
	public boolean removeEdge(StreetEdge e) {
		return super.removeEdge(e);
	}

	@Override
	public StreetEdge removeEdge(RouteableNode sourceVertex, RouteableNode targetVertex) {
		return super.removeEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeVertex(RouteableNode v) {
		return super.removeVertex(v);
	}

	@Override
	public Set<RouteableNode> vertexSet() {
		return super.vertexSet();
	}

}
