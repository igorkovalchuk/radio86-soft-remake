package radio86java.file;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoteFile implements SimpleFileInterface {

  private final String path;

  private List<String> data;

  public RemoteFile(String path) {
    this.path = path;
  }

  public void loadToArray() {
    try {
      System.out.println("Loading: " + path);
      if (!path.startsWith("http") && !path.startsWith("ftp")) {
        throw new IOException("http/https/ftp remote file path required!");
      }

      URL url = new URL(path);
      Scanner s = new Scanner(url.openStream());

      data = new ArrayList<>();

      while (s.hasNext()) {
        data.add(s.nextLine());
      }
      System.out.println("Loading: done");
    } catch (IOException ex) {
      System.err.format("IOException: %s%n", ex);
    }
  }

  @Override
  public String[] asArray() {
    String[] result = new String[data.size()];
    return data.toArray(result);
  }

}
