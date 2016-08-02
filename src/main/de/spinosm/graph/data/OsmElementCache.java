package de.spinosm.graph.data;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.WeakHashMap;


public class OsmElementCache <T> {
	private HashMap<Long, List<T>> buffer;
	
	public OsmElementCache(){
		buffer = new HashMap<Long, List<T>>();
	}
	
	public List<T> getElementList(long id) {
		if(buffer.containsKey(id))
			return buffer.get(id);
		return null;
	}

	public void addElementList(Long id, List<T> list) {
		this.buffer.put(id, list);
	}	
	
	public boolean contains(Long id) {
		return this.buffer.containsKey(id);
	}
}
