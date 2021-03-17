package radio86java.uiswing;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

class Charset {
	
	private Map<Integer, BufferedImage> map1 = new HashMap<Integer, BufferedImage>();
	private Map<Integer, ImageIcon> map2 = new HashMap<Integer, ImageIcon>();
	private Map<Integer, BufferedImage> bufferedImageMap16x16 = new HashMap<Integer, BufferedImage>();
	private Map<Integer, ImageIcon> imageIconMap16x16 = new HashMap<Integer, ImageIcon>();

	private static boolean testing = false;

	public /* static */ void main(String[] argv) {
		Charset c = new Charset();
		testing = true;
		c.init();
		c.output(1);
	}

	ImageIcon getImageIcon(Integer index) {
		return map2.get(index);
	}

	BufferedImage getBufferedImage(Integer index) {
		return map1.get(index);
	}

	ImageIcon getImageIcon16(Integer index) {
		return imageIconMap16x16.get(index);
	}

	BufferedImage getBufferedImage16(Integer index) {
		return bufferedImageMap16x16.get(index);
	}

	void output(int index) {
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
	
	void init() {
		InputStream is = Utils.getCharsetResourceAsStream();
		
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

				BufferedImage bi16 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);

				if (true) {
					int color;
					int targetY = 0;
					int targetX = 0;
					for (int sourceY = 0; sourceY < 8; sourceY++) {
						targetY = sourceY * 2;
						for (int sourceX = 0; sourceX < 8; sourceX++) {
							color = bi23.getRGB(sourceX, sourceY);
							targetX = sourceX * 2;
							bi16.setRGB(targetX, targetY, color);
							bi16.setRGB(targetX + 1, targetY, color);
							bi16.setRGB(targetX, targetY + 1, color);
							bi16.setRGB(targetX + 1, targetY + 1, color);
						}
					}
				}

				if (testing)
					System.out.println("2)-------------");
				
				
				
				map1.put(i, bi2);
				
				ImageIcon ii2 = new ImageIcon(bi2);
				map2.put(i, ii2);

				bufferedImageMap16x16.put(i, bi16);

				ImageIcon ii16 = new ImageIcon(bi16);
				imageIconMap16x16.put(i, ii16);

				if (testing)
					System.out.println("read: " + i);
			}
			
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		
	}
	
}
