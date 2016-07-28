package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.BiDirectionalDijkstra;
import de.spinosm.graph.algorithm.Dijkstra;
import de.spinosm.graph.data.OsmApiWrapper;


public class PlayingWithJgrapht {

	private static StreetGraph streetgraph;
	private static OsmApiWrapper osmapi;
	
	private static long CAMPUS_SUED =  2524487607l; //Campus-Süd
	private static long CAMPUS_WEST = 417403147l; //Campus-West	
	private static long EI_KOE = 45107617l; //Eichhornstraße - Koenlnerstraße
	private static long KOE_HA = 116108105l;  // Kölnerstraße - Hafelstraße
	private static long RA_GRO = 1579971496l;  // Raderfeld - Gropperstraße
	
	private static long start = KOE_HA;
	private static long end = RA_GRO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		osmapi = new OsmApiWrapper();
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
		BiDirectionalDijkstra bid = new BiDirectionalDijkstra(streetgraph);
		List<RouteableNode> graphPath = bid.getShortestPath(startJunction, endJunction);
		//List<RouteableNode> graphPath = new AStar(streetgraph).getShortestPath(startJunction, endJunction);

		for(RouteableNode n : graphPath)
			System.out.println(n);
		
		
		StreetGraph sg = bid.getGraph();
		
		for(RouteableNode rn : sg.vertexSet()){
			System.out.println(rn);
			for(RouteableEdge re :rn.getEdges()){
				System.out.println("\t" + re);
			}
		}
		System.out.println();
		
	}

}
