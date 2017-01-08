package de.spinsom;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.spinosm.AnalyseRoutePlaning;

@RunWith(Parameterized.class)
public class PathTestDriver {
	private static final String SOURCE_PATH = "C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm";
	private static final String BASE_PATH = "E:\\OSM-Files\\OSM.compiler\\deliveries\\shortestPaths\\costFunctions";
	private static String KLEVE =  "196218547";
	private static String KREFELD = "437854596";
	
	private static String[] costFunctions = {
			"crowFlies",
			"crowFliesTime",
			"pythagorean",
			"pythagoreanTime",
			"unweighted"
	};
	private String costFunction;


	public PathTestDriver(String costFunction){
		this.costFunction = costFunction;
		new AnalyseRoutePlaning();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
 	@Parameters
 	public static List<String> values() {
 		List<String> parameters = new ArrayList<String>();
 		
		for(int i = 0; i < costFunctions.length; i++){
			parameters.add(costFunctions [i]);
		}
 		
 		return parameters;
 	}


 	@Ignore
	@Test
	public void test() {
		System.out.println(costFunction);
		String args[] = {"-dp", "local", 
				"-dpsrc",SOURCE_PATH, 
				"-wf", costFunction,
				"-s", BASE_PATH + "\\P-local-"+costFunction+"\\",
				"-alg", "dijkstra",
				"-heu", costFunction,
				"-start", KREFELD,
				"-end", KLEVE
		};
	AnalyseRoutePlaning.main(args);
	}
	
	@Test
	public void test1() {
		System.out.println(costFunction);
		String args[] = {"-dp", "local", 
				"-dpsrc",SOURCE_PATH, 
				"-wf", "crowFliesTime",
				"-s", BASE_PATH + "\\P-local-Heuristics-crowFlies-crowFlies\\",
				"-alg", "a-star",
				"-heu", "crowFliesTime",
				"-start", KREFELD,
				"-end", KLEVE
		};
	AnalyseRoutePlaning.main(args);
	}
	
	@Test
	public void test2() {
		System.out.println(costFunction);
		String args[] = {"-dp", "local", 
				"-dpsrc",SOURCE_PATH, 
				"-wf", "crowFliesTime",
				"-s", BASE_PATH + "\\P-local-Heuristics-crowFlies-pyth\\",
				"-alg", "a-star",
				"-heu", "pythagoreanTime",
				"-start", KREFELD,
				"-end", KLEVE
		};
	AnalyseRoutePlaning.main(args);
	}

}
