package ctxs.descriptions;

import java.util.Optional;

import descriptions.fx.Effect;
import descriptions.regions.Region;
import descriptions.regions.RegionVar;
import descriptions.types.Type;

public class RgnVarCtx extends CtxImpl {

	private Region region;
	
	protected RgnVarCtx(RegionVar rv, Region rg, DescCtx ctx) {
		this.name = rv.getName();
		this.region = rg;
		this.previous = ctx;
	}

	protected RgnVarCtx(String name, Region region, DescCtx ctx) {
		this.name = name;
		this.region = region;
		this.previous = ctx;
	}

	@Override
	public Optional<Type> lookupType(String name) {
		return previous == null ? null : previous.lookupType(name);
	}

	@Override
	public Optional<Region> lookupRegion(String name) {
		return this.name.equals(name) ? Optional.of(region) : previous.lookupRegion(name);
	}

	@Override
	public Optional<Effect> lookupEffect(String name) {
		return previous == null ? null : previous.lookupEffect(name);
	}
	
}
