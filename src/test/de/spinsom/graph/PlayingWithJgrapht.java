package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.depr.Dijkstra;


public class PlayingWithJgrapht {

	private static StreetGraph streetgraph;
	private static OsmApiWrapper osmapi;
	
	private static long start = 300677464l;
	private static long end = 1579971675l;
	
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
		//DijkstraShortestPath<RouteableNode, StreetEdge> dijkstra = new DijkstraShortestPath<RouteableNode, StreetEdge>(streetgraph, startJunction, endJunction);
		
		//List<StreetEdge> graphPath = dijkstra.findPathBetween(streetgraph, startJunction, endJunction);
		List<RouteableNode> graphPath = new Dijkstra(streetgraph).getShortestPath(startJunction, endJunction);
		for(RouteableNode n : graphPath)
			System.out.println(n);
	}

}
