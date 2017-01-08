package de.spinosm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.algorithm.AStar;
import de.spinosm.graph.algorithm.BiDirectionalDijkstra;
import de.spinosm.graph.algorithm.Dijkstra;
import de.spinosm.graph.algorithm.ShortestPath;
import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.CrowFliesDistanceWeight;
import de.spinosm.graph.weights.CrowFliesHeuristic;
import de.spinosm.graph.weights.CrowFliesTimeHeuristic;
import de.spinosm.graph.weights.CrowFliesTimeWeight;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.Heuristic;
import de.spinosm.graph.weights.PythagoreanDistanceHeuristic;
import de.spinosm.graph.weights.PythagoreanDistanceWeight;
import de.spinosm.graph.weights.PythagoreanTimeHeuristic;
import de.spinosm.graph.weights.PythagoreanTimeWeight;
import de.spinosm.graph.weights.Unweighted;
import de.spinosm.graph.weights.WeightFunction;

public class AnalyseRoutePlaning {
	
	private static final String A_STAR_SHORTESTPATH = "a-star";
	private static final String BI_DIJKSTRA_SHORTESTPATH = "bi-dijkstra";
	private static final String DIJKSTRA_SHORTESTPATH = "dijkstra";
	private static final String LOCAL_DATAPROVIDER = "local";
	private static final String ONLINE_DATAPROVIDER = "online";
	private static final String PRECALCULATED_DATAPROVIDER = "precalculated";
	private static final String UNWEIGHTED = "unweighted";
	private static final String PYTHAGOREAN_TIME = "pythagoreanTime";
	private static final String PYTHAGOREAN = "pythagorean";
	private static final String CROW_FLIES_TIME = "crowFliesTime";
	private static final String CROW_FLIES = "crowFlies";
	private static final String HEURISTIC_WEIGHT_PARNAME = "-heuw";
	private static final String HEURISTIC_PARNAME = "-heu";
	private static final String ALG_PARNAME = "-alg";
	private static final String WF_PARNAME = "-wf";
	private static final String DP_PARNAME = "-dp";
	private static final String START_ID_PAR = "-start";
	private static final String END_ID_PAR = "-end";
	private static final String REUSE_PAR = "-r";
	private static final String SAVE_PAR = "-s";
	private static final Object DPSRC_PARNAME = "-dpsrc";	
	private static DataProvider dp;
	private static StreetGraph sg;
	private static LinkedList<ShortestPath> sps;
	private static LinkedList<String> heuristicWeightStrings = new LinkedList<String>();
	private static LinkedList<String> heuristicStrings = new LinkedList<String>();
	private static LinkedList<String> algStrings = new LinkedList<String>();
	private static String wfString;
	private static String dpString;
	private static long startId;
	private static long endId;
	private static boolean reuse;
	private static boolean save;
	private static String dpSrcString;
	private static RouteableVertex startVertex;
	private static RouteableVertex endVertex;
	private static String savePath;
	private static WeightFunction wf;
	private static Date endTime;
	private static Date startTime;
	private static Date initEndTime;
	private static Date initStartTime;
	private static Date graphInitStart;
	private static Date graphInitEnd;

	public AnalyseRoutePlaning(){
		dp = null;
		sg = null;
		sps = null;
		heuristicWeightStrings = new LinkedList<String>();
		heuristicStrings = new LinkedList<String>();
		algStrings = new LinkedList<String>();
		wfString = null;
		dpString = null;
		startId = 0;
		endId = 0;
		reuse = false;
		save = false;
		dpSrcString = null;
		startVertex = null;
		endVertex = null;
		savePath = null;
		wf = null;
		endTime = null;
		startTime = null;
		initEndTime = null;
		initStartTime = null;
		
	}

	public static void main(String[] args) {
		parseArguments(args);
		startAnlyse();
	}

	
	private static boolean parseArguments(String[] args) {
		for(int i = 0; i < (args.length - 1); i=i+2)			
			if(!parseKeyValue(args[i], args[i+1]))
				return false;
		parseGraph();
		parseAlgorithm();
		
		return true;
	}


