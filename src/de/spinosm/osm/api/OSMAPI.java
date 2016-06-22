package de.spinosm.osm.api;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.spinosm.osm.datatypes.OSMNode;
import de.spinosm.osm.datatypes.OSMRelation;
import de.spinosm.osm.datatypes.OSMWay;

public interface OSMAPI {
	OSMNode getNode(String uid) throws IOException, SAXException, ParserConfigurationException;
	OSMWay getWay(String uid);
	OSMRelation getRelation(String uid);
	InputStream connect(String query) throws IOException;
	Document getOsmData(InputStream xml) throws SAXException, IOException, ParserConfigurationException;

}
