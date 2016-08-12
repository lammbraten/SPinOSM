package de.spinosm.graph.pattern;

import java.util.Comparator;

import de.spinosm.graph.StreetVertex;

public class HashComparator implements Comparator<StreetVertex> {

	@Override
	public int compare(StreetVertex o1, StreetVertex o2) {
		return o1.hashCode() - o2.hashCode();
	}

}
