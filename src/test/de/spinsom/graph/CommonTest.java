package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;

import de.spinosm.common.Common;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.WeightFunction;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.OsmLatLon;
import de.westnordost.osmapi.map.data.Way;

public class CommonTest {
	private static OsmLatLon KREFELD_CAMPUS_SUED = new OsmLatLon(51.316273, 6.569993);
	private static OsmLatLon KREFELD_CAMPUS_SUED_BIB = new OsmLatLon(51.316642, 6.569489);
	private static OsmLatLon KREFELD_CAMPUS_WEST = new OsmLatLon(51.3240241, 6.5539725);
	private static OsmLatLon MGLADBACH_CAMPUS = new OsmLatLon(51.1791281, 6.4412589);
	private static OsmLatLon FISCHELN_RATHAUS = new OsmLatLon(51.305541, 6.5871852);
	private static OsmLatLon STADTBAD_FISCHELN = new OsmLatLon(51.3089328, 6.5837814);
	private static OsmLatLon WEDELSTRASSE_19 = new OsmLatLon(51.3062602, 6.5898457);
	private static OsmLatLon WEDELSTRASSE_17 = new OsmLatLon(51.3063454, 6.590146);
	private static OsmLatLon MIT = new OsmLatLon(42.3553475, -71.1054415);
	
	private static OsmLatLon[] TARTANBAHN = {
			new OsmLatLon(51.3115358, 6.5826916),
			new OsmLatLon(51.3116099, 6.5826717),
			new OsmLatLon(51.3116842, 6.582676),
			new OsmLatLon(51.3117531, 6.5827099),
			new OsmLatLon(51.3118221, 6.5827609),
			new OsmLatLon(51.311869, 6.5828176),
			new OsmLatLon(51.3119029, 6.5829069),
			new OsmLatLon(51.3119362, 6.58299),
			new OsmLatLon(51.3119574, 6.5830876),
			new OsmLatLon(51.31196, 6.5832107),
			new OsmLatLon(51.3119494, 6.5833337),
			new OsmLatLon(51.3119282, 6.5834441),
			new OsmLatLon(51.3118964, 6.583512),
			new OsmLatLon(51.3118579, 6.5835921),
			new OsmLatLon(51.3110881, 6.5843118),
			new OsmLatLon(51.3109892, 6.584331),
			new OsmLatLon(51.3109096, 6.5843267),
			new OsmLatLon(51.3108353, 6.5842928),
			new OsmLatLon(51.3107717, 6.5842291),
			new OsmLatLon(51.3107186, 6.5841485),
			new OsmLatLon(51.3106815, 6.5840467),
			new OsmLatLon(51.3106576, 6.5839321),
			new OsmLatLon(51.310647, 6.583809),
			new OsmLatLon(51.3106576, 6.583686),
			new OsmLatLon(51.3106841, 6.5835671),
			new OsmLatLon(51.3107213, 6.5834653),
			new OsmLatLon(51.3107581, 6.5834023),	
			new OsmLatLon(51.3115358, 6.5826916)
	};
			
	
	private static double LONG_DISTANCE = 17600;
	private static double MIDDLE_DISTANCE = 445.7;
	private static double SHORT_DISTANCE = 22.9;
	private static double TARTANBAHN_DISTANCE = 400;
	
	private static double COST_FOR_MIDDLEDISTANCE = 0.008904603;

	@Test
	public void calcPythDistanceTest() {
		double long_distance = 0.0;
		System.out.println(long_distance);
		long_distance = Common.PythagoreanDistance(KREFELD_CAMPUS_SUED , MGLADBACH_CAMPUS);
		double middle_distance = Common.PythagoreanDistance(FISCHELN_RATHAUS , STADTBAD_FISCHELN);
		double short_distance = Common.PythagoreanDistance(WEDELSTRASSE_19 , WEDELSTRASSE_17);
		assertEquals(SHORT_DISTANCE, short_distance, 15);
		assertEquals(MIDDLE_DISTANCE, middle_distance, 100);	
		assertEquals(LONG_DISTANCE, long_distance, 4000);	
	}
	
