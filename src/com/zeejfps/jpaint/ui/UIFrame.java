package com.zeejfps.jstickz.ui;

import javax.swing.JFrame;

class UIFrame extends JFrame {

	private UIContentPanel contentPanel;
	
	public UIFrame(int width, int height, String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPanel = new UIContentPanel(width, height);
		setContentPane(contentPanel);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public UIContentPanel getContentPanel() {
		return contentPanel;
	}
	
}
