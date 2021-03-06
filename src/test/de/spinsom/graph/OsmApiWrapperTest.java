package de.spinsom.graph;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.WeightFunction;

public class OsmApiWrapperTest {

	private static final long[] EXISTING_NODES = {
			116108105l,  
			45107632l,
			45107637l, 
			1573918799l,
			415866944l,
			415866943l,
			415866943l,
	};
	
	private static long HAF_KOE = 116108105l;
	private static long RA_GRO = 1579971496l; 
	private static long HAF_HAF = 105715296;
	
	static OsmApiWrapper osmapiwrapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WeightFunction wf = new DefaultCostFunction();
		osmapiwrapper = new OsmApiWrapper(wf);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void testGetStreetJunction() {
		RouteableVertex sj = osmapiwrapper.getVertex(HAF_KOE);
		System.out.println(sj.toString());
		/*sj = osmapiwrapper.getStreetJunction(RA_GRO);
		System.out.println(sj.toString());
		for(RouteableEdge e : sj.getEdges())
			System.out.println("\t" + e.toString());*/
		sj = osmapiwrapper.getVertex(HAF_HAF);
		System.out.println(sj.toString());
	}


}
