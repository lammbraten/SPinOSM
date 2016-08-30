package de.spinosm.graph.pattern;

import java.io.Serializable;
import java.util.Comparator;

import de.spinosm.graph.StreetVertex;

public class HashComparator implements Comparator<StreetVertex>, Serializable {
	private static final long serialVersionUID = 6524928072416751327L;

	@Override
	public int compare(StreetVertex o1, StreetVertex o2) {
		return o1.hashCode() - o2.hashCode();
	}

}
