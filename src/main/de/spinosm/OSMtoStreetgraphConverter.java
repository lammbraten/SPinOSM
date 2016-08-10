package de.spinosm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedWeightedSubgraph;
import org.jgrapht.graph.GraphUnion;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.Subgraph;

import de.spinosm.common.Common;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.algorithm.DepthFirstSearch;
import de.spinosm.graph.data.LocalProvider;
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
			generateOSMFileReader();
			new DepthFirstSearch(streetgraph, nid, -1);
			System.out.println( "  ending at " + new Date() );
			
			//new GraphMapViewer(streetgraph);	
			System.out.println("Write Streetgraph to File:");
			writeStreetgraph();
			
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
			ois.close();
			new GraphMapViewer(active);					
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void writeStreetgraph() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFile+".bin") );
			oos.writeObject(streetgraph);
			oos.close();				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*LinkedHashSet<DirectedWeightedSubgraph<StreetJunction, StreetEdge>> subgraphs = new LinkedHashSet<DirectedWeightedSubgraph<StreetJunction, StreetEdge>>();
		LinkedHashSet<StreetJunction> vsubset = new LinkedHashSet<StreetJunction>();
		LinkedHashSet<StreetEdge> esubset = new LinkedHashSet<StreetEdge>();
		for(StreetJunction sj : streetgraph.vertexSet()){
			vsubset.add(sj);
			esubset.addAll((Collection<? extends StreetEdge>) streetgraph.incomingEdgesOf(sj));
			if(vsubset.size() > 100){
				subgraphs.add(new DirectedWeightedSubgraph<StreetJunction, StreetEdge>(streetgraph.getGraph(), vsubset, esubset));
				vsubset = new LinkedHashSet<StreetJunction>();
				esubset = new LinkedHashSet<StreetEdge>();
			}
		}		
		
		try {
			int fileCounter = 0;
			for(DirectedWeightedSubgraph<StreetJunction, StreetEdge> sg : subgraphs){
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFile+fileCounter+".bin") );
				DirectedGraph<StreetJunction, StreetEdge> toWrite = sg.getBase();
				oos.writeObject(toWrite);
				oos.close();			
				fileCounter++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	private static void generateOSMFileReader() {
		dataprovider = new LocalProvider(inFile);
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
