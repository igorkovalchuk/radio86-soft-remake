package radio86java;

public class Radio86rkAPI {

	private final Radio86rk screen;

	public Radio86rkAPI(Radio86rk screen) {
		this.screen = screen;
	}

	public void PRINT(String string) {
		print(string);
	}

	public void print(String string) {
		System.out.println("PRINT " + string);
		if (screen != null) {
			screen.getConsole().println(string);
			screen.updateScreen();
		}
	}

	public void CUR(int x, int y) {
		cur(x, y);
	}

	public void cur(int x, int y) {
		System.out.println("CUR " + x + " " + y);
		if (screen != null)
			screen.getConsole().point(x, y);
	}

	public void CLS() {
		cls();
	}

	public void cls() {
		System.out.println("CLS");
		if (screen != null) {
			screen.getConsole().cls();
			screen.updateScreen();
		}
	}

	public void PLOT(int x, int y, int z) {
		plot(x, y, z);
	}

	public void plot(int x, int y, int z) {
		System.out.println("PLOT " + x + " " + y + " " + z);
		if (screen != null) {
			screen.getConsole().plot(x, y, z);
			screen.updateScreen();
		}
	}

}
