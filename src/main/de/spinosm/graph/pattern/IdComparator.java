package de.spinosm.graph.pattern;

import java.io.Serializable;
import java.util.Comparator;

import de.spinosm.graph.RouteableVertex;

public class IdComparator implements Comparator<RouteableVertex>, Serializable {
	private static final long serialVersionUID = 2613321841972873454L;

	@Override
	public int compare(RouteableVertex o1, RouteableVertex o2) {
		if((o1.getId() - o2.getId())>0)
			return 1;
		if((o1.getId() - o2.getId())<0)
			return -1;
		return 0;
	}

}
