package radio86java;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class TerminalModel {

	private final int maxX = 64; // 64
	private final int maxY = 25; // 25
	private final int lastX = maxX - 1;
	private final int lastY = maxY - 1;
	private char[][] screen = new char[maxY][maxX];

	private Memory memory = new Memory();

	private int cursorX = 0;
	private int cursorY = 0;

	// In this place we define a coordinate system type;
	// 1 => 0 at bottom, 24 at top; 0 => 0 at top, 24 at bottom;
	private final int directionUp = 1; // 0 or 1; default is 1;

	public TerminalModel() {
		cls();
		pointUpLeft();
	}

	private void modifyYX(int y, int x, char c) {
		screen[y][x] = c;
		if (directionUp == 1) {
			y = lastY - y;
		}
		memory.poke(memory.getAddr(y, x), c);
	}

	public void poke(int addr, int value) {
		memory.poke(addr, value);
		int[] yx = memory.getYX(addr);
		if (yx[0] != Memory.UNKNOWN_COORD) {
			if (directionUp == 1) {
				yx[0] = lastY - yx[0];
			}
			screen[yx[0]][yx[1]] = (char) value;
		}
	}

	public int peek(int addr) {
		return memory.peek(addr);
	}

	public int getDirectionUp() {
		return directionUp;
	}

	public final void pointUpLeft() {
		point(0, lastY);
	}

	//public char[][] getScreenCopy() {
		//return Arrays.copyOf(screen, screen.length);
	//}

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

	public final void cls() {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				modifyYX(y, x, ' ');
			}
		}
	}

	public void print(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			print(c, false);
		}
	}

	public void println(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			print(c, false);
		}
		cr();
		lf();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

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

	////////////////////////////////////////////////////////////////////////////////////////////////

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
					move1(false);
					move2(false);
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
				//System.out.println("Keyboard put in queue: " + e);
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
			//System.out.println("Keyboard returns from queue: " + event);
		}
		return event;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean inScreen(int x, int y) {
		if ((y >= 0) && (y <= lastY) && (x >= 0) && (x <= lastX))
			return true;
		return false;
	}

	private void print(char c, boolean fixed) {
		if (c == 10) {
			// перевод строки;
			lf(fixed);
		} else if (c == 13) {
			// возврат каретки;
			cr();
		} else {
			move1(fixed);
			if (inScreen(cursorX, cursorY))
				modifyYX(cursorY, cursorX, Charset.converse(c));
			move2(fixed);
		}
	}

	// move cursor
	private void move1(boolean fixed) {
		if (cursorX > lastX) {
			if (fixed) {
				cursorX = lastX;
			} else {
				cursorX = 0;
				lf(false);
			}
		}
	}

	// move cursor
	private void move2(boolean fixed) {
		cursorX++;
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
		if (directionUp > 0) {
			// coordinates of (0, 0) at the bottom left corner of the screen;
			cursorY++;
			if (cursorY > lastY)
				cursorY = lastY;
		}
		else {
			// coordinates of (0, 0) at the top left corner;
			cursorY--;
			if (cursorY < 0)
				cursorY = 0;
		}
	}

	public void lf() {
		lf(false);
	}

	private void lf(boolean fixed) {
		if (directionUp > 0) {
			if (cursorY <= 0) {
				cursorY = 0;
				if (!fixed)
					scroll();
			}
			else {
				cursorY--;
			}
		}
		else {
			cursorY++;
			if (cursorY > lastY) {
				cursorY = lastY;
				if (!fixed)
					scroll();
			}
		}
	}

	public void cr() {
		cursorX = 0;
	}

	private void scroll() {
		if (directionUp > 0) {
			for (int y = lastY; y > 0; y--) {
				for (int x = 0; x < maxX; x++) {
					modifyYX(y, x, getYX(y - 1, x));
				}
			}
			for (int x = 0; x < maxX; x++) {
				modifyYX(0, x, ' ');
			}
		}
		else {
			for (int y = 0; y < lastY; y++) {
				for (int x = 0; x < maxX; x++) {
					modifyYX(y, x, getYX(y + 1, x));
				}
			}
			for (int x = 0; x < maxX; x++) {
				modifyYX(lastY, x, ' ');
			}
		}
	}

	public char get(int x, int y) {
		if (inScreen(x,y))
			return screen[y][x];
		return 0;
	}

	private char getYX(int y, int x) {
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

	public void tab(int x) {
		if ((cursorX + x) > lastX) {
			// ignore - this is an incorrect value;
		}
		else {
			cursorX = cursorX + x;
		}
	}

	private int pointX = 0;
	private int pointY = 0;

	public void plot(int x, int y, int z) {
		
		//System.out.println("plot " + x + " " + y + " " + z);
		
		pointX = x;
		pointY = y;
		int x1 = x / 2;
		int y1 = y / 2;
		if (x1 < 0 || y1 < 0 || x1 > lastX || y1 > lastY)
			return;
		int rx = x - x1 * 2; // rest 1 or 0;
		int ry;
		if (directionUp > 0) {
			ry = 1 - (y - y1 * 2);
		}
		else {
			ry = (y - y1 * 2);
		}
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
			if (z == 0) {
				// don't draw empty points at all;
			}
			else {
				c = (char)pseudo1;
			}
		}
		set(x1, y1, c);
	}

	public void line(int toX, int toY) {
		int x1 = pointX;
		int y1 = pointY;
		int x2 = toX;
		int y2 = toY;
		int tmp;

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		if (x1 == x2) {
			if (y1 > y2) { tmp = y1; y1 = y2; y2 = tmp; }
			for(int y = y1; y <= y2; y++)
				plot(x1, y, 1);
		}
		else if (y1 == y2) {
			if (x1 > x2) { tmp = x1; x1 = x2; x2 = tmp; }
			for(int x = x1; x <= x2; x++)
				plot(x, y1, 1);
		}
		else {
			if (dx > dy) {
				if (x1 > x2) {
					tmp = x1; x1 = x2; x2 = tmp;
					tmp = y1; y1 = y2; y2 = tmp;
				}
				dy = y2 - y1;
				for(int x = x1; x <= x2; x++) {
					plot(x, y1 + (x - x1) * dy/dx, 1);
				}
			}
			else {
				if (y1 > y2) {
					tmp = x1; x1 = x2; x2 = tmp;
					tmp = y1; y1 = y2; y2 = tmp;
				}
				dx = x2 - x1;
				for(int y = y1; y <= y2; y++) {
					plot(x1 + (y - y1) * dx/dy, y, 1);
				}
			}
		}

		pointX = toX;
		pointY = toY;
	}

	private boolean coloredCharset = false;

	public void setColoredCharset(boolean value) {
		this.coloredCharset = value;
	}

	public boolean isColoredCharset() {
		return this.coloredCharset;
	}

}
