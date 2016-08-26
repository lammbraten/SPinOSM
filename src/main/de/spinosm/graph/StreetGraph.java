package de.spinosm.graph;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import de.spinosm.graph.data.DataProvider;
import de.spinosm.graph.data.DefaultDataProvider;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class StreetGraph extends SimpleDirectedWeightedGraph<RouteableVertex, StreetEdge> implements Serializable{

	private static final long serialVersionUID = -67998995199008728L;
	transient private DataProvider dataprovider;
	public static int DEFAULT_DIRECTION = 1;

	public StreetGraph(DataProvider dataprovider){
		super(new StreetEdgeFactory());
		if(dataprovider == null)
			this.dataprovider = new DefaultDataProvider();
		else
			this.dataprovider = dataprovider;
	}
	
	public void setVertecies(TreeSet<RouteableVertex> vertecies) {
		if(vertecies != null){
			super.removeAllVertices(vertecies);
			for(RouteableVertex v : vertecies)
				super.addVertex(v);
		}else{
			throw new IllegalArgumentException("vertecies is null");
		}
	}
	
	public Set<RouteableVertex> getVertecies() {
		return super.vertexSet();	
	}

	public RouteableVertex getVertex(long id){
		RouteableVertex returnValue = checkBufferedVerticesForId(id);
		if(returnValue != null)
			return returnValue;
		
		return loadVertexFromDataProvider(id);
	}

	public Set<StreetEdge> edgesOf(RouteableVertex startVertex, int direction) {
		if(startVertex.isEdgesLoaded())
			return checkBufferedEdgeForId(startVertex, direction);
		else{
			loadEdgesFormDataprovider(startVertex);
			return checkBufferedEdgeForId(startVertex, direction); //Had to that because, Loading From dataprovider with direction makes no sense
		}
	}
	
	@Override
	public Set<StreetEdge> edgesOf(RouteableVertex v){
		try {
			throw new Exception("Don't use me. use getEdgesForVertec(sj)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addEdge(StreetEdge e, RouteableVertex sv) {
		super.addVertex(e.getOtherKnotThan(sv));
		StreetEdge se = addEdge(e.getStart(), e.getEnd());
		if(se != null)
			se.setWeight(e.getWeight());
	}

	public DataProvider getDataprovider() {
		return dataprovider;
	}

	public void setDataprovider(DataProvider dataprovider) {
		this.dataprovider = dataprovider;
	}

	private Set<StreetEdge> loadEdgesFormDataprovider(RouteableVertex startVertex) {
		Set<StreetEdge> edges =	dataprovider.getStreetEdgesForVertex(startVertex);
		for(StreetEdge e: edges)
			addEdge(e, startVertex);
		startVertex.setEdgesLoaded(true);		
		return edges;
	}

	private RouteableVertex loadVertexFromDataProvider(long id) {
		RouteableVertex returnValue = null;
		try {
			returnValue = this.dataprovider.getVertex(id);
			this.integrateNewVertexToGraph(returnValue);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return returnValue;
	}

	private Set<StreetEdge> checkBufferedEdgeForId(RouteableVertex startVertex, int direction) {
		if(direction > 0)
			return outgoingEdgesOf(startVertex);
		else
			return incomingEdgesOf(startVertex);
	}
	
	private RouteableVertex checkBufferedVerticesForId(long id) {
		for(RouteableVertex n : super.vertexSet())
			if(n.getId() == id && n.isEdgesLoaded())
				return n;
		return null;
	}
	
	private void integrateNewVertexToGraph(RouteableVertex newNode){
		//Prüfe ob benachbarte knoten schon im graph. wenn ja verlinke sie
//		linkWithAlredyKnownNodes(newNode);
		super.addVertex(newNode);
	}

}
