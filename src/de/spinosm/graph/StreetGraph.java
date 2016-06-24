package de.spinosm.graph;

import java.util.LinkedList;

import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.OsmApiWrapper;

public class StreetGraph extends DirectedGraph{

	private DataProvider dataprovider;
	
	StreetGraph(DataProvider dataprovider){
		this.dataprovider = dataprovider;
	}
	
	
	@Override
	public void setNodes(LinkedList<Knot> nodes) {
		this.nodes = nodes;
		
	}
	
	@Override
	public LinkedList<Knot> getNodes() {
		return this.nodes;
	}

}
