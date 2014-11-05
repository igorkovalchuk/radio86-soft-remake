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
		//System.out.println("PRINT " + string);
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().println(string);
			instance.screen.updateScreen();
		}
	}

	public static String SPC(int n) {
		return spc(n);
	}

	public static String spc(int n) {
		if (n <= 0)
			return "";
		char[] c = new char[n];
		Arrays.fill(c, ' ');
		return String.valueOf(c);
	}

	public static void CUR(int x, int y) {
		cur(x, y);
	}

	public static void cur(int x, int y) {
		//System.out.println("CUR " + x + " " + y);
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().point(x, y);
	}

	public static void TAB(int x) {
		tab(x);
	}

	public static void tab(int x) {
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().tab(x);
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

	public static void PLOT(int x, int y, int z) {
		plot(x, y, z);
	}

	public static void plot(int x, int y, int z) {
		//System.out.println("PLOT " + x + " " + y + " " + z);
		if (instance != null && instance.screen != null) {
			instance.screen.getConsole().plot(x, y, z);
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

	public String SCREEN(int x, int y) {
		return screen(x, y);
	}

	public String screen(int x, int y) {
		String value = "";
		if (instance != null && instance.screen != null) {
			value = String.valueOf(instance.screen.getConsole().get(x, y));
		}
		return value;
	}

}
