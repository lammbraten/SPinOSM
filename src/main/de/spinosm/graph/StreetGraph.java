package de.spinosm.graph;

import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;
import de.westnordost.osmapi.map.data.OsmNode;

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
		
		return getNodeFromServer(id);
	}

	/**
	 * @param id
	 * @return
	 */
	private StreetJunction getNodeFromServer(long id) {
		StreetJunction returnValue;
		OsmNode osmNode = (OsmNode) this.dataprovider.getNode(id);
		returnValue = new StreetJunction(osmNode);
		this.nodes.add(returnValue);
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
	

	

}
