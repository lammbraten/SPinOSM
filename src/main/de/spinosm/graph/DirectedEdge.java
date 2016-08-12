package de.spinosm.graph;

public abstract class DirectedEdge implements RouteableEdge{


	private static final long serialVersionUID = -9135933798984497839L;
	
	private StreetVertex source;
	private StreetVertex target;
	private double weight;
	
	DirectedEdge(StreetVertex start, StreetVertex end, double weight){
		if(start.equals(end))
			return;
		this.source = start;
		this.target = end;
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
	
	public StreetVertex getStart() {
		return source;
	}
	
	public void setStart(StreetVertex start) {
		this.source = start;		
	}

	public StreetVertex getEnd() {
		return target;
	}
	
	public void setEnd(StreetVertex end) {
		this.target = end;
		
	}
	
	public StreetVertex getOtherKnotThan(StreetVertex that) {
		if(isStartVertex(that))
			return target;
		else if(isEndVertex(that))
			return source;
		else
			throw new IllegalArgumentException("The Vertex " + that + " isn't in this Graph");
	}
	
	public void replace(StreetVertex oldVertex, StreetVertex newVertex){
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
		return Long.hashCode(source.getId()) + Long.hashCode(target.getId());
    }
    
}
