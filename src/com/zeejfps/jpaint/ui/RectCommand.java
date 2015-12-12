package com.zeejfps.jpaint.ui;

import java.awt.Graphics;

public class RectCommand implements DrawingCommand {

	private int x, y, width, height;
	
	public RectCommand(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
	}

}
