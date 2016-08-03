package de.spinsom.graph.data;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.data.LocalProvider;

public class TestLocalProvider {

	private static LocalProvider lp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lp = new LocalProvider("E:\\OSM-Files\\OSM.compiler\\deliveries\\dues-RB_hw.clean.norel.osm");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		lp.getStreetJunction(0l);
	}

}
