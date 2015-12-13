package com.zeejfps.jstickz.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class UIContentPanel extends JPanel {

	private int width, height;
	private UIToolBar toolBar;
	private DrawingPanel drawPanel;
	
	public UIContentPanel(int width, int height) {
		super(new BorderLayout());
		
		this.width = width;
		this.height = height;
		
		toolBar = new UIToolBar();
		add(toolBar, BorderLayout.WEST);
		
		drawPanel = new DrawingPanel();

		JPanel t = new JPanel();
		t.setOpaque(false);
		t.add(new JLabel("Label"));
		t.add(new JButton("x"));
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setFocusable(false);
		tabPane.addTab("Tab 1", drawPanel);
		tabPane.addTab("Tab 2", new JPanel());
		tabPane.addTab("Tab 3", new JPanel());
		tabPane.setTabComponentAt(0, t);
		add(tabPane, BorderLayout.CENTER);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public DrawingPanel getDrawingPanel() {
		return drawPanel;
	}

	
}
