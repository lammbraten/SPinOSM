package de.spinosm.osm.api.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

import de.spinosm.osm.api.Api06Wrapper;
import de.spinosm.osm.datatypes.OSMNode;

@RunWith(Parameterized.class)
public class Api06WrapperTest {
	private String testNodeId;
	private boolean testNodeResult;
	private Class<Throwable> testException;
	private String lat; 
	private String lon;
	private String version;
	
	private static Api06Wrapper api06wrapper;
	
	public Api06WrapperTest(String id, boolean result, Class<Throwable> exception, String lat, String lon, String version){
		super();
		this.testNodeId = id;
		this.testNodeResult = result;
		this.testException = exception;
		this.lat = lat;
		this.lon = lon;
		this.version = version;
	}

    @Rule
    public ExpectedException expected = ExpectedException.none();
	
 	@Parameters
 	public static Collection values() {
 		return Arrays.asList(new Object[][] {
 				{"2637350171", true, null, "51.3166648","6.5710590", "1"}, 
 				{"1001", false, null, "51.3166648","6.5710590", "1" }, 
 				{"10011", false, FileNotFoundException.class, null, null, null}  
 		});
 	}

	@BeforeClass
	public static void start() throws MalformedURLException {
		System.out.println("Start Api06Wrapper");
		api06wrapper = new Api06Wrapper();
	}
 
	@AfterClass
	public static void kill() {
		System.out.println("Kill Api06Wrapper");
		api06wrapper = null;
	}	
	
	@Test(timeout = 1000)
	public void connect() throws IOException {
        if (testException != null)
            expected.expect(testException);
		
		assertNotNull(api06wrapper.connectWith("node/"+testNodeId));
	}
	
	@Test
	public void getNode() throws IOException, SAXException, ParserConfigurationException {
        if (testException != null)
            expected.expect(testException);
		
		OSMNode refNode = new OSMNode(testNodeId, lat, lon, version);
		OSMNode testNode = api06wrapper.getNode(testNodeId);
		assertEquals(testNode.equals(refNode), testNodeResult);
	}
	
	@Ignore("not Implemented yet")
	@Test
	public void getWay(){}

	@Ignore("not Implemented yet")
	@Test
	public void getRelation(){}
	
	@Test
	public void getOsmData() throws IOException, SAXException, ParserConfigurationException{
        if (testException != null)
            expected.expect(testException);
        
		InputStream xml = api06wrapper.connectWith("node/"+testNodeId);
		assertNotNull(api06wrapper.getOsmDataFrom(xml));
	}
	
	@Ignore("not ready to test now")
	@Test
	public void getNodes(){}
}
