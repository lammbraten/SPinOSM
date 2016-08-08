package de.spinosm.graph.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.spinosm.common.Common;
import de.spinosm.common.Vehicle;
import de.spinosm.graph.StreetEdge;
import de.spinosm.graph.StreetJunction;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Way;

abstract class AbstractProvider implements DataProvider {

	@Override
	public StreetJunction getStreetJunction(long id) {
		// TODO Auto-generated method stub
		return null;
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

	protected StreetJunction buildNewStreetJunction(OsmNode osmNode) {
		StreetJunction returnValue;
		//LinkedList<RouteableEdge> waysFromNode = getRouteableEdgesForNode(osmNode);	
		returnValue = new StreetJunction(osmNode);
		return returnValue;
	}
	
	protected Set<StreetEdge> getRouteableEdgesForNode(StreetJunction sj) {
		Set<StreetEdge> waysFromNode = new HashSet<StreetEdge>();
		List<Way> ways = this.getWaysForNode(sj.getId());
		for(Way way : ways){
			try{
				waysFromNode.addAll(parseToRouteableEdge(way, sj));
			}catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return waysFromNode;

	}

	protected List<StreetEdge> parseToRouteableEdge(Way way, StreetJunction startingNode) {
		List<StreetEdge> edges = new LinkedList<StreetEdge>();
		List<Long> nids = way.getNodeIds();
		List<Node> nodes =  this.getWayNodesComplete(way.getId(), nids);
		for(Node node : nodes){
			if(startingNode.getId() == node.getId()){
				try {
					if(isUseableDownTheRoad(way))
						edges.add(shapeNewEdgeDownTheRoad(way, startingNode, nodes, node));
				} 
				catch (Exception e) {/*System.out.println(e.getMessage());*/}
				try {
					if(isUseableUpTheRoad(way))
						edges.add(shapeNewEdgeUpTheRoad(way, startingNode, nodes, node));
				} catch (Exception e) {/*System.out.println(e.getMessage());*/}
			}
		}
		return edges;
	}

	private boolean isUseableUpTheRoad(Way way) {
		if(way.getTags().containsKey("oneway")){
			String val = way.getTags().get("oneway");
			if(val.equals("yes"))
				return true;
			else if(val.equals("1"))
				return true;
			else if(val.equals("true"))
				return true;
			else 
				return false;
		}
		return true;
	}

	private boolean isUseableDownTheRoad(Way way) {
		if(way.getTags().containsKey("oneway")){
			String val = way.getTags().get("oneway");
			if(val.equals("-1"))
				return true;
			else if(val.equals("reverse"))
				return true;
			else 
				return false;
		}
		return true;
	}

	protected StreetEdge shapeNewEdgeUpTheRoad(Way way, StreetJunction startingNode, List<Node> nodes, Node node) throws Exception {
		LinkedList<Node> shapingNodes = new LinkedList<Node>();	
		shapingNodes.add(node);
		for(int i = nodes.indexOf(node)+1; i < nodes.size(); i++){
			shapingNodes.add(nodes.get(i));
			if(isRouteableJunction(nodes.get(i))){
				return new StreetEdge(startingNode, new StreetJunction((OsmNode) nodes.get(i)), calcCost(way, shapingNodes));
			}
		}
		throw new Exception("No junction found");
	}

	/**
	 * @param way
	 * @param startingNode
	 * @param nodes
	 * @param node
	 * @param shapingNodes
	 * @return 
	 * @throws Exception 
	 */
	protected StreetEdge shapeNewEdgeDownTheRoad(Way way, StreetJunction startingNode, List<Node> nodes, Node node) throws Exception {
		LinkedList<Node> shapingNodes = new LinkedList<Node>();	
		shapingNodes.add(node);
		for(int i = nodes.indexOf(node)-1; i >= 0; i--){
			shapingNodes.add(nodes.get(i));
			if(isRouteableJunction(nodes.get(i))){
				return new StreetEdge(startingNode, new StreetJunction((OsmNode) nodes.get(i)), calcCost(way, shapingNodes));
			}
		}
		throw new Exception("No junction found");
	}

	/**
	 * Had to write this because OSM-Delivers for request for multiply nodes the nodes not ordered
	 * OSM does this even for GET way/<id\>/complete
	 * @param nodes - The unordered array
	 * @param nids - Nodes should be in this order
	 * @return the ordered List of Nodes
	 */
	protected List<Node> inWayOrder(List<Node> nodes, List<Long> nids) {
		ArrayList<Node> orderedList = new ArrayList<Node>();
		
		for(long nid : nids)
			for(Node node : nodes)
				if(node.getId() == nid)
					orderedList.add(node);				
		
		return orderedList;
	}

	protected double calcCost(Way way, LinkedList<Node> shapingNodes) {
		LinkedList<LatLon> nodes = new LinkedList<LatLon>();
		for(Node node : shapingNodes)
			nodes.add(node.getPosition());
		return Common.calcCost(nodes, way);
	}

	protected boolean isRouteableJunction(Node node) {
		List<Way> waysOfNode = this.getWaysForNode(node.getId());
		if(waysOfNode.size() >= 2)
			return hasAnotherRoute(waysOfNode);
		return false;
	}

	/**
	 * @param waysOfNode
	 */
	protected boolean hasAnotherRoute(List<Way> waysOfNode) {
		int routeableWaysCounter = 0;	
		for(Way way: waysOfNode){
			if(Common.wayIsUseable(way, Vehicle.CAR))
				routeableWaysCounter++;
			if(routeableWaysCounter >= 2)
				return true;
		}
		return false;
	}
	


}
