package de.spinsom.graph;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.StreetGraph;
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
	
	static OsmApiWrapper osmapiwrapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		osmapiwrapper = new OsmApiWrapper();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testOsmApiWrapper() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWay() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWays() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWaysForNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRelations() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRelationsForNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRelationsForRelation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRelationsForWay() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStreetJunction() {
		StreetJunction sj = osmapiwrapper.getStreetJunction(45107632l);
		sj.toString();
	}


}
