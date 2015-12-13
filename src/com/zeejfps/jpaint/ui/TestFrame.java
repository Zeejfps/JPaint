package com.zeejfps.jstickz.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TestFrame extends JFrame {

	private ContentPanel contentPanel;
	
	public TestFrame() {
		super("JStickz v0.0.1");
		
		contentPanel = new ContentPanel();
		setContentPane(contentPanel);
		pack();
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
	
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				
				
				new TestFrame();
			}
		});
	}
	
}
