package de.spinosm.gui;

import java.awt.Color;
import java.awt.HeadlessException;
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

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.algorithm.ShortestPath;
import de.spinosm.gui.drawing.ArrowPainter;
import de.spinosm.gui.drawing.GenericWaypointRenderer;
import de.spinosm.gui.drawing.LabeldWayPoint;
import de.spinosm.gui.drawing.RoutePainter;

public class GraphMapViewer extends Thread implements Observer, Runnable{

	private StreetGraph sg;
	private List<StreetVertex> route;
	final static JXMapKit mapView = new JXMapKit();
	private List<Painter<JXMapViewer>> painters;
	private JXMapViewer map;
	private List<StreetVertex> border;
	private List<StreetVertex> finisched;
	
	
	/**
	 * @param graphPath 
	 * @wbp.parser.constructor
	 */
	public GraphMapViewer() {
		this.painters = new ArrayList<Painter<JXMapViewer>>();
		
		//showMap();
	}

	
	public void showMap(){
		map = initMap();   
		handle();
		showFrame();
	}
	
	/**
	 * @throws HeadlessException
	 */
	private void showFrame() throws HeadlessException {
		JFrame frame = new JFrame("SPinOSM");
        frame.getContentPane().add(mapView);
        frame.setSize(1024, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	/**
	 * @param map
	 */
	private void handle() {

 	
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
			for(RouteableVertex routePoint : route)
				track.add(routeableNodeToGeoPosiotion(routePoint));
		}
		RoutePainter routePainter = new RoutePainter(track);
		painters.add(routePainter);
	}

	/**
	 * 
	 */
	private void prepareNodeEdgesForPainting() {
		paintAlsoEdgesOf(sg.vertexSet());
	}


	/**
	 * 
	 */
	public void paintAlsoEdgesOf(Set<StreetVertex> vertecies) {
		for(StreetVertex node : vertecies){
			Color edgeColorForThisVertex = generateRandomColor();
			for(StreetEdge routeEdge : sg.getEdgesForVertex(node, StreetGraph.DEFAULT_DIRECTION, false)){
				addEdgeToPainters(edgeColorForThisVertex, routeEdge);
			}
		}
	}

	/**
	 * 
	 */
	private void prepareVerteciesForPainting(boolean showLabel) {
		Set<Waypoint> vertecieWaypoints = generateWaypointsFromVertecies(sg.vertexSet(), showLabel);
		addVertecieWaypointsToPainters(vertecieWaypoints, new Color(0, 143, 255), 3);
	}

	private void prepareBorderForPainting(boolean showLabel) {
		Set<Waypoint> vertecieWaypoints = generateWaypointsFromVertecies(new HashSet<StreetVertex>(border), showLabel);
		addVertecieWaypointsToPainters(vertecieWaypoints, new Color(238, 77, 46), 5);
	}

	private void prepareFinishedForPainting(boolean showLabel) {
		Set<Waypoint> vertecieWaypoints = generateWaypointsFromVertecies(new HashSet<StreetVertex>(finisched), showLabel);
		addVertecieWaypointsToPainters(vertecieWaypoints, new Color(91, 158, 28), 4);
	}
	
	/**
	 * @param vertecieWaypoints
	 */
	private void addVertecieWaypointsToPainters(Set<Waypoint> vertecieWaypoints, Color color, int radius) {
		WaypointPainter<Waypoint> graphNodesPainter = new WaypointPainter<Waypoint>();	
		graphNodesPainter.setWaypoints(vertecieWaypoints);
		graphNodesPainter.setRenderer(new GenericWaypointRenderer(color, radius));
		painters.add(graphNodesPainter);
	}

	/**
	 * @param set
	 */
	private Set<Waypoint> generateWaypointsFromVertecies(Set<StreetVertex> set, boolean showLabel) {
		Set<Waypoint> vertecieWaypoints = new HashSet<Waypoint>();	
		for(RouteableVertex graphPoint : set){
			GeoPosition gp = new GeoPosition(graphPoint.getPosition().getLatitude(), graphPoint.getPosition().getLongitude());
			if(showLabel)
				vertecieWaypoints.add(new LabeldWayPoint(gp, shortendDoubleString(graphPoint.getDistance()) + ", " + shortendDoubleString(graphPoint.getHeuristic())));
			else
				vertecieWaypoints.add(new LabeldWayPoint(gp, ""));			
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
		ArrowPainter arrowPainter = new ArrowPainter(start, end, edgeColorForThisVertex, shortendDoubleString(routeEdge.getWeight()));	
		painters.add(arrowPainter);
	}


	/**
	 * @param routeEdge
	 * @return
	 */
	protected String shortendDoubleString(double value) {
		return String.format("%.4f", value);
	}

	private Color generateRandomColor() {
		Random genrator = new Random();
		return new Color(genrator.nextInt(255), genrator.nextInt(255), genrator.nextInt(255));
	}

	private GeoPosition routeableNodeToGeoPosiotion(RouteableVertex node) {
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

	public void paintAlsoGraph(StreetGraph graph, boolean showLabel) {
		sg = graph;
		prepareVerteciesForPainting(showLabel);
		prepareNodeEdgesForPainting();	
	}

	public void paintAlsoRoute(List<StreetVertex> graphPath) {
		route = graphPath;
		prepareShortestPathForPainting();			
	}

	public void paintAlsoBorder(List<StreetVertex> borderVertecies, boolean showLabel) {
		border = borderVertecies;
		prepareBorderForPainting(showLabel);			
	}

	public void paintAlsoFinished(List<StreetVertex> finishedVertecies, boolean showLabel) {
		finisched = finishedVertecies;
		prepareFinishedForPainting(showLabel);			
	}


	public StreetGraph getSg() {
		return sg;
	}


	public void setSg(StreetGraph sg) {
		this.sg = sg;
	}



}
