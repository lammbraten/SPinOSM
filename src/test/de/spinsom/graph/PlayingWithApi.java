package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Way;

public class PlayingWithApi {
	//Testdaten
	private static long KOELNERSTRASSE = 182261569l;
	private static long KIMPLERSTRASSE = 25977672l;
	private static long HAFELSTRASSE = 144455735l;
	private static long BIRMESSTRASSE = 27850800l;
	
	private static OsmApiWrapper osmapi = new OsmApiWrapper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Ignore
	public void testKoelnerStraﬂe() {
		for(Long n : osmapi.getWay(KOELNERSTRASSE).getNodeIds()){
			List<Way> ways = osmapi.getWaysForNode(n);
			System.out.println(n + ":" + ways.size());
			if(ways.size() > 2){
				for(Way way : ways){
					System.out.println("\t" +way.getId() +": ");
					for (long nid : way.getNodeIds()){
						System.out.println("\t\t"+nid);
					}
				}
			}
		}
		System.out.println(osmapi.getWaysForNode(415866943l));
	}
	
	@Test
	@Ignore
	public void testOtherStreet() {
		for(Long n : osmapi.getWay(BIRMESSTRASSE).getNodeIds()){
			List<Way> ways = osmapi.getWaysForNode(n);
			System.out.println(n + ":" + ways.size());
			if(ways.size() >= 2){
				for(Way way : ways){
					System.out.println("\t" +way.getId() +": ");
					
					for (long nid : way.getNodeIds()){
						//System.out.println("\t\t"+nid);
					}
				}
			}
		}
		/*System.out.println("-----------------------------");
		for(long nid : osmapi.getWay(HAFELSTRASSE).getNodeIds())
			System.out.println(nid);
		System.out.println(osmapi.getWaysForNode(415866943l));*/
	}

	@Test
	public void testGetWayComplete() {
		ArrayList<Node> nodeList = (ArrayList<Node>) osmapi.getWayNodesComplete(KOELNERSTRASSE);
		for(Node node : nodeList)
			System.out.println(node.getId());
	}
	
}
