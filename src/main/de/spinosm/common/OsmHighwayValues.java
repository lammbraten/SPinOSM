package de.spinosm.common;

import de.westnordost.osmapi.map.data.Way;

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
	private static final String TRACK = "track";
	
	private static final double TOMS = 3.6;
	
	public static boolean isRouateableForCars(Way way) {
		String value = way.getTags().get("highway");
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


	public static boolean isRouateableForTrucks(Way way) {
		// TODO Auto-generated method stub
		return false;
	}


	public static boolean isRouateableForPedestrians(Way way) {
		if(!way.getTags().get("sidewalk").contains("none"))
			return true;
		String value = way.getTags().get("highway");
		if(value.equals(RESIDENTIAL)||
				value.equals(SERVICE)||
				value.equals(LIVING_STREET)||
				value.equals(PEDESTRIAN)||
				value.equals(TRACK)||
				value.equals(ROAD)||
				value.equals(FOOTWAY)||
				value.equals(STEPS)||
				value.equals(PATH))
			return true;
		return false;
	}


	public static boolean isRouateableForBicycles(Way way) {
		// TODO Auto-generated method stub
		return false;
	}


	public static double getCarSpeedLimits(Way way) throws NumberFormatException {
		if(way.getTags().containsKey("maxspeed"))
			return Integer.parseInt(way.getTags().get("maxspeed")) /TOMS;			

		String value = way.getTags().get("highway");
		switch(value){
			case MOTORWAY:
				return 130 /TOMS;
			case TRUNK:
				return 120 /TOMS;
			case PRIMARY:
				return 90 /TOMS;
			case SECONDARY:
				return 80 /TOMS;
			case TERTIARY:
				return 50 /TOMS;
			case RESIDENTIAL:
				return 30 /TOMS;
			case MOTORWAY_LINK:
				return 80 /TOMS;
			case TRUNK_LINK:
				return 70 /TOMS;
			case PRIMARY_LINK:
				return 50 /TOMS;
			case SECONDARY_LINK:
				return 40 /TOMS;
			case TERTIARY_LINK:
				return 30 /TOMS;
			case LIVING_STREET:
				return 6 /TOMS;
			case ROAD:
				return 50 /TOMS;
			default:
				return 50 /TOMS;
		}
	}


	public static int getTruckSpeedLimits(Way way) {
		// TODO Auto-generated method stub
		return -1;
	}


	public static int getPedestrianSpeeds(Way way) {
		// TODO Auto-generated method stub
		return 7;
	}


	public static int getBycicleSpeeds(Way way) {
		// TODO Auto-generated method stub
		return 20;
	}
	

}
