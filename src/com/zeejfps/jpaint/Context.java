package com.zeejfps.jpaint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Stack;

public class Context {
	
	private BufferedImage buffer;
	private int width, height;
	private int[] pixels;
	
	private Stack<Command> commands;

	public Context(int width, int height) {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
		commands = new Stack<Command>();
		this.width = width;
		this.height = height;
		
		
	}

	public void undo() {
		if (commands.isEmpty()) return;
		
		Command c = commands.pop();
		c.undo();
	}
	
	public void doCommand(Command c) {
		commands.push(c);
		c.execute();
	}
	
	public void clear(int color) {
		Arrays.fill(pixels, color);
	}
	
	public void setPixel(int x, int y, int color) {
		if (x < width && x > -1 && y < height && y > -1) {
			pixels[y*width + x] = color;
		}
	}
	
	public int getPixel(int x, int y) {
		if (x < width && x > -1) {
			return pixels[y*width + x];
		}
		return 0;
	}
	
	public Graphics getGraphics() {
		return buffer.getGraphics();
	}
	
	public BufferedImage getBuffer() {
		return buffer;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
