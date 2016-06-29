package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetGraphTest2 {
	
	private static final int TEST_SHOULD_WORK = 0;
	private static final int TEST_SHOULD_FAIL = 1;
	private static final int TEST_THROWS_EXCEPTION = 2;
	private static final OsmNode EXISTING_NODE1 = new OsmNode(1636160756l, 1,  50.8992329, 7.0318133, null, null);
	private static final OsmNode EXISTING_NODE2 = new OsmNode(203986826l, 7, 50.8975988, 7.0364495, null, null);
	private static final OsmNode EXISTING_NODE3 = new OsmNode(1861698092l, 2, 50.8978525, 7.0351034, null, null);
	private static final OsmNode NOT_EXISTING_NODE = new OsmNode(10011l, 0, null, null, null, null);
	private static final StreetJunction[] EXISTING_NODE_ARRAY = {
			new StreetJunction(EXISTING_NODE1),
			new StreetJunction(EXISTING_NODE2),
			new StreetJunction(EXISTING_NODE3)
	};
	private static final StreetJunction[] INITIAL_NODE_ARRAY = {
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
	
	static StreetGraph streetGraph;
	static OsmApiWrapper osmapiwrapper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		osmapiwrapper = new OsmApiWrapper();
		TreeSet<StreetJunction> bufferedTreeSet = new TreeSet<StreetJunction>();
		for(StreetJunction sj : INITIAL_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		streetGraph = new StreetGraph(osmapiwrapper, bufferedTreeSet);
	}
	
	@Before
	public void setUpBeforeMethod() throws Exception {
		TreeSet<StreetJunction> bufferedTreeSet = new TreeSet<StreetJunction>();
		for(StreetJunction sj : INITIAL_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		streetGraph = new StreetGraph(osmapiwrapper, bufferedTreeSet);
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		streetGraph= null;
	}

	@Test
	public void testGetSetStreetJunctions() {
		//Test for initialValues
		assertEquals(INITIAL_NODE_ARRAY.length, streetGraph.getStreetJunctions().size());
		//Test for NullPointerExption
		streetGraph.setStreetJunctions(null);		
		try {
			streetGraph.getStreetJunctions().size();
			fail("No Exeption was thrown");
		} catch (NullPointerException e){//alles gut 
		}catch (Exception e) {fail("Wrong Exeption");}
		//Test for settin new Nodes
		TreeSet<StreetJunction> bufferedTreeSet = new TreeSet<StreetJunction>();
		for(StreetJunction sj : EXISTING_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		streetGraph.setStreetJunctions(bufferedTreeSet);
		assertEquals(bufferedTreeSet, streetGraph.getStreetJunctions());
	}


	@Test(timeout = 10000)
	public void testGetNode() {
		System.out.println(streetGraph.getStreetJunctions().size());
		//Test for loaded Nodes
		assertEquals(EXISTING_NODE1.getId(), streetGraph.getNode(EXISTING_NODE1.getId()).getId());
		assertEquals(EXISTING_NODE3.getId(), streetGraph.getNode(EXISTING_NODE3.getId()).getId());
		
		//Test for get Node from Server
		assertEquals(EXISTING_NODE2.getId(), streetGraph.getNode(EXISTING_NODE2.getId()).getId());
		
		//Test for Getting an Not-EXisting-Node from Server
		try {
			assertNull(streetGraph.getNode(NOT_EXISTING_NODE.getId()));
			fail("This should throw an Exeption");
		} catch (NullPointerException e) {
		}catch (Exception e) {fail("Wrong Exeption");}
	}

}
