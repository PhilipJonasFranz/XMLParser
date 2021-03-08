package XMLParser;

import java.util.ArrayList;
import java.util.List;

import XMLParser.Token.TokenType;

public class Lexer {
	
	private String xml = null;
	
	/**
	 * Parses a list of tokens from the given string that 
	 * represents a flattened XML-File.
	 * @param xml The contents of the xml file, flattened to one string.
	 * @return A list of tokens representing the xml file.
	 */
	public List<Token> xmlToTokenStream(String xml0) {
		this.xml = xml0;
		
		List<Token> tokens = new ArrayList();
		
		while (!xml.isEmpty()) {
			if (xml.startsWith("<")) {
				tokens.add(new Token(TokenType.OPEN_BRACKET));
				cut(1);
			}
			else if (xml.startsWith(">")) {
				tokens.add(new Token(TokenType.CLOSE_BRACKET));
				cut(1);
			}
			else if (xml.startsWith("/")) {
				tokens.add(new Token(TokenType.SLASH));
				cut(1);
			}
			else {
				String value = "";
				while (!xml.isEmpty() && !(xml.startsWith("<") || xml.startsWith(">"))) {
					value += xml.charAt(0);
					cut(1);
				}
				
				tokens.add(new Token(TokenType.STRING, value));
			}
		}
		
		return tokens;
	}
	
	/** Cuts of the first n characters of the currently parsed string. */
	public void cut(int n) {
		xml = xml.substring(n);
	}
	
}