/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package radio86java;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import static radio86java.Utils.loadResource;

public class Radio86rk extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

	private boolean freeze = false;

	private TerminalModel console = new TerminalModel();

	public Radio86rk() {
		initComponents();
		this.setLocationByPlatform(true);

		this.setTitle("Симулятор Радио 86РК");

		openMenuItem.setEnabled(false);
		saveMenuItem.setEnabled(false);
		saveAsMenuItem.setEnabled(false);
		contentsMenuItem.setEnabled(false);
		aboutMenuItem.setEnabled(false);

		canvas = new Monitor(console, 1, 1);

		jScrollPaneConsole.setViewportView(canvas);

		canvas.init();

		KeyAdapter listener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				canvas.getConsole().key(e);

				if (canvas.getConsole().isInteractive()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							canvas.setPartialRepaint(true);
							canvas.repaint();
							canvas.setPartialRepaint(false);
						}
					});
				}

			}

		};

		canvas.addKeyListener(listener);
		jTabbedPane.addKeyListener(listener);

		// TODO: check
		jTabbedPane.addChangeListener((ChangeEvent e) -> {
			if (jTabbedPane.getSelectedIndex() == 0) {
				canvas.requestFocus();
			}
		});

		// drawTestImage();
	}

	private void drawTestImage() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (true) {
					TerminalModel c = canvas.getConsole();
					for(int r = 3; r < 25; r+=4)
						circle(c, 75, 25, r);
				}
				canvas.setPartialRepaint(true);
				canvas.repaint();
				canvas.setPartialRepaint(false);
			}
		});
	}

	public JEditorPane getEditor() {
		return jEditorPane;
	}

	public void updateScreen() {
		if (!freeze) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					TerminalModel c = canvas.getConsole();
					canvas.setPartialRepaint(true);
					canvas.repaint();
					canvas.setPartialRepaint(false);
				}
			});
		}
	}

	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}

	public TerminalModel getConsole() {
		return canvas.getConsole();
	}

	public Monitor getCanvas() {
		return canvas;
	}

	private void circle(TerminalModel c, double rx, double ry, double r) {
		for (double i = 0; i <= 2 * Math.PI; i += 0.03) {
			c.plot(
					(int)Math.rint(rx + Math.cos(i) * r),
					(int)Math.rint(ry + Math.sin(i) * r), 1);
		}

	}
	
	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT
	 * modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jTabbedPane = new javax.swing.JTabbedPane();
    jScrollPaneConsole = new javax.swing.JScrollPane();
    jScrollPaneBasic = new javax.swing.JScrollPane();
    jEditorPane = new javax.swing.JEditorPane();
    menuBar = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    newMenuItem = new javax.swing.JMenuItem();
    openMenuItem = new javax.swing.JMenuItem();
    saveMenuItem = new javax.swing.JMenuItem();
    saveAsMenuItem = new javax.swing.JMenuItem();
    exitMenuItem = new javax.swing.JMenuItem();
    runMenu = new javax.swing.JMenu();
    runMenuItem = new javax.swing.JMenuItem();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenuItemStop = new javax.swing.JMenuItem();
    jCheckBoxMenuItemColoredCharset = new javax.swing.JCheckBoxMenuItem();
    loadMenu = new javax.swing.JMenu();
    sample1MenuItem = new javax.swing.JMenuItem();
    sample2MenuItem = new javax.swing.JMenuItem();
    sample3MenuItem = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JPopupMenu.Separator();
    sampleSignalMenuItem = new javax.swing.JMenuItem();
    sampleOhotaMenuItem = new javax.swing.JMenuItem();
    jSeparator4 = new javax.swing.JPopupMenu.Separator();
    sampleCircles01MenuItem = new javax.swing.JMenuItem();
    sampleCircles02MenuItem = new javax.swing.JMenuItem();
    sampleTextMenuItem = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JPopupMenu.Separator();
    sample1BasMenuItem = new javax.swing.JMenuItem();
    sample2BasMenuItem = new javax.swing.JMenuItem();
    sample3BasMenuItem = new javax.swing.JMenuItem();
    helpMenu = new javax.swing.JMenu();
    contentsMenuItem = new javax.swing.JMenuItem();
    aboutMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jScrollPaneConsole.setMinimumSize(new java.awt.Dimension(600, 200));
    jTabbedPane.addTab("Console", jScrollPaneConsole);

    jScrollPaneBasic.setViewportView(jEditorPane);

    jTabbedPane.addTab("Editor", jScrollPaneBasic);

    fileMenu.setMnemonic('f');
    fileMenu.setText("File");

    newMenuItem.setText("New");
    newMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(newMenuItem);

    openMenuItem.setMnemonic('o');
    openMenuItem.setText("Open");
    fileMenu.add(openMenuItem);

    saveMenuItem.setMnemonic('s');
    saveMenuItem.setText("Save");
    fileMenu.add(saveMenuItem);

    saveAsMenuItem.setMnemonic('a');
    saveAsMenuItem.setText("Save As ...");
    saveAsMenuItem.setDisplayedMnemonicIndex(5);
    fileMenu.add(saveAsMenuItem);

    exitMenuItem.setMnemonic('x');
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    runMenu.setText("Run");

    runMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
    runMenuItem.setText("Run Program");
    runMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        runMenuItemActionPerformed(evt);
      }
    });
    runMenu.add(runMenuItem);

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
    jMenuItem1.setText("Console/Basic");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    runMenu.add(jMenuItem1);

    jMenuItemStop.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
    jMenuItemStop.setText("Stop");
    jMenuItemStop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemStopActionPerformed(evt);
      }
    });
    runMenu.add(jMenuItemStop);

    jCheckBoxMenuItemColoredCharset.setText("Colored charset");
    jCheckBoxMenuItemColoredCharset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jCheckBoxMenuItemColoredCharsetActionPerformed(evt);
      }
    });
    runMenu.add(jCheckBoxMenuItemColoredCharset);

    menuBar.add(runMenu);

    loadMenu.setText("Load");

    sample1MenuItem.setText("Пример 1 (js)");
    sample1MenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sample1MenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sample1MenuItem);

    sample2MenuItem.setText("Пример 2 (js)");
    sample2MenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sample2MenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sample2MenuItem);

    sample3MenuItem.setText("Пример 3 (js)");
    sample3MenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sample3MenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sample3MenuItem);
    loadMenu.add(jSeparator2);

    sampleSignalMenuItem.setText("Сигнал (тест TV)");
    sampleSignalMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sampleSignalMenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sampleSignalMenuItem);

    sampleOhotaMenuItem.setText("Охота на лис");
    sampleOhotaMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sampleOhotaMenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sampleOhotaMenuItem);
    loadMenu.add(jSeparator4);

    sampleCircles01MenuItem.setText("Circles 1, freeze (js)");
    sampleCircles01MenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sampleCircles01MenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sampleCircles01MenuItem);

    sampleCircles02MenuItem.setText("Circles 2, freeze (js)");
    sampleCircles02MenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sampleCircles02MenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sampleCircles02MenuItem);

    sampleTextMenuItem.setText("Text effects (js)");
    sampleTextMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sampleTextMenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sampleTextMenuItem);
    loadMenu.add(jSeparator1);

    sample1BasMenuItem.setText("Пример 1 (bas)");
    sample1BasMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sample1BasMenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sample1BasMenuItem);

    sample2BasMenuItem.setText("Пример 2 (bas)");
    sample2BasMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sample2BasMenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sample2BasMenuItem);

    sample3BasMenuItem.setText("Пример 3 (bas)");
    sample3BasMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sample3BasMenuItemActionPerformed(evt);
      }
    });
    loadMenu.add(sample3BasMenuItem);

    menuBar.add(loadMenu);

    helpMenu.setMnemonic('h');
    helpMenu.setText("Help");

    contentsMenuItem.setMnemonic('c');
    contentsMenuItem.setText("Contents");
    helpMenu.add(contentsMenuItem);

    aboutMenuItem.setMnemonic('a');
    aboutMenuItem.setText("About");
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jTabbedPane)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
		System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

	private ThreadGroup tg = null;

	private void runMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runMenuItemActionPerformed
		String listing = jEditorPane.getText();
		listing += " "; // TODO fix it
		jTabbedPane.setSelectedIndex(0);
		//Basic basic = new Basic();
		//basic.run(listing, this);
		canvas.requestFocus();
		final Listing listing1 = new Listing(listing);

		stop();
		tg = new ThreadGroup ("rk js thread group");

		Thread thread = new Thread (tg, new Runnable() {
			@Override
			public void run() {
				InterpreterInterface interp = InterpreterFactory.create(listing1.getLanguage());
				interp.run(listing1, Radio86rk.this);
			}
		});
		thread.start();
	}//GEN-LAST:event_runMenuItemActionPerformed

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    toggleCanvasEditor();
	}//GEN-LAST:event_jMenuItem1ActionPerformed

  private void toggleCanvasEditor() {
		jTabbedPane.setSelectedIndex(1 - jTabbedPane.getSelectedIndex());
		
		if (jTabbedPane.getSelectedIndex() == 0)
			canvas.requestFocus();
  }

  private void showEditor() {
    jTabbedPane.setSelectedIndex(1);
    this.getEditor().requestFocus();
  }

    private void jCheckBoxMenuItemColoredCharsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemColoredCharsetActionPerformed
        TerminalModel console = canvas.getConsole();
		if (jCheckBoxMenuItemColoredCharset.isSelected()) {
			console.setColoredCharset(true);
		}
		else {
			console.setColoredCharset(false);
		}
    }//GEN-LAST:event_jCheckBoxMenuItemColoredCharsetActionPerformed

    private void jMenuItemStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStopActionPerformed
		stop();
    }//GEN-LAST:event_jMenuItemStopActionPerformed

  private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
    updateText("");
  }//GEN-LAST:event_newMenuItemActionPerformed

  private void sample1MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample1MenuItemActionPerformed
    String f = loadResource("01.js");
    updateText(f);
  }//GEN-LAST:event_sample1MenuItemActionPerformed

  private void sample2MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample2MenuItemActionPerformed
    String f = loadResource("02.js");
    updateText(f);
  }//GEN-LAST:event_sample2MenuItemActionPerformed

  private void sample3MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample3MenuItemActionPerformed
    String f = loadResource("03.js");
    updateText(f);
  }//GEN-LAST:event_sample3MenuItemActionPerformed

  private void sampleSignalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleSignalMenuItemActionPerformed
    String f = loadResource("tv_tbl.js");
    updateText(f);
  }//GEN-LAST:event_sampleSignalMenuItemActionPerformed

  private void sampleOhotaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleOhotaMenuItemActionPerformed
    String f = loadResource("ohota_na_lis.js");
    updateText(f);
  }//GEN-LAST:event_sampleOhotaMenuItemActionPerformed

  private void sample1BasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample1BasMenuItemActionPerformed
    String f = loadResource("01.bas");
    updateText(f);
  }//GEN-LAST:event_sample1BasMenuItemActionPerformed

  private void sample2BasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample2BasMenuItemActionPerformed
    String f = loadResource("02.bas");
    updateText(f);
  }//GEN-LAST:event_sample2BasMenuItemActionPerformed

  private void sample3BasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sample3BasMenuItemActionPerformed
    String f = loadResource("03.bas");
    updateText(f);
  }//GEN-LAST:event_sample3BasMenuItemActionPerformed

  private void sampleTextMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleTextMenuItemActionPerformed
    String f = loadResource("text_effects.js");
    updateText(f);
  }//GEN-LAST:event_sampleTextMenuItemActionPerformed

  private void sampleCircles01MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleCircles01MenuItemActionPerformed
    String f = loadResource("circles_01.js");
    updateText(f);
  }//GEN-LAST:event_sampleCircles01MenuItemActionPerformed

  private void sampleCircles02MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sampleCircles02MenuItemActionPerformed
    String f = loadResource("circles_02.js");
    updateText(f);
  }//GEN-LAST:event_sampleCircles02MenuItemActionPerformed

  private void updateText(String text) {
    if (this.getEditor().getText().trim().length() == 0) {
      this.getEditor().setText(text);
      showEditor();
    }
    else {
      int value = JOptionPane.showConfirmDialog(this, "Ignore existing code?",
              "New/Load", JOptionPane.OK_CANCEL_OPTION);
      if (value == JOptionPane.OK_OPTION) {
        this.getEditor().setText(text);
        showEditor();
      }
    }
  }

	private void stop() {
		ThreadGroup tg1 = this.tg;
		this.tg = null;
        if (tg1 != null) {
			//System.out.println("Attempt to stop the application...");
			if (tg1.activeCount() > 0) {
				System.out.println("Attempt to stop the application... ");
				tg1.stop();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
				}
				tg1.destroy();
			}
			else {
				//System.out.println("We don't need to stop the application...");
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public /* static */ void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and
		 * feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Radio86rk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Radio86rk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Radio86rk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Radio86rk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new Radio86rk().setVisible(true);
			}
		});
	}
	
	private Monitor canvas;
	
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem aboutMenuItem;
  private javax.swing.JMenuItem contentsMenuItem;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenu helpMenu;
  private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemColoredCharset;
  private javax.swing.JEditorPane jEditorPane;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItemStop;
  private javax.swing.JScrollPane jScrollPaneBasic;
  private javax.swing.JScrollPane jScrollPaneConsole;
  private javax.swing.JPopupMenu.Separator jSeparator1;
  private javax.swing.JPopupMenu.Separator jSeparator2;
  private javax.swing.JPopupMenu.Separator jSeparator4;
  private javax.swing.JTabbedPane jTabbedPane;
  private javax.swing.JMenu loadMenu;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JMenuItem newMenuItem;
  private javax.swing.JMenuItem openMenuItem;
  private javax.swing.JMenu runMenu;
  private javax.swing.JMenuItem runMenuItem;
  private javax.swing.JMenuItem sample1BasMenuItem;
  private javax.swing.JMenuItem sample1MenuItem;
  private javax.swing.JMenuItem sample2BasMenuItem;
  private javax.swing.JMenuItem sample2MenuItem;
  private javax.swing.JMenuItem sample3BasMenuItem;
  private javax.swing.JMenuItem sample3MenuItem;
  private javax.swing.JMenuItem sampleCircles01MenuItem;
  private javax.swing.JMenuItem sampleCircles02MenuItem;
  private javax.swing.JMenuItem sampleOhotaMenuItem;
  private javax.swing.JMenuItem sampleSignalMenuItem;
  private javax.swing.JMenuItem sampleTextMenuItem;
  private javax.swing.JMenuItem saveAsMenuItem;
  private javax.swing.JMenuItem saveMenuItem;
  // End of variables declaration//GEN-END:variables
}
