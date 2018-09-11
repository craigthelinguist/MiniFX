package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import exprs.ArithOper;
import exprs.BoolOper;
import exprs.Expr;
import exprs.Lambda;
import exprs.Var;
import fx.EffectCheckException;
import fx.Effects;
import parsing.LexException;
import parsing.Lexer;
import parsing.ParseException;
import parsing.Parser;
import types.Type;
import types.TypeCheckException;
import types.Types;

public class Utils {

	public static Expr Compile(String input) throws ParseException, LexException {
		List<String> tokens = new Lexer(input).lex();
		return new Parser(tokens).parse();
	}
	
	public static String StringFromFile(File file) {
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
	
	public static Expr Compile(File file) throws ParseException, LexException, IOException {
		return Compile(StringFromFile(file));
	}
	
	public static void ShouldType(Expr ast, Type expected) {
		try {
			Type actual = ast.typeCheck(new TypeContext());
			assertTrue(Types.equivalent(expected, actual));
		}
		catch (TypeCheckException | EffectCheckException e) {
			System.err.println("Should have type and effect checked, but it didn't.");
			System.err.println(e.toString());
			fail(e.toString());
		}
	}
	
	public static void ShouldntType(String prog) {
		try {
			Expr ast = Compile(prog);
			ast.typeCheck(new TypeContext());
		}
		catch (ParseException | LexException e) {
			fail("Should have parsed and lexed, but it didn't.");
		}
		catch (EffectCheckException efce) {
			fail("Should have effect-checked, but it didn't.");
		}
		catch (TypeCheckException tce) {
		}
	}
	
	public static void ShouldntEffectCheck(String prog) {
		try {
			Expr ast = Compile(prog);
			ast.typeCheck(new TypeContext());
		}
		catch (ParseException | LexException e) {
			fail("Should have parsed and lexed, but it didn't.");
		}
		catch (EffectCheckException efce) {
			
		}
		catch (TypeCheckException tce) {
			fail("Should have typed, but it didn't.");
		}
	}
	
	public static void ShouldReduceTo(Expr ast, Expr result) {
		Expr astReduced = ast.reduce(new Runtime());
		assertEquals(astReduced, result);
	}
	
	public static void TestProg(String input, Type expectedType, Expr expectedResult) {
		try {
			Expr ast = Compile(input);
			ShouldType(ast, expectedType);
			ShouldReduceTo(ast, expectedResult);
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
	
	public static void TestProg(File file, Type expectedType, Expr expectedResult) {
		TestProg(StringFromFile(file), expectedType, expectedResult);
	}
	
	
	public static Lambda IdIntFunction() {
		return new Lambda("x", Types.IntType(), new Var("x"));
	}
	
	public static Lambda AddTwoIntsFunction() {
		return new Lambda(
				Arrays.asList(
						new Var("x"),
						new Var("y")),
				Arrays.asList(
						Types.IntType(),
						Types.IntType()),
				Effects.PureEffect(),
				ArithOper.NewAdd(Arrays.asList(
						new Var("x"),
						new Var("y"))));	
	}
	
	public static Lambda NandFunction() {
		return new Lambda(
				Arrays.asList(
						new Var("x"),
						new Var("y")),
				Arrays.asList(
						Types.BoolType(),
						Types.BoolType()),
				Effects.PureEffect(),
				BoolOper.NewNot(Arrays.asList(
						BoolOper.NewAnd(Arrays.asList(
								new Var("x"),
								new Var("y"))))));
	}
	
}
