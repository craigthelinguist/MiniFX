package descriptions.types;

public class Types {

	private static final Bool BOOL_TYPE = new Bool();
	private static final Int INT_TYPE = new Int();
	private static final Unit UNIT_TYPE = new Unit();
	
	public static Bool BoolType(){ return BOOL_TYPE; }
	public static Int IntType(){ return INT_TYPE; }
	public static Type UnitType(){ return UNIT_TYPE; }
	
	public static Type leastUpperBound(Type t1, Type t2) {
		
		if (t1.subtypeOf(t2)) return t2;
		if (t2.subtypeOf(t1)) return t1;
		
		/*
		if (t1 instanceof Int && t2 instanceof Nat || t1 instanceof Nat && t2 instanceof Int) {
			return INT_TYPE;
		}
		*/
		
		return null;
	}
	
	public static boolean equivalent(Type t1, Type t2) {
		return t1.subtypeOf(t2) && t2.subtypeOf(t1);
	}
	
}
