package exprs;

import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

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
		return lambdaBody.typeCheck(ctx.extend(argNames, argTypes));
	}

	public Expr getBody() {
		return this.lambdaBody;
	}

	
}
