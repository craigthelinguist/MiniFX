package ctxs.vars;

import java.util.Optional;

import descriptions.types.Type;

public class EmptyVarCtx implements VarCtx {

	@Override
	public Optional<Type> lookup(String name) {
		return Optional.empty();
	}

	@Override
	public boolean hasBinding(String name) {
		return false;
	}

	@Override
	public VarCtx extend(String name, Type type) {
		return new VarCtxImpl(name, type, this);
	}

}
