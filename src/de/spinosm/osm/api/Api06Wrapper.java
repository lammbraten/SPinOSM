package de.spinosm.osm.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.spinosm.osm.datatypes.OSMNode;
import de.spinosm.osm.datatypes.OSMRelation;
import de.spinosm.osm.datatypes.OSMWay;

public class Api06Wrapper implements OSMAPI {
	
	private static final String API06_ROOT_URL = "http://www.openstreetmap.org/api/0.6/";

	private URL api06_url;
		
	public Api06Wrapper() throws MalformedURLException{
		this.api06_url = new URL(API06_ROOT_URL);		
	}

	@Override
	public InputStream connect(String query) throws IOException{
		URL query_url = new URL(api06_url + query);
		HttpURLConnection connection = (HttpURLConnection) query_url.openConnection();
		return connection.getInputStream();
	}

	@Override
	public OSMNode getNode(String id) throws IOException, SAXException, ParserConfigurationException {
		List<OSMNode> nodes = getNodes(getOsmData(connect("node/" + id)));
		if (!nodes.isEmpty()) {
			return nodes.iterator().next();
		}
		return null;
	}

	@Override
	public OSMWay getWay(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OSMRelation getRelation(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document getOsmData(InputStream xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		return docBuilder.parse(xml);
	}
	
	public static List<OSMNode> getNodes(Document xmlDocument) {
		List<OSMNode> osmNodes = new ArrayList<OSMNode>();

		// Document xml = getXML(8.32, 49.001);
		Node osmRoot = xmlDocument.getFirstChild();
		NodeList osmXMLNodes = osmRoot.getChildNodes();
		for (int i = 1; i < osmXMLNodes.getLength(); i++) {
			Node item = osmXMLNodes.item(i);
			if (item.getNodeName().equals("node")) {
				NamedNodeMap attributes = item.getAttributes();
				NodeList tagXMLNodes = item.getChildNodes();
				Map<String, String> tags = new HashMap<String, String>();
				for (int j = 1; j < tagXMLNodes.getLength(); j++) {
					Node tagItem = tagXMLNodes.item(j);
					NamedNodeMap tagAttributes = tagItem.getAttributes();
					if (tagAttributes != null) {
						tags.put(tagAttributes.getNamedItem("k").getNodeValue(), tagAttributes.getNamedItem("v")
								.getNodeValue());
					}
				}
				Node namedItemID = attributes.getNamedItem("id");
				Node namedItemLat = attributes.getNamedItem("lat");
				Node namedItemLon = attributes.getNamedItem("lon");
				Node namedItemVersion = attributes.getNamedItem("version");

				String id = namedItemID.getNodeValue();
				String latitude = namedItemLat.getNodeValue();
				String longitude = namedItemLon.getNodeValue();
				String version = "0";
				if (namedItemVersion != null) {
					version = namedItemVersion.getNodeValue();
				}

				osmNodes.add(new OSMNode(id, latitude, longitude, version));
			}

		}
		return osmNodes;
	}

	
	



}
