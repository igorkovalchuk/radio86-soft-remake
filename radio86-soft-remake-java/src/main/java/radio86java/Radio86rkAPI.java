package radio86java;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import radio86java.file.FileUtils;
import radio86java.file.SimpleFileInterface;

/**
 * The screen has 0-24 (25 Y), 0-63 (64 X) chars;
 * Coords start at left bottom corner;
 */
public class Radio86rkAPI {

  private final ComputerModelIntf computerModel;
  private final UserInterfaceIntf ui;

  public Radio86rkAPI(ComputerModelIntf computerModel, UserInterfaceIntf ui) {
    this.computerModel = computerModel;
    this.ui = ui;
  }

  private TerminalModel getTerminalModel() {
    return computerModel.getTerminalModel();
  }

  private void updateScreen() {
    ui.updateScreen();
  }

  public void interactive(boolean interact) {
    getTerminalModel().setInteractive(interact);
  }

  public void PRINT(String string) {
    print(string);
  }

  public void print(Object message) {

    getTerminalModel().print(String.valueOf(message));
    updateScreen();
  }

  public void PRINTLN(String string) {
    println(string);
  }

  public void println(Object message) {
    getTerminalModel().println(String.valueOf(message));
    updateScreen();
  }

  public void CR() {
    cr();
  }

  public void cr() {
    print("\r");
  }

  public void LF() {
    lf();
  }

  public void lf() {
    print("\n");
  }

  public void CRLF() {
    crlf();
  }

  public void crlf() {
    print("\r\n");
  }

  public void freeze() {
    ui.setFreeze(true);
  }

  public void unfreeze() {
    ui.setFreeze(false);
  }

  public String SPC(double n) {
    return spc(n);
  }

  public String spc(double nn) {
    int n = toInt(nn);
    if (n <= 0) {
      return "";
    }
    char[] c = new char[n];
    Arrays.fill(c, ' ');
    return String.valueOf(c);
  }

  public void CUR(double x, double y) {
    cur(x, y);
  }

  public void cur(double x, double y) {
    //System.out.println("CUR " + x + " " + y);
    getTerminalModel().point(toInt(x), toInt(y));
  }

  public void PRINTTAB(double x) {
    printtab(x);
  }

  public void printtab(double x) {
    getTerminalModel().tab(toInt(x));
  }

  public void CLS() {
    cls();
  }

  public void cls() {
    //System.out.println("CLS");
    getTerminalModel().cls();
    updateScreen();
  }

  public void PLOT(double x, double y, int z) {
    plot(x, y, z);
  }

  public void plot(double x, double y, int z) {
    //System.out.println("PLOT " + x + " " + y + " " + z);
    getTerminalModel().plot(toInt(x), toInt(y), z);
    updateScreen();
  }

  // arc in degrees;
  public void arcD(double x, double y, double r, double a1, double a2) {
    TerminalModel c = getTerminalModel();
    for (double i = a1 * Math.PI; i <= a2 * Math.PI; i += 0.03) {
      c.plot(
              (int) Math.rint(x + Math.cos(i) * r),
              (int) Math.rint(y + Math.sin(i) * r), 1);
    }
    updateScreen();
  }

  public void circle(double x, double y, double r) {
    TerminalModel c = getTerminalModel();
    for (double i = 0; i <= 2 * Math.PI; i += 0.03) {
      c.plot((int) Math.rint(x + Math.cos(i) * r),
              (int) Math.rint(y + Math.sin(i) * r), 1);
    }
    updateScreen();
  }

  public String INPUT(String message) {
    return input(message);
  }

  //private static Object inputMonitor = new Object();
  public String input(String message) {
    String value;
    value = ui.showInputDialog(message);
    //while(true) {
    // if without an JOptionPane wait and allow another thread 
    // to read an input String from user;	
    //}
    println(message + " " + value);
    return value;
  }

  public void PAUSE(double seconds) {
    pause(seconds);
  }

  public void pause(double seconds) {
    if (seconds == 0) {
      // waiting for the user input (press any key);
      long time1 = System.currentTimeMillis();
      KeyEvent event = null;
      while (event == null) {
        if ((System.currentTimeMillis() - time1) > 5 * 60 * 1000) {
          break; // 5 minutes timeout;
        }
        event = getTerminalModel().getLastKeyboardEvent(100);
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      }
    } else if (seconds > 0) {
      try {
        Thread.sleep((int) (seconds * 1000));
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  public String INKEY(int mode) {
    return inkey(mode);
  }

  public String inkey(int mode) {
    KeyEvent event = null;

    if (mode == 0) {
      // wait until input;
      long time1 = System.currentTimeMillis();
      while (event == null) {
        if ((System.currentTimeMillis() - time1) > 5 * 60 * 1000) {
          break; // 5 minutes timeout;
        }
        event = getTerminalModel().getLastKeyboardEvent(5000);
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      }
    } else {
      // don't wait;
      event = getTerminalModel().getLastKeyboardEvent(5000);
    }

    if (event == null) {
      return "";
    }

    char c = event.getKeyChar();
    int k = event.getKeyCode();

    if (event.isActionKey()) {
      if (k == KeyEvent.VK_UP) {
        return "up";
      } else if (k == KeyEvent.VK_DOWN) {
        return "down";
      } else if (k == KeyEvent.VK_RIGHT) {
        return "right";
      } else if (k == KeyEvent.VK_LEFT) {
        return "left";
      } else {
        return "";
      }
    } else if (k == KeyEvent.VK_ESCAPE) {
      return "esc";
    }

    return String.valueOf(c);
  }

  public String SCREEN(double x, double y) {
    return screen(x, y);
  }

  public String screen(double x, double y) {
    String value;
    value = String.valueOf(getTerminalModel().get(toInt(x), toInt(y)));
    return value;
  }

  public void LINE(double x, double y) {
    line(x, y);
  }

  public void line(double x, double y) {
    getTerminalModel().line(toInt(x), toInt(y));
    updateScreen();
  }

  private int toInt(double x) {
    if (x >= 0) {
      return (int) (x + 0.5);
    }
    return (int) (x - 0.5);
  }

  public void poke(int addr, int value) {
    getTerminalModel().poke(addr, value);
    // TODO: optimize
    updateScreen();
  }

  public int peek(int addr) {
    return getTerminalModel().peek(addr);
  }

  public char chr(int value) {
    return (char) value;
  }

  public int asc(String value) {
    if (value.length() == 0) {
      return 0; // TODO: error
    }
    return (int) value.toCharArray()[0];
  }

  public SimpleFileInterface localFile(String path, String fileName) {
    SimpleFileInterface file = FileUtils.loadLocalFile(path, fileName);
    return file;
  }

  public SimpleFileInterface remoteFile(String path) {
    SimpleFileInterface file = FileUtils.loadRemoteFile(path);
    return file;
  }

  public void log(Object message) {
    System.out.println("LOG: " + String.valueOf(message));
  }
}
