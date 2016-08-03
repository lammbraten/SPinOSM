package de.spinosm.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.xml.sax.SAXException;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.BiDirectionalDijkstra;
import de.spinosm.graph.algorithm.Dijkstra;
import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.ApiResponseReader;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.data.BoundingBox;

import javax.swing.JButton;

public class MainFrame extends JFrame {

	
	private static long CAMPUS_SUED =  2524487607l; //Campus-S¸d
	private static long CAMPUS_WEST = 417403147l; //Campus-West	
	private static long EI_KOE = 45107617l; //Eichhornstraﬂe - Koenlnerstraﬂe
	private static long KOE_HA = 116108105l;  // Kˆlnerstraﬂe - Hafelstraﬂe
	private static long RA_GRO = 1579971496l;  // Raderfeld - Gropperstraﬂe
	
	private static long start = RA_GRO;
	private static long end = KOE_HA;
	
	private JPanel contentPane;
	final static JXMapKit jXMapKit = new JXMapKit();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {


			        // Display the viewer in a JFrame
					new MainFrame();
					
					
			        JFrame frame = new JFrame("JXMapviewer2 Example 6");
			        frame.getContentPane().add(jXMapKit);
			        frame.setSize(800, 600);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		BoundingBox bounds = new BoundingBox(51.3042508, 6.5919314, 51.3051842, 6.5900314);	
		
		
		
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapKit.setTileFactory(tileFactory);

        
        JXMapViewer map = jXMapKit.getMainMap();
        
        OsmApiWrapper osmapi = new OsmApiWrapper();
        StreetGraph streetgraph = new StreetGraph(osmapi);
        Dijkstra aStar = new Dijkstra(streetgraph);
                
		StreetJunction startJunction = osmapi.getStreetJunction(start);
		StreetJunction endJunction = osmapi.getStreetJunction(end);
		
		streetgraph.addVertex(startJunction);
		streetgraph.addVertex(endJunction);
        
        List<RouteableNode> route = aStar.getShortestPath(startJunction, endJunction);
        List<GeoPosition> track = new LinkedList<GeoPosition>();
        List<GeoPosition> graph = new LinkedList<GeoPosition>();
        
        for(RouteableNode routePoint : route)
        	track.add(new GeoPosition(routePoint.getPosition().getLatitude(), routePoint.getPosition().getLongitude()));

        for(RouteableNode graphPoint : aStar.getGraph().vertexSet())
        	graph.add(new GeoPosition(graphPoint.getPosition().getLatitude(), graphPoint.getPosition().getLongitude()));

        
		RoutePainter routePainter = new RoutePainter(track);

		// Set the focus
		map.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

        jXMapKit.setZoom(11);
        
        
		// Create waypoints from the geo-positions
		Set<Waypoint> waypoints = new HashSet<Waypoint>();
		
		for(GeoPosition gp : track)
			waypoints.add(new DefaultWaypoint(gp));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
		waypointPainter.setWaypoints(waypoints);

		
		// Create waypoints from the geo-positions
		Set<Waypoint> graphNodes = new HashSet<Waypoint>();
		
		for(GeoPosition gp : graph)
			graphNodes.add(new DefaultWaypoint(gp));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<Waypoint> graphNodesPainter = new WaypointPainter<Waypoint>();
		graphNodesPainter.setWaypoints(graphNodes);
		
		// Create a compound painter that uses both the route-painter and the waypoint-painter
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(routePainter);
		painters.add(waypointPainter);
		painters.add(graphNodesPainter);
		
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		map.setOverlayPainter(painter);


	}

	
}
