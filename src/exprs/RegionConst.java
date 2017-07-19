package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Type;
import types.Types;

public class RegionConst implements Expr {

	private int id;
	
	public RegionConst(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		RegionConst other = (RegionConst) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
