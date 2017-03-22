package exprs;

import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;
import types.Types;
import types.Bool;

public class BoolOper implements Expr {

	private OperType operator;
	private Expr[] args;
	
	private enum OperType {
		AND, OR, XOR, NOT
	}
	
	public static BoolOper NewAnd(List<Expr> exprs) {
		if (exprs.size() < 2)
			throw new RuntimeException("Need at least two arguments for conjunction.");
		BoolOper bo = new BoolOper(exprs);
		bo.operator = OperType.AND;
		return bo;
	}
	
	public static BoolOper NewOr(List<Expr> exprs) {
		if (exprs.size() < 2)
			throw new RuntimeException("Need at least two arguments for disjunction.");
		BoolOper bo = new BoolOper(exprs);
		bo.operator = OperType.OR;
		return bo;
	}
	
	public static BoolOper NewXor(List<Expr> exprs) {
		if (exprs.size() < 2)
			throw new RuntimeException("Need at least two arguments for exclusive or.");
		BoolOper bo = new BoolOper(exprs);
		bo.operator = OperType.XOR;
		return bo;
	}
	
	public static BoolOper NewNot(List<Expr> exprs) {
		if (exprs.size() < 1)
			throw new RuntimeException("Need at least one argument for negation.");
		BoolOper bo = new BoolOper(exprs);
		bo.operator = OperType.NOT;
		return bo;
	}
	
	private BoolOper(List<Expr> exprs) {
		this.args = new Expr[exprs.size()];
		for (int i = 0; i < exprs.size(); i++) {
			this.args[i] = exprs.get(i);
		}
	}
	
	@Override
	public Expr reduce(Runtime rtm) {
		boolean unit;
		switch(operator) {
		case AND: 
			for (Expr arg : args) {
				BoolConst bool = (BoolConst) arg.reduce(rtm);
				if (!bool.asBool()) return Exprs.False();
			}
			return Exprs.True();
		case OR:
			for (Expr arg : args) {
				BoolConst bool = (BoolConst) arg.reduce(rtm);
				if (bool.asBool()) return Exprs.True();
			}
			return Exprs.False();
		case XOR:
			unit = ((BoolConst)args[0]).asBool();
			for (int i = 1; i < args.length; i++) {
				unit = ((BoolConst)args[i]).asBool() != unit;
			}
			return unit ? Exprs.True() : Exprs.False();
		case NOT:
			boolean val = ((BoolConst)args[0]).asBool();
			return val ? Exprs.False() : Exprs.True();
		default:
			throw new RuntimeException("Operator " + operator + " not yet implemented.");
		}
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		for (Expr expr : args) {
			Type t = expr.typeCheck(ctx);
			if (!(t instanceof Bool))
				throw new RuntimeException("Must supply Bools to a boolean operator");
		}
		return Types.BoolType();
	}
	
	
	
}
