package parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exprs.Application;
import exprs.ArithOper;
import exprs.Begin;
import exprs.BoolConst;
import exprs.BoolOper;
import exprs.Expr;
import exprs.Exprs;
import exprs.Get;
import exprs.If;
import exprs.IntConst;
import exprs.Lambda;
import exprs.Let;
import exprs.NewRef;
import exprs.NewRegion;
import exprs.RefSet;
import exprs.Var;
import fx.Effect;
import fx.EffectAlloc;
import fx.EffectRead;
import fx.EffectUnion;
import fx.EffectVar;
import fx.EffectWrite;
import fx.Effects;
import types.Type;
import types.Types;

public class Parser {

	private List<String> tokens;
	private int i;

	private static List<String> KEYWORDS = Arrays.asList
		("LAMBDA", "LET", "GET", "SET", "REF", "NEW-REGION", "IF", "BEGIN");
	private static List<String> EFFECT_KEYWORDS = Arrays.asList
		("PURE", "READ", "WRITE", "ALLOC", "MAXEFF");
	private static List<String> OPERATORS = new ArrayList<>();
	private static List<String> BOOL_OPERS = Arrays.asList
		("and", "or", "not", "xor");
	private static List<String> ARITH_OPERS = Arrays.asList
		("*", "+", "-", "/");
	
	static {
		OPERATORS.addAll(BOOL_OPERS);
		OPERATORS.addAll(ARITH_OPERS);
	}
	
	private final String LEFT_PAREN = "(";
	private final String RIGHT_PAREN = ")";
	
	public Parser(List<String> tokens) {
		this.tokens = tokens;
	}
	
	public Expr parse() throws ParseException {
		this.i = 0;
		return parseExpr();
	}
	
	private boolean gobble(String expected) {
		if (tokens.get(i).equals(expected)) {
			i++;
			return true;
		}
		return false;
	}
	
	private boolean match(String expected) {
		return tokens.get(i).equals(expected);
	}
	
	private Expr parseExpr() throws ParseException {
		if (gobble(LEFT_PAREN)) {
			if (isKeyword(tokens.get(i))) {
				Expr xpr = parseKeyword();
				if (!gobble(RIGHT_PAREN))
					throw new ParseException(RIGHT_PAREN + " must close a compound expression, but got " + tokens.get(i));
				return xpr;
			}
			else if (isOperator(tokens.get(i))) {
				Expr xpr = parseOperator();
				if (!gobble(RIGHT_PAREN))
					throw new ParseException(RIGHT_PAREN + " must close a compound expression, but got " + tokens.get(i));
				return xpr;
			}
			else { // must be an application
				List<Expr> exprs = new ArrayList<>();
				while (!match(RIGHT_PAREN)) {
					exprs.add(parseExpr());
				}
				if (exprs.size() < 1)
					throw new ParseException("An application must have at least one argument.");
				return new Application(exprs);
			}
		}
		else if (isNumber(tokens.get(i))) {
			return new IntConst(Integer.parseInt(tokens.get(i++)));
		}
		else if (isBool(tokens.get(i))) {
			return tokens.get(i++).equals("true") ? new BoolConst(true) : new BoolConst(false);
		}
		else if (gobble("nil")) {
			return Exprs.Nil();
		}
		else if (isIdentifier(tokens.get(i))) {
			return new Var(tokens.get(i++));
		}
		else {
			throw new ParseException(tokens.get(i) + " is not a valid identifier."); 
		}
	}
	
	private Expr parseKeyword() throws ParseException {
		switch(tokens.get(i)) {
		case "BEGIN": return parseBegin();
		case "GET": return parseGet();
		case "IF": return parseIf();
		case "LAMBDA": return parseLambda();
		case "LET": return parseLet();
		case "REF": return parseNewRef();
		case "SET": return parseSet();
		case "NEW-REGION": return parseNewRegion();
		default: throw new ParseException(tokens.get(i-1) + " is not a keyword.");
		}
	}

	private Expr parseNewRegion() throws ParseException {
		if (!gobble("NEW-REGION"))
			throw new ParseException("Expected NEW-REGION when parsing a new region allocation.");
		return new NewRegion();
	}

	private RefSet parseSet() throws ParseException {
		if (!gobble("SET"))
			throw new ParseException("Expected SET when parsing a set expression.");
		Expr ref = parseExpr();
		Expr newValue = parseExpr();
		return new RefSet(ref, newValue);
	}

