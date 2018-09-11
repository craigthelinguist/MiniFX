package testing;

import org.junit.Test;

import exprs.IntConst;
import types.Types;

public class LetTests {

	@Test
	public void letIdentity() {
		Utils.TestProg("(LET ((x 5)) x)",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void letAdd() {
		Utils.TestProg("(LET ((x 5) (y 10)) (+ x y))",
				Types.IntType(),
				new IntConst(15));
	}
	@Test
	public void cumulativeLet() {
		Utils.TestProg("(LET"
					 + "     ((x 5)"
					 + "      (y 10)"
					 + "      (z (+ x y)))"
					 + "     z)",
					 Types.IntType(),
					 new IntConst(15));
	}
	
	@Test
	public void letWithCompoundBody() {
		Utils.TestProg("(LET ((x 5) (y 5)) (+ x y))", Types.IntType(), new IntConst(10));
	}
	
	@Test
	public void applicationInLetBinding() {
		Utils.TestProg("(LET ((f (LAMBDA () PURE 1)) (x (f NIL))) x)",
				Types.IntType(),
				new IntConst(1));
	}
}
