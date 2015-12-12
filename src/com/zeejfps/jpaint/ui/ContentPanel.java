package com.zeejfps.jpaint.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ContentPanel extends JPanel {

	private DrawingPanel drawPanel;
	
	public ContentPanel() {
		super(new BorderLayout());
		drawPanel = new DrawingPanel();
		add(drawPanel, BorderLayout.CENTER);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(640, 480);
	}
	
}
