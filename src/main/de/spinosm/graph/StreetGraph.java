package de.spinosm.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.OsmWay;
import de.westnordost.osmapi.map.data.Way;

public class StreetGraph extends DirectedGraph{

	private DataProvider dataprovider;
	private TreeSet<StreetJunction> nodes;
	
	public StreetGraph(DataProvider dataprovider){
		super();
		this.dataprovider = dataprovider;
	}
	
	public StreetGraph(DataProvider dataprovider, TreeSet<StreetJunction> nodes){
		this.dataprovider = dataprovider;
		this.nodes = nodes;
	}
	
	public void setStreetJunctions(TreeSet<StreetJunction> junctions) {
		this.nodes = junctions;	
	}
	
	public TreeSet<StreetJunction> getStreetJunctions() {
		return this.nodes;	
	}
	

	public StreetJunction getNode(long id){
		StreetJunction returnValue = checkBufferedNodesForId(id);
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
	private StreetJunction checkBufferedNodesForId(long id) {
		for(StreetJunction n : nodes)
			if(n.getId() == id)
				return n;
		return null;
	}
	
	private void integrateNewNodeToGraph(StreetJunction newNode){
		//Prüfe ob benachbarte knoten schon im graph. wenn ja verlinke sie
		linkWithAlredyKnownNodes(newNode);
		this.nodes.add(newNode);
	}
	
	private void linkWithAlredyKnownNodes(StreetJunction newNode){
		for(RouteableEdge edge : newNode.getEdges()){
			RouteableNode other = edge.getOtherKnotThan(newNode);
			if(nodes.contains(other))
					linkEdgeWithAlredyKnownNode(edge, other);
				
		}
	}

	private void linkEdgeWithAlredyKnownNode(RouteableEdge edge, RouteableNode toReplace){
		for(RouteableNode node : nodes){
			if(node.hasSameId(toReplace)){
				edge.replace(toReplace, node);
			}
		}
	}

}