	private NewRef parseNewRef() throws ParseException {
		if (!gobble("REF"))
			throw new ParseException("Expected REF when parsing a new expression.");
		Expr region = parseExpr();
		Type type = parseType();
		Expr initValue = parseExpr();
		return new NewRef(region, type, initValue);
	}
	
	private Get parseGet() throws ParseException {
		if (!gobble("GET"))
			throw new ParseException("Expected GET when parsing a get expression.");
		Expr ref = parseExpr();
		return new Get(ref);
	}

	private Type parseType() throws ParseException {
		String t = tokens.get(i);
		Type type = null;
		if (t.equals("Bool")) type = Types.BoolType();
		if (t.equals("Unit")) type = Types.UnitType();
		if (t.equals("Int")) type = Types.IntType();
		if (type == null) throw new ParseException(t + " cannot be parsed as a type.");
		i++;
		return type;
	}

	private Expr parseLet() throws ParseException {
		if (!gobble("LET"))
			throw new ParseException("Expected LET at start of let expression.");
		if (!gobble(LEFT_PAREN))
			throw new ParseException("Expected ( to start bindings in let expression.");
		List<Var> namesToBind = new ArrayList<>();
		List<Expr> exprs = new ArrayList<>();
		while (!gobble(RIGHT_PAREN)) {			
			if (!gobble(LEFT_PAREN))
				throw new ParseException("( should start beginning of a binding.");
			namesToBind.add(parseVariable());
			exprs.add(parseExpr());
			if (!gobble(RIGHT_PAREN))
				throw new ParseException(") should end a binding.");
		}
		Expr body = parseExpr();
		return new Let(namesToBind, exprs, body);
	}

	private Var parseVariable() throws ParseException {
		if (!isIdentifier(tokens.get(i)))
			throw new ParseException(tokens.get(i) + " is not a valid variable name.");
		return new Var(tokens.get(i++));
	}

	private Expr parseLambda() throws ParseException {
		if (!gobble("LAMBDA"))
			throw new ParseException("Expected LAMBDA at begininng of lambda expression.");
		List<Var> argNames = new ArrayList<>();
		List<Type> argTypes = new ArrayList<>();
		if (!gobble(LEFT_PAREN))
			throw new ParseException("Expected ( to start function arguments of lambda.");
		while (!gobble(RIGHT_PAREN)) {
			if (!gobble(LEFT_PAREN))
				throw new ParseException("( should start beginning of definition of function arguments");
			argNames.add(parseVariable());
			argTypes.add(parseType());
			if (!gobble(RIGHT_PAREN))
				throw new ParseException(") should end a binding.");
		}
		Effect effect = parseEffect();
		Expr lambdaBody = parseExpr();
		return new Lambda(argNames, argTypes, effect, lambdaBody);
	}

	private Expr parseIf() throws ParseException {
		if (!gobble("IF"))
			throw new ParseException("Expected IF at start of conditional expression.");
		Expr guard = parseExpr();
		Expr trueBranch = parseExpr();
		Expr falseBranch = parseExpr();
		return new If(guard, trueBranch, falseBranch);
	}

	private Expr parseBegin() throws ParseException {
		if (!gobble("BEGIN"))
			throw new ParseException("Expected BEGIN at start of sequential execution block.");
		List<Expr> exprs = new ArrayList<>();
		while (!match(RIGHT_PAREN)) {
			exprs.add(parseExpr());
		}
		return new Begin(exprs);
	}

	private Expr parseOperator() throws ParseException {
		switch(tokens.get(i)) {
		case "*": 
		case "+":
		case "-":
		case "/":
			return parseArithOper();
		case "xor":
		case "and":
		case "or":
		case "not":
			return parseBoolOper();
		default:
			throw new ParseException(tokens.get(i-1) + " is not a valid operator.");
		}
	}

	private ArithOper parseArithOper() throws ParseException {
		if (!ARITH_OPERS.contains(tokens.get(i)))
			throw new ParseException(tokens.get(i) + " is not a valid arithmetic operator.");
		String operator = tokens.get(i++);
		List<Expr> exprs = new ArrayList<>();
		while (!match(RIGHT_PAREN)) {
			exprs.add(parseExpr());
		}
		switch(operator) {
		case "+": return ArithOper.NewAdd(exprs);
		case "-": return ArithOper.NewSub(exprs);
		case "/": return ArithOper.NewDiv(exprs);
		case "*": return ArithOper.NewMul(exprs);
		default: throw new RuntimeException("Unknown arithmetic operator " + operator);
		}
	}

