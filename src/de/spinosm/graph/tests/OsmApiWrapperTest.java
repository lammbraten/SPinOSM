package de.spinosm.graph.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import de.spinosm.graph.data.OsmApiWrapper;

@RunWith(Parameterized.class)
public class OsmApiWrapperTest {
	private static final int NODEINDEX = 0;
	private static final int WAYINDEX = 1;
	private static final int RELATIONINDEX = 2;
	private static final boolean TEST_SHOULD_FAIL = true;
	private static final long[] EXISTING_ELEMENTS = {
			26576175L, //Node
			23564402L, //Way
			180627L //Relation
	};
	private static final long[] FAILING_ELEMENTS = {
			10011L, //Node
			10011L, //Way
			10011L //Relation
	};
	private static final long[] FAILING_ELEMENTS2 = {
			-10011L, //Node
			-10011L, //Way
			-10011L //Relation
	};
	
	private long[] testcase;
	private boolean testShouldFail;
	private static OsmApiWrapper osmapiwrapper;
	
	public OsmApiWrapperTest(boolean testShouldFail, long[] testcase){
		super();
		this.testcase = testcase;
		this.testShouldFail = testShouldFail;
	}

    @Rule
    public ExpectedException expected = ExpectedException.none();
	
 	@Parameters
 	public static Collection values() {
 		return Arrays.asList(new Object[][] {	
 			{!TEST_SHOULD_FAIL, EXISTING_ELEMENTS},
 			{TEST_SHOULD_FAIL, FAILING_ELEMENTS},
 			{TEST_SHOULD_FAIL, FAILING_ELEMENTS2}
 		});
 	}
 	
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
		if(testShouldFail)
			assertNull(osmapiwrapper.getNode(testcase[NODEINDEX]));
		else
			assertNotNull(osmapiwrapper.getNode(testcase[NODEINDEX]));
	}
	
	@Test
	public void getWay(){
		if(testShouldFail)
			assertNull(osmapiwrapper.getWay(testcase[WAYINDEX]));
		else
			assertNotNull(osmapiwrapper.getWay(testcase[WAYINDEX]));
	}
	
	@Test
	public void getRelation(){
		if(testShouldFail)
			assertNull(osmapiwrapper.getRelation(testcase[RELATIONINDEX]));
		else
			assertNotNull(osmapiwrapper.getRelation(testcase[RELATIONINDEX]));
	}
}
