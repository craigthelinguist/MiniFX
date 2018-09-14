package ctxs.descriptions;

import java.util.Optional;

import descriptions.fx.Effect;
import descriptions.fx.EffectVar;
import descriptions.regions.Region;
import descriptions.types.Type;

public class FxVarCtx extends CtxImpl {

	private Effect effect;
	
	protected FxVarCtx(String name, Effect effect, DescCtx ctx) {
		this.name = name;
		this.effect = effect;
		this.previous = ctx;
	}

	protected FxVarCtx(EffectVar var, Effect effect, DescCtx ctx) {
		this.name = var.getName();
		this.effect = effect;
		this.previous = ctx;
	}

	@Override
	public Optional<Type> lookupType(String name) {
		return previous == null ? Optional.empty() : previous.lookupType(name);
	}

	@Override
	public Optional<Region> lookupRegion(String name) {
		return previous == null ? Optional.empty() : previous.lookupRegion(name);
	}

	@Override
	public Optional<Effect> lookupEffect(String name) {
		return this.name.equals(name) ? Optional.of(effect) : previous.lookupEffect(name);
	}
	
}
