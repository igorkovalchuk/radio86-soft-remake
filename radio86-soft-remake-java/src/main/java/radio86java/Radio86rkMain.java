package radio86java;

//import radio86java.basic.Basic;

public class Radio86rkMain {

    public static void main(String[] argv) {

        String listing = "";

        final Radio86rk screen = new Radio86rk();

        screen.getEditor().setText(listing);

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                screen.setVisible(true);
                screen.getCanvas().requestFocus();
            }
        });

        //Basic basic = new Basic();
        //basic.run(listing, screen);
    }

}
