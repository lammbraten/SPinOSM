package de.spinosm.graph.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.spinosm.common.Common;
import de.spinosm.common.Vehicle;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.common.errors.OsmConnectionException;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.ListOsmElementHandler;
import oauth.signpost.OAuthConsumer;

public class OsmApiWrapper implements DataProvider {

	private static final String SECURE_OSM_API_URL = "https://api.openstreetmap.org/api/0.6/";
	private static final String OSM_API_URL = "http://openstreetmap.org/api/0.6/";
	private static final String OSM_TEST_API_URL = "http://api06.dev.openstreetmap.org/api/0.6/";
	private static final String XAPI = "http://informationfreeway.org/api/0.6/";
	private static final String USER_AGENT = "SPinOSM";
	private static final int TIMEOUT = 10000; //10 secs
	private static final int MAX_ATTEMPTS = 5;
	private static final OAuthConsumer OSM_AUTH= null;
	private static final int DOWNTHEROAD = -1;
	private static final int UPTHEROAD = 1; 
	
	private OsmConnection osm;
	private MapDataDao mddao;
	
	private OsmElementBuffer<Node> osmNodeListBuffer;
	private OsmElementBuffer<Way> osmWaysOfNodeBuffer;
	
	public OsmApiWrapper(){
		this.osm = new OsmConnection(OSM_API_URL, USER_AGENT, OSM_AUTH, TIMEOUT);
		this.mddao = new MapDataDao(osm);

		osmNodeListBuffer = new OsmElementBuffer<Node>();
		osmWaysOfNodeBuffer = new OsmElementBuffer<Way>();
	}
	
