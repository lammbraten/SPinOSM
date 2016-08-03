package de.spinosm.graph.data;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.westnordost.osmapi.map.data.BoundingBox;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.MapDataHandler;

public class OsmXmlHandler extends DefaultHandler implements MapDataHandler  {
	@Override
	public void startElement( String namespaceURI, String localName, String qName, Attributes atts ){
	System.out.println( "namespaceURI: " + namespaceURI );
	System.out.println( "localName: " + localName );
	System.out.println( "qName: " + qName );
	for ( int i = 0; i < atts.getLength(); i++ )
		System.out.printf( "Attribut no. %d: %s = %s%n", i,atts.getQName( i ), atts.getValue( i ) );
	
	
	OsmNode node = new OsmNode(Long.parseLong(atts.getValue("id")), Integer.parseInt(atts.getValue("id")), null, null, null, null);

	}

	@Override
	public void handle(BoundingBox bounds) {
		System.out.println("Bounds");
	}

	@Override
	public void handle(Node node) {
		System.out.println("Node");
	}

	@Override
	public void handle(Way way) {
		System.out.println("Way");
		System.out.println(way.getId());
		for(long nid: way.getNodeIds())
			System.out.println(nid);
	}

	@Override
	public void handle(Relation relation) {
		System.out.println("Relation");
		
	}
	
}
