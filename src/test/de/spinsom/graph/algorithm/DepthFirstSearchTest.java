package de.spinsom.graph.algorithm;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.DepthFirstSearch;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.WeightFunction;
import de.spinosm.gui.GraphMapViewer;

public class DepthFirstSearchTest {
	
	private static long KOE_HA = 116108105l;

	private static StreetGraph streetgraph;
	private static DataProvider osmapi;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//osmapi = new OsmApiWrapper();
		WeightFunction wf = new DefaultCostFunction();
        osmapi = new LocalProvider("E:\\OSM-Files\\OSM.compiler\\deliveries\\dues-RB_hw.clean.norel.osm", wf);
		streetgraph = new StreetGraph(osmapi);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDFS() {
		streetgraph.getVertex(KOE_HA);
		DepthFirstSearch dfs = new DepthFirstSearch(streetgraph, KOE_HA , 1000);
		
		Common.writeStreetGraph(streetgraph);

		GraphMapViewer gmv = new GraphMapViewer(streetgraph);	
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
