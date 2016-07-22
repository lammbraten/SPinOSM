package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetGraphTest2 {
	
	private static final OsmNode EXISTING_NODE1 = new OsmNode(116108105l, 1,  51.305541, 6.5871852, null, null);
	private static final OsmNode EXISTING_NODE2 = new OsmNode(45107632l, 7, 51.3063599, 6.5877938, null, null);
	private static final OsmNode EXISTING_NODE3 = new OsmNode(45107637l, 2, 51.3087028, 6.5891021, null, null);
	private static final OsmNode EXISTING_NODE4 = new OsmNode(1573918799l, 2, 51.3083259, 6.5869527, null, null);
	private static final OsmNode EXISTING_NODE5 = new OsmNode(415866944l, 2, 51.3082266, 6.5865005, null, null);
	private static final OsmNode EXISTING_NODE6 = new OsmNode(415866943l, 2, 51.3078516, 6.5848661, null, null);
	private static final OsmNode EXISTING_NODE7 = new OsmNode(415866943l , 2, 51.3078953, 6.5848224, null, null);
	private static final OsmNode NOT_EXISTING_NODE = new OsmNode(10011l, 0, null, null, null, null);
	private static final StreetJunction[] EXISTING_NODE_ARRAY = {
			new StreetJunction(EXISTING_NODE1, null),
			new StreetJunction(EXISTING_NODE2, null),
			new StreetJunction(EXISTING_NODE3, null),
			new StreetJunction(EXISTING_NODE4, null),
			new StreetJunction(EXISTING_NODE5, null),
			new StreetJunction(EXISTING_NODE6, null),
			new StreetJunction(EXISTING_NODE7, null)
	};
	private static final StreetJunction[] INITIAL_NODE_ARRAY = {
			new StreetJunction(EXISTING_NODE1, null),
			new StreetJunction(EXISTING_NODE3, null)
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
		streetGraph = new StreetGraph(osmapiwrapper);
		assertEquals(0,streetGraph.getStreetJunctions().size());
		//Test for setting new Nodes
		TreeSet<StreetJunction> bufferedTreeSet = new TreeSet<StreetJunction>();
		for(StreetJunction sj : EXISTING_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		streetGraph.setStreetJunctions(bufferedTreeSet);
		assertEquals(bufferedTreeSet, streetGraph.getStreetJunctions());
	}

	
	@Test()
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
		} catch (IllegalArgumentException e) {
		}catch (Exception e) {fail("Wrong Exeption"+e);}
	}
	@Test()
	public void testGraphBuilding() {
		//streetGraph.setStreetJunctions(new TreeSet<StreetJunction>());
		streetGraph = new StreetGraph(osmapiwrapper);
		for(StreetJunction sj : EXISTING_NODE_ARRAY){
			streetGraph.getNode(sj.getId());
		}
		for(RouteableNode sj : streetGraph.getStreetJunctions()){
			System.out.println(sj.getId());
			System.out.println("\t" +sj.getEdges());
		}
	}
	
	

}
