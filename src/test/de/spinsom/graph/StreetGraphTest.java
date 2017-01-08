package de.spinsom.graph;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.WeightFunction;
import de.spinosm.gui.GraphMapViewer;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetGraphTest {
	
	private static final OsmNode EXISTING_NODE1 = new OsmNode(116108105l, 1,  51.305541, 6.5871852, null, null);
	private static final OsmNode EXISTING_NODE2 = new OsmNode(45107632l, 7, 51.3063599, 6.5877938, null, null);
	private static final OsmNode EXISTING_NODE3 = new OsmNode(45107637l, 2, 51.3087028, 6.5891021, null, null);
	private static final OsmNode EXISTING_NODE4 = new OsmNode(1573918799l, 2, 51.3083259, 6.5869527, null, null);
	private static final OsmNode EXISTING_NODE5 = new OsmNode(415866944l, 2, 51.3082266, 6.5865005, null, null);
	private static final OsmNode EXISTING_NODE6 = new OsmNode(415866943l, 2, 51.3078516, 6.5848661, null, null);
	private static final OsmNode EXISTING_NODE7 = new OsmNode(415866943l , 2, 51.3078953, 6.5848224, null, null);
	private static final OsmNode NOT_EXISTING_NODE = new OsmNode(10011l, 0, null, null, null, null);
	private static final StreetVertex[] EXISTING_NODE_ARRAY = {
			new StreetVertex(EXISTING_NODE1),
			new StreetVertex(EXISTING_NODE2),
			new StreetVertex(EXISTING_NODE3),
			new StreetVertex(EXISTING_NODE4),
			new StreetVertex(EXISTING_NODE5),
			new StreetVertex(EXISTING_NODE6),
			new StreetVertex(EXISTING_NODE7)
	};
	private static final StreetVertex[] INITIAL_NODE_ARRAY = {
			new StreetVertex(EXISTING_NODE1),
			new StreetVertex(EXISTING_NODE3)
	};
	private static final StreetVertex[] NO_NODES_BUFFERED = {
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
		WeightFunction wf = new DefaultCostFunction();
		osmapiwrapper = new OsmApiWrapper(wf);
		TreeSet<StreetVertex> bufferedTreeSet = new TreeSet<StreetVertex>();
		for(StreetVertex sj : INITIAL_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		//streetGraph = new StreetGraph(osmapiwrapper, bufferedTreeSet);
	}
	
	@Before
	public void setUpBeforeMethod() throws Exception {
		TreeSet<StreetVertex> bufferedTreeSet = new TreeSet<StreetVertex>();
		for(StreetVertex sj : INITIAL_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		streetGraph = new StreetGraph(osmapiwrapper);
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	@Test
	public void testGetSetStreetJunctions() {
		//Test for NullPointerExption
		streetGraph = new StreetGraph(osmapiwrapper);
		assertEquals(0,streetGraph.getVertices().size());
		//Test for setting new Nodes
		TreeSet<RouteableVertex> bufferedTreeSet = new TreeSet<RouteableVertex>();
		for(StreetVertex sj : EXISTING_NODE_ARRAY){
			bufferedTreeSet.add(sj);
		}
		streetGraph.setVertices(bufferedTreeSet);
		assertEquals(bufferedTreeSet, streetGraph.getVertices());
	}


	@Test()
	public void testGetNode() {
		//Test for loaded Nodes
		assertEquals(EXISTING_NODE1.getId(), streetGraph.getVertex(EXISTING_NODE1.getId()).getId());
		assertEquals(EXISTING_NODE3.getId(), streetGraph.getVertex(EXISTING_NODE3.getId()).getId());
		
		//Test for get Node from Server
		assertEquals(EXISTING_NODE2.getId(), streetGraph.getVertex(EXISTING_NODE2.getId()).getId());
		
		//Test for Getting an Not-EXisting-Node from Server
		try {
			assertNull(streetGraph.getVertex(NOT_EXISTING_NODE.getId()));
		} catch (IllegalArgumentException e) {
		}catch (Exception e) {fail("Wrong Exeption"+e);}
	}
	
	@Test()
	public void testGraphBuilding() {
		streetGraph = new StreetGraph(osmapiwrapper);

		for(StreetVertex sj : EXISTING_NODE_ARRAY){
			streetGraph.getVertex(sj.getId());
		}
	}
}
