package exprs;

import java.util.Arrays;
import java.util.List;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.Effect;
import descriptions.fx.EffectCheckException;
import descriptions.types.Subr;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;
import runtimes.Runtime;

public class Lambda extends Value {

	private String[] argNames;
	private Type[] argTypes;
	private Expr lambdaBody;
	private Type outputType;
	
	public Lambda(List<Var> argNames2, List<Type> argTypes, Effect effect, Expr body) {
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
	
	public Value apply(Runtime rtm, DescCtx descCtx, Expr[] actualArgs) {
		if (argNames.length == 0 && actualArgs.length != 1)
			throw new RuntimeException("Wrong number of arguments supplied to function.");
		if (argNames.length > 0 && actualArgs.length != argNames.length)
			throw new RuntimeException("Wrong number of arguments supplied to function.");
		
		Runtime extended = rtm;
		if (argNames.length > 0) {
			Value[] argsReduced = new Value[actualArgs.length];
			for (int i = 0; i < argsReduced.length; i++) {
				argsReduced[i] = actualArgs[i].reduce(rtm, descCtx);
			}
			extended = rtm.extend(argNames, argsReduced);
		}
		
		
		return lambdaBody.reduce(extended, descCtx);
	}
	
	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws TypeCheckException, EffectCheckException {
		Type bodyType = lambdaBody.typeCheck(ctx.extend(argNames, argTypes), descCtx);
		this.outputType = bodyType;
		return Subr.Pure(argTypes, bodyType);
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
		if (this.outputType == null)
			throw new RuntimeException("Must type this first.");
		return this.outputType;
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
	
	@Override
	public String toString() {
		
		String[] types = new String[argTypes.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = "(" + argNames[i] + " " + argTypes[i].toString() + ")";
		}
		
		return "(LAMBDA (" + String.join(" ", types) + ") " + lambdaBody + ")";
	}
	
}
