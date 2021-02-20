package radio86java;

import radio86java.basic.Basic;

public class Radio89rkExample {

	public static void main(String[] argv) {

		String listing = 
			"10 X=0: Y=10: A = 5: B=10: CUR X, Y\n" +
			"20 PRINT A: PRINT B\n" +
			"30 PRINT \"*123*\"\n" +
			"40 PRINT A+B\n" +
			"50 PLOT 15,15,1: PLOT 17,15,1 ";
		
		listing = "CLS\n"+
"PLOT 20,15,1\n" +
"PLOT 21,15,1\n" +
"PLOT 22,15,1";

listing = "CLS\n"+
"PLOT 19,12,1 : PLOT 21,12,1 : PLOT 23,12,1\n"+
"PLOT 18,13,1 : PLOT 19,13,1 : PLOT 20,13,1 : PLOT 22,13,1 : PLOT 24,13,1\n"+
"PLOT 19,14,1 : PLOT 20,14,1 : PLOT 21,14,1 : PLOT 23,14,1\n"+
"PLOT 20,15,1 : PLOT 21,15,1 : PLOT 22,15,1\n"+

"PLOT 21,16,1 : PLOT 21,17,1 : PLOT 21,18,1 : PLOT 21,19,1\n"+
"PLOT 22, 18,1 : PLOT 23, 17,1\n"+
"CUR 15, 3: PRINT  \"cwetok\"\n";

listing = "r.cls()\n"+
"r.plot(19,12,1); r.plot(21,12,1); r.plot(23,12,1)\n"+
"r.plot(18,13,1); r.plot(19,13,1); r.plot(20,13,1); r.plot(22,13,1); r.plot(24,13,1)\n"+
"r.plot(19,14,1); r.plot(20,14,1); r.plot(21,14,1); r.plot(23,14,1)\n"+
"r.plot(20,15,1); r.plot(21,15,1); r.plot(22,15,1)\n"+
"r.plot(21,16,1); r.plot(21,17,1); r.plot(21,18,1); r.plot(21,19,1)\n"+
"r.plot(22,18,1); r.plot(23,17,1)\n"+
"r.cur(15, 3); r.print(\"cwetok\")\n"+
"for(radius = 2; radius < 22; radius += 4)\n"+
"for(i=0; i <= 359; i+=3) {\n"+
"  a = i * 3.14159 / 180;\n"+
"  dx = Math.cos(a) * radius;\n"+
"  dy = Math.sin(a) * radius;\n"+
"  r.plot(50+dx,30+dy,1);\n"+
"}\n";

listing = "CLS()\n"+
"PLOT(19,12,1); PLOT(21,12,1); PLOT(23,12,1)\n"+
"PLOT(18,13,1); PLOT(19,13,1); PLOT(20,13,1); PLOT(22,13,1); PLOT(24,13,1)\n"+
"PLOT(19,14,1); PLOT(20,14,1); PLOT(21,14,1); PLOT(23,14,1)\n"+
"PLOT(20,15,1); PLOT(21,15,1); PLOT(22,15,1)\n"+
"PLOT(21,16,1); PLOT(21,17,1); PLOT(21,18,1); PLOT(21,19,1)\n"+
"PLOT(22,18,1); PLOT(23,17,1)\n"+
"CUR(15, 3); PRINT(\"cwetok\")\n"+
"for(radius = 2; radius < 22; radius += 4)\n"+
"for(i=0; i <= 359; i+=3) {\n"+
"  a = i * 3.14159 / 180;\n"+
"  dx = Math.cos(a) * radius;\n"+
"  dy = Math.sin(a) * radius;\n"+
"  PLOT(50+dx,30+dy,1);\n"+
"}\n";

listing = 
"cls()\n" +
"cur(3, 3); print(\"Прямоугольник\") \n" +
"for(x = 0; x < 128; x+=1) {\n" +
"  plot(x,0,1);\n" +
"  plot(x,49,1);\n" +
"}\n" +
"for(y = 0; y < 50; y+=1) {\n" +
"  plot(0,y,1)\n" +
"  plot(127,y,1)\n" +
"}\n";

		final Radio86rk screen = new Radio86rk();
		
		screen.getEditor().setText(listing);
		//final Radio86rk screen = null;

		if (screen != null)
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				screen.setVisible(true);
				screen.getCanvas().requestFocus();
			}
		});

		
		
		if (true)
			return;
		
		Basic basic = new Basic();
		basic.run(listing, screen);
	}

}
