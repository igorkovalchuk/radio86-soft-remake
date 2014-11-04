package radio86java;

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

	public static void CUR(int x, int y) {
		cur(x, y);
	}

	public static void cur(int x, int y) {
		//System.out.println("CUR " + x + " " + y);
		if (instance != null && instance.screen != null)
			instance.screen.getConsole().point(x, y);
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

	public static void INPUT() {
		input();
	}

	public static void input() {
		if (instance != null && instance.screen != null) {
			
		}
	}

}
