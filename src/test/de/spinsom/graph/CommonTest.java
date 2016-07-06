package de.spinsom.graph;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.common.Common;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.LatLons;
import de.westnordost.osmapi.map.data.OsmLatLon;

public class CommonTest {
	private static OsmLatLon KREFELD_CAMPUS_SUED = new OsmLatLon(51.3164074, 6.5697757);
	private static OsmLatLon MGLADBACH_CAMPUS = new OsmLatLon(51.1791281, 6.4412589);
	private static double DISTANCE = 17.6;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void calcDistanceTest() {
		double distance = 0.0;
		for(int i = 0; i<10000000 ; i++)		
			distance = Common.calcDistance(KREFELD_CAMPUS_SUED , MGLADBACH_CAMPUS);
		System.out.println(distance);
		assertEquals(DISTANCE, distance, 0.1);
	}

}
