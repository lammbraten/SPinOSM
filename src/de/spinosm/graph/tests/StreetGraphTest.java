package de.spinosm.graph.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.map.data.OsmNode;

@RunWith(Parameterized.class)
public class StreetGraphTest {
	private static final boolean TEST_SHOULD_FAIL = true;
	private static final OsmNode EXISTING_NODE1 = new OsmNode(1636160756l, 1,  50.8992329, 7.0318133, null, null);
	private static final OsmNode EXISTING_NODE2 = new OsmNode(203986826l, 7, 50.8975988, 7.0364495, null, null);
	private static final OsmNode EXISTING_NODE3 = new OsmNode(1861698092l, 2, 50.8978525, 7.0351034, null, null);
	private static final OsmNode NOT_EXISTING_NODE = new OsmNode(10011l, 0, null, null, null, null);
	private static final StreetJunction[] ALL_NODES_BUFFERED = {
			new StreetJunction(EXISTING_NODE1),
			new StreetJunction(EXISTING_NODE2),
			new StreetJunction(EXISTING_NODE3)
	};
	private static final StreetJunction[] SOME_NODES_BUFFERED = {
			new StreetJunction(EXISTING_NODE1),
			new StreetJunction(EXISTING_NODE3)
	};
	private static final StreetJunction[] NO_NODES_BUFFERED = {
	};
	private static final OsmNode[] TEST_SET1 = {
			EXISTING_NODE1,
			EXISTING_NODE2,
			EXISTING_NODE3
	};
	private static final OsmNode[] TEST_SET2 = {
			EXISTING_NODE1,
			EXISTING_NODE2,
			NOT_EXISTING_NODE
	};
	
	private boolean testShouldFail;
	private StreetGraph streetGraph;
	private OsmNode[] testSet;
	static OsmApiWrapper osmapiwrapper;
	
	public StreetGraphTest(boolean testShouldFail, StreetJunction[] buffer, OsmNode[] testSet){
		super();
		streetGraph = new StreetGraph(osmapiwrapper);
		this.testShouldFail = testShouldFail;
		TreeSet<StreetJunction> bufferedTreeSet = new TreeSet<StreetJunction>();
		for(StreetJunction sj : buffer){
			bufferedTreeSet.add(sj);
		}
		this.streetGraph.setStreetJunctions(bufferedTreeSet);
		this.testSet = testSet;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		osmapiwrapper = new OsmApiWrapper();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
 	@Parameters
 	public static Collection values() {
 		return Arrays.asList(new Object[][] {	
 			{!TEST_SHOULD_FAIL, ALL_NODES_BUFFERED, TEST_SET1},
 			{!TEST_SHOULD_FAIL, SOME_NODES_BUFFERED, TEST_SET1},
 			{!TEST_SHOULD_FAIL, NO_NODES_BUFFERED, TEST_SET1},
 			{TEST_SHOULD_FAIL, ALL_NODES_BUFFERED, TEST_SET2}
 		});
 	}

	@Test
	public void testGetNodes() {
		if(testShouldFail)
			assertNull(streetGraph.getNodes());
		else
			assertNotNull(streetGraph.getStreetJunctions());	
	}

	@Test
	public void testSetNodes() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreetGraphDataProvider() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreetGraphDataProviderLinkedListOfStreetJunction() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNode() {
		fail("Not yet implemented");
	}

}
