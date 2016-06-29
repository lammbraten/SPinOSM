package de.spinosm.osm.api;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.spinosm.osm.datatypes.OSMNode;

public class OSMParser {
	private Document osmXmlFile;
	
	/*OSMParser(Document osmXmlFile){
		this.osmXmlFile = osmXmlFile;
	}*/

	/**
	 * Bewusst als statische Methode deklariert, da das xmlDocument eh nicht gespeichert werden soll, 
	 * sondern nur die darin enthaltenen Nodes. Man braucht das File danach nie wieder.
	 * @param xmlDocument
	 * @return
	 */
	public static List<OSMNode> extractNodesfrom(Document xmlDocument) {
		List<OSMNode> osmNodes = new ArrayList<OSMNode>();

		Node osmRoot = xmlDocument.getFirstChild();
		NodeList osmXMLNodes = osmRoot.getChildNodes();
		iterateThroughOsmFile(osmNodes, osmXMLNodes);
		return osmNodes;
	}

	/**
	 * Durchläuft die OSM-Daten und überprüft auf "Node"-Bezeichner
	 * @param osmNodes
	 * @param osmXMLNodes
	 */
	private static void iterateThroughOsmFile(List<OSMNode> osmNodes, NodeList osmXMLNodes) {
		for (int i = 1; i < osmXMLNodes.getLength(); i++) {
			Node item = osmXMLNodes.item(i);
			checkForNodeName(osmNodes, item);
		}
	}

	/**
	 * Überprüft ob der XML-Node auch ein OSM-Node ist.
	 * @param osmNodes
	 * @param item
	 */
	private static void checkForNodeName(List<OSMNode> osmNodes, Node item) {
		if (item.getNodeName().equals("node")) {
			NamedNodeMap attributes = item.getAttributes();
			osmNodes.add(createNewOSMNodefrom(attributes));
		}
	}

	/**
	 * Erstellt eine neuen OSMNode-Instanz aus den XML-Attributen.
	 * @param attributes
	 * @return neuer OSM-Node mit den Attributen aus der XML-Datei
	 */
	private static OSMNode createNewOSMNodefrom(NamedNodeMap attributes) {
		String id = attributes.getNamedItem("id").getNodeValue();
		String latitude = attributes.getNamedItem("lat").getNodeValue();
		String longitude = attributes.getNamedItem("lon").getNodeValue();
		String version = getAttributesNamedItemNodeValue(attributes);

		return new OSMNode(id, latitude, longitude, version);
	}
	
	private static String getAttributesNamedItemNodeValue(NamedNodeMap attributes){
		String version = "0";
		if (attributes.getNamedItem("version") != null) {
			version =  attributes.getNamedItem("version").getNodeValue();
		}
		return version;
	}


	
}
