package de.spinsom;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.AnalyseRoutePlaning;
import de.spinosm.OSMtoStreetgraphConverter;

public class Converter {

	private static final String startNode = "116108105";
	private static final String TARGET_PATH = "E:\\OSM-Files\\OSM.compiler\\deliveries\\graphs\\";
	private static final String SOURCE_PATH = "C:\\Users\\lammbraten\\Desktop\\nrw.streets.osm";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() {
		//System.gc();
	}	
	
	@Test
	public void localToCrowFliesDistance() {
		String args[] = {"-rx", SOURCE_PATH,
				"-wx", TARGET_PATH + "nrw.streets.crowFlies.bin",
				"-nid", startNode,
				"-wf", "crowFlies"
		};
		OSMtoStreetgraphConverter.main(args);
	}
	
	@Test
	public void localToCrowFliesTime() {
		String args[] = {"-rx", SOURCE_PATH,
				"-wx", TARGET_PATH + "nrw.streets.crowFliesTime.bin",
				"-nid", startNode,
				"-wf", "crowFliesTime"
		};
		OSMtoStreetgraphConverter.main(args);
	}
	
	@Test
	public void localTopythagoreanDistance() {
		String args[] = {"-rx", SOURCE_PATH,
				"-wx", TARGET_PATH + "nrw.streets.pythagorean.bin",
				"-nid", startNode,
				"-wf", "pythagorean"
		};
		OSMtoStreetgraphConverter.main(args);
	}
	
	@Test
	public void localTopythagoreanTime() {
		String args[] = {"-rx", SOURCE_PATH,
				"-wx", TARGET_PATH + "nrw.streets.pythagoreanTime.bin",
				"-nid", startNode,
				"-wf", "pythagoreanTime"
		};
		OSMtoStreetgraphConverter.main(args);
	}
	
	@Test
	public void localToUnweighted() {
		String args[] = {"-rx", SOURCE_PATH,
				"-wx", TARGET_PATH + "nrw.streets.unweighted.bin",
				"-nid", startNode,
				"-wf", "unweighted"
		};
		OSMtoStreetgraphConverter.main(args);
	}
	

}
