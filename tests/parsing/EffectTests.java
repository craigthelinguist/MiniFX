package parsing;

import org.junit.Test;

import exprs.IntConst;
import types.Types;

public class EffectTests {

	@Test
	public void goodAnnotation() {
		Utils.TestProg("(LET ((MAKE-COUNTER"
					 + "          (LAMBDA ((r Region))"
					 + "                  (ALLOC r)"
					 + "                  (REF r Int 0)))"
					 + "      (MY-COUNTER (MAKE-COUNTER NEW-REGION)))"
					 + "      (GET MY-COUNTER))",
				Types.IntType(),
				new IntConst(0));
	}
	
	@Test
	public void badAnnotation() {
		Utils.ShouldntType("((LAMBDA ((r Region))"
						 + "         PURE"
						 + "         (REF r Int 0)) NEW-REGION)");
	}
	
}
