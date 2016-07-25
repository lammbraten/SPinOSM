package de.spinosm.graph.pattern;

import java.util.Comparator;

import de.spinosm.graph.RouteableNode;

public class IdComparator implements Comparator<RouteableNode> {

	@Override
	public int compare(RouteableNode o1, RouteableNode o2) {
		return (int) (o1.getId() - o2.getId());
	}

}
