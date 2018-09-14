package ctxs.descriptions;

import java.util.Optional;

import descriptions.fx.Effect;
import descriptions.regions.Region;
import descriptions.types.Type;
import descriptions.types.TypeVar;

public class TypeVarCtx extends CtxImpl {

	private Type type;
	
	protected TypeVarCtx(String name, Type type, DescCtx previous) {
		this.name = name;
		this.type = type;
		this.previous = previous;
	}

	protected TypeVarCtx(TypeVar tv, Type type, DescCtx previous) {
		this.name = tv.getName();
		this.type = type;
		this.previous = previous;
	}
	
	@Override
	public Optional<Type> lookupType(String name) {
		return this.name.equals(name) ? Optional.of(type) : previous.lookupType(name);
	}

	@Override
	public Optional<Region> lookupRegion(String name) {
		return previous == null ? null : previous.lookupRegion(name);
	}

	@Override
	public Optional<Effect> lookupEffect(String name) {
		return previous == null ? null : previous.lookupEffect(name);
	}
	
}
