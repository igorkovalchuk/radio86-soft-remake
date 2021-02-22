package radio86java;

//import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Monitor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Charset charset = new Charset();
	private Console console = new Console();

	private int pixelsX = 8 + 1;
	private int pixelsY = 8 + 1;

	private boolean freezeJPanel = false;

	public void init() {

		setBackground(Color.BLACK);
		Dimension d = new Dimension(console.getMaxX() * pixelsX, console.getMaxY() * pixelsY);
		setMaximumSize(d);
		setMinimumSize(d);
		setPreferredSize(d);
		setSize(d);
		charset.init();

		//this.addKeyListener(new KeyAdapter() {

			//@Override
			//public void keyPressed(KeyEvent e) {
				//System.out.println("Pressed: Monitor " + e);
			//}
		//});

	}

	public Console getConsole() {
		return console;
	}

	private static long t1 = 0;
	
	private boolean partial = false;

	public void setPartialRepaint(boolean value) {
		this.partial = value;
	}

	public boolean isFreezeJPanel() {
		return freezeJPanel;
	}

	public void setFreezeJPanel(boolean freezeJPanel) {
		this.freezeJPanel = freezeJPanel;
	}

	@Override
	public void paintComponent(Graphics g) {

		if (freezeJPanel)
			return;

		if (((System.currentTimeMillis()) - t1) < 500) {
			return;
		}

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		fullRepaint(g2d);

		// Cursor;
		g2d.setColor(Color.YELLOW);
		int x = console.getCursorX();
		int y = console.getCursorY();
		drawCursor(g2d, x, y);
		//System.out.println("paint: " + System.currentTimeMillis());
	}

	private void drawCursor(Graphics2D g2d, int x, int y) {
		if (console.getDirectionUp() > 0) {
			y = (console.getMaxY() - 1 - console.getCursorY());
		}
		if (x >= 0 && x < console.getMaxX() && y >= 0 && y < console.getMaxY()) {
			x = x * pixelsX;
			y = y * pixelsY + pixelsY;
			g2d.drawLine(x, y, x + pixelsX, y);
		}
	}

	private void fullRepaint(Graphics2D g2d) {
		
		//plot(0,0,1);
		//plot(1,0,1);
		//plot(1,1,1);
		//plot(0,1,1);

		int screenY = 0;
		int screenX = 0;

		if (console.getDirectionUp() > 0) {
			for (int y = (console.getMaxY() - 1); y >= 0; y--) {
				for (int x = 0; x < console.getMaxX(); x++) {
					char c = console.get(x, y);
					render(g2d, screenX, screenY, c, console);
					screenX++;
				}
				screenY++;
				screenX = 0;
			}
		}
		else {
			for (int y = 0; y < console.getMaxY(); y++) {
				for (int x = 0; x < console.getMaxX(); x++) {
					char c = console.get(x, y);
					render(g2d, screenX, screenY, c, console);
					screenX++;
				}
				screenY++;
				screenX = 0;
			}			
		}

	}

	private void render(Graphics2D g2d, int screenX, int screenY, char c, Console console) {
		ImageIcon imageIcon;
		BufferedImage bi;

		if (((int)c == 32) || ((int)c == 0))
			return;

		imageIcon = charset.getImageIcon((int) c);
		if (imageIcon == null)
			return;

		if (console.isColoredCharset()) {  
			bi = charset.getBufferedImage((int) c);
			for (int xx = 0; xx < bi.getWidth(); xx++) {
				for (int yy = 0; yy < bi.getHeight(); yy++) {
					int rgb = bi.getRGB(xx, yy);
					if (rgb != -16777216)
						rgb -= 3;
					bi.setRGB(xx, yy, rgb);
				}
			}
			imageIcon = new ImageIcon(bi);
		}

		g2d.drawImage(imageIcon.getImage(), screenX * pixelsX, screenY * pixelsY, this);
	}

}