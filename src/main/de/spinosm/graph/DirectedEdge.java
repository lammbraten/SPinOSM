package de.spinosm.graph;

public abstract class DirectedEdge implements RouteableEdge{


	private static final long serialVersionUID = -9135933798984497839L;
	
	private StreetJunction source;
	private StreetJunction target;
	private double weight;
	
	DirectedEdge(StreetJunction start, StreetJunction end, double weight){
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
	
	public StreetJunction getStart() {
		return source;
	}
	
	public void setStart(StreetJunction start) {
		this.source = start;		
	}

	public StreetJunction getEnd() {
		return target;
	}
	
	public void setEnd(StreetJunction end) {
		this.target = end;
		
	}
	
	public StreetJunction getOtherKnotThan(StreetJunction that) {
		if(isStartKnot(that))
			return target;
		else if(isEndKnot(that))
			return source;
		else
			throw new IllegalArgumentException("The Knot " + that + " isn't in this Graph");
	}
	
	public void replace(StreetJunction oldNode, StreetJunction newNode){
		if(isStartKnot(oldNode))
			this.setStart(newNode);
		else if(isEndKnot(oldNode))
			this.setEnd(newNode);
		else
			throw new IllegalArgumentException("The Knot " + oldNode + " isn't in this Graph");
	}
	
	@Override
	public String toString(){
		return "s: " + this.getStart() + "; e: " + this.getEnd() + "; c: " + this.getWeight();
	}

	private boolean isStartKnot(RouteableNode that) {
		if(that.hasSameId(source))
			return true;
		return false;
	}

	private boolean isEndKnot(RouteableNode that) {
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
