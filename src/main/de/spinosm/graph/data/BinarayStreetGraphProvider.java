package de.spinosm.graph.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import de.spinosm.graph.StreetGraph;

public class BinarayStreetGraphProvider{

	private File binaryStreetGraph;
	private StreetGraph preRenderedStreetGraph;
	
	public BinarayStreetGraphProvider(String filePath){
		this.binaryStreetGraph = new File(filePath);
		readStreetGraphFile();
	}
	
	
	private void readStreetGraphFile() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(binaryStreetGraph));
			preRenderedStreetGraph = (StreetGraph) ois.readObject();
			ois.close();		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public StreetGraph getStreetGraph(long id) {
		return preRenderedStreetGraph;
	}
}
