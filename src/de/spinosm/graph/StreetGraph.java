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
	
	StreetGraph(DataProvider dataprovider, LinkedList<Knot> knots){
		this.dataprovider = dataprovider;
		this.nodes = knots;
	}
	
	
	@Override
	public void setNodes(LinkedList<Knot> nodes) {
		super.setNodes(nodes);	
	}
	
	@Override
	public LinkedList<Knot> getNodes() {
		return super.getNodes();
	}

}
