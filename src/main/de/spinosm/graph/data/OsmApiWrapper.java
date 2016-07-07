package de.spinosm.graph.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.spinosm.common.Common;
import de.spinosm.common.Vehicle;
import de.spinosm.graph.RouteableEdge;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.MapDataHandler;
import oauth.signpost.OAuthConsumer;

public class OsmApiWrapper implements DataProvider {

	private static final String OSM_API_URL = "https://api.openstreetmap.org/api/0.6/";
	//private static final String OSM_TEST_API_URL = "http://api06.dev.openstreetmap.org/api/0.6/";
	private static final String USER_AGENT = "SPinOSM";
	private static final int TIMEOUT = 10000; //10 secs
	private static final OAuthConsumer OSM_AUTH= null;
	private static final int DOWNTHEROAD = -1;
	private static final int UPTHEROAD = 1; 
	
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
	
	/*public List<Node> getNodesForWay(long id){
		ListOsmElementHandler<Node> nodeListHandler = new ListOsmElementHandler<Node>(Node.class);
		new MapDataDao(osm).getWayComplete(id, nodeListHandler);
		return nodeListHandler.get();
	}*/
	
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

	@Override
	public StreetJunction getStreetJunction(long id) {
		StreetJunction returnValue;
		OsmNode osmNode = (OsmNode) this.getNode(id);
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
		LinkedList<RouteableEdge> waysFromNode = getRouteableEdgesForNode(osmNode.getId());	
		returnValue = new StreetJunction(osmNode, waysFromNode);
		return returnValue;
	}
	
	private LinkedList<RouteableEdge> getRouteableEdgesForNode(long id) {
		LinkedList<RouteableEdge> waysFromNode = new LinkedList<RouteableEdge>();
		List<Way> ways = this.getWaysForNode(id);
		StreetJunction thatNode= new StreetJunction((OsmNode) this.getNode(id));
		for(Way way : ways){
			try{
				waysFromNode.add(parseToRouteableEdge(DOWNTHEROAD, way, thatNode));
			}catch(Exception e){}
			try{
				waysFromNode.add(parseToRouteableEdge(UPTHEROAD, way, thatNode));
			}catch(Exception e){}
						
			/*if(Common.wayIsUseable(way, Vehicle.CAR)){
				Common.calcCost(getRawEdgeCoordinates(way), way);
						
					
					//System.out.println(way.getId()+": " + nid);
				//StreetEdge edge = new StreetEdge()					
			}*/
		}
		return waysFromNode;

	}

	private RouteableEdge parseToRouteableEdge(int direction, Way way, StreetJunction startingNode) {
		List<Long> nids = way.getNodeIds();
		for(long nid : nids){
			if(startingNode.getId() == nid){
				if(direction < 0){
					LinkedList<Long> shapingNodeIds = new LinkedList<Long>() ;					
					for(int i = nids.indexOf(nid); i <= 0; i--){
						shapingNodeIds.add(nids.get(i));
						if(isRouteableJunction(nids.get(i))){
							return new StreetEdge(startingNode, new StreetJunction((OsmNode) this.getNode(nids.get(i))), calcCost(way, shapingNodeIds ));
						}
					}
				}else if(direction > 0){
					LinkedList<Long> shapingNodeIds = new LinkedList<Long>() ;					
					for(int i = nids.indexOf(nid); i < nids.size(); i++){
						shapingNodeIds.add(nids.get(i));
						if(isRouteableJunction(nids.get(i))){
							return new StreetEdge(startingNode, new StreetJunction((OsmNode) this.getNode(nids.get(i))), calcCost(way, shapingNodeIds ));
						}
					}
				}
				throw new IllegalStateException("direction should only be positive or negativ, not 0");
			}
		}
		throw new IllegalArgumentException("Starting-Node is not in given way!");
	}

	private double calcCost(Way way, LinkedList<Long> shapingNodeIds) {
		LinkedList<LatLon> nodes = new LinkedList<LatLon>();
		for(long nid : shapingNodeIds)
			nodes.add(this.getNode(nid).getPosition());
		return Common.calcCost(nodes, way);
	}

	/*
	private LinkedList<LatLon> getRawEdgeCoordinates(Way way) {
		LinkedList<LatLon> nodePositions = new LinkedList<LatLon>();
		for(long n : getNodeIdsToNextJunction(way))
			nodePositions.add(this.getNode(n).getPosition());
		return nodePositions;
	}*/
	

	public boolean isRouteableJunction(long id) {
		List<Way> waysOfNode = this.getWaysForNode(id);
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
	
	/*
	private boolean isOneWay(Way way){
		if(way.getTags().containsKey("oneway")) {
			String value = way.getTags().get("oneway");
			if(value.equals("yes")||value.equals("true")||value.equals("1")||value.equals("-1"))
				return true;
		}
		return false;
	}*/
	
	/*
	private LinkedList<Long> getNodeIdsToNextJunction(Way way){
		for(long node : way.getNodeIds()){
			
		}
		if(isOneWay(way)){
			//TODO: prüfe laufrichtung
		}
		return null;
	}*/
}
