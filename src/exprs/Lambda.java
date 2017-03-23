package exprs;

import java.util.Arrays;
import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Arrow;
import types.Type;
import types.Types;

public class Lambda implements Expr {

	private String[] argNames;
	private Type[] argTypes;
	private Expr lambdaBody;
	
	public Lambda(List<Var> argNames2, List<Type> argTypes, Expr body) {
		if (argNames2.size() != argTypes.size()) 
			throw new RuntimeException("Need a type for every argument.");
		this.argNames = new String[argNames2.size()];
		this.argTypes = new Type[argNames2.size()];
		for (int i = 0; i < argNames2.size(); i++) {
			this.argNames[i] = argNames2.get(i).getName();
			this.argTypes[i] = argTypes.get(i);
		}
		this.lambdaBody = body;
	}
	
	public Lambda(String varName, Type argType, Expr body) {
		this.argNames = new String[]{ varName };
		this.argTypes = new Type[]{ argType };
		this.lambdaBody = body;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		return this;
	}
	
	public Expr apply(Runtime rtm, Expr[] actualArgs) {
		if (actualArgs.length != argNames.length)
			throw new RuntimeException("Wrong number of arguments supplied to function.");
		Expr[] argsReduced = new Expr[actualArgs.length];
		for (int i = 0; i < argsReduced.length; i++) {
			argsReduced[i] = actualArgs[i].reduce(rtm);
		}
		Runtime extended = rtm.extend(argNames, argsReduced);
		return lambdaBody.reduce(extended);
	}
	
	@Override
	public Type typeCheck(TypeContext ctx) {
		Type bodyType = lambdaBody.typeCheck(ctx.extend(argNames, argTypes));
		return new Arrow(argTypes, bodyType);
	}
	
	public int numArgs() {
		return this.argTypes.length;
	}

	public Expr getBody() {
		return this.lambdaBody;
	}
	
	public Type[] getTypes() {
		return this.argTypes;
	}

	public Type outputType() {
		return this.outputType();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(argNames);
		result = prime * result + Arrays.hashCode(argTypes);
		result = prime * result + ((lambdaBody == null) ? 0 : lambdaBody.hashCode());
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
		Lambda other = (Lambda) obj;
		if (other.argTypes.length != this.argTypes.length)
			return false;
		for (int i = 0; i < this.argTypes.length; i++) {
			if (!Types.equivalent(this.argTypes[i], other.argTypes[i]))
				return false;
		}
		return this.lambdaBody.equals(other.lambdaBody);
	}
	
}
