package de.spinosm.graph.data;

import java.util.Collection;
import java.util.List;

import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.OsmMapDataFactory;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.ListOsmElementHandler;
import de.westnordost.osmapi.map.handler.MapDataHandler;
import de.westnordost.osmapi.user.UserDao;
import de.westnordost.osmapi.user.UserInfo;
import oauth.signpost.OAuthConsumer;

public class OsmApiWrapper implements DataProvider {

	private static final String OSM_API_URL = "https://api.openstreetmap.org/api/0.6/";
	//private static final String OSM_TEST_API_URL = "http://api06.dev.openstreetmap.org/api/0.6/";
	private static final String USER_AGENT = "SPinOSM";
	private static final int TIMEOUT = 10000; //10 secs
	private static final OAuthConsumer OSM_AUTH= null; 
	
	private OsmConnection osm;
	private MapDataHandler mdh;
	
	public OsmApiWrapper(){
		this.osm = new OsmConnection(OSM_API_URL, USER_AGENT, OSM_AUTH, TIMEOUT);

		//this.myMapDataHandler = new OsmMapDataFactory();

	}
	
	public Node getNode(long id){
		return new MapDataDao(osm).getNode(id);
	}
	
	public Way getWay(long id){
		return new MapDataDao(osm).getWay(id);
	}
	
	public List<Node> getNodesForWay(long id){
		ListOsmElementHandler<Node> nodeListHandler = new ListOsmElementHandler<Node>(Node.class);
		new MapDataDao(osm).getWayComplete(id, nodeListHandler);
		return nodeListHandler.get();
	}
	
	public List<Way> getWays(Collection<Long> wayIds){
		return new MapDataDao(osm).getWays(wayIds);
	}
	
	public List<Way> getWaysForNode(long id){
		return new MapDataDao(osm).getWaysForNode(id);
	}
	
	public Relation getRelation(long id){
		return new MapDataDao(osm).getRelation(id);
	}
	
	public List<Relation> getRelations(Collection<Long> relationIds){
		return new MapDataDao(osm).getRelations(relationIds);
	}
	
	public List<Relation> getRelationsForNode(long id){
		return new MapDataDao(osm).getRelationsForNode(id);
	}
	
	public List<Relation> getRelationsForRelation(long id){
		return new MapDataDao(osm).getRelationsForRelation(id);
	}
	
	public List<Relation> getRelationsForWay(long id){
		return new MapDataDao(osm).getRelationsForWay(id);
	}
	
	public OsmConnection getConnection(){
		return osm;
	}
}
