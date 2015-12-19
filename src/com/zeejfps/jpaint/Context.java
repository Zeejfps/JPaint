package com.zeejfps.jpaint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Context {
	
	private BufferedImage buffer;
	private int width, height;
	private int[] pixels;

	public Context(int width, int height) {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
		this.width = width;
		this.height = height;
	}

	public void clear(int color) {
		Arrays.fill(pixels, color);
	}
	
	public void setPixel(int x, int y, int color) {
		if (x < width && x > -1) {
			pixels[y*width + x] = color;
		}
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
