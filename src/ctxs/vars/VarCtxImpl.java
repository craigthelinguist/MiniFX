package ctxs.vars;

import java.util.Optional;

import descriptions.types.Type;

public class VarCtxImpl implements VarCtx {

	private String name;
	private Type type;
	private VarCtx previous;
	
	protected VarCtxImpl(String name, Type type, VarCtx previous) {
		this.name = name;
		this.type = type;
		this.previous = previous;
	}
	
	@Override
	public Optional<Type> lookup(String name) {
		return this.name.equals(name) ? Optional.of(type) : previous.lookup(name);
	}

	@Override
	public boolean hasBinding(String name) {
		return this.name.equals(name) || previous.hasBinding(name);
	}

	@Override
	public VarCtx extend(String name, Type type) {
		return new VarCtxImpl(name, type, this);
	}

}
