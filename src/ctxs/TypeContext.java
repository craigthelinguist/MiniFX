package ctxs;

import java.util.HashMap;
import java.util.Map;

import types.Type;

public class TypeContext {
	
	private Map<String, Type> bindings;
	
	public TypeContext() {
		this.bindings = new HashMap<>();
	}
	
	public boolean hasBinding(String varName) {
		return bindings.containsKey(varName);
	}
	
	public Type lookupType(String varName) {
		return bindings.get(varName);
	}
	
	public TypeContext extend(String varName, Type t) {
		TypeContext ctx2 = new TypeContext();
		for (String str : bindings.keySet()) {
			ctx2.bindings.put(str, bindings.get(str));
		}
		ctx2.bindings.put(varName, t);
		return ctx2;
	}

	public TypeContext extend(String[] varNames, Type[] types) {
		if (varNames.length != types.length)
			throw new RuntimeException("Must extend with a type for every name.");
		TypeContext ctx2 = new TypeContext();
		for (String str : bindings.keySet()) {
			ctx2.bindings.put(str, bindings.get(str));
		}
		for (int i = 0; i < varNames.length; i++) {
			ctx2.bindings.put(varNames[i], types[i]);
		}
		return ctx2;
	}

}
