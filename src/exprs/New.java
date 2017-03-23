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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentType == null) ? 0 : componentType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		New other = (New) obj;
		if (componentType == null) {
			if (other.componentType != null)
				return false;
		} else if (!componentType.equals(other.componentType))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
}
