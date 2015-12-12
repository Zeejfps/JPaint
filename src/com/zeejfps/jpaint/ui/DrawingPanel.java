package com.zeejfps.jpaint.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.w3c.dom.css.Rect;

import com.zeejfps.jpaint.Bitmap;
import com.zeejfps.jpaint.tools.Tool;

public class DrawingPanel extends JPanel {

	private Frame f;

	private Tool currTool;
	private Bitmap bitmap;
	private int x, y;
	
	public DrawingPanel() {
		setFocusable(true);
		f = new Frame(this);
		bitmap = new Bitmap(600, 400);
	
		x = bitmap.getWidth() / 2;
		y = bitmap.getHeight() / 2;
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
	public Dimension getPreferredSize() {
		return new Dimension(bitmap.getWidth(), bitmap.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {

		bitmap.fill();
		Graphics gg = bitmap.getImage().getGraphics();
		gg.setColor(Color.BLACK);
		f.draw(gg);
		currTool.draw(gg);
		gg.dispose();
		
		g.drawImage(bitmap.getImage(), 0, 0, null);
	}

	public Frame getFrame() {
		return f;
	}
	
}
