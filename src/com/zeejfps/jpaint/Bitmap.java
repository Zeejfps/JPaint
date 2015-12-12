package com.zeejfps.jpaint;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import org.w3c.dom.css.Rect;

public class Bitmap {

	private final BufferedImage image;
	private final int width, height;
	private final int[] pixels;
	
	public Bitmap(int width, int height) {
		
		this.width = width;
		this.height = height;

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
		
	    image = config.createCompatibleImage(width, height, Transparency.OPAQUE);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();	
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void fill() {
		Arrays.fill(pixels, 0xffffffff);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
}
