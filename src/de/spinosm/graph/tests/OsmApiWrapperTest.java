package de.spinosm.graph.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

import de.spinosm.graph.OsmApiWrapper;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.common.errors.OsmNotFoundException;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.handler.DefaultMapDataHandler;
import de.westnordost.osmapi.map.handler.MapDataHandler;


public class OsmApiWrapperTest {
	private long testNodeId;
	private int version;
	private boolean testNodeResult;
	private Class<Throwable> testException;
	private double lat; 
	private double lon;

	private static OsmApiWrapper osmapiwrapper;
	

	

 	

 	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		osmapiwrapper = new OsmApiWrapper();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		osmapiwrapper = null;
	}
	

	@Test
	public void getNode(){
		OsmConnection osm = new OsmConnection("https://api.openstreetmap.org/api/0.6/", "osmapi unit test", null);
		
		new MapDataDao(osm);

	}
	
	@Test
	public void testNotFound()
	{
		//OsmConnection osm = new OsmConnection("http://api06.dev.openstreetmap.org/api/0.6/", "osmapi unit test", null);

		MapDataDao dao = new MapDataDao(osmapiwrapper.getConnection());
		MapDataHandler h = new DefaultMapDataHandler();

		try
		{
			dao.getWayComplete(Long.MAX_VALUE, h);
			fail();
		}
		catch(OsmNotFoundException e)
		{
		}

		try
		{
			dao.getRelationComplete(Long.MAX_VALUE, h);
			fail();
		}
		catch(OsmNotFoundException e)
		{
		}
	}
	
	@Test	
	public void testWayComplete()
	{
		new MapDataDao(osmapiwrapper.getConnection()).getWayComplete(27308882, new DefaultMapDataHandler());
	}

	@Test
	public void testRelationComplete()
	{
		new MapDataDao(osmapiwrapper.getConnection()).getRelationComplete(3301989, new DefaultMapDataHandler());
	}

}
