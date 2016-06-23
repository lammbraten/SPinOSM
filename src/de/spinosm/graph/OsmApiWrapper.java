package de.spinosm.graph;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.OsmMapDataFactory;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.user.UserDao;
import de.westnordost.osmapi.user.UserInfo;
import oauth.signpost.OAuthConsumer;

public class OsmApiWrapper implements DataProvider {

	private static final String OSM_API_URL = "https://api.openstreetmap.org/api/0.6/";
	private static final String OSM_TEST_API_URL = "http://api06.dev.openstreetmap.org/api/0.6/";
	private static final String USER_AGENT = "SPinOSM";
	private static final int TIMEOUT = 10000; //10 secs
	private static final OAuthConsumer OSM_AUTH= null; 
	
	private OsmConnection osm;
	private OsmMapDataFactory myMapDataHandler;
	
	public OsmApiWrapper(){
		this.osm = new OsmConnection(OSM_API_URL, USER_AGENT, null);

		//this.myMapDataHandler = new OsmMapDataFactory();

	}
	
	public Node getNode(long id){
		//return new MapDataDao(osm).getNode(id);
		return new MapDataDao(osm).getNode(26576175L);
	}
	
	public OsmConnection getConnection(){
		return osm;
	}
}
