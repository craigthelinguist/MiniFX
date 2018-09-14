package ctxs.descriptions;

import java.util.Optional;

import descriptions.fx.Effect;
import descriptions.fx.EffectVar;
import descriptions.regions.Region;
import descriptions.regions.RegionVar;
import descriptions.types.Type;
import descriptions.types.TypeVar;

public interface DescCtx {

	public DescCtx previous();
	
	public default boolean hasBinding(TypeVar var) {
		return this.hasBinding(var.getName());
	}
	
	public default boolean hasBinding(RegionVar var) {
		return this.hasBinding(var.getName());
	}
	
	public default boolean hasBinding(EffectVar var) {
		return this.hasBinding(var.getName());
	}
	
	public boolean hasBinding(String var);
	
	public DescCtx extend(String name, Type type);
	public DescCtx extend(String name, Region region);
	public DescCtx extend(String name, Effect effect);

	public default DescCtx extend(TypeVar var, Type type) {
		return extend(var.getName(), type);
	}

	public default DescCtx extend(RegionVar var, Region region) {
		return extend(var.getName(), region);
	}

	public default DescCtx extend(EffectVar var, Effect effect) {
		return extend(var.getName(), effect);
	}

	public default DescCtx extend(String[] vars, Type[] types) {
		DescCtx ctx = this;
		for (int i = 0; i < vars.length; i++) {
			ctx = ctx.extend(vars[i], types[i]);
		}
		return ctx;
	}

	public default DescCtx extend(String[] vars, Region[] regions) {
		DescCtx ctx = this;
		for (int i = 0; i < vars.length; i++) {
			ctx = ctx.extend(vars[i], regions[i]);
		}
		return ctx;
	}
	
	public default DescCtx extend(String[] vars, Effect[] effects) {
		DescCtx ctx = this;
		for (int i = 0; i < vars.length; i++) {
			ctx = ctx.extend(vars[i], effects[i]);
		}
		return ctx;
	}
	
	public default DescCtx extend(TypeVar[] vars, Type[] types) {
		DescCtx ctx = this;
		for (int i = 0; i < vars.length; i++) {
			ctx = ctx.extend(vars[i], types[i]);
		}
		return ctx;
	}
	
	public default DescCtx extend(RegionVar[] vars, Region[] regions) {
		DescCtx ctx = this;
		for (int i = 0; i < vars.length; i++) {
			ctx = ctx.extend(vars[i], regions[i]);
		}
		return ctx;
	}

	public default DescCtx extend(EffectVar[] vars, Effect[] effects) {
		DescCtx ctx = this;
		for (int i = 0; i < vars.length; i++) {
			ctx = ctx.extend(vars[i], effects[i]);
		}
		return ctx;
	}
	
	public Optional<Type> lookupType(String name);
	public Optional<Region> lookupRegion(String name);
	public Optional<Effect> lookupEffect(String name);

	public default Optional<Type> lookupType(TypeVar var) {
		return lookupType(var.getName());
	}
	
	public default Optional<Region> lookupRegion(RegionVar var) {
		return lookupRegion(var.getName());
	}
	
	public default Optional<Effect> lookupEffect(EffectVar var) {
		return lookupEffect(var.getName());
	}
	
	public static EmptyCtx Empty() {
		return new EmptyCtx();
	}

}
