package de.spinosm.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
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

import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.gui.drawing.ArrowPainter;
import de.spinosm.gui.drawing.GenericWaypointRenderer;
import de.spinosm.gui.drawing.RoutePainter;
import de.westnordost.osmapi.map.data.BoundingBox;

public class GraphMapViewer extends Thread implements Observer, Runnable{

	private StreetGraph sg;
	private List<StreetJunction> route;
	final static JXMapKit mapView = new JXMapKit();
	private List<Painter<JXMapViewer>> painters;
	private JXMapViewer map;
	
	public GraphMapViewer(StreetGraph g) {
		this(g, null);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public GraphMapViewer(StreetGraph g, List<StreetJunction> graphPath) {
		this.sg = g;
		this.route = graphPath;
		this.painters = new ArrayList<Painter<JXMapViewer>>();
		
		
		showMap();
		
        JFrame frame = new JFrame("SPinOSM");
        frame.getContentPane().add(mapView);
        frame.setSize(1024, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public void showMap(){
		map = initMap();   
		handle();
	}

	/**
	 * @param map
	 */
	private void handle() {
		prepareVerteciesForPainting();
		prepareNodeEdgesForPainting();
		prepareShortestPathForPainting();	 	
		paintOnMap(map);
	}

	/**
	 * @param map
	 */
	private void paintOnMap(JXMapViewer map) {
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		map.setOverlayPainter(painter);
	}

	/**
	 * @return
	 */
	private JXMapViewer initMap() {
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapView.setTileFactory(tileFactory);
		JXMapViewer map = mapView.getMainMap();
		mapView.setZoom(17);
		return map;
	}

	/**
	 * 
	 */
	private void prepareShortestPathForPainting() {
		List<GeoPosition> track = new LinkedList<GeoPosition>();
		if(route != null){
			for(RouteableNode routePoint : route)
				track.add(routeableNodeToGeoPosiotion(routePoint));
		}
		RoutePainter routePainter = new RoutePainter(track);
		painters.add(routePainter);
	}

	/**
	 * 
	 */
	private void prepareNodeEdgesForPainting() {
		for(StreetJunction node : sg.vertexSet()){
			Color edgeColorForThisVertex = generateRandomColor();
			for(StreetEdge routeEdge : sg.getEdgesForNode(node, StreetGraph.DEFAULT_DIRECTION, false)){
				addEdgeToPainters(edgeColorForThisVertex, routeEdge);
			}
		}
	}

	/**
	 * 
	 */
	private void prepareVerteciesForPainting() {
		Set<Waypoint> vertecieWaypoints = generateWaypointsFromGraphVertecies();
		addVertecieWaypointsToPainters(vertecieWaypoints);
	}

	/**
	 * @param vertecieWaypoints
	 */
	private void addVertecieWaypointsToPainters(Set<Waypoint> vertecieWaypoints) {
		WaypointPainter<Waypoint> graphNodesPainter = new WaypointPainter<Waypoint>();	
		graphNodesPainter.setWaypoints(vertecieWaypoints);
		graphNodesPainter.setRenderer(new GenericWaypointRenderer());
		painters.add(graphNodesPainter);
	}

	/**
	 * @param vertecies
	 */
	private Set<Waypoint> generateWaypointsFromGraphVertecies() {
		Set<Waypoint> vertecieWaypoints = new HashSet<Waypoint>();	
		for(RouteableNode graphPoint : sg.vertexSet()){
			GeoPosition gp = new GeoPosition(graphPoint.getPosition().getLatitude(), graphPoint.getPosition().getLongitude());
			vertecieWaypoints.add(new DefaultWaypoint(gp));
		}
		return vertecieWaypoints;
	}

	/**
	 * @param edgeColorForThisVertex
	 * @param routeEdge
	 */
	private void addEdgeToPainters(Color edgeColorForThisVertex, StreetEdge routeEdge) {
		GeoPosition start = routeableNodeToGeoPosiotion(routeEdge.getStart());
		GeoPosition end = routeableNodeToGeoPosiotion(routeEdge.getEnd());	   
		double label = routeEdge.getWeight();
		String formattedLabel = String.format("%.4f", label);
		//ArrowPainter arrowPainter = new ArrowPainter(start, end, edgeColorForThisVertex, formattedLabel);	
		//painters.add(arrowPainter);
	}

	private Color generateRandomColor() {
		Random genrator = new Random();
		return new Color(genrator.nextInt(255), genrator.nextInt(255), genrator.nextInt(255));
	}

	private GeoPosition routeableNodeToGeoPosiotion(RouteableNode node) {
		return new GeoPosition(node.getPosition().getLatitude(), node.getPosition().getLongitude());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		handle();
		
	}
	
	@Override 
	public void run(){
		try {sleep(1000);}
		catch(InterruptedException e) {}
		handle();
	}
}
