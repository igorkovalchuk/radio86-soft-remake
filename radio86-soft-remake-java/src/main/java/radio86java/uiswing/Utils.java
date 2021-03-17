package radio86java.uiswing;

import java.io.InputStream;
import java.util.Scanner;

public class Utils {

  private static final String RESOURCES_PATH = "/radio86java/";
  private static final String SAMPLES_PATH = RESOURCES_PATH + "samples/";

  public static InputStream getCharsetResourceAsStream() {
    String name = "rk_font1.bmp";
    InputStream is = Class.class.getResourceAsStream(RESOURCES_PATH + name);
    if (is == null) {
      throw new RuntimeException("Graphics InputStream == null");
    }
    return is;
  }

  public static String loadResource(String name) {
    StringBuilder listing = new StringBuilder();

    InputStream is = Class.class.getResourceAsStream(SAMPLES_PATH + name);
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
