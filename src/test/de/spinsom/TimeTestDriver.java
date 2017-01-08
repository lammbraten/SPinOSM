package de.spinsom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
public class TimeTestDriver {

	private static final String BINARY_SOURCE_PATH = "C:\\Users\\lammbraten\\Desktop\\nrw.streets.pythagoreanTime.bin";
	private static final String SOURCE_PATH = "C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm";
	private static final String BASE_PATH = "E:\\OSM-Files\\OSM.compiler\\deliveries\\shortestPaths-vorfuehrung\\crowFlies";
	private static final String WEIGHTFUNCTION = "pythagoreanTime";
	private static String BIELEFELD = "1237436430"; 
	private static String BOCHUM = "269700413";
	private static String BONN = "31720180"; 
	private static String DORTMUND = "477805";
	private static String DUISBURG = "80079822";
	private static String DUESSELDORF = "60783082"; 
	private static String HAMM = "1808641408"; 
	private static String ISERLOHN = "599402271"; 
	private static String KLEVE =  "196218547";
	private static String KOELN = "353429081";	
	private static String KREFELD = "437854596";
	private static String MUELHEIM = "1577488734";
	private static String MUENSTER = "1002637775";
	private static String SIEGEN = "98181977";
	private static String WUPPERTAL = "297920702"; 
	
	private static String targets[] = {
			BIELEFELD, 
			BOCHUM, 
			BONN, 
			DORTMUND, 
			DUISBURG,
			DUESSELDORF, 
			HAMM, 
			ISERLOHN,  
			KLEVE,
			KOELN,   
			MUELHEIM, 
			MUENSTER, 
			SIEGEN, 
			WUPPERTAL
			};
	private String target;

	public TimeTestDriver(String target){
		this.target = target;
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
 		
		for(int i = 0; i < targets.length; i++){
			parameters.add(targets[i]);
		}
 		
 		return parameters;
 	}


	@Test
	public void analyseLocalRouteBuilding() {
		System.out.println(target);
			String args[] = {"-dp", "local", 
					"-dpsrc",SOURCE_PATH, 
					"-wf", WEIGHTFUNCTION,
					"-s", BASE_PATH + "\\SP-local-"+target+"\\",
					"-alg", "a-star",
					"-alg", "dijkstra",
					"-alg", "bi-dijkstra",
					"-heu", WEIGHTFUNCTION,
					"-start", KREFELD,
					"-end", target
			};
		AnalyseRoutePlaning.main(args);
	}
 	
 	@Ignore	
	@Test
	public void analysePrecalcRouteBuilding() {
		System.out.println(target);
			String args[] = {"-dp", "precalculated", 
					"-dpsrc",BINARY_SOURCE_PATH, 
					"-wf", WEIGHTFUNCTION,
					"-s", BASE_PATH +"\\SP-precalc-crowFlies"+target+"\\",
					"-alg", "a-star",
					"-alg", "dijkstra",
					"-alg", "bi-dijkstra",
					"-heu", WEIGHTFUNCTION,
					"-start", KREFELD,
					"-end", target
			};
		AnalyseRoutePlaning.main(args);
	}

 	@Ignore	
	@Test
	public void analyseOnlineRouteBuilding() {
		System.out.println(target);
		if(target.equals(DUESSELDORF) ){
				String args[] = {"-dp", "online", 
						"-wf", WEIGHTFUNCTION,
						"-s", BASE_PATH +"\\SP-online-"+target+"\\",
						"-alg", "a-star",
						"-alg", "dijkstra",
						"-alg", "bi-dijkstra",
						"-heu", WEIGHTFUNCTION,
						"-start", KREFELD,
						"-end", target
				};
			AnalyseRoutePlaning.main(args);
		}
	}

}
