package de.spinosm.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.ShortestPath;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.gui.drawing.RoutePainter;
import de.westnordost.osmapi.map.data.BoundingBox;

public class MainFrame extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2823547452898441096L;
	
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
		
		System.out.println("Hallo neuer Code");
		
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapKit.setTileFactory(tileFactory);

        
        JXMapViewer map = jXMapKit.getMainMap();
        
        //OsmApiWrapper osmapi = new OsmApiWrapper();
        LocalProvider osmapi = new LocalProvider("E:\\OSM-Files\\OSM.compiler\\deliveries\\dues-RB_hw.clean.norel.osm");
        StreetGraph streetgraph = new StreetGraph(osmapi);
        ShortestPath aStar = new AStar(streetgraph, null);
                
		StreetJunction startJunction = osmapi.getStreetJunction(start);
		StreetJunction endJunction = osmapi.getStreetJunction(end);
		
		streetgraph.addVertex(startJunction);
		streetgraph.addVertex(endJunction);
        
        List<StreetJunction> route = aStar.getShortestPath(startJunction, endJunction);
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
