package XMLParser;

/**
 * This class represents a single building block of an XML-File.
 * The contained token type dictates the type of the token, and
 * the spelling stores the concrete spelling in the source file.
 * 
 * @author jonas.franz
 */
public class Token {
		
			/* --- NESTED --- */
	/**
	 * Enum to describe the type of a token.
	 */
	public enum TokenType {
		
		OPEN_BRACKET("<"),
		CLOSE_BRACKET(">"),
		SLASH("/"),
		STRING("");
		
		/** Default spelling of this token type */
		private final String spelling;
		
		private TokenType(String spelling) {
			this.spelling = spelling;
		}
		
		public String getSpelling() {
			return this.spelling;
		}
		
	}
	
	/**
	 * The type of the token.
	 */
	TokenType type;
	
	/**
	 * The spelling of the token. Can be used to get
	 * the actual String from a STRING-Token.
	 */
	String spelling;
	
	
			/* --- CONSTRUCTORS --- */
	/**
	 * Create a new token with a type and custom spelling.
	 */
	public Token(TokenType type, String spelling) {
		this.type = type;
		this.spelling = spelling;
	}
	
	/**
	 * Create a new token with a type and the default spelling
	 * of the token type.
	 */
	public Token(TokenType type) {
		this.type = type;
		this.spelling = type.getSpelling();
	}
	
	
			/* --- METHODS --- */
	/**
	 * Returns the type of this token.
	 */
	public TokenType getType() {
		return this.type;
	}
	
	/**
	 * Returns the spelling of this token.
	 */
	public String getSpelling() {
		return this.spelling;
	}
	
	@Override
	public String toString() {
		return "[" + this.type.toString() + ", " + this.spelling + "]\n";
	}
	
}