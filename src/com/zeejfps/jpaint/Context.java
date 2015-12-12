package com.zeejfps.jpaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JPanel;

import com.zeejfps.jpaint.ui.DrawingCommand;

@SuppressWarnings("serial")
public class Context extends JPanel {

	private final BufferedImage image;
	private final BufferedImage buffer;
	private final int width, height;
	private final int[] pixels;
	private final Application app;
	
	private Stack<DrawingCommand> redoStack;
	private Stack<DrawingCommand> undoStack;
	
	public Context(int width, int height, Application app) {
		
		this.width = width;
		this.height = height;
		this.app = app;

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice device = env.getDefaultScreenDevice();
	    GraphicsConfiguration config = device.getDefaultConfiguration();
	
	    image = config.createCompatibleImage(width, height, Transparency.OPAQUE);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();	
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		buffer.getGraphics().fillRect(0, 0, width, height);
		
		redoStack = new Stack<>();
		undoStack = new Stack<>();
	}
	
	private void buffer() {
		Graphics g = buffer.createGraphics();
		undoStack.firstElement().draw(g);
		g.dispose();
		undoStack.remove(0);
	}
	
	public void pushCommand(DrawingCommand c) {
		if (undoStack.size() >= Application.MAX_UNDOS) {
			buffer();
		}
	
		redoStack.clear();
		undoStack.push(c);
	}
	
	public void clear(int color) {
		Arrays.fill(pixels, color);
	}
	
	public void undo() {	
		if (!undoStack.isEmpty()) {
			redoStack.push(undoStack.pop());	
		}
	}
	
	public void redo() {
		if (!redoStack.isEmpty()) {
			undoStack.push(redoStack.pop());
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		clear(0xffffffff);
		
		Graphics gg = getImage().createGraphics();
		gg.drawImage(buffer, 0, 0, null);
		for (DrawingCommand command : undoStack) {
			command.draw(gg);
		}
		app.getCurrentTool().draw(gg);
		gg.dispose();
		
		g.drawImage(image, 0, 0, null);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
}
