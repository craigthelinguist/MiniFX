package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

public class Location implements Expr {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + location;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (location != other.location)
			return false;
		return true;
	}

	private int location;
	
	public Location(int l) {
		this.location = l;
	}
	
	public int getLocation() {
		return this.location;
	}
	
	@Override
	public Expr reduce(Runtime rtm) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		throw new RuntimeException("Location is a runtime form and does not typecheck.");
	}

}
