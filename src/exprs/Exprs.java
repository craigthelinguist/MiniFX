package exprs;

public class Exprs {

	private static final Nil NIL = new Nil();
	private static final BoolConst TRUE = new BoolConst(true);
	private static final BoolConst FALSE = new BoolConst(false);
	
	public static Nil Nil(){ return NIL; }
	public static BoolConst True() { return TRUE; }
	public static BoolConst False() { return FALSE; }
	
}
