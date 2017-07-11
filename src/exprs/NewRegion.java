package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Type;
import types.Types;

public class NewRegion implements Expr {

	@Override
	public Expr reduce(Runtime rtm) {
		return rtm.makeRegion();
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
