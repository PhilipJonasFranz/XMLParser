package XMLParser;

import java.util.ArrayList;
import java.util.List;

import XMLParser.Token.TokenType;

/**
 * The lexer is responsible for converting the passed xml in string format
 * into a stream of Tokens. The tokens will contain their spelling, as well
 * as a token type that denote their type.
 * 
 * @author jonas.franz
 *
 */
public class Lexer {
	
	/**
	 * Internal buffer for passed xml.
	 */
	private String xml = null;
	
	/**
	 * Parses a list of tokens from the given string that 
	 * represents a flattened XML-File.
	 * @param xml0 The contents of the xml file, flattened to one string.
	 * @return A list of tokens representing the xml file.
	 */
	public List<Token> xmlToTokenStream(String xml0) {
		this.xml = xml0;
		
		List<Token> tokens = new ArrayList();
		
		while (!xml.isEmpty()) {
			if (xml.startsWith("<!--")) {
				while (!xml.isEmpty() && !xml.startsWith(">")) cut(1);
				cut(1);
			}
			else if (xml.startsWith("<")) {
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
	
	/** 
	 * Cuts of the first n characters of the currently parsed string. 
	 * @param n Cuts of the first n characters of {@link #xml}.
	 */
	public void cut(int n) {
		xml = xml.substring(n);
	}
	
}