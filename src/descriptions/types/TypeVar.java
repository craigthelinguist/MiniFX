package descriptions.types;

import descriptions.DVar;
import descriptions.Kind;

public class TypeVar implements Type, DVar {

	private String name;
	
	public TypeVar(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean subtypeOf(Type other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Kind kind() {
		return Kind.TYPE;
	}

}
