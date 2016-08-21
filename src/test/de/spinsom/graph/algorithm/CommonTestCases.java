package de.spinsom.graph.algorithm;

import java.util.HashSet;
import java.util.Set;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;

public class CommonTestCases {

	private static StreetVertex CUX = new StreetVertex();
	private static StreetVertex HH = new StreetVertex();
	private static StreetVertex OL = new StreetVertex();
	private static StreetVertex EMD = new StreetVertex();
	private static StreetVertex HB = new StreetVertex();
	private static StreetVertex OS = new StreetVertex();
	private static StreetVertex BI = new StreetVertex();
	private static StreetVertex MS = new StreetVertex();
	private static StreetVertex DU = new StreetVertex();
	private static StreetVertex H= new StreetVertex();
	private static StreetVertex KS = new StreetVertex();
	private static StreetVertex K = new StreetVertex();
	private static StreetVertex D = new StreetVertex();
	private static StreetVertex F = new StreetVertex();
	private static StreetVertex WUE = new StreetVertex();
	private static StreetVertex DO = new StreetVertex();
		
	public static StreetGraph buildExampleGraph(){
		StreetGraph streetgraph = initStreetGraph();

		
		
		return null;
		
	}
	
	private static StreetGraph initStreetGraph(){
		DataProvider defaultProvider = new DefaultDataProvider();
		return new StreetGraph(defaultProvider);		
	}
	
	private static Set<StreetEdge> setUpEdges(){
		HashSet<StreetEdge> edges = new HashSet<StreetEdge>();
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(HB, CUX, 102));
		edges.add(new StreetEdge(HH, HB, 119));
		edges.add(new StreetEdge(HB, HH, 119));
		edges.add(new StreetEdge(OL, HB, 45));
		edges.add(new StreetEdge(HB, OL, 45));
		edges.add(new StreetEdge(H, HB, 133));
		edges.add(new StreetEdge(HB, H, 133));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		edges.add(new StreetEdge(CUX, HB, 102));
		
		
		
		return edges;
	}
}
