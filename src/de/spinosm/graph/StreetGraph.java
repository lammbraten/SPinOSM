package de.spinosm.graph;

import java.util.LinkedList;

import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.OsmApiWrapper;

public class StreetGraph extends DirectedGraph{

	private DataProvider dataprovider;
	
	StreetGraph(DataProvider dataprovider){
		super();
		this.dataprovider = dataprovider;
	}
	
	StreetGraph(DataProvider dataprovider, LinkedList<RouteableNode> knots){
		this.dataprovider = dataprovider;
		this.nodes = knots;
	}
	
	
	@Override
	public void setNodes(LinkedList<RouteableNode> nodes) {
		super.setNodes(nodes);	
	}
	
	@Override
	public LinkedList<RouteableNode> getNodes() {
		return super.getNodes();
	}

}
