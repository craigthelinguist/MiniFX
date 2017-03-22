package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

public class New implements Expr {

	private Expr value;
	private Type componentType;
	
	public New(Type type, Expr initValue) {
		this.componentType = type;
		this.value = initValue;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		Expr init = value.reduce(rtm);
		return rtm.allocate(init);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		Type valueType = value.typeCheck(ctx);
		if (!(valueType.subtypeOf(componentType)))
			throw new RuntimeException("Initialising reference with something of inappropriate type.");
		return componentType;
	}

}
