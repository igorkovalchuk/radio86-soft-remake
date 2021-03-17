package radio86java.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// TODO: probably it is better to sandbox these things for security reasons, will see...
public class LocalFile implements FileInterface, SimpleFileInterface {

  private final String location;

  private final String fileName;

  private BufferedReader reader;

  private List<String> data;

  private boolean opened;

  public LocalFile(String location, String fileName) {
    this.location = location;
    this.fileName = fileName;
    this.opened = false;
  }

  @Override
  public void open() {
    try {
      Path path = FileSystems.getDefault().getPath(location, fileName);
      java.nio.charset.Charset charset = java.nio.charset.Charset.forName("UTF-8");
      reader = Files.newBufferedReader(path, charset);
      opened = true;
    } catch (IOException ex) {
      System.err.format("IOException: %s%n", ex);
    }
  }

  @Override
  public String read() {
    String line = "";
    if (opened) {
      try {
        line = reader.readLine();
      } catch (IOException ex) {
        System.err.format("IOException: %s%n", ex);
      }
      if (line == null) {
        close();
        opened = false;
        line = "";
      }
    }
    return line;
  }

  public void loadToArray() {
    data = new ArrayList<>();
    String s;
    while (!isCompleted()) {
      s = read();
      data.add(s);
    }
  }

  @Override
  public boolean isCompleted() {
    return !opened;
  }

  @Override
  public void close() {
    if (opened) {
      try {
        opened = false;
        reader.close();
      } catch (IOException ex) {
        System.err.format("IOException: %s%n", ex);
      }
    }
  }

  @Override
  public String[] asArray() {
    String[] result = new String[data.size()];
    return data.toArray(result);
  }

}
