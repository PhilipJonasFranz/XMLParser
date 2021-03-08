package XMLParser;

/**
 * This exception is thrown when it is attempted to barse bad XML input, 
 * or when the XML that is parsed does not follow the XML-Grammar.
 * 
 * @author jonas.franz
 */
public class MalformedXMLException extends Exception {

			/* --- FIELDS --- */
	private static final long serialVersionUID = 4954963204648493440L;
	
	/** The message of this exception */
	private String message;
	
	
			/* --- CONSTRUCTORS --- */
	/**
	 * Create a new instance with a custom message.
	 */
	public MalformedXMLException(String message) {
		this.message = message;
	}
	
	/**
	 * Create a new instance with the default message.
	 */
	public MalformedXMLException() {
		this.message = "The given XML file is malformed!";
	}

	
			/* --- METHODS --- */
	@Override
	public String getMessage() {
		return this.message;
	}
	
}
