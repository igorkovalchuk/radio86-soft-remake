package radio86java;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.SwingUtilities;

public class Console {

	private int cursorX = 0;
	private int cursorY = 0;
	private int maxX = 64;
	private int maxY = 25;
	private int lastX = maxX - 1;
	private int lastY = maxY - 1;
	private char[][] screen = new char[maxY][maxX];

	public Console() {
		cls();
		//print("1\n2\n3\n4\n5\n12345\n12345\n");
	}

	public char[][] getScreenCopy() {
		return Arrays.copyOf(screen, screen.length);
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getCursorX() {
		return cursorX;
	}

	public int getCursorY() {
		return cursorY;
	}

	public void cls() {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				screen[y][x] = ' ';
			}
		}
	}

	public void println(String s) {
		print(s);
		cr();
		lf();
	}

	public void print(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			print(c, false);
		}
	}

	private boolean interactive = true;

	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
		synchronized(keyboardQueue) {
			keyboardQueue.clear();
			keyboardQueueDate.clear();
		}
	}

	public boolean isInteractive() {
		return interactive;
	}

	private final LinkedList<KeyEvent> keyboardQueue = new LinkedList<KeyEvent>();
	private final LinkedList<Long> keyboardQueueDate = new LinkedList<Long>();

	public void key(KeyEvent e) {
		char c = e.getKeyChar();
		int k = e.getKeyCode();

		if (interactive) {
			if (k == KeyEvent.VK_SHIFT) {
				// does nothing;
			} else if (e.isActionKey()) {
				if (k == KeyEvent.VK_UP) {
					moveUp();
				} else if (k == KeyEvent.VK_DOWN) {
					lf(false);
				} else if (k == KeyEvent.VK_RIGHT) {
					move(false);
				} else if (k == KeyEvent.VK_LEFT) {
					moveLeft(false);
				}
			} else {
				print(c, false);
			}
		}
		else {
			synchronized(keyboardQueue) {
				keyboardQueue.add(e);
				keyboardQueueDate.add(System.currentTimeMillis());
				System.out.println("Keyboard put in queue: " + e);
			}
		}

	}

	public KeyEvent getLastKeyboardEvent(int timeout) {
		KeyEvent event;
		Long time;
		synchronized(keyboardQueue) {
			while(true) {
				event = null;
				time = null;
				try {
					event = keyboardQueue.pop();
					time = keyboardQueueDate.pop();
				}
				catch(java.util.NoSuchElementException ex) {
				}
				if (event == null) {
					break;
				}
				else {
					if (timeout < 0)
						break;
					if ((System.currentTimeMillis() - time) < timeout) {
						break;
					}
					else {
						event = null;
					}
				}
			}
		}
		if (event != null) {
			System.out.println("Keyboard returns from queue: " + event);
		}
		return event;
	}

	public void print(char c, boolean fixed) {
		if (c == 10) {
			// перевод строки;
			lf(fixed);
		} else if (c == 13) {
			// возврат каретки;
			cr();
		} else {
			screen[cursorY][cursorX] = Charset.converse(c);
			move(fixed);
		}
	}

	// move cursor
	private void move(boolean fixed) {
		cursorX++;
		if (cursorX > lastX) {
			if (fixed) {
				cursorX = lastX;
			} else {
				cursorX = 0;
				lf(fixed);
			}
		}
	}

	private void moveLeft(boolean fixed) {
		cursorX--;
		if (cursorX < 0) {
			cursorX = lastX;
			if (!fixed)
				moveUp();
		}
	}

	private void moveUp() {
		cursorY--;
		if (cursorY < 0)
			cursorY = 0;
	}

	public void lf() {
		lf(false);
	}

	private void lf(boolean fixed) {
		cursorY++;
		if (cursorY > lastY) {
			cursorY = lastY;
			if (!fixed)
				scroll();
		}
	}

	public void cr() {
		cursorX = 0;
	}

	private void scroll() {
		for (int y = 0; y < lastY; y++) {
			for (int x = 0; x < maxX; x++) {
				screen[y][x] = screen[y + 1][x];
			}
		}
		for (int x = 0; x < maxX; x++) {
			screen[lastY][x] = ' ';
		}
	}

	public char get(int x, int y) {
		return screen[y][x];
	}

	public void set(int x, int y, char c) {
		int cX = cursorX;
		int cY = cursorY;
		point(x, y);
		print(c, true);
		cursorX = cX;
		cursorY = cY;
	}

	public void point(int x, int y) {
		if (x > lastX) {
			x = lastX;
		}
		if (y > lastY) {
			y = lastY;
		}
		cursorX = x;
		cursorY = y;
	}

	private int pointX = 0;
	private int pointY = 0;

	public void plot(int x, int y, int z) {
		pointX = x;
		pointY = y;
		int x1 = x / 2;
		int y1 = y / 2;
		if (x1 < 0 || y1 < 0 || x1 > lastX || y1 > lastY)
			return;
		int rx = x - x1 * 2; // rest 1 or 0;
		int ry = y - y1 * 2;
		int pseudo1 = 0;
		if (rx == 0 && ry == 0) {
			pseudo1 = 1;
		} else if (rx == 1 && ry == 0) {
			pseudo1 = 2;
		} else if (rx == 0 && ry == 1) {
			pseudo1 = 16;
		} else if (rx == 1 && ry == 1) {
			pseudo1 = 4;
		}		
		char c = get(x1, y1);
		int ci = (int)c;
		if ((ci >= 1 && ci <= 7) || (ci >= 16 && ci <= 23)) {
			if (z == 0) {
				ci = ci & (~pseudo1);
				c = (char)ci;
			} else if (z == 1){
				ci = ci | pseudo1;
				c = (char)ci;
			} else {
				
			}
		} else {
			c = (char)pseudo1;
		}
		set(x1, y1, c);
	}

	private boolean coloredCharset = false;

	public void setColoredCharset(boolean value) {
		this.coloredCharset = value;
	}

	public boolean isColoredCharset() {
		return this.coloredCharset;
	}

}
