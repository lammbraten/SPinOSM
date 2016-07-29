package de.spinsom.graph;

import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.spinosm.graph.data.OsmApiWrapper;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public class PlayingWithApi {
	//Testdaten
	private static long KOELNERSTRASSE = 182261569l;
	private static long KIMPLERSTRASSE = 25977672l;
	private static long HAFELSTRASSE = 144455735l;
	private static long BIRMESSTRASSE = 27850800l;
	
	private static List<Long> KOELNER_NODES = new ArrayList();
	
	private static long[] KOELNER_NODE_ARRAY = {116108105l,
			1847223183l,
			1850025724l,
			1850025762l,
			1855725703l,
			2880765573l,
			3580697599l,
			283390258l,
			415866943l};
	
	private static OsmApiWrapper osmapi = new OsmApiWrapper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for(long id : KOELNER_NODE_ARRAY)
			KOELNER_NODES.add(id);
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
		ArrayList<Node> nodeList = null;
		for(int i = 0; i < 10; i++){
			//nodeList = (ArrayList<Node>) osmapi.getWayNodesComplete(KOELNERSTRASSE);
		}
		for(Node node : nodeList)
			System.out.println(node.getId());
	}
	
	@Test
	public void testGetNodes() {
		ArrayList<Node> nodeList = null;
		for(int i = 0; i < 10; i++){
			nodeList = (ArrayList<Node>) osmapi.getNodes(KOELNER_NODES);
		}
		for(Node node : nodeList)
			System.out.println(node.getId());
	}
	
}
