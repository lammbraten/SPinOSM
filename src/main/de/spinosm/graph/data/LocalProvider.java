package de.spinosm.graph.data;

import java.io.File;
import java.util.Collection;
import java.util.List;

import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;

public class LocalProvider implements DataProvider{

	File xmlFile;
	OsmXmlElementParser osmxmlelements;
	
	public LocalProvider(String filePath){
		this.xmlFile = new File(filePath);
		osmxmlelements = new OsmXmlElementParser(xmlFile);
	}
	
	@Override
	public StreetJunction getStreetJunction(long id) {
		osmxmlelements.getNode();
		return null;
	}

	@Override
	public StreetEdge getStreetEdge(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetJunction> getStreetJunctionsForStreetEdge(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdges(Collection<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
