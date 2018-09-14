package ctxs.descriptions;

import descriptions.fx.Effect;
import descriptions.regions.Region;
import descriptions.types.Type;

public abstract class CtxImpl implements DescCtx {
	
	protected DescCtx previous;
	protected String name;
	
	@Override
	public boolean hasBinding(String name) {
		return this.name.equals(name) || previous.hasBinding(name);
	}
	
	@Override
	public DescCtx previous() {
		return this.previous;
	}
	
	@Override
	public TypeVarCtx extend(String name, Type type) {
		return new TypeVarCtx(name, type, this);
	}

	@Override
	public RgnVarCtx extend(String name, Region region) {
		return new RgnVarCtx(name, region, this);
	}
	
	@Override
	public FxVarCtx extend(String name, Effect effect) {
		return new FxVarCtx(name, effect, this);
	}
	
}
