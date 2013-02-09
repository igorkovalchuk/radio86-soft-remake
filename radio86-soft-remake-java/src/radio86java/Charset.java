package radio86java;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Charset {
	
	private Map<Integer, BufferedImage> map1 = new HashMap<Integer, BufferedImage>();
	private Map<Integer, ImageIcon> map2 = new HashMap<Integer, ImageIcon>();
	
	private static boolean testing = false;
	
	public /* static */ void main(String[] argv) {
		Charset c = new Charset();
		testing = true;
		c.init();
		c.output(1);
	}
	
	public ImageIcon getImageIcon(Integer index) {
		return map2.get(index);
	}

	public BufferedImage getBufferedImage(Integer index) {
		return map1.get(index);
	}

	public void output(int index) {
		BufferedImage bi = map1.get(index);
		if (bi == null)
			return;
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				System.out.print(bi.getRGB(x, y) + " ");
			}
			System.out.println("");
		}
	}
	
	public void init() {
		InputStream is = Monitor.class.getResourceAsStream("rk_font1.bmp");
		if (is == null)
			throw new RuntimeException("Graphics InputStream == null");
		
		BufferedImage image;
		try {
			image = javax.imageio.ImageIO.read(is);
			
			for(int i = 0; i <= 127; i++) {
				BufferedImage bi23 = image.getSubimage(0, i * 8, 8, 8);
				
				BufferedImage bi2 = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
				//bi2.setData(bi21.set);
				
				
				if (testing)
					System.out.println("1)-------------");
				if (true) {
					Raster r2 = bi23.getRaster();
					for(int y = 0; y < 8; y++) {
						for(int x = 0; x < 8; x++) {
							
							bi2.setRGB(x, y, bi23.getRGB(x, y));
							
							int n =  r2.getSample(x, y, 0);
							if (n == 1) {
								if (testing) 
									System.out.print("1");
							} else {
								if (testing) 
									System.out.print(" ");
							}
							
						}
						if (testing) 
							System.out.println(" ");
					}
				}
				if (testing)
					System.out.println("2)-------------");
				
				
				
				map1.put(i, bi2);
				
				ImageIcon ii2 = new ImageIcon(bi2);
				map2.put(i, ii2);
				
				if (testing)
					System.out.println("read: " + i);
			}
			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
	}
	
}
