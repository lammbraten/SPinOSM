package de.spinosm.osm.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.spinosm.osm.datatypes.OSMNode;
import de.spinosm.osm.datatypes.OSMRelation;
import de.spinosm.osm.datatypes.OSMWay;

public class Api06Wrapper implements OSMAPI {
	
	private static final String API06_ROOT_URL = "http://www.openstreetmap.org/api/0.6/";

	private URL api06Url;
	//private OSMParser osmParser;
		
	public Api06Wrapper() throws MalformedURLException{
		this.api06Url = new URL(API06_ROOT_URL);	
	}

	@Override
	public InputStream connectWith(String query) throws IOException{
		URL query_url = new URL(api06Url + query);
		HttpURLConnection connection = (HttpURLConnection) query_url.openConnection();
		return connection.getInputStream();
	}

	@Override
	public OSMNode getNode(String id) throws IOException, SAXException, ParserConfigurationException {
		List<OSMNode> nodes = getNodesFrom(getOsmDataFrom(connectWith("node/" + id)));
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
	public Document getOsmDataFrom(InputStream xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		return docBuilder.parse(xml);
	}
	
	public static List<OSMNode> getNodesFrom(Document xmlDocument) {
		return OSMParser.extractNodesfrom(xmlDocument);
	}

	
	



}
