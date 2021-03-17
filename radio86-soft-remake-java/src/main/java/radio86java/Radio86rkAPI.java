package radio86java;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.JOptionPane;
import radio86java.file.FileUtils;
import radio86java.file.SimpleFileInterface;

/**
 * The screen has 0-24 (25 Y), 0-63 (64 X) chars;
 * Coords start at left bottom corner;
 */
public class Radio86rkAPI {

	private static Radio86rkAPI instance;

	private final Radio86rk screen;

	public static Radio86rkAPI initializeInstance(Radio86rk screen1) {
		instance = new Radio86rkAPI(screen1);
		return instance;
	}

	public Radio86rkAPI(Radio86rk screen1) {
		screen = screen1;
	}

	public void interactive(boolean interact) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().setInteractive(interact);
		}
	}

	public void PRINT(String string) {
		print(string);
	}

	public void print(Object message) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().print(String.valueOf(message));
			instance.screen.updateScreen();
		}
	}

	public void PRINTLN(String string) {
		println(string);
	}

	public void println(Object message) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().println(String.valueOf(message));
			instance.screen.updateScreen();
		}
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
		if (instance != null && instance.screen != null) {
			instance.screen.getCanvas().setFreezeJPanel(true);
			instance.screen.setFreeze(true);
		}
	}

	public void unfreeze() {
		if (instance != null && instance.screen != null) {
			instance.screen.setFreeze(false);
			instance.screen.getCanvas().setFreezeJPanel(false);
			instance.screen.updateScreen();
		}
	}

	public String SPC(double n) {
		return spc(n);
	}

	public String spc(double nn) {
		int n = toInt(nn);
		if (n <= 0)
			return "";
		char[] c = new char[n];
		Arrays.fill(c, ' ');
		return String.valueOf(c);
	}

	public void CUR(double x, double y) {
		cur(x, y);
	}

	public void cur(double x, double y) {
		//System.out.println("CUR " + x + " " + y);
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().point(toInt(x), toInt(y));
	}

	public void PRINTTAB(double x) {
		printtab(x);
	}

	public void printtab(double x) {
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().tab(toInt(x));
	}

	public void CLS() {
		cls();
	}

	public void cls() {
		//System.out.println("CLS");
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().cls();
			instance.screen.updateScreen();
		}
	}

	public void PLOT(double x, double y, int z) {
		plot(x, y, z);
	}

	public void plot(double x, double y, int z) {
		//System.out.println("PLOT " + x + " " + y + " " + z);
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().plot(toInt(x), toInt(y), z);
			instance.screen.updateScreen();
		}
	}

	// arc in degrees;
	public void arcD(double x, double y, double r, double a1, double a2) {
		if (instance != null && instance.screen != null) {
			Console c = instance.screen.getConsole();
			for (double i = a1 * Math.PI; i <= a2 * Math.PI; i += 0.03) {
				c.plot(
					(int)Math.rint(x + Math.cos(i) * r),
					(int)Math.rint(y + Math.sin(i) * r), 1);
			}
			instance.screen.updateScreen();
		}
	}

	public void circle(double x, double y, double r) {
		if (instance != null && instance.screen != null) {
			Console c = instance.screen.getConsole();
			for (double i = 0; i <= 2 * Math.PI; i += 0.03) {
				c.plot((int)Math.rint(x + Math.cos(i) * r),
					(int)Math.rint(y + Math.sin(i) * r), 1);
			}
			instance.screen.updateScreen();
		}
	}

	public String INPUT(String message) {
		return input(message);
	}

	//private static Object inputMonitor = new Object();

	public String input(String message) {
		String value = "";
		if (instance != null && instance.screen != null) {
			value = JOptionPane.showInputDialog(instance.screen, message, "");
			//while(true) {
				// if without an JOptionPane wait and allow another thread 
				// to read an input String from user;	
			//}
		}
		println(message + " " + value);
		return value;
	}

	public void PAUSE(double seconds) {
		pause(seconds);
	}

	public void pause(double seconds) {
		if (!(instance != null && instance.screen != null)) {
			return;
		}
		if (seconds == 0) {
			// waiting for the user input (press any key);
			long time1 = System.currentTimeMillis();
			KeyEvent event = null;
			while(event == null) {
				if ((System.currentTimeMillis() - time1) > 5 * 60 * 1000) {
					break; // 5 minutes timeout;
				}
				event = instance.screen.getConsole().getLastKeyboardEvent(100);
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		else if (seconds > 0) {
			try {
				Thread.sleep((int)(seconds * 1000));
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String INKEY(int mode) {
		return inkey(mode);
	}

	public String inkey(int mode) {
		if (!(instance != null && instance.screen != null)) {
			return "";
		}
		
		KeyEvent event = null;
		
		if (mode == 0) {
			// wait until input;
			long time1 = System.currentTimeMillis();
			while(event == null) {
				if ((System.currentTimeMillis() - time1) > 5 * 60 * 1000) {
					break; // 5 minutes timeout;
				}
				event = instance.screen.getConsole().getLastKeyboardEvent(5000);
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		else {
			// don't wait;
			event = instance.screen.getConsole().getLastKeyboardEvent(5000);
		}
		
		if (event == null)
			return "";
		
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
		String value = "";
		if (instance != null && instance.screen != null) {
			value = String.valueOf(instance.screen.getConsole().get(toInt(x), toInt(y)));
		}
		return value;
	}

	public void LINE(double x, double y) {
		line(x, y);
	}

	public void line(double x, double y) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().line(toInt(x), toInt(y));
			instance.screen.updateScreen();
		}
	}

	private int toInt(double x) {
		if (x >= 0) {
			return (int)(x + 0.5);
		}
		return (int)(x - 0.5);
	}

	public void poke(int addr, int value) {
		instance.screen.getConsole().poke(addr, value);
		// TODO: optimize
		instance.screen.updateScreen();
	}

	public int peek(int addr) {
		return instance.screen.getConsole().peek(addr);
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
