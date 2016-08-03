package de.spinosm.graph.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.westnordost.osmapi.common.XmlParser;
import de.westnordost.osmapi.map.MapDataParser;
import de.westnordost.osmapi.map.OsmMapDataFactory;

public class OsmXmlElementParser extends XmlParser{

	File xmlFile;
	
	public OsmXmlElementParser(File xmlFile) {
		this.xmlFile = xmlFile;
		try {		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			OsmXmlHandler handler = new OsmXmlHandler();
			OsmMapDataFactory mdf = new OsmMapDataFactory();
			//saxParser.parse( this.xmlFile, handler);
			MapDataParser mdp = new MapDataParser(handler, mdf);
			
			InputStream in = new FileInputStream(xmlFile);
			mdp.parse(in);
		} catch (SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getNode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onStartElement() throws Exception {	}

	@Override
	protected void onEndElement() throws Exception {	}

	
	
}
