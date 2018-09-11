package testing;

import org.junit.Test;

import exprs.Exprs;
import exprs.IntConst;
import types.Types;

public class ScopingTests {

	@Test
	public void testShadowing() {
		Utils.TestProg("(LET ((x 5)) "
				+      "     (LET ((x 10)) x))",
				Types.IntType(),
				new IntConst(10));
	}
	
	
	@Test
	public void testReferenceShadowing() {
		Utils.TestProg("(LET ((r (REF (REGION 1) Int 5)))"
				     + "     (BEGIN"
				     + "         (LET ((r (REF (REGION 2) Int 5)))"
				     + "             (SET r 20))"
				     + "         (GET r)))",
			   Types.IntType(),
			   new IntConst(5));
	}

	@Test
	public void testAccidentalCapture() {
		Utils.TestProg("((LAMBDA ((x Int)) PURE (LET ((x 5)) x)) 10)",
				Types.IntType(),
				new IntConst(5));
	}
	
		
}
