package testing;

import org.junit.Test;

import descriptions.types.Types;
import exprs.Exprs;
import exprs.IntConst;
import runtimes.Standard;

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
		Utils.TestProg("(LET ((r (NEW std-heap Int 5)))"
				     + "     (BEGIN"
				     + "         (LET ((r (NEW std-heap Int 5)))"
				     + "             (SET r 20))"
				     + "         (GET r)))",
				     Standard.StdPrelude(),
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