	@Test
	public void astheCrowFliesTest() {
		double long_distance = 0.0;
		System.out.println(long_distance);
		long_distance = Common.asTheCrowFlies(KREFELD_CAMPUS_SUED , MGLADBACH_CAMPUS);
		double middle_distance = Common.asTheCrowFlies(FISCHELN_RATHAUS , STADTBAD_FISCHELN);
		double short_distance = Common.asTheCrowFlies(WEDELSTRASSE_19 , WEDELSTRASSE_17);
		assertEquals(SHORT_DISTANCE, short_distance, 0.1);
		assertEquals(MIDDLE_DISTANCE, middle_distance, 10);	
		assertEquals(LONG_DISTANCE, long_distance, 500);	
	}

	@Test
	public void testDistanceOnTartanbahnCrow() {
		double length = 0.0;
		for(int i = 0; TARTANBAHN.length-1 > i; i++){
			length += Common.asTheCrowFlies(TARTANBAHN[i], TARTANBAHN[i+1]);
		}
		System.out.println("CrowFlies:" + length);
		assertEquals(TARTANBAHN_DISTANCE, length, 0.5);
		
	}
	
	@Test
	public void testDistanceOnTartanbahnPyth() {
		double length = 0.0;
		for(int i = 0; TARTANBAHN.length-1 > i; i++){
			length += Common.PythagoreanDistance(TARTANBAHN[i], TARTANBAHN[i+1]);
		}
		System.out.println("Pyth:" + length);
		assertEquals(TARTANBAHN_DISTANCE, length, 105);
	}

	@Test
	public void calcCostTest() {
		WeightFunction wf = new DefaultCostFunction();
		OsmApiWrapper osmapiwrapper = new OsmApiWrapper(wf);
		Way koelnerstrasse = osmapiwrapper.getWay(182261569);
		LinkedList<LatLon> latlons = new LinkedList<LatLon>();
		for(long nid : koelnerstrasse.getNodeIds())
			latlons.add(osmapiwrapper.getNode(nid).getPosition());
		//double cost = osmapiwrapper.calcCost(latlons, koelnerstrasse);
		//assertEquals(COST_FOR_MIDDLEDISTANCE, cost, 0.00001);
	}
	
	@Test
	public void writeProtocol() {
		System.out.println("------");
		for(int i = 0; i < 10; i++)
			//System.out.println("10^"+i+": "+benchmarkCrow(Math.pow(10,i)));
		System.out.println("------");
		for(int i = 0; i < 10; i++)
			System.out.println("10^"+i+": "+benchmarkPyth(Math.pow(10,i)));
						
		System.out.println("------");
		System.out.println("x\t\t\t| Pythagorean\t\t| Crowflies\t\t| Difference\t");
		System.out.println("Mensa - Bibliothek\t|"+values(KREFELD_CAMPUS_SUED, KREFELD_CAMPUS_SUED_BIB));
		System.out.println("Campus-Süd - Campus-West|"+values(KREFELD_CAMPUS_SUED, KREFELD_CAMPUS_WEST));
		System.out.println("Campus-Süd - Campus-MG\t|"+values(KREFELD_CAMPUS_SUED, MGLADBACH_CAMPUS));
		System.out.println("Campus-Süd - MIT\t|" +values(KREFELD_CAMPUS_SUED, MIT));
	}

	/**
	 * 
	 */
	public String values(OsmLatLon node1, OsmLatLon node2) {
		double crow = Common.asTheCrowFlies(node1 , node2);
		double pyth = Common.PythagoreanDistance(node1 , node2);
		double diff = (pyth/crow);
		return ""+pyth+"\t| "+crow+"\t|"+diff;
	}
	
	private long benchmarkCrow(double d){
		Date start = new Date();
		for(int i = 0; i < d; i++)
			Common.asTheCrowFlies(KREFELD_CAMPUS_SUED , MGLADBACH_CAMPUS);
		Date end = new Date();
		return end.getTime() - start.getTime() ;
	}
	
	private long benchmarkPyth(double d){
		Date start = new Date();
		for(int i = 0; i < d; i++)
			Common.PythagoreanDistance(KREFELD_CAMPUS_SUED , MGLADBACH_CAMPUS);
		Date end = new Date();
		return end.getTime() - start.getTime() ;
	}
}