	public Node getNode(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getNode(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Node> getNodes(List<Long> nodeIds){
		/*for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
			List<Node> nodes = new LinkedList<Node>();
			for(Long nid : nodeIds)
				try{nodes.add(mddao.getNode(nid));}catch(OsmConnectionException e){}
			return nodes;
		}*/
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
			try{return inWayOrder(mddao.getNodes(nodeIds), nodeIds);}catch(OsmConnectionException e){}			
		}
		return null;
	}
	
	public Way getWay(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getWay(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Node> getWayNodesComplete(long id) {
		if(osmNodeListBuffer.contains(id))
			return getWayCompleteFromBuffer(id);
		return getWayCompleteFromServer(id);
	}
	
	public List<Way> getWays(Collection<Long> wayIds){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getWays(wayIds);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Way> getWaysForNode(long id){
		if(osmWaysOfNodeBuffer.contains(id))
			return getWaysOfNodeFromBuffer(id);
		return getWayForNodeFromServer(id);
	}
	
	public Relation getRelation(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelation(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelations(Collection<Long> relationIds){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelations(relationIds);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelationsForNode(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelationsForNode(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelationsForRelation(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelationsForRelation(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelationsForWay(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelationsForWay(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public OsmConnection getConnection(){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return osm;}catch(OsmConnectionException e){}
		return null;
	}

	@Override
	public StreetJunction getStreetJunction(long id) {
		StreetJunction returnValue;
		OsmNode osmNode = (OsmNode) this.getNode(id);
		if(osmNode == null)
			throw new IllegalArgumentException("Node not existing in OSM");
		returnValue = buildNewStreetJunction(osmNode);
		return returnValue;
	}

	@Override
	public StreetEdge getStreetEdge(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetJunction> getStreetJunctionsForStreetEdge(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdges(Collection<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StreetEdge> getStreetEdgesForStreetJunction(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private StreetJunction buildNewStreetJunction(OsmNode osmNode) {
		StreetJunction returnValue;
		LinkedList<RouteableEdge> waysFromNode = getRouteableEdgesForNode(osmNode);	
		returnValue = new StreetJunction(osmNode, waysFromNode);
		return returnValue;
	}
	
	private LinkedList<RouteableEdge> getRouteableEdgesForNode(Node n) {
		LinkedList<RouteableEdge> waysFromNode = new LinkedList<RouteableEdge>();
		List<Way> ways = this.getWaysForNode(n.getId());
		StreetJunction thatNode= new StreetJunction((OsmNode) n);
		for(Way way : ways){
			try{
				waysFromNode.add(parseToRouteableEdge(DOWNTHEROAD, way, thatNode));
			}catch(Exception e){
				//System.out.println(e.getMessage());
			}
			try{
				waysFromNode.add(parseToRouteableEdge(UPTHEROAD, way, thatNode));
			}catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return waysFromNode;

	}

	private RouteableEdge parseToRouteableEdge(int direction, Way way, StreetJunction startingNode) {
		List<Long> nids = way.getNodeIds();
		List<Node> nodes =  this.getWayNodesComplete(way.getId());
		for(Node node : nodes){
			if(startingNode.getId() == node.getId()){
				if(direction < 0){
					LinkedList<Node> shapingNodes = new LinkedList<Node>();	
					shapingNodes.add(node);
					for(int i = nodes.indexOf(node)-1; i >= 0; i--){
						shapingNodes.add(nodes.get(i));
						if(isRouteableJunction(nodes.get(i))){
							return new StreetEdge(startingNode, new StreetJunction((OsmNode) nodes.get(i)), calcCost(way, shapingNodes));
						}
					}
				}else if(direction > 0){
					LinkedList<Node> shapingNodes = new LinkedList<Node>();	
					shapingNodes.add(node);
					for(int i = nodes.indexOf(node)+1; i < nodes.size(); i++){
						shapingNodes.add(nodes.get(i));
						if(isRouteableJunction(nodes.get(i))){
							return new StreetEdge(startingNode, new StreetJunction((OsmNode) nodes.get(i)), calcCost(way, shapingNodes));
						}
					}
				}else{
					throw new IllegalStateException("direction should only be positive or negativ, not 0");
				}
			}
		}
		throw new IllegalArgumentException("Starting-Node is not in given way! \n"
				+ "Direction: " + direction + "\n"
				+ "way: " + way.getId() + "\n"
				+ "StartingNode: " + startingNode + "\n"
				+ "nids-size: " + nids.size() + "\n"
				+ "--------------------------") ; 
	}

	/**
	 * Had to write this because OSM-Delivers for request for multiply nodes the nodes not ordered
	 * OSM does this even for GET way/<id\>/complete
	 * @param nodes - The unordered array
	 * @param nids - Nodes should be in this order
	 * @return the ordered List of Nodes
	 */
	private List<Node> inWayOrder(List<Node> nodes, List<Long> nids) {
		ArrayList<Node> orderedList = new ArrayList<Node>();
		
		for(long nid : nids)
			for(Node node : nodes)
				if(node.getId() == nid)
					orderedList.add(node);				
		
		return orderedList;
	}

	private double calcCost(Way way, LinkedList<Node> shapingNodes) {
		LinkedList<LatLon> nodes = new LinkedList<LatLon>();
		for(Node node : shapingNodes)
			nodes.add(node.getPosition());
		return Common.calcCost(nodes, way);
	}

	public boolean isRouteableJunction(Node node) {
		List<Way> waysOfNode = this.getWaysForNode(node.getId());
		if(waysOfNode.size() >= 2)
			return hasAnotherRoute(waysOfNode);
		return false;
	}

	/**
	 * @param waysOfNode
	 */
	private boolean hasAnotherRoute(List<Way> waysOfNode) {
		int routeableWaysCounter = 0;	
		for(Way way: waysOfNode){
			if(Common.wayIsUseable(way, Vehicle.CAR))
				routeableWaysCounter++;
			if(routeableWaysCounter >= 2)
				return true;
		}
		return false;
	}
	
	private List<Node> getWayCompleteFromBuffer(long id) {
		return osmNodeListBuffer.getElementList(id);
	}

	/**
	 * @param id
	 * @return
	 */
	private List<Node> getWayCompleteFromServer(long id) {
		ListOsmElementHandler<Node>  mdh = new ListOsmElementHandler<Node>(Node.class);
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{
				mddao.getWayComplete(id, mdh);
				break;
			}catch(OsmConnectionException e){}
		osmNodeListBuffer.addElementList(id, mdh.get());
		return mdh.get();
	}
	
	/**
	 * @param id
	 * @return 
	 */
	private List<Way> getWaysOfNodeFromBuffer(long id) {
		return osmWaysOfNodeBuffer.getElementList(id);
	}

	/**
	 * @param id
	 * @return 
	 */
	private List<Way> getWayForNodeFromServer(long id) {
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{
				List<Way> returnValue= mddao.getWaysForNode(id);
				osmWaysOfNodeBuffer.addElementList(id, returnValue);
				return returnValue;
			}catch(OsmConnectionException e){}
		return null;
	}
}
