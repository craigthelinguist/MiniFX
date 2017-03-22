package parsing;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
	
	private char[] data;
	private int i;
	private List<String> tokens;
	private char[] symbols = { '(', ')', ':', '+', '-', '*', '/', '!'};
	
	public Lexer(String input) {
		this.data = input.toCharArray();
	}
	
	public List<String> lex() throws LexException {
		this.i = 0;
		this.tokens = new ArrayList<>();
		skipWhitespace();
		
		while (i < data.length) {
			if (isSymbol(data[i])) lexSymbol();
			else if (Character.isLetter(data[i])) lexIdentifier();
			else if (Character.isDigit(data[i])) lexNumber();
			else throw new LexException("I don't know how to lex " + data[i]);
			skipWhitespace();
		}
		
		return tokens;
	}
	
	private void lexSymbol() throws LexException {
		if (!isSymbol(data[i]))
			throw new LexException(data[i] + " is not a valid symbol.");
		tokens.add("" + data[i++]);
	}

	private void lexNumber() throws LexException {
		if (!Character.isDigit(data[i]))
			throw new LexException(data[i] + " is not a digit, cannot lex as number.");
		
		int j = i;
		StringBuilder sb = new StringBuilder();
		
		while (j < data.length && Character.isDigit(data[j])) {
			sb.append(data[j++]);
		}

		i += j - i;
		tokens.add(sb.toString());	
	}

	private void lexIdentifier() throws LexException {
		
		if (!Character.isLetter(data[i])) {
			throw new LexException("Valid identifiers must begin with a letter, which " + data[i] + " is not.");
		}
		
		int j = i;
		StringBuilder sb = new StringBuilder();
		while (j < data.length && validIdentifierChar(data[j]))
			sb.append(data[j++]);
		
		i += j - i;
		tokens.add(sb.toString());
	}
	
	private boolean validIdentifierChar(char c) {
		return Character.isLetter(c)
				|| c == '-'
				|| c == '_'
				|| Character.isDigit(c);
	}

	private boolean isSymbol(char c) {
		for (int j = 0; j < symbols.length; j++) {
			if (c == symbols[j]) return true;
		}
		return false;
	}
	
	private void skipWhitespace() {
		while (i < data.length && Character.isWhitespace(data[i]))
			i++;
	}
	
}
