package XMLParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

			/* ---< METHODS >--- */
	/** Reads the contents of given file and returns a list containing each line as a string. */
	public static List<String> readFile(File file) {
		try (Stream<String> s = Files.lines(Paths.get(file.getAbsolutePath()))) {
			return s.collect(Collectors.toList());
		} catch (IOException e) {
			return null;
		}
	}

} 