	private BoolOper parseBoolOper() throws ParseException {
		if (!BOOL_OPERS.contains(tokens.get(i)))
			throw new ParseException(tokens.get(i) + " is not a valid boolean operator.");	
		String operator = tokens.get(i++); 
		List<Expr> exprs = new ArrayList<>();
		while (!match(RIGHT_PAREN)) {
			exprs.add(parseExpr());
		}
		switch(operator) {
		case "and": return BoolOper.NewAnd(exprs);
		case "or": return BoolOper.NewOr(exprs);
		case "xor": return BoolOper.NewXor(exprs);
		case "not": return BoolOper.NewNot(exprs);
		default: throw new RuntimeException("Unknown boolean operator " + operator);
		}
	}

	private boolean isBool(String string) {
		return string.equals("true") || string.equals("false");
	}

	private boolean isOperator(String token) {
		for (String oper : OPERATORS) {
			if (token.equals(oper)) return true;
		}
		return false;
	}
	
	private boolean isNumber(String token) {
		if(token.length() == 0)
			return false;
		for (int j = 0; j < token.length(); j++) {
			if (!Character.isDigit(token.charAt(j)))
				return false;
		}
		return true;
	}
	
	private boolean isIdentifier(String token) {
		if (token.length() == 0)
			return false;
		if (!Character.isLetter(token.charAt(0)))
			return false;
		for (int j = 1; j < token.length(); j++) {
			if (!validIdentifierChar(token.charAt(j)))
				return false;
		}
		return !isKeyword(token);
	}
	
	private boolean isKeyword(String token) {
		for (String kw : KEYWORDS) {
			if (token.equals(kw))
				return true;
		}
		return false;
	}
	
	private boolean isEffectKeyword(String token) {
		for (String kw : EFFECT_KEYWORDS) {
			if (token.equals(kw))
				return true;
		}
		return false;
	}
	
	private boolean validIdentifierChar(char c) {
		return Character.isLetter(c) || Character.isDigit(c) || c == '-' || c == '_';
	}
	
	private Effect parseEffect() throws ParseException {
		if (gobble(LEFT_PAREN)) {
			String kw = tokens.get(i);
			Effect effect;
			switch(kw) {
				case "WRITE": effect = parseWriteEffect(); break;
				case "READ": effect = parseReadEffect(); break;
				case "ALLOC": effect = parseAllocEffect(); break;
				case "MAXEFF": effect = parseUnionEffect(); break;	
				default: throw new ParseException("Unknown keyword found while parsing effect: " + kw);
			}
			if (!gobble(RIGHT_PAREN))
				throw new ParseException("Missing right bracket while parsing effect.");
			return effect;
		}
		else if (tokens.get(i).equals("PURE")) {
			return parsePureEffect();
		}
		else if (isIdentifier(tokens.get(i))) {
			return parseEffectVar();
		}
		else {
			throw new ParseException("Failed parsing effect on token " + tokens.get(i));
		}
	}
	
	private Effect parseUnionEffect() throws ParseException {
		if (!gobble("MAXEFF"))
			throw new ParseException("Expected MAXEFF while parsing a union of effects.");
		List<Effect> effects = new ArrayList<>();
		while (!tokens.get(i).equals(RIGHT_PAREN)) {
			effects.add(parseEffect());
		}
		gobble(RIGHT_PAREN);
		return new EffectUnion(effects);
	}

	private Effect parseEffectVar() throws ParseException {
		if (!isIdentifier(tokens.get(i)))
			throw new ParseException("Failed parsing effect variable: " + tokens.get(i) + " is not a valid identifier.");
		return new EffectVar(tokens.get(i++));
	}
	
	private Effect parseWriteEffect() throws ParseException {
		if (!gobble("WRITE"))
			throw new ParseException("Expected WRITE while parsing a write effect.");
		Expr region = parseExpr();
		return new EffectWrite(region);
	}
	
	private Effect parseReadEffect() throws ParseException {
		if (!gobble("READ"))
			throw new ParseException("Expected READ while parsing a read effect.");
		Expr region = parseExpr();
		return new EffectRead(region);
	}
	
	private Effect parseAllocEffect() throws ParseException {
		if (!gobble("ALLOC"))
			throw new ParseException("Expected ALLOC while parsing an allocation effect.");
		Expr region = parseExpr();
		return new EffectAlloc(region);
	}
	
	private Effect parsePureEffect() throws ParseException {
		if (!gobble("PURE"))
			throw new ParseException("Expected PURE while parsing the pure effect.");
		return Effects.PureEffect();
	}
	
}
