package gui;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public MainWindow() { 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Title");
		setSize(450, 450);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
}
