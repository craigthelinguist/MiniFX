package types;

import java.util.List;

public class Arrow implements Type {

	private Type[] inputTypes;
	private Type outputType;
	
	public Arrow(List<Type> inputTypes, Type outputType) {
		this.inputTypes = new Type[inputTypes.size()];
		for (int i = 0; i < inputTypes.size(); i++) {
			this.inputTypes[i] = inputTypes.get(i);
		}
		this.outputType = outputType;
	}
	
	public Type[] inputTypes() {
		return this.inputTypes;
	}
	
	public Type outputType() {
		return this.outputType;
	}

	@Override
	public boolean subtypeOf(Type other) {
		if (!(other instanceof Arrow)) return false;
		Arrow arr = (Arrow) other;
		if (arr.inputTypes.length != inputTypes.length)
			return false;
		if (!this.outputType().subtypeOf(arr.outputType))
			return false;
		for (int i = 0; i < inputTypes.length; i++) {
			if (!arr.inputTypes[i].subtypeOf(this.inputTypes[i]))
				return false;
		}
		return true;
	}
	
}
