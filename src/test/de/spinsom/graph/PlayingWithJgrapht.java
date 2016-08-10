package de.spinsom.graph;

import java.io.IOException;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.BiDirectionalDijkstra;
import de.spinosm.graph.algorithm.DefaultHeuristic;
import de.spinosm.graph.algorithm.Dijkstra;
import de.spinosm.graph.algorithm.Heuristic;
import de.spinosm.graph.algorithm.ShortestPath;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.gui.GraphMapViewer;


public class PlayingWithJgrapht {

	private static StreetGraph streetgraph;
	private static DataProvider osmapi;
	
	private static long CAMPUS_SUED =  2524487607l; //Campus-S¸d
	private static long CAMPUS_WEST = 417403147l; //Campus-West	
	private static long CAMPUS_WEST_MAIN_ROAD = 313576111l; //Campus-West faster Found	
	private static long EI_KOE = 45107617l; //Eichhornstraﬂe - Koenlnerstraﬂe
	private static long KOE_HA = 116108105l;  // Kˆlnerstraﬂe - Hafelstraﬂe
	private static long RA_GRO = 1579971496l;  // Raderfeld - Gropperstraﬂe
	private static long SCH_GU = 120044927l; //Kleve
	private static long KREEFLD = 1556233014l; //KREFELD
	private static long DORTMUND = 342488679l; //DORTMUND
	
	private static long start = KREEFLD;
	private static long end = DORTMUND;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//osmapi = new OsmApiWrapper();
        osmapi = new LocalProvider("C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm");
		streetgraph = new StreetGraph(osmapi);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		StreetJunction startJunction = osmapi.getStreetJunction(start);
		StreetJunction endJunction = osmapi.getStreetJunction(end);
		
		streetgraph.addVertex(startJunction);
		streetgraph.addVertex(endJunction);
		
		//DijkstraShortestPath<RouteableNode, StreetEdge> dijkstra = new DijkstraShortestPath<RouteableNode, StreetEdge>(streetgraph, startJunction, endJunction);
		
		//List<StreetEdge> graphPath = DijkstraShortestPath.findPathBetween(streetgraph, startJunction, endJunction);
//		dijkstra.getPath();
		//List<RouteableNode> graphPath = new Dijkstra(streetgraph).getShortestPath(startJunction, endJunction);
		//BiDirectionalDijkstra bid = new BiDirectionalDijkstra(streetgraph);
		//List<RouteableNode> graphPath = bid.getShortestPath(startJunction, endJunction);
		//StreetGraph sg = bid.getGraph();		
		ShortestPath sp = new Dijkstra(streetgraph);
		List<StreetJunction> graphPath = sp.getShortestPath(startJunction, endJunction);
		StreetGraph sg = sp.getGraph();	

		
		

		
		Common.writeStreetGraph(streetgraph);
		System.out.println("------------");
		for(RouteableNode n : graphPath)
			System.out.println(n.getId() + ": " + n.getDistance());
		System.out.println("------------");		
		GraphMapViewer gmv = new GraphMapViewer(sg, graphPath);	
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
