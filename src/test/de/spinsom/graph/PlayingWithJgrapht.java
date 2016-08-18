package de.spinsom.graph;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.BiDirectionalDijkstra;
import de.spinosm.graph.algorithm.Dijkstra;
import de.spinosm.graph.algorithm.ObservableShortestPath;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.PythagoreanDistanceWeight;
import de.spinosm.graph.weights.SimpleCrowFliesHeuristic;
import de.spinosm.graph.weights.SimpleCrowFliesTimeHeuristic;
import de.spinosm.graph.weights.WeightFunction;
import de.spinosm.gui.GraphMapViewer;
import de.spinosm.gui.ShortestPathObserver;


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
	private static long PADERBORN = 6566103l; 
	
	private static long start = RA_GRO;
	private static long end = SCH_GU;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//osmapi = new OsmApiWrapper();
		WeightFunction wf = new PythagoreanDistanceWeight();
        osmapi = new LocalProvider("C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm", wf);
		streetgraph = new StreetGraph(osmapi);
		//ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:\\OSM-Files\\OSM.compiler\\deliveries\\graphs\\nrw.streets3.streetgraph.bin"));
		//streetgraph = (StreetGraph) ois.readObject();
		//streetgraph.setDataprovider(new DefaultDataProvider());
		//ois.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		//StreetJunction startJunction = osmapi.getStreetJunction(start);
		//StreetJunction endJunction = osmapi.getStreetJunction(end);
		StreetVertex startJunction = streetgraph.getVertex(start);
		StreetVertex endJunction = streetgraph.getVertex(end);
		
		//streetgraph.addVertex(startJunction);
		//streetgraph.addVertex(endJunction);
		
		//DijkstraShortestPath<RouteableNode, StreetEdge> dijkstra = new DijkstraShortestPath<RouteableNode, StreetEdge>(streetgraph, startJunction, endJunction);
		
		//List<StreetEdge> graphPath = DijkstraShortestPath.findPathBetween(streetgraph, startJunction, endJunction);
//		dijkstra.getPath();
		//List<RouteableNode> graphPath = new Dijkstra(streetgraph).getShortestPath(startJunction, endJunction);
		//BiDirectionalDijkstra bid = new BiDirectionalDijkstra(streetgraph);
		//List<RouteableNode> graphPath = bid.getShortestPath(startJunction, endJunction);
		//StreetGraph sg = bid.getGraph();	

		//Thread gmvt = new Thread(gmv);
		//gmvt.start();
		ShortestPathObserver spo = new ShortestPathObserver();
		//spo.start();
		ObservableShortestPath sp = new Dijkstra(streetgraph);
		//ObservableShortestPath sp = new AStar(streetgraph, new SimpleCrowFliesHeuristic(endJunction, 1));
		//sp.addObserver(spo);
		//sp.addObserver(gmv);

		
		List<StreetVertex> graphPath = sp.getShortestPath(startJunction, endJunction);

		GraphMapViewer gmv = new GraphMapViewer();	
		//gmv.paintAlsoGraph(sp.getGraph());
		gmv.paintAlsoRoute(graphPath);
		gmv.paintAlsoBorder(sp.getBorderVertecies());
		//gmv.paintAlsoFinished(sp.getFinishedVertecies());
		gmv.showMap();
		
		
		//Common.writeStreetGraph(streetgraph);
		System.out.println("------------");
		for(RouteableVertex n : graphPath)
			System.out.println(n.getId() + ": " + n.getDistance());
		System.out.println("------------");		
		//GraphMapViewer gmv = new GraphMapViewer(sg, graphPath);	
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
