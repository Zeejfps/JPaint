package com.zeejfps.jstickz.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GUI {

	public static final int WIDTH = 640;
	public static final int HEIGHT =  480;
	public static final String TITLE = "JStickz v0.0.1";
	
	private UIFrame frame;
	private UIMenuBar menuBar;
	
	public GUI() {
		
		frame = new UIFrame(WIDTH, HEIGHT, TITLE);
		menuBar = new UIMenuBar();
		
		final DrawingPanel panel = frame.getContentPanel().getDrawingPanel();
		menuBar.getUndoMenuItem().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.getFrame().undo();;
			}
		});
		
		menuBar.getRedoMenuItem().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.getFrame().redo();
			}
		});
		
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
	}

}
