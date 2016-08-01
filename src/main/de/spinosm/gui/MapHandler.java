package de.spinosm.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

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

public class MapHandler {
	TileFactoryInfo info;
	DefaultTileFactory tileFactory;
	
	public MapHandler(){
		// Create a TileFactoryInfo for OpenStreetMap
		info = new OSMTileFactoryInfo();
		tileFactory = new DefaultTileFactory(info);;
		tileFactory.setThreadPoolSize(8);		
	}


	public void getExampleRoute(){
		GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
		GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
		GeoPosition mainz     = new GeoPosition(50,  0, 0, 8, 16, 0);
		GeoPosition darmstadt = new GeoPosition(49, 52, 0, 8, 39, 0);
		GeoPosition offenbach = new GeoPosition(50,  6, 0, 8, 46, 0);
	
		// Create a track from the geo-positions
		List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden, mainz, darmstadt, offenbach);
		RoutePainter routePainter = new RoutePainter(track);
	

	// Set the focus
	//mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);

	// Create waypoints from the geo-positions
	Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
			new DefaultWaypoint(frankfurt),
			new DefaultWaypoint(wiesbaden),
			new DefaultWaypoint(mainz),
			new DefaultWaypoint(darmstadt),
			new DefaultWaypoint(offenbach)));

	// Create a waypoint painter that takes all the waypoints
	WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
	waypointPainter.setWaypoints(waypoints);
	
	// Create a compound painter that uses both the route-painter and the waypoint-painter
	List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
	painters.add(routePainter);
	painters.add(waypointPainter);
	
	CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
	//mapViewer.setOverlayPainter(painter);
	}

}
