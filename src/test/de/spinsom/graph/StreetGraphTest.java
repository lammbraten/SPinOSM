package de.spinsom.graph;

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

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.map.data.OsmNode;

@RunWith(Parameterized.class)
public class StreetGraphTest {
	private static final int TEST_SHOULD_WORK = 0;
	private static final int TEST_SHOULD_FAIL = 1;
	private static final int TEST_THROWS_EXCEPTION = 2;
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
	
	private int testResult;
	private StreetGraph streetGraph;
	private OsmNode[] testSet;
	private TreeSet<StreetJunction> bufferedTreeSet;
	static OsmApiWrapper osmapiwrapper;
	
	
	public StreetGraphTest(int testResult, StreetJunction[] buffer, OsmNode[] testSet){
		super();
		this.testResult = testResult;
		bufferedTreeSet = new TreeSet<StreetJunction>();
		for(StreetJunction sj : buffer){
			bufferedTreeSet.add(sj);
		}
		streetGraph = new StreetGraph(osmapiwrapper, bufferedTreeSet);		
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
 	public static Collection<Object[]> values() {
 		return Arrays.asList(new Object[][] {	
 			{TEST_SHOULD_WORK, ALL_NODES_BUFFERED, TEST_SET1},
 			{TEST_SHOULD_WORK, SOME_NODES_BUFFERED, TEST_SET1},
 			{TEST_SHOULD_WORK, NO_NODES_BUFFERED, TEST_SET1},
 			{TEST_SHOULD_FAIL, NO_NODES_BUFFERED, TEST_SET2}
 		});
 	}

	@Test
	public void testgetStreetJunctions() {
		switch(testResult){
			case TEST_SHOULD_WORK:
				assertNotNull(streetGraph.getStreetJunctions());
				break;
			case TEST_SHOULD_FAIL:
				assertTrue(streetGraph.getStreetJunctions().isEmpty());		
				break;
			case TEST_THROWS_EXCEPTION: //Not Used yet
				try{
					streetGraph.getStreetJunctions();
					fail();
				} catch(Exception e){}
				break;
		}		
	}

	@Test
	public void testSetNodes() {
		int size = bufferedTreeSet.size();
		switch(testResult){
			case TEST_SHOULD_WORK:
				streetGraph.setStreetJunctions(bufferedTreeSet);
				assertEquals(3, streetGraph.getStreetJunctions().size());
				break;
			case TEST_SHOULD_FAIL:
				assertTrue(streetGraph.getStreetJunctions().isEmpty());		
				break;
			case TEST_THROWS_EXCEPTION: //Not Used yet
				try{
					streetGraph.getStreetJunctions();
					fail();
				} catch(Exception e){}
				break;
		}
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
