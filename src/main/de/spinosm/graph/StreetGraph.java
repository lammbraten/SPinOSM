package de.spinosm.graph;


import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;


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
		StreetJunction returnValue = this.dataprovider.getStreetJunction(id);
		this.integrateNewNodeToGraph(returnValue);
		return returnValue;
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

}
