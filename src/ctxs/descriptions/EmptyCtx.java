package ctxs.descriptions;

import java.util.Optional;

import descriptions.fx.Effect;
import descriptions.regions.Region;
import descriptions.types.Type;

public class EmptyCtx implements DescCtx {

	@Override
	public DescCtx previous() {
		throw new RuntimeException("Empty context has no previous");
	}

	@Override
	public boolean hasBinding(String var) {
		return false;
	}

	@Override
	public DescCtx extend(String name, Type type) {
		return new TypeVarCtx(name, type, this);
	}

	@Override
	public DescCtx extend(String name, Region region) {
		return new RgnVarCtx(name, region, this);
	}

	@Override
	public DescCtx extend(String name, Effect effect) {
		return new FxVarCtx(name, effect, this);
	}

	@Override
	public Optional<Type> lookupType(String name) {
		return Optional.empty();
	}

	@Override
	public Optional<Region> lookupRegion(String name) {
		return Optional.empty();
	}

	@Override
	public Optional<Effect> lookupEffect(String name) {
		return Optional.empty();
	}

	
	
}
