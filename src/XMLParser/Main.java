package XMLParser;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		try {
			XMLParser.parse(new File("res/test.xml")).print(System.out);
		} catch (MalformedXMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
