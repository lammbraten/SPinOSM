package de.spinsom.graph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.StreetVertex;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.graph.weights.DefaultCostFunction;
import de.spinosm.graph.weights.WeightFunction;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetJunctionTest {
	private static final OsmNode EXISTING_NODE1 = new OsmNode(116108105l, 1,  51.305541, 6.5871852, null, null);
	private static final OsmNode EXISTING_NODE2 = new OsmNode(45107632l, 7, 51.3063599, 6.5877938, null, null);
	private static final OsmNode EXISTING_NODE3 = new OsmNode(45107637l, 2, 51.3087028, 6.5891021, null, null);

	
	private static final StreetVertex[] INITIAL_NODE_ARRAY = {
			new StreetVertex(EXISTING_NODE1),
			new StreetVertex(EXISTING_NODE3)
	};

	static OsmApiWrapper osmapiwrapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		WeightFunction wf = new DefaultCostFunction();
		osmapiwrapper = new OsmApiWrapper(wf);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSerializeable() {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( "C:\\Users\\lammbraten\\Dropbox\\Bachelorarbeit\\05_Tests\\TestStreetJunction.sg" ) );
			oos.writeObject(INITIAL_NODE_ARRAY[0] );
			oos.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}


		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream( new FileInputStream( "C:\\Users\\lammbraten\\Dropbox\\Bachelorarbeit\\05_Tests\\TestStreetJunction.sg" ) );
			StreetVertex sj = (StreetVertex) ois.readObject();
			ois.close();		
			System.out.println(sj.getId() + ": " +sj.getPosition().getLatitude());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
