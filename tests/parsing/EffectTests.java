package parsing;

import org.junit.Test;

import exprs.IntConst;
import types.Types;

public class EffectTests {

	@Test
	public void goodAnnotation() {
		Utils.TestProg("(LET ((MAKE-COUNTER"
					 + "          (LAMBDA () (ALLOC 1) (REF (REGION 1) Int 0)))"
					 + "      (MY-COUNTER (MAKE-COUNTER NIL)))"
					 + "      (GET MY-COUNTER))",
				Types.IntType(),
				new IntConst(0));
	}
	
	@Test
	public void badAnnotation() {
		Utils.ShouldntType("((LAMBDA () PURE (REF (REGION 1) Int 0)) NIL)");
	}
	
}
