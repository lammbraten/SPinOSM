package de.spinsom.graph;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;

public class OsmApiWrapperTest2 {

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
		osmapiwrapper = new OsmApiWrapper();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void testGetStreetJunction() {
		StreetJunction sj = osmapiwrapper.getStreetJunction(HAF_KOE);
		System.out.println(sj.toString());
		/*sj = osmapiwrapper.getStreetJunction(RA_GRO);
		System.out.println(sj.toString());
		for(RouteableEdge e : sj.getEdges())
			System.out.println("\t" + e.toString());*/
		sj = osmapiwrapper.getStreetJunction(HAF_HAF);
		System.out.println(sj.toString());
	}


}
