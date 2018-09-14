package testing;

import java.util.Arrays;

import org.junit.Test;

import descriptions.types.Subr;
import descriptions.types.Types;
import exprs.Exprs;
import exprs.IntConst;

public class FunctionTests {

	@Test
	public void nilConst() {
		Utils.TestProg("NIL", Types.UnitType(), Exprs.Nil());
	}
	
	@Test
	public void lambdaOneArg() {
		Utils.TestProg("(LAMBDA ((x Int)) PURE x)",
				Subr.Pure(Types.IntType(), Types.IntType()),
				Utils.IdIntFunction());
	}
	
	@Test
	public void lambdaTwoArgs() {
		Utils.TestProg("(LAMBDA ((x Int) (y Int)) PURE (+ x y))",
				Subr.Pure(
					Arrays.asList(
							Types.IntType(),
							Types.IntType()),
					Types.IntType()),
				Utils.AddTwoIntsFunction());				
	}
	
	@Test
	public void lambdaBools() {
		Utils.TestProg("(LAMBDA ((x Bool) (y Bool)) PURE (not (and x y)))",
				Subr.Pure(Arrays.asList(
					Types.BoolType(),
					Types.BoolType()),
				Types.BoolType()),
				Utils.NandFunction());
	}
	
	@Test
	public void applyIdentity() {
		Utils.TestProg("((LAMBDA ((x Int)) PURE x) 5)",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void applyNand() {
		Utils.TestProg("((LAMBDA ((x Bool) (y Bool)) PURE (not (and x y))) true true)",
				Types.BoolType(),
				Exprs.False());
	}
	
	@Test
	public void applyAdd() {
		Utils.TestProg("((LAMBDA ((x Int) (y Int)) PURE (+ x y)) 5 10)",
				Types.IntType(),
				new IntConst(15));
	}

	@Test
	public void lambdaNoArgs() {
		Utils.TestProg("((LAMBDA () PURE 3) NIL)",
				Types.IntType(),
				new IntConst(3));
	}
	
	@Test
	public void lambdaNoArgs2() {
		Utils.TestProg("(LET ((f (LAMBDA () PURE 5))) (f NIL))",
				Types.IntType(),
				new IntConst(5));
	}
	
}