	/**
	 * @param key
	 * @param value
	 */
	public static boolean parseKeyValue(String key, String value) {
		if(isDPsetDP(key, value))
			return true;
		if(isDPSRCsetDPSRC(key, value))
			return true;
		if(isWFsetWF(key, value))
			return true;
		if(isALGaddALG(key, value))
			return true;
		if(isHEUaddHEU(key, value))
			return true;
		if(isHEUWaddHEUW(key, value))
			return true;
		if(isSTARTsetSTART(key, value))
			return true;
		if(isENDsetEND(key, value))
			return true;
		if(isREUSEsetREUSE(key, value))
			return true;
		if(isSAVEsetSAVE(key, value))
			return true;
		return false;
	}
	
	private static boolean isDPSRCsetDPSRC(String key, String value) {
		if(key.equals(DPSRC_PARNAME)){
			dpSrcString = value;
			return true;
		}
		return false;
	}


	private static boolean isSAVEsetSAVE(String key, String value) {
		if(key.equals(SAVE_PAR)){
			save = true;
			savePath = value;
			return true;
		}
		return false;
	}


	private static boolean isREUSEsetREUSE(String key, String value) {
		if(key.equals(REUSE_PAR)){
			reuse = true;
			return true;
		}
		return false;
	}


	private static boolean isSTARTsetSTART(String key, String value) {
		if(key.equals(START_ID_PAR)){
			startId = Long.parseLong(value);
			return true;
		}
		return false;
	}


	private static boolean isENDsetEND(String key, String value) {
		if(key.equals(END_ID_PAR)){
			endId = Long.parseLong(value);
			return true;
		}
		return false;
	}


	private static boolean isHEUWaddHEUW(String key, String value) {
		if(key.equals(HEURISTIC_WEIGHT_PARNAME)){
			heuristicWeightStrings.add(value);
			return true;
		}
		return false;
	}


	private static boolean isHEUaddHEU(String key, String value) {
		if(key.equals(HEURISTIC_PARNAME)){
			heuristicStrings.add(value);
			return true;
		}
		return false;
	}


	private static boolean isALGaddALG(String key, String value) {
		if(key.equals(ALG_PARNAME)){
			algStrings.add(value);
			return true;
		}
		return false;
	}


	private static boolean isWFsetWF(String key, String value) {
		if(key.equals(WF_PARNAME)){
			wfString = value;
			return true;
		}
		return false;
	}


	private static boolean isDPsetDP(String key, String value) {
		if(key.equals(DP_PARNAME)){
			dpString = value;
			return true;
		}
		return false;
	}


	protected static void parseGraph(){
		setGraphInitStartTime();
		switch (wfString){
		case CROW_FLIES:
			wf = new CrowFliesDistanceWeight();
			break;
		case CROW_FLIES_TIME:
			wf = new CrowFliesTimeWeight();
			break;
		case PYTHAGOREAN:
			wf = new PythagoreanDistanceWeight();
			break;
		case PYTHAGOREAN_TIME:
			wf = new PythagoreanTimeWeight();
			break;
		case UNWEIGHTED:
			wf = new Unweighted();
			break;
		default:
			wf = new DefaultCostFunction();
		}
		
		switch (dpString){
		case PRECALCULATED_DATAPROVIDER:
			dp = new DefaultDataProvider();
			break;
		case ONLINE_DATAPROVIDER:
			dp = new OsmApiWrapper(wf);
			break;
		case LOCAL_DATAPROVIDER:
			dp = new LocalProvider(dpSrcString, wf);
			break;
		default:
			throw new IllegalArgumentException("Dataprovider not valid");
		}
		
		sg = new StreetGraph(dp);
		setGraphInitEndTime();
	}
	
	private static void setGraphInitEndTime() {
		graphInitEnd = new Date();
		
	}

	private static void setGraphInitStartTime() {
		graphInitStart= new Date();
		
	}

	protected static void parseAlgorithm(){
		sps = new LinkedList<ShortestPath>();
		for(String algName : algStrings){
			switch(algName){
			case DIJKSTRA_SHORTESTPATH:
				sps.add(new Dijkstra(sg));
				break;
			case BI_DIJKSTRA_SHORTESTPATH:
				sps.add(new BiDirectionalDijkstra(sg));
				break;
			case A_STAR_SHORTESTPATH:
				for(Heuristic h: parseHeuristicNames())
					sps.add(new AStar(sg, h));
				break;
			default:
				break;
			}
		}
		
	}
	
