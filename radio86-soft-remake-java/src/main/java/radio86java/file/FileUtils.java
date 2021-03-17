package radio86java.file;

public class FileUtils {

  public static SimpleFileInterface loadLocalFile(String location, String fileName) {
    LocalFile f = new LocalFile(location, fileName);
    f.open();
    f.loadToArray();
    return f;
  }

  public static SimpleFileInterface loadRemoteFile(String path) {
    RemoteFile f = new RemoteFile(path);
    f.loadToArray();
    return f;
  }

}
