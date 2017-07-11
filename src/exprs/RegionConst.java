package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Type;
import types.Types;

public class RegionConst implements Expr {

	private static int RegionCounter = 0;
	private int id;
	
	private RegionConst(int id) {
		this.id = id;
	}
	
	public static RegionConst NewRegion() {
		RegionConst r = new RegionConst(RegionCounter++);
		return r;
	}
	
	@Override
	public Expr reduce(Runtime rtm) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		return Types.RegionType();
	}

	@Override
	public Set<Effect> effectCheck(TypeContext ctx) {
		return new HashSet<>();
	}

}
