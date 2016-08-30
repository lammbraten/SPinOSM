package de.spinsom;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.AnalyseRoutePlaning;

public class TestDriver {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void analyseRouteBuilding() {
		String args[] = {"-dp", "precalculated", 
				//"-dpsrc","C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm", 
				"-dpsrc","E:\\OSM-Files\\OSM.compiler\\deliveries\\graphs\\nrw.streets.crowFlies.bin", 
				"-wf", "crowFlies",
				"-s", "E:\\OSM-Files\\OSM.compiler\\deliveries\\shortestPaths\\SP",
				"-alg", "a-star",
				"-alg", "dijkstra",
				"-alg", "bi-dijkstra",
				"-heu", "crowFlies",
				"-start", "2524487607",
				"-end", "120044927",
				"-r", "true"
		};
		AnalyseRoutePlaning.main(args);
	}

}
