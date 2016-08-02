package de.spinosm.graph.data;

import java.util.Map;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

public class DefaultSink implements Sink {

	@Override
	public void initialize(Map<String, Object> metaData) {
		System.out.println("initialize");
		// TODO Auto-generated method stub

	}

	@Override
	public void complete() {
		System.out.println("complte");
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		System.out.println("close");
		// TODO Auto-generated method stub

	}

	@Override
	public void process(EntityContainer entityContainer) {
		System.out.println("process");
		// TODO Auto-generated method stub

	}

}
