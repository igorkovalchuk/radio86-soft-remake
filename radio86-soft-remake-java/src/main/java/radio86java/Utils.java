package radio86java;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

  public static String loadResource(String name) {
    StringBuilder listing = new StringBuilder();

    String prefix = "/radio86java/samples/";
    InputStream is = Class.class.getResourceAsStream(prefix + name);
    if (is == null) {
      throw new RuntimeException("Samples InputStream == null");
    }

    Scanner s = new Scanner(is);
    while (s.hasNextLine()) {
      listing.append(s.nextLine()).append("\n");
    }
    s.close();

    return listing.toString();
  }

}
