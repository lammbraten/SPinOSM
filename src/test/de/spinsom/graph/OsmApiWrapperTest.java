package de.spinsom.graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.WeightFunction;

@RunWith(Parameterized.class)
public class OsmApiWrapperTest {
	private static final int NODEINDEX = 0;
	private static final int WAYINDEX = 1;
	private static final int RELATIONINDEX = 2;
	private static final boolean TEST_SHOULD_FAIL = true;
	private static final long[] EXISTING_ELEMENTS = {
			2880765573l, //Node
			144455735l, //Way
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
 	public static Collection<Object[]> values() {
 		return Arrays.asList(new Object[][] {	
 			{!TEST_SHOULD_FAIL, EXISTING_ELEMENTS},
 			{TEST_SHOULD_FAIL, FAILING_ELEMENTS},
 			{TEST_SHOULD_FAIL, FAILING_ELEMENTS2}
 		});
 	}
 	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WeightFunction wf = new DefaultCostFunction();
		osmapiwrapper = new OsmApiWrapper(wf);
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
	public void getRelationsForWay(){
		if(testShouldFail){
			try{
				assertFalse(osmapiwrapper.getRelationsForWay(testcase[WAYINDEX]).size() > 0);		
			}catch(Exception e){}
		}else
			assertTrue(osmapiwrapper.getRelationsForWay(testcase[WAYINDEX]).size() > 0);
	}
	
	@Test 
	public void getWaysForNode(){
		if(testShouldFail){
			try{
				assertFalse(osmapiwrapper.getWaysForNode(testcase[NODEINDEX]).size() > 0);		
			}catch(Exception e){}
		}else
			assertTrue(osmapiwrapper.getWaysForNode(testcase[NODEINDEX]).size() > 0);
	}	
	
	@Test 
	public void getRelationsForNode(){
		if(testShouldFail){
			try{
				assertFalse(osmapiwrapper.getRelationsForNode(testcase[NODEINDEX]).size() > 0);	
			}catch(Exception e){}
		}else
			assertTrue(osmapiwrapper.getRelationsForNode(testcase[NODEINDEX]).size() > 0);
	}	
	
	@Test
	@Ignore("No testdata yet")
	public void getRelationsForRelation(){
		if(testShouldFail){
			try{
				assertFalse(osmapiwrapper.getRelationsForRelation(testcase[RELATIONINDEX]).size() > 0);			
			}catch(Exception e){}
		}else
			assertTrue(osmapiwrapper.getRelationsForRelation(testcase[RELATIONINDEX]).size() > 0);
	}	
		
	@Test
	public void getRelation(){
		if(testShouldFail)
			assertNull(osmapiwrapper.getRelation(testcase[RELATIONINDEX]));
		else
			assertNotNull(osmapiwrapper.getRelation(testcase[RELATIONINDEX]));
	}
	
	@Test
	public void getStreetJunction(){
		if(testShouldFail)
			try{
				assertNull(osmapiwrapper.getStreetJunction(testcase[NODEINDEX]));
				fail();
			}catch(IllegalArgumentException e){
				
			}catch(Exception e){fail();}
		else{
			StreetJunction sj = osmapiwrapper.getStreetJunction(testcase[NODEINDEX]);
			assertNotNull(sj);		
		}
	}
}
