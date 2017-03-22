package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

public class Location implements Expr {

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
