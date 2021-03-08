package XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import XMLParser.Token.TokenType;

/**
 * This class represents the XML-Dataformat as a tree structure and offers
 * the ability to load the datastructure from a given XML-File.
 * 
 * @author jonas.franz
 *
 */
public class XMLParser {
	
			/* ---< NESTED >--- */
	/**
	 * This class represents a single node in the XML-Tree.
	 * A node may contain a value:<br>
	 * 
	 * <code>&lt;foo&gt;value&lt;/foo&gt;</code><br>
	 * 
	 * or child-nodes:<br>
	 * 
	 * <code>&lt;foo&gt;</code><br>
	 * <code>&lt;bar&gt;value&lt;/bar&gt;</code><br>
	 * <code>&lt;/foo&gt;</code><br>
	 * 
	 * A node will always contain an id, in the example: 'foo'.
	 * 
	 * @author jonas.franz
	 *
	 */
	public static class XMLNode {
		
				/* ---< FIELDS >--- */
		/**
		 * The ID of this node. For example, the id for an xml-construct like<br>
		 * <br>
		 * 		&lt;foo&gt;value&lt;/foo&gt;<br>
		 * <br>
		 * should be 'foo'.
		 */
		private String ID;
		
		/**
		 * The value of this node. A node can either have a value or childs. The
		 * value of a node like<br>
		 * <br>
		 * 		&lt;foo&gt;value&lt;/foo&gt;<br>
		 * <br>
		 * should be 'value'.
		 */
		private String value;
		
		/**
		 * The childs of this node. A node can either have a value or childs.
		 */
		private List<XMLNode> children = new ArrayList();
		
		
				/* ---< CONSTRUCTORS >--- */
		/**
		 * Parses a XML-Node from the given token stream. This call may be recursive, and
		 * build the XML-Subtree defined in the token stream.
		 * @param tokens The Tokens that represent the XML-File.
		 * @throws MalformedXMLException Thrown if the XML-Input does not follow XML-Conventions.
		 */
		public XMLNode(List<Token> tokens) throws MalformedXMLException {
			accept(tokens, TokenType.OPEN_BRACKET);
			
			this.ID = accept(tokens, TokenType.STRING).getSpelling();
			
			accept(tokens, TokenType.CLOSE_BRACKET);
			
			if (tokens.isEmpty()) throw new MalformedXMLException();
			else if (tokens.get(0).getType() == TokenType.STRING) {
				/* Parse Value */
				this.value = accept(tokens).getSpelling();
			}
			else {
				/* Parse Children */
				while (tokens.size() > 2 && !(tokens.get(0).getType() == TokenType.OPEN_BRACKET && 
						tokens.get(1).getType() == TokenType.SLASH)) {
					
					this.children.add(new XMLNode(tokens));
				}
			}
			
			accept(tokens, TokenType.OPEN_BRACKET);
			accept(tokens, TokenType.SLASH);
			
			String closeID = accept(tokens, TokenType.STRING).getSpelling();
			if (!closeID.equals(this.ID)) throw new MalformedXMLException();
			
			accept(tokens, TokenType.CLOSE_BRACKET);
			
			return;
		}
		
		
				/* ---< METHODS >--- */
		/**
		 * Checks if the first token type is the given type. If this is the case, 
		 * the first token is removed from the list and returned.
		 * @param tokens The token list.
		 * @param type The expected type of the first token.
		 * @return The removed first token.
		 * @throws MalformedXMLException Thrown if the list is empty or the token type is not equal to the expected.
		 */
		private Token accept(List<Token> tokens, TokenType type) throws MalformedXMLException {
			if (tokens.isEmpty()) throw new MalformedXMLException();
			else {
				if (tokens.get(0).getType() == type) return accept(tokens);
				else throw new MalformedXMLException("Expected Token '" + type.toString() + "', but got '" + tokens.get(0).getType().toString() + "'");
			}
		}
		
		/**
		 * Removes the first token from the given list and returns it.
		 * @param tokens The token list.
		 * @return The removed first token.
		 * @throws MalformedXMLException Thrown if the list is empty.
		 */
		private Token accept(List<Token> tokens) throws MalformedXMLException {
			if (tokens.isEmpty()) throw new MalformedXMLException();
			else return tokens.remove(0);
		}
		
		/**
		 * Returns the node with given ID.
		 * @param ID The ID of the searched node.
		 * @return Returns the first match when the xml-tree is traversed inorder.
		 */
		public XMLNode getNode(String ID) {
			return this.getNodeRec(ID);
		}
		
		/**
		 * Recursiveley searches for the given ID in the XML-Tree and
		 * returns the first match or null, if no match is found.
		 * @param ID The searched ID
		 * @return The found node or null.
		 */
		private XMLNode getNodeRec(String ID) {
			if (this.ID.equals(ID)) return this;
			else {
				for (XMLNode n0 : this.children) {
					XMLNode n1 = n0.getNode(ID);
					if (n1 != null)return n1;
				}
				return null;
			}
		}
		
		/**
		 * Returns the value of this node. May be null.
		 * @return The value of this node.
		 */
		public String getValue() {
			return this.value;
		}
		
		/**
		 * Returns the value of the node with given ID. If this node has
		 * the given ID, the value of this node is returned. Otherwise, the
		 * subtree of this node is searched for the given ID.
		 * @param ID The ID of the searched node.
		 * @return The value that was found or null if no node exists with the given id.
		 */
		public String getValue(String ID) {
			if (this.getNode(ID) == null)return null;
			else return this.getNode(ID).value;
		}
		
		/**
		 * Returns the ID of this node.
		 * @return Returns the id of this node as string.
		 */
		public String getID() {
			return this.ID;
		}
		
		/**
		 * Returns the child nodes of this node as list.
		 * @return Returns the children of this node as list.
		 */
		public List<XMLNode> getChildren() {
			return this.children;
		}
		
	}
	
	
			/* ---< CONSTRUCTORS >--- */
	/**
	 * Creates a new XML-Tree from the contents of the given file.
	 * @param f The file that contains the XML data.
	 * @return A parsed XML node, which represents the root of the underlying XML-Tree.
	 * @throws MalformedXMLException Thrown if the XML Data is malformed.
	 */
	public static XMLNode parse(File f) throws MalformedXMLException {
		if (f == null) return null;
		
		/* Read file */
		List<String> file = Util.readFile(f);
		
		if (file == null) return null;
		
		/* Flatten to single string */
		String s = "";
		for (String s0 : file) s += s0.trim();
		
		/* Convert to token stream */
		List<Token> tokens = new Lexer().xmlToTokenStream(s);
		
		/* Parse XML Tree */
		return new XMLNode(tokens);
	}
	
	/**
	 * Creates a new XML-Tree from the contents of the strings
	 * @param xml The xml to be parsed as a list of strings
	 * @return A parsed XML node, which represents the root of the underlying XML-Tree.
	 * @throws MalformedXMLException Thrown if the XML Data is malformed.
	 */
	public static XMLNode parse(List<String> xml) throws MalformedXMLException {
		if (xml == null) return null;
		
		/* Flatten to single string */
		String s = "";
		for (String s0 : xml) s += s0.trim();
		
		/* Convert to token stream */
		List<Token> tokens = new Lexer().xmlToTokenStream(s);
		
		/* Parse XML Tree */
		return new XMLNode(tokens);
	}
	
} 
