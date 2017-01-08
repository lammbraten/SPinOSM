package de.spinosm.graph;

public abstract class DirectedEdge implements RouteableEdge{
	private static final long serialVersionUID = -9135933798984497839L;
	
	private RouteableVertex source;
	private RouteableVertex target;
	private double weight;
	
	DirectedEdge(RouteableVertex source2, RouteableVertex target2, double weight){
		if(source2.equals(target2))
			return;
		this.source = source2;
		this.target = target2;
		this.weight = weight;
		
	}
	
	@Override
	public double getWeight() {
		return weight;
	}
	
	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public RouteableVertex getStart() {
		return source;
	}
	
	public void setStart(RouteableVertex start) {
		this.source = start;		
	}

	public RouteableVertex getEnd() {
		return target;
	}
	
	public void setEnd(RouteableVertex end) {
		this.target = end;
		
	}
	
	public RouteableVertex getOtherVertexThan(RouteableVertex u) {
		if(isStartVertex(u))
			return target;
		else if(isEndVertex(u))
			return source;
		else
			throw new IllegalArgumentException("The Vertex " + u + " isn't in this Graph");
	}
	
	public void replace(RouteableVertex oldVertex, RouteableVertex newVertex){
		if(isStartVertex(oldVertex))
			this.setStart(newVertex);
		else if(isEndVertex(oldVertex))
			this.setEnd(newVertex);
		else
			throw new IllegalArgumentException("The Vertex " + oldVertex + " isn't in this Graph");
	}
	
	@Override
	public String toString(){
		return "s: " + this.getStart() + "; e: " + this.getEnd() + "; c: " + this.getWeight();
	}

	@Override 
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this == other)
			return true;
		if(other instanceof DirectedEdge){
			DirectedEdge otherDirectedEdge = (DirectedEdge) other;
			if(this.source == otherDirectedEdge.source &&
					this.target == otherDirectedEdge.target)
				return true;
		}
		return false;
	}
    
    @Override 
    public int hashCode(){
		return Long.hashCode(Long.hashCode(source.getId()) + Long.hashCode(target.getId()));
    }

	private boolean isStartVertex(RouteableVertex that) {
		if(that.hasSameId(source))
			return true;
		return false;
	}

	private boolean isEndVertex(RouteableVertex that) {
		if(that.hasSameId(target))
			return true;
		return false;
	}
    
}
