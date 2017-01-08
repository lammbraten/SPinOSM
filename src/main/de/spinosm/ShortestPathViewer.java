package de.spinosm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.algorithm.ShortestPath;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.gui.GraphMapViewer;

public class ShortestPathViewer {

	private static final String PATH_PAR = "-read";
	private static final String GRAPH_PAR = "-show-graph";
	private static final String ROUTE_PAR = "-show-route";
	private static final String BORDER_PAR = "-show-border";
	private static final String FINISHED_PAR = "-show-finished";
	private static String filePath;
	private static ShortestPath sp;
	private static boolean graphLabel = false;
	private static boolean routeLabel =false;
	private static boolean finishedLabel = false;
	private static boolean borderLabel = false;
	private static boolean showGraph = false;
	private static boolean showRoute = false;
	private static boolean showFinished = false;
	private static boolean showBorder = false;

	
	public static void main(String[] args) {
		parseArguments(args);
		loadShortestPath();
		viewShortestPath();
	}

	private static void loadShortestPath() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			sp = (ShortestPath) ois.readObject();
			//sp.getGraph().setDataprovider(new DefaultDataProvider());
			ois.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
	}

	private static void viewShortestPath() {
		GraphMapViewer gmv = new GraphMapViewer();
		gmv.setSg(sp.getGraph());
		if(showGraph)
			gmv.paintAlsoGraph(sp.getGraph(), graphLabel);
		if(showBorder)
			gmv.paintAlsoBorder(sp.getBorderVertices(), borderLabel);
		if(showFinished)
			gmv.paintAlsoFinished(sp.getFinishedVertices(), finishedLabel);
		if(showRoute)
			gmv.paintAlsoRoute(sp.getCalculatedShortestPath());
		gmv.showMap();
	}

	private static boolean parseArguments(String[] args) {
		for(int i = 0; i < (args.length - 1); i=i+2)			
			if(!parseKeyValue(args[i], args[i+1]))
				return false;
		
		return true;
	}

	private static boolean parseKeyValue(String key, String value) {
		if(isPATHsetPATH(key, value))
			return true;
		if(isBORDERsetBORDER(key, value))
			return true;
		if(isVISITEDsetVISITED(key, value))
			return true;
		if(isROUTEsetROUTE(key, value))
			return true;
		if(isGRAPHsetGRAPH(key, value))
			return true;
		return false;
	}

	private static boolean isGRAPHsetGRAPH(String key, String value) {
		if(key.equals(GRAPH_PAR)){
			showGraph = true;
			graphLabel = Boolean.getBoolean(value);
			return true;
		}
		return false;
	}

	private static boolean isROUTEsetROUTE(String key, String value) {
		if(key.equals(ROUTE_PAR)){
			showRoute = true;
			routeLabel = Boolean.getBoolean(value);
			return true;
		}
		return false;
	}

	private static boolean isVISITEDsetVISITED(String key, String value) {
		if(key.equals(FINISHED_PAR)){
			showFinished = true;
			finishedLabel = Boolean.getBoolean(value);
			return true;
		}
		return false;
	}

	private static boolean isBORDERsetBORDER(String key, String value) {
		if(key.equals(BORDER_PAR)){
			showBorder = true;
			borderLabel = Boolean.getBoolean(value);
			return true;
		}
		return false;
	}

	private static boolean isPATHsetPATH(String key, String value) {
		if(key.equals(PATH_PAR)){
			filePath = value;
			return true;
		}
		return false;
	}

}
