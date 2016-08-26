package de.spinosm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.spinosm.graph.RouteableVertex;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.algorithm.DepthFirstSearch;
import de.spinosm.graph.data.DefaultDataProvider;
import de.spinosm.graph.data.LocalProvider;
import de.spinosm.graph.weights.AsTheCrowFliesDistanceWeight;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.PythagoreanDistanceWeight;
import de.spinosm.graph.weights.WeightFunction;
import de.spinosm.gui.GraphMapViewer;

public class OSMtoStreetgraphConverter {
	private static Matcher matcher;
	private static String inFile;
	private static String outFile;
	private static LocalProvider dataprovider;
	private static StreetGraph streetgraph;
	private static long nid;

	public static void main(String[] args) {

		if (parseArguments(args)){ 
			System.out.println( "starting at " + new Date());
			//generateOSMFileReader();
			//ShortestPathObserver spo = new ShortestPathObserver();			
			//new DepthFirstSearch(streetgraph, nid, -1).searchDephtFirst();

			
			System.out.println( "  ending at " + new Date() );
			
			//new GraphMapViewer(streetgraph);	
			System.out.println("Write Streetgraph to File:");
			//writeStreetgraph();
			
			System.out.println("Read Streetgraph from File:");
			readStreetgraph();
		}
		else{
			System.out.println("Wrong arguments.");
			System.out.println("-rx InFilePath -wx OutFilePath -nid SomeNodeIdInTheFile");
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
		WeightFunction wf = new AsTheCrowFliesDistanceWeight();
		dataprovider = new LocalProvider(inFile, wf);
		streetgraph = new StreetGraph(dataprovider);
	}

	private static boolean parseArguments(String[] args) {
		if (args.length < 6)
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
				
		return true;
	}
}