	private static List<Heuristic> parseHeuristicNames() {
		List<Heuristic> heuristics = new LinkedList<Heuristic>();
		List<Float> heuristicWeights = new LinkedList<Float>();
		
		for(String heuristicWeightString : heuristicWeightStrings)
			heuristicWeights.add(Float.parseFloat(heuristicWeightString));
		if(heuristicWeights.isEmpty())
			heuristicWeights.add(1.0f);
		for(String hName: heuristicStrings){
			switch(hName){
			case CROW_FLIES:
				for(float heuristicWeight : heuristicWeights)
					heuristics.add(new CrowFliesHeuristic(null, heuristicWeight));
				break;
			case CROW_FLIES_TIME:
				for(float heuristicWeight : heuristicWeights)
					heuristics.add(new CrowFliesTimeHeuristic(null, heuristicWeight, StreetGraph.MAXSPEED));
				break;
			case PYTHAGOREAN:
				for(float heuristicWeight : heuristicWeights)
					heuristics.add(new PythagoreanDistanceHeuristic(null, heuristicWeight));
				break;
			case PYTHAGOREAN_TIME:
				for(float heuristicWeight : heuristicWeights)
					heuristics.add(new PythagoreanTimeHeuristic(null, heuristicWeight, StreetGraph.MAXSPEED));
				break;
			default:
				break;
			}
		}
		return heuristics;
	}


	private static void initAnalyse(){
		setInitStartTime();
		if(dp instanceof DefaultDataProvider)
			sg = readSG();
		else{
			sg = new StreetGraph(dp);			
		}
		startVertex = sg.getVertex(startId);
		endVertex = sg.getVertex(endId);
		setInitEndTime();
	}
	
	public static void startAnlyse(){
		initAnalyse();
		int i = 0;
		for(ShortestPath sp : sps){
			i++;
			if(!reuse)
				initAnalyse();
			startTimeMeasurment();
			sp.setGraph(sg);
			sp.getShortestPath(startVertex, endVertex);
			stopTimeMeasurment();
			printResults(sp, i);
			if(save)
				writeSP(sp, i);
		}
	}


	private static void printResults(ShortestPath sp, int i) {
		long time = endTime.getTime() - startTime.getTime() ;
		long initTime = initEndTime.getTime() - initStartTime.getTime();
		long graphInitTime = graphInitEnd.getTime() - graphInitStart.getTime();		

		try {
			File f = new File(savePath);
			f.mkdirs();
			PrintWriter writer = new PrintWriter(savePath+i+"-logfile"+".txt", "UTF-8");
			writer.write("Results for " + sp.getClass().getName() + " with " + wf.getClass().getName()+"\n");
			writer.write("DataProvider: " + sp.getGraph().getDataprovider().getClass().getName()+"\n");
			writer.write("Start: " + startVertex + ", End: " + endVertex +"\n");

			writer.write("Graph-Init-Time: " + graphInitTime + "ms\n");
			writer.write("Init-Time: " + initTime + "ms\n");			
			writer.write("Time: " + time + "ms\n");
			writer.write("Nodes covered:"+ sp.getFinishedVertices().size() +  " from " + sp.getGraph().vertexSet().size()+"\n");	
		
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {e.printStackTrace();} 
	}


	private static void stopTimeMeasurment() {
		endTime = new Date();
		
	}


	private static void startTimeMeasurment() {
		startTime = new Date();
		
	}


	private static void setInitStartTime() {
		initStartTime = new Date();
		
	}


	private static void setInitEndTime() {
		initEndTime = new Date();
		
	}


	private static void writeSP(ShortestPath sp, int i) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath+i+".bin") );
			oos.writeObject(sp);
			oos.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static StreetGraph readSG() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dpSrcString));
			StreetGraph sg  = (StreetGraph) ois.readObject();
			sg.setDataprovider(new DefaultDataProvider());
			ois.close();	
			return sg;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
