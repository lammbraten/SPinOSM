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
	private static String filePath;
	private static ShortestPath sp;

	
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
		//gmv.paintAlsoGraph(sp.getGraph(), false);
		gmv.setSg(sp.getGraph());
		//gmv.paintAlsoBorder(sp.getBorderVertecies(), true);
		gmv.paintAlsoFinished(sp.getFinishedVertecies(), false);
		gmv.paintAlsoEdgesOf(sp.getVisitedVertecies());
		//gmv.paintAlsoFinished(new LinkedList<RouteableVertex>(sp.getFinishedVertecies()), false);
		//gmv.paintAlsoRoute(sp.getCalculatedShortestPath());
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
