package parsing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import ctxs.Runtime;
import ctxs.TypeContext;
import exprs.ArithOper;
import exprs.BoolOper;
import exprs.Expr;
import exprs.Exprs;
import exprs.IntConst;
import exprs.Lambda;
import exprs.Location;
import exprs.Var;
import parsing.LexException;
import parsing.Lexer;
import parsing.ParseException;
import parsing.Parser;
import types.Arrow;
import types.Ref;
import types.Type;
import types.Types;

public class ParsingTests {

	private Expr compile(String input) throws ParseException, LexException {
		List<String> tokens = new Lexer(input).lex();
		return new Parser(tokens).parse();
	}
	
	private String stringFromFile(File file) {
		String input = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
			br.close();
			input = sb.toString();
		}
		catch (IOException ioe) {
			fail("Could not read from file " + input);
		}
		return input;
	}
	
	private Expr compile(File file) throws ParseException, LexException, IOException {
		return compile(stringFromFile(file));
	}
	
	private void shouldType(Expr ast, Type expected) {
		Type actual = ast.typeCheck(new TypeContext());
		System.out.println();
		assertTrue(Types.equivalent(expected, actual));
	}
	
	private void shouldReduceTo(Expr ast, Expr result) {
		Expr astReduced = ast.reduce(new Runtime());
		assertEquals(astReduced, result);
	}
	
	private void testProg(String input, Type expectedType, Expr expectedResult) {
		try {
			Expr ast = compile(input);
			shouldType(ast, expectedType);
			shouldReduceTo(ast, expectedResult);
		}
		catch (ParseException pe) {
			System.err.println("Error parsing");
			pe.printStackTrace();
			fail();
		}
		catch (LexException le) {
			System.err.println("Error lexing");
			le.printStackTrace();
			fail();
		}
	}
	
	private void testProg(File file, Type expectedType, Expr expectedResult) {
		testProg(stringFromFile(file), expectedType, expectedResult);
	}
	
	@Test
	public void intConst() {
		testProg("3", Types.IntType(), new IntConst(3));
	}
	
	@Test
	public void boolConstTrue() {
		testProg("true", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void boolConstFalse() {
		testProg("false", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void nilConst() {
		testProg("nil", Types.UnitType(), Exprs.Nil());
	}
	
	@Test
	public void addTwoInts() {
		testProg("(+ 1 2)", Types.IntType(), new IntConst(3));
	}
	
	@Test
	public void addFourInts() {
		testProg("(+ 1 2 3 4)", Types.IntType(), new IntConst(10));
	}
	
	@Test
	public void negateInt() {
		testProg("(- 2)", Types.IntType(), new IntConst(-2));
	}
	
	@Test
	public void subTwoInts() {
		testProg("(- 4 2)", Types.IntType(), new IntConst(2));
	}
	
	@Test
	public void mulTwoInts() {
		testProg("(* 3 4)", Types.IntType(), new IntConst(12));
	}
	
	@Test
	public void divTwoInts() {
		testProg("(/ 9 3)", Types.IntType(), new IntConst(3));
	}

	@Test
	public void notTrue() {
		testProg("(not true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void notFalse() {
		testProg("(not false)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void andTrueAndTrue() {
		testProg("(and true true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void andFalseAndTrue() {
		testProg("(and false true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void orFalseAndTrue() {
		testProg("(or false true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void orFalseAndFalse() {
		testProg("(or false false)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void xorFalseAndTrue() {
		testProg("(xor false true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void xorTrueAndTrue() {
		testProg("(xor true true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void lambdaOneArg() {
		testProg("(LAMBDA ((x Int)) x)",
				new Arrow(Types.IntType(), Types.IntType()),
				idIntFunction());
	}
	
	@Test
	public void lambdaTwoArgs() {
		testProg("(LAMBDA ((x Int) (y Int)) (+ x y))",
				new Arrow(
					Arrays.asList(
							Types.IntType(),
							Types.IntType()),
					Types.IntType()),
				addTwoIntsFunction());				
	}
	
	@Test
	public void lambdaBools() {
		testProg("(LAMBDA ((x Bool) (y Bool)) (not (and x y)))",
				new Arrow(Arrays.asList(
					Types.BoolType(),
					Types.BoolType()),
				Types.BoolType()),
				nandFunction());
	}
	
	@Test
	public void applyIdentity() {
		testProg("((LAMBDA ((x Int)) x) 5)",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void applyNand() {
		testProg("((LAMBDA ((x Bool) (y Bool)) (not (and x y))) true true)",
				Types.BoolType(),
				Exprs.False());
	}
	
	@Test
	public void applyAdd() {
		testProg("((LAMBDA ((x Int) (y Int)) (+ x y)) 5 10)",
				Types.IntType(),
				new IntConst(15));
	}
	
	@Test
	public void letIdentity() {
		testProg("(LET ((x 5)) x)",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void letAdd() {
		testProg("(LET ((x 5) (y 10)) (+ x y))",
				Types.IntType(),
				new IntConst(15));
	}
	
	@Test
	public void lambdaNoArgs() {
		testProg("((LAMBDA () 3) nil)",
				Types.IntType(),
				new IntConst(3));
	}
	
	@Test
	public void lambdaNoArgs2() {
		testProg("(LET ((f (LAMBDA () 5))) (f nil))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void newRef() {
		testProg("(NEW Int 5)",
				new Ref(Types.IntType()),
				new Location(0));
	}
	
	@Test
	public void readRef() {
		testProg("(GET (NEW Int 5))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void letWithRef() {
		testProg("(LET ((loc (NEW Int 5))) (GET loc))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void setRef() {
		testProg("(LET ((loc (NEW Int 5)))"
			   + "    (LET ((x (SET loc 10)))"
			   + "         (GET loc))) ",
			   Types.IntType(),
			   new IntConst(10));
	}
	
	@Test
	public void beginBlock() {
		testProg("(LET ((r (NEW Int 5)))"
			   + "    (BEGIN"
			   + "        (SET r 10)"
			   + "        (SET r 15)"
			   + "        (GET r)))",
			   Types.IntType(),
			   new IntConst(15));
	}
	
	private Lambda idIntFunction() {
		return new Lambda("x", Types.IntType(), new Var("x"));
	}
	
	private Lambda addTwoIntsFunction() {
		return new Lambda(
				Arrays.asList(
						new Var("x"),
						new Var("y")),
				Arrays.asList(
						Types.IntType(),
						Types.IntType()),
				ArithOper.NewAdd(Arrays.asList(
						new Var("x"),
						new Var("y"))));	
	}
	
	private Lambda nandFunction() {
		return new Lambda(
				Arrays.asList(
						new Var("x"),
						new Var("y")),
				Arrays.asList(
						Types.BoolType(),
						Types.BoolType()),
				BoolOper.NewNot(Arrays.asList(
						BoolOper.NewAnd(Arrays.asList(
								new Var("x"),
								new Var("y"))))));
	}
	
	
	
}
