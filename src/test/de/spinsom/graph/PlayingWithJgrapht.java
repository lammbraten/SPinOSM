package de.spinsom.graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.BiDirectionalDijkstra;
import de.spinosm.graph.algorithm.Dijkstra;
import de.spinosm.graph.algorithm.ObservableShortestPath;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.PythagoreanDistanceWeight;
import de.spinosm.graph.weights.CrowFliesHeuristic;
import de.spinosm.graph.weights.CrowFliesTimeHeuristic;
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
	
	private static long start = SCH_GU;
	private static long end = CAMPUS_SUED;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WeightFunction wf = new PythagoreanDistanceWeight();
		//osmapi = new OsmApiWrapper(wf);
        osmapi = new LocalProvider("C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm", wf);
		streetgraph = new StreetGraph(osmapi);
		/*ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:\\OSM-Files\\OSM.compiler\\deliveries\\graphs\\nrw.streets4.streetgraph.bin"));
		streetgraph = (StreetGraph) ois.readObject();
		streetgraph.setDataprovider(new DefaultDataProvider());
		ois.close();*/
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		//StreetJunction startJunction = osmapi.getStreetJunction(start);
		//StreetJunction endJunction = osmapi.getStreetJunction(end);
		RouteableVertex startJunction = streetgraph.getVertex(start);
		RouteableVertex endJunction = streetgraph.getVertex(end);
		
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
		//ObservableShortestPath sp = new Dijkstra(streetgraph);
		//ObservableShortestPath sp = new BiDirectionalDijkstra(streetgraph);
		ObservableShortestPath sp = new AStar(streetgraph, new CrowFliesHeuristic(endJunction, 1.0f));
		//sp.addObserver(spo);
		//sp.addObserver(gmv);

		
		List<RouteableVertex> graphPath = sp.getShortestPath(startJunction, endJunction);

		GraphMapViewer gmv = new GraphMapViewer();	
		//gmv.paintAlsoGraph(sp.getGraph());
		gmv.setSg(sp.getGraph());
		//gmv.paintAlsoEdgesOf(new HashSet<RouteableVertex>(sp.getFinishedVertecies()));
		//gmv.paintAlsoEdgesOf(new HashSet<StreetVertex>(graphPath));
		//gmv.paintAlsoFinished(sp.getFinishedVertecies(), false);
		gmv.paintAlsoBorder(sp.getBorderVertecies(), false);
		gmv.paintAlsoRoute(graphPath);
		gmv.showMap();
		
		
		//Common.writeStreetGraph(streetgraph);
		System.out.println("------------");
		for(RouteableVertex n : graphPath)
			System.out.println(n.getId() + ": " + n.getDistance());
		System.out.println("------------");		
		
		writeToLogFileedge(sp.getGraph().edgeSet());
		writeToLogFile(sp.getGraph().vertexSet());
		//GraphMapViewer gmv = new GraphMapViewer(sg, graphPath);	
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void writeToLogFile(Set<RouteableVertex> vertexSeT) {
		try {
			PrintWriter writer = new PrintWriter("C:\\Users\\lammbraten\\Desktop\\vertex.txt", "UTF-8");
			for(RouteableVertex v : vertexSeT)
				writer.write(v + ": " + v.isEdgesLoaded() + "\n");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToLogFileedge(Set<StreetEdge> edgeSeT) {
		try {
			PrintWriter writer = new PrintWriter("C:\\Users\\lammbraten\\Desktop\\edge.txt", "UTF-8");
			for(StreetEdge e : edgeSeT)
				writer.write(e + "\n");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
