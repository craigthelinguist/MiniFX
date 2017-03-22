package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Ref;
import types.Type;

public class Get implements Expr {

	private Expr refToGet;
	
	public Get(Expr ref) {
		this.refToGet = ref;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		Expr loc = refToGet.reduce(rtm);
		if (!(loc instanceof Location))
			throw new RuntimeException("Can only dereference a location.");
		Location location = (Location) loc;
		return rtm.dereference(location);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		Type refToGetType = refToGet.typeCheck(ctx);
		if (!(refToGetType instanceof Ref))
			throw new RuntimeException("Can only dereference a reference type.");
		Ref reference = (Ref) refToGetType;
		return reference.componentType();
	}

}
