package de.spinosm.graph.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.TreeMap;

import de.westnordost.osmapi.map.MapDataParser;
import de.westnordost.osmapi.map.OsmMapDataFactory;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Way;

public class OsmXmlFilerHandler{

	File xmlFile;
	OsmXmlHandler handler;
	OsmMapDataFactory mdf;
	MapDataParser mdp;
	
	HashMap<Long, Node> nodes = new HashMap<Long, Node>();
	HashMap<Long, Way> ways = new HashMap<Long, Way>();
	
	public OsmXmlFilerHandler(File xmlFile) {
		this.xmlFile = xmlFile;
		try {		
			handler = new OsmXmlHandler(nodes, ways);
			mdf = new OsmMapDataFactory();
			mdp = new MapDataParser(handler, mdf);
			mdp.parse(new FileInputStream(xmlFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Node getNode(long key) {
		return nodes.get(key);
		
	}	
}
