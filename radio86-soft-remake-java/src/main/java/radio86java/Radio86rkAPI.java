package radio86java;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

	private Radio86rkAPI(Radio86rk screen1) {
		screen = screen1;
	}

	public static void interactive(boolean interact) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().setInteractive(interact);
		}
	}

	public static void PRINT(String string) {
		print(string);
	}

	public static void print(String string) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().print(string);
			instance.screen.updateScreen();
		}
	}

	public static void PRINTLN(String string) {
		println(string);
	}

	public static void println(String string) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().println(string);
			instance.screen.updateScreen();
		}
	}

	public static void CR() {
		cr();
	}

	public static void cr() {
		print("\r");
	}

	public static void LF() {
		lf();
	}

	public static void lf() {
		print("\n");
	}

	public static void CRLF() {
		crlf();
	}

	public static void crlf() {
		print("\r\n");
	}

	public static void freeze() {
		if (instance != null && instance.screen != null) {
			instance.screen.getCanvas().setFreezeJPanel(true);
			instance.screen.setFreeze(true);
		}
	}

	public static void unfreeze() {
		if (instance != null && instance.screen != null) {
			instance.screen.setFreeze(false);
			instance.screen.getCanvas().setFreezeJPanel(false);
			instance.screen.updateScreen();
		}
	}

	public static String SPC(double n) {
		return spc(n);
	}

	public static String spc(double nn) {
		int n = toInt(nn);
		if (n <= 0)
			return "";
		char[] c = new char[n];
		Arrays.fill(c, ' ');
		return String.valueOf(c);
	}

	public static void CUR(double x, double y) {
		cur(x, y);
	}

	public static void cur(double x, double y) {
		//System.out.println("CUR " + x + " " + y);
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().point(toInt(x), toInt(y));
	}

	public static void TAB(double x) {
		tab(x);
	}

	public static void tab(double x) {
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().tab(toInt(x));
	}

	public static void CLS() {
		cls();
	}

	public static void cls() {
		//System.out.println("CLS");
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().cls();
			instance.screen.updateScreen();
		}
	}

	public static void PLOT(double x, double y, int z) {
		plot(x, y, z);
	}

	public static void plot(double x, double y, int z) {
		//System.out.println("PLOT " + x + " " + y + " " + z);
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().plot(toInt(x), toInt(y), z);
			instance.screen.updateScreen();
		}
	}

	// arc in degrees;
	public static void arcD(double x, double y, double r, double a1, double a2) {
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

	public static void circle(double x, double y, double r) {
		if (instance != null && instance.screen != null) {
			Console c = instance.screen.getConsole();
			for (double i = 0; i <= 2 * Math.PI; i += 0.03) {
				c.plot((int)Math.rint(x + Math.cos(i) * r),
					(int)Math.rint(y + Math.sin(i) * r), 1);
			}
			instance.screen.updateScreen();
		}
	}

	public static String INPUT(String message) {
		return input(message);
	}

	//private static Object inputMonitor = new Object();

	public static String input(String message) {
		String value = "";
		if (instance != null && instance.screen != null) {
			value = JOptionPane.showInputDialog(instance.screen, message, "");
			//while(true) {
				// if without an JOptionPane wait and allow another thread 
				// to read an input String from user;	
			//}
		}
		print(message + " " + value);
		return value;
	}

	public static void PAUSE(double seconds) {
		pause(seconds);
	}

	public static void pause(double seconds) {
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

	public static String INKEY(int mode) {
		return inkey(mode);
	}

	public static String inkey(int mode) {
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

	public static void LINE(double x, double y) {
		line(x, y);
	}

	public static void line(double x, double y) {
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().line(toInt(x), toInt(y));
			instance.screen.updateScreen();
		}
	}

	private static int toInt(double x) {
		if (x >= 0) {
			return (int)(x + 0.5);
		}
		return (int)(x - 0.5);
	}

}
