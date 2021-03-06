package de.spinosm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.algorithm.BreadthFirstSearch;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.weights.CrowFliesDistanceWeight;
import de.spinosm.graph.weights.CrowFliesTimeWeight;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.PythagoreanDistanceWeight;
import de.spinosm.graph.weights.PythagoreanTimeWeight;
import de.spinosm.graph.weights.Unweighted;
import de.spinosm.graph.weights.WeightFunction;
import de.spinosm.gui.GraphMapViewer;

public class OSMtoStreetgraphConverter {
	private static final String UNWEIGHTED = "unweighted";
	private static final String PYTHAGOREAN_TIME = "pythagoreanTime";
	private static final String PYTHAGOREAN = "pythagorean";
	private static final String CROW_FLIES_TIME = "crowFliesTime";
	private static final String CROW_FLIES = "crowFlies";
	private static Matcher matcher;
	private static String inFile;
	private static String outFile;
	private static LocalProvider dataprovider;
	private static StreetGraph streetgraph;
	private static long nid;
	private static WeightFunction wf;

	public static void main(String[] args) {

		if (parseArguments(args)){ 
			System.out.println( "starting at " + new Date());
			generateOSMFileReader();
			System.out.println( "parsing at " + new Date());
			//ShortestPathObserver spo = new ShortestPathObserver();			
			new BreadthFirstSearch(streetgraph, nid, -1).searchBraedthFirst();

			
			System.out.println( "  ending at " + new Date() );
			
			//new GraphMapViewer(streetgraph);	
			System.out.println("Write Streetgraph to File:");
			writeStreetgraph();
			
			System.out.println("Read Streetgraph from File:");
			//readStreetgraph();
		}
		else{
			System.out.println("Wrong arguments.");
			System.out.println("-rx InFilePath -wx OutFilePath -nid SomeNodeIdInTheFile -wf SomeValidWeightFunction-Name");
		}
	}

	private static void readStreetgraph() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outFile+".bin"));
			StreetGraph active = (StreetGraph) ois.readObject();
			active.setDataprovider(new DefaultDataProvider());
			ois.close();
			GraphMapViewer gmv = new GraphMapViewer();
			//gmv.paintAlsoGraph(active, true);
			gmv.paintAlsoFinished(new LinkedList<RouteableVertex>(active.vertexSet()), false);
			gmv.showMap();
			//Thread gmvt = new Thread(gmv);
			//gmvt.start();
		} catch (IOException|ClassNotFoundException e) {e.printStackTrace();}
	}

	private static void writeStreetgraph() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFile+".bin") );
			oos.writeObject(streetgraph);
			oos.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateOSMFileReader() {
		dataprovider = new LocalProvider(inFile, wf);
		streetgraph = new StreetGraph(dataprovider);
	}

	private static boolean parseArguments(String[] args) {
		if (args.length < 8)
			return false; // no nid!
		
		if (!args[0].equals("-rx"))   
			return false;
		Pattern   tmpPattern = Pattern.compile( "(.+)\\.osm" );
		matcher = tmpPattern.matcher( args[1] );
		if (matcher.find())
			inFile  = matcher.group(0);
		System.out.println( "    read: " + inFile );
		
		if ( !args[2].equals("-wx"))   
			return false;
		tmpPattern = Pattern.compile( "(.+)\\.bin" );
		matcher = tmpPattern.matcher(args[3]);
		
		if (matcher.find()) 
			outFile = matcher.group(0);
		outFile = outFile.substring(0, outFile.length()-4);
		System.out.println( "   write: " + outFile );
		
		if ( !args[4].equals("-nid"))   
			return false;
		nid = Long.parseLong(args[5]);
				
		if ( !args[6].equals("-wf"))   
			return false;
		parseWF(args[7]);
		return true;
	}
	
	protected static void parseWF(String wfString){
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
	}
}
