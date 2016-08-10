package de.spinosm.graph.pattern;

import java.util.Comparator;

import de.spinosm.graph.StreetJunction;

public class HashComparator implements Comparator<StreetJunction> {

	@Override
	public int compare(StreetJunction o1, StreetJunction o2) {
		return o1.hashCode() - o2.hashCode();
	}

}
