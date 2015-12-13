package com.zeejfps.jstickz.ui;

import java.awt.Graphics;

public class CircleCommand implements DrawingCommand {

	private int x, y, width, height;
	
	public CircleCommand(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawOval(x, y, width, height);
	}

}
