package de.spinosm.graph.data;

public class OsmHighwayValues {
	private static final String MOTORWAY = "motorway";
	private static final String TRUNK = "trunk";
	private static final String PRIMARY = "primary";
	private static final String SECONDARY = "secondary";
	private static final String TERTIARY = "tertiary";
	private static final String UNCLASSIFIED = "unclassified";
	private static final String RESIDENTIAL = "residential";
	private static final String SERVICE = "service";
	private static final String MOTORWAY_LINK = "motorway_link";
	private static final String TRUNK_LINK = "trunk_link";
	private static final String PRIMARY_LINK = "primary_link";
	private static final String SECONDARY_LINK = "secondary_link";
	private static final String TERTIARY_LINK = "tertiary_link";
	private static final String LIVING_STREET = "living_street";
	private static final String PEDESTRIAN = "pedestrian";
	private static final String BUS_GUIDEWAY = "bus_guideway";
	private static final String RACEWAY = "raceway";
	private static final String ROAD = "road";
	private static final String FOOTWAY = "footway";
	private static final String BRIDLEWAY = "bridleway";
	private static final String STEPS = "steps";
	private static final String PATH = "path";
	private static final String CYCLEWAY = "cycleway";
	private static final String PROPOSED = "proposed";
	private static final String CONSTRUCTION = "construction";
	
	
	public static boolean isRouateable(String value){
		if(value.equals(MOTORWAY)||
				value.equals(TRUNK)||
				value.equals(PRIMARY)||
				value.equals(SECONDARY)||
				value.equals(TERTIARY)||
				value.equals(UNCLASSIFIED)||
				value.equals(RESIDENTIAL)||
				value.equals(MOTORWAY_LINK)||
				value.equals(TRUNK_LINK)||
				value.equals(PRIMARY_LINK)||
				value.equals(SECONDARY_LINK)||
				value.equals(TERTIARY_LINK)||
				value.equals(LIVING_STREET)||
				value.equals(ROAD))
			return true;
		return false;
	}

}
