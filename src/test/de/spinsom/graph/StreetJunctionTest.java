package de.spinsom.graph;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.spinosm.graph.RouteableNode;
import de.spinosm.graph.StreetGraph;
import de.spinosm.graph.StreetJunction;
import de.spinosm.graph.data.OsmApiWrapper;
import de.spinosm.gui.GraphMapViewer;
import de.westnordost.osmapi.map.data.OsmNode;

public class StreetJunctionTest {
	private static final OsmNode EXISTING_NODE1 = new OsmNode(116108105l, 1,  51.305541, 6.5871852, null, null);
	private static final OsmNode EXISTING_NODE2 = new OsmNode(45107632l, 7, 51.3063599, 6.5877938, null, null);
	private static final OsmNode EXISTING_NODE3 = new OsmNode(45107637l, 2, 51.3087028, 6.5891021, null, null);

	
	private static final StreetJunction[] INITIAL_NODE_ARRAY = {
			new StreetJunction(EXISTING_NODE1),
			new StreetJunction(EXISTING_NODE3)
	};

	static OsmApiWrapper osmapiwrapper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		osmapiwrapper = new OsmApiWrapper();
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
			StreetJunction sj = (StreetJunction) ois.readObject();
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
