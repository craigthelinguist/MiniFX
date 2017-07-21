package types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.scenario.effect.Effect;

public class Arrow implements Type {

	private Type[] inputTypes;
	private Type outputType;
	private Set<Effect> effects;
	
	public static Arrow Pure(List<Type> inputTypes, Type outputType) {
		return new Arrow(inputTypes, outputType, new HashSet<>());
	}
	
	public Arrow(List<Type> inputTypes, Type outputType, Set<Effect> fx) {
		this.inputTypes = new Type[inputTypes.size()];
		for (int i = 0; i < inputTypes.size(); i++) {
			this.inputTypes[i] = inputTypes.get(i);
		}
		this.outputType = outputType;
		this.effects = fx;
	}
	
	public static Arrow Pure(Type inputType, Type outputType) {
		return new Arrow(inputType, outputType, new HashSet<>());
	}
	
	public Arrow(Type inputType, Type outputType, Set<Effect> fx) {
		this.inputTypes = new Type[]{ inputType };
		this.outputType = outputType;
		this.effects = fx;
	}

	public static Arrow Pure(Type[] inputTypes, Type outputType) {
		return new Arrow(inputTypes, outputType, new HashSet<>());
	}
	
	public Arrow(Type[] inputTypes, Type outputType, Set<Effect> fx) {
		this.inputTypes = inputTypes;
		this.outputType = outputType;
		this.effects = fx;
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
		if (!this.effects.containsAll(arr.effects))
			return false;
		for (int i = 0; i < inputTypes.length; i++) {
			if (!arr.inputTypes[i].subtypeOf(this.inputTypes[i]))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(inputTypes);
		result = prime * result + ((outputType == null) ? 0 : outputType.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		String[] inputs = null;
		if (inputs.length == 0) {
			inputs = new String[]{ "Unit" };
		}
		else {
			inputs = new String[inputTypes.length];
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = inputTypes[i].toString();
			}
		}
		return String.join(" x ", inputs) + " --> " + outputType;
	}

	public int numArgs() {
		return inputTypes.length;
	}

}
