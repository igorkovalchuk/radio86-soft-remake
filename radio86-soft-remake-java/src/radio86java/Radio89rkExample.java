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
