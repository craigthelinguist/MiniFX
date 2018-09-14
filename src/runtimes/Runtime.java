package runtimes;

import java.util.HashMap;
import java.util.Map;

import descriptions.fx.Effect;
import descriptions.fx.EffectVar;
import descriptions.regions.Region;
import descriptions.regions.RegionConst;
import descriptions.regions.RegionVar;
import descriptions.types.Type;
import descriptions.types.TypeVar;
import exprs.Location;
import exprs.Value;

public class Runtime {

	private Map<Location, Value> store;
	private Map<String, Value> varMapping;
	private Map<EffectVar, Effect> effectVarMapping;
	private Map<RegionVar, Region> regionVarMapping;
	private Map<TypeVar, Type> typeVarMapping;
	
	private Map<String, RegionConst> regions;
	
	private int counter = 0;
	
	public Runtime() {
		this.store = new HashMap<>();
		this.varMapping = new HashMap<>();
		this.regionVarMapping = new HashMap<>();
		this.typeVarMapping = new HashMap<>();
		this.effectVarMapping = new HashMap<>();
		this.regions = new HashMap<>();
	}
	
	private Runtime(Runtime other) {
		this.store = other.store;
		this.varMapping = new HashMap<>();
		for (String key : other.varMapping.keySet()) {
			this.varMapping.put(key, other.varMapping.get(key));
		}
	}
	
	protected void addHeap(String name) {
		if (regions.containsKey(name)) {
			throw new IllegalStateException("Adding heap `" + name + "` that already exists.");
		}
		regions.put(name, new RegionConst(name, counter++));
	}
	
	public RegionConst getHeap(String name) {
		return regions.get(name);
	}
	
	public boolean hasBinding(String name) {
		return this.varMapping.containsKey(name);
	}
	
	public Value lookupVariable(String name) {
		return this.varMapping.get(name);
	}
	
	public Runtime extend(String varName, Value value) {
		Runtime rtm2 = new Runtime(this);
		for (String name : varMapping.keySet()) {
			rtm2.varMapping.put(name, varMapping.get(name));
		}
		rtm2.varMapping.put(varName, value);		
		for (Location loc : store.keySet()) {
			rtm2.store.put(loc, store.get(loc));
		}
		return rtm2;
	}

	public Runtime extend(String[] varNames, Value[] bindingsReduced) {
		if (varNames.length != bindingsReduced.length)
			throw new RuntimeException("Must give a binding for each variable name.");
		Runtime rtm2 = new Runtime();
		rtm2.store = this.store;
		for (String varName : varMapping.keySet()) {
			rtm2.varMapping.put(varName, varMapping.get(varName));
		}
		for (int i = 0; i < varNames.length; i++) {
			rtm2.varMapping.put(varNames[i], bindingsReduced[i]);
		}
		for (Location loc : store.keySet()) {
			rtm2.store.put(loc,  store.get(loc));
		}
		return rtm2;
	}
	
	public Value deref(Location loc) {
		if (!store.containsKey(loc)) {
			throw new RuntimeException("Cannot dereference location " + loc);
		}
		return store.get(loc);
	}

	public void setRef(Location loc, Value val) {
		if (!store.containsKey(loc)) {
			throw new RuntimeException("Cannot set reference at location " + loc);
		}
		// TODO: could do a runtime typecheck of val here
		store.put(loc, val);	
	}

	public Value allocate(Region region, Type componentType, Value val) {
		Location loc = new Location(region, componentType);
		store.put(loc, val);
		return loc;
	}

}
