package model.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

/**
 * Class that represents the operations required to read a script to be used by the controller.
 */
public class FileUtil {

  /**
   * Reads the script in the given file and returns a Readable object containing the commands.
   *
   * @param filename of the file to read from
   * @return the Readable containing the commands
   * @throws IllegalArgumentException if it is not able to read from the file
   */
  public StringReader readScript(String filename) throws IllegalArgumentException {

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.equals("") && s.charAt(0) != '#') {
        builder.append(s + " ");
      }
    }

    return new StringReader(builder.toString());
  }
}
