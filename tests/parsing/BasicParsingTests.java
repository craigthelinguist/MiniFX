package parsing;
import java.util.Arrays;

import org.junit.Test;

import exprs.Exprs;
import exprs.IntConst;
import exprs.Location;
import types.Arrow;
import types.Ref;
import types.Types;

public class BasicParsingTests {

	@Test
	public void intConst() {
		Utils.TestProg("3", Types.IntType(), new IntConst(3));
	}
	
	@Test
	public void boolConstTrue() {
		Utils.TestProg("true", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void boolConstFalse() {
		Utils.TestProg("false", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void nilConst() {
		Utils.TestProg("NIL", Types.UnitType(), Exprs.Nil());
	}
	
	@Test
	public void addTwoInts() {
		Utils.TestProg("(+ 1 2)", Types.IntType(), new IntConst(3));
	}
	
	@Test
	public void addFourInts() {
		Utils.TestProg("(+ 1 2 3 4)", Types.IntType(), new IntConst(10));
	}
	
	@Test
	public void negateInt() {
		Utils.TestProg("(- 2)", Types.IntType(), new IntConst(-2));
	}
	
	@Test
	public void subTwoInts() {
		Utils.TestProg("(- 4 2)", Types.IntType(), new IntConst(2));
	}
	
	@Test
	public void mulTwoInts() {
		Utils.TestProg("(* 3 4)", Types.IntType(), new IntConst(12));
	}
	
	@Test
	public void divTwoInts() {
		Utils.TestProg("(/ 9 3)", Types.IntType(), new IntConst(3));
	}

	@Test
	public void notTrue() {
		Utils.TestProg("(not true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void notFalse() {
		Utils.TestProg("(not false)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void andTrueAndTrue() {
		Utils.TestProg("(and true true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void andFalseAndTrue() {
		Utils.TestProg("(and false true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void orFalseAndTrue() {
		Utils.TestProg("(or false true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void orFalseAndFalse() {
		Utils.TestProg("(or false false)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void xorFalseAndTrue() {
		Utils.TestProg("(xor false true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void xorTrueAndTrue() {
		Utils.TestProg("(xor true true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void conditional() {
		Utils.TestProg("(IF true 1 0)", Types.IntType(), new IntConst(1));
	}
	
	@Test
	public void conditional2() {
		Utils.TestProg("(IF false 1 0)",  Types.IntType(), new IntConst(0));
	}
	
	@Test
	public void lambdaOneArg() {
		Utils.TestProg("(LAMBDA ((x Int)) PURE x)",
				new Arrow(Types.IntType(), Types.IntType()),
				Utils.IdIntFunction());
	}
	
	@Test
	public void lambdaTwoArgs() {
		Utils.TestProg("(LAMBDA ((x Int) (y Int)) PURE (+ x y))",
				new Arrow(
					Arrays.asList(
							Types.IntType(),
							Types.IntType()),
					Types.IntType()),
				Utils.AddTwoIntsFunction());				
	}
	
	@Test
	public void lambdaBools() {
		Utils.TestProg("(LAMBDA ((x Bool) (y Bool)) PURE (not (and x y)))",
				new Arrow(Arrays.asList(
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
	
	@Test
	public void newRef() {
		Utils.TestProg("(REF NEW-REGION Int 5)",
				new Ref(Types.IntType()),
				new Location(0));
	}
	
	@Test
	public void readRef() {
		Utils.TestProg("(GET (REF NEW-REGION Int 5))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void letWithRef() {
		Utils.TestProg("(LET ((loc (REF NEW-REGION Int 5))) (GET loc))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void setRef() {
		Utils.TestProg("(LET ((loc (REF NEW-REGION Int 5)))"
			   + "    (LET ((x (SET loc 10)))"
			   + "         (GET loc))) ",
			   Types.IntType(),
			   new IntConst(10));
	}
	
	@Test
	public void beginBlock() {
		Utils.TestProg("(LET ((r (REF NEW-REGION Int 5)))"
			   + "    (BEGIN"
			   + "        (SET r 10)"
			   + "        (SET r 15)"
			   + "        (GET r)))",
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
