package de.spinosm.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

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
import de.spinosm.graph.data.LocalProvider;
import de.westnordost.osmapi.map.data.BoundingBox;

public class GraphMapViewer {

	StreetGraph sg;
	final static JXMapKit jXMapKit = new JXMapKit();
	
	public GraphMapViewer(StreetGraph g) {
		this.sg = g;
		
		showMap();
		
        JFrame frame = new JFrame("JXMapviewer2 Example 6");
        frame.getContentPane().add(jXMapKit);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		
	}
	
	public void showMap(){
		BoundingBox bounds = new BoundingBox(51.3042508, 6.5919314, 51.3051842, 6.5900314);	
		
		
		
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapKit.setTileFactory(tileFactory);

        
        JXMapViewer map = jXMapKit.getMainMap();
        
        //OsmApiWrapper osmapi = new OsmApiWrapper();
        LocalProvider osmapi = new LocalProvider("E:\\OSM-Files\\OSM.compiler\\deliveries\\dues-RB_hw.clean.norel.osm");
        List<GeoPosition> graph = new LinkedList<GeoPosition>();
        
       
        for(RouteableNode graphPoint : sg.vertexSet())
        	graph.add(new GeoPosition(graphPoint.getPosition().getLatitude(), graphPoint.getPosition().getLongitude()));

        

        jXMapKit.setZoom(11);
        
        

		
		// Create waypoints from the geo-positions
		Set<Waypoint> graphNodes = new HashSet<Waypoint>();
		
		for(GeoPosition gp : graph)
			graphNodes.add(new DefaultWaypoint(gp));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<Waypoint> graphNodesPainter = new WaypointPainter<Waypoint>();
		graphNodesPainter.setWaypoints(graphNodes);
		
		// Create a compound painter that uses both the route-painter and the waypoint-painter
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(graphNodesPainter);
		
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		map.setOverlayPainter(painter);


	}
}
