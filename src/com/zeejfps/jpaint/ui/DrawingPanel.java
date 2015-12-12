package com.zeejfps.jpaint.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.zeejfps.jpaint.tools.Tool;

public class DrawingPanel extends JPanel {

	private Frame f;

	private Tool currTool;
	
	public DrawingPanel() {
		setBackground(Color.WHITE);
		setFocusable(true);
		f = new Frame(this);
	}
	
	public void setTool(Tool t) {
		removeMouseListener(currTool);
		removeMouseMotionListener(currTool);
		
		currTool = t;
		currTool.setFrame(f);
		addMouseMotionListener(currTool);
		addMouseListener(currTool);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//System.out.println("Painting...");
		super.paintComponent(g);
		f.draw(g);
		currTool.draw(g);
	}

	public Frame getFrame() {
		return f;
	}
	
}
