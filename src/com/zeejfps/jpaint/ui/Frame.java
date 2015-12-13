package com.zeejfps.jstickz.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Frame {

	private static final int MAX_UNDOS = 50;
	
	private Stack<DrawingCommand> redoStack;
	private Stack<DrawingCommand> commands;
	private Component c;
	private BufferedImage bakedImage;
	
	public Frame(Component c) {
		redoStack = new Stack<>();
		commands = new Stack<>();
		this.c = c;
	}
	
	public void addCommand(DrawingCommand c){
		
		if (commands.size() >= MAX_UNDOS) {
			bake();
		}
		
		redoStack.clear();
		commands.push(c);
	}
	
	public void draw(Graphics g) {
		
		if (bakedImage != null) {
			System.out.println("Drawing image");
			g.drawImage(bakedImage, 0, 0, null);
		}
		
		for (DrawingCommand command : commands) {
			System.out.println(command.getClass());
			command.draw(g);
		}

	}
	
	public void undo() {
		
		if (!commands.isEmpty()) {
			System.out.println("Undoing...");
			redoStack.push(commands.pop());	
		}
	}
	
	public void redo() {
		if (!redoStack.isEmpty())
			commands.push(redoStack.pop());
	}
	
	private void bake() {
		System.out.println("Baking...");
		if (bakedImage == null) {
			System.out.println(c.getWidth() + ", " + c.getHeight());
			bakedImage = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_RGB);
			bakedImage.getGraphics().fillRect(0, 0, bakedImage.getWidth(), bakedImage.getHeight());
		}
		Graphics g = bakedImage.getGraphics();
		g.setColor(Color.BLACK);
		commands.firstElement().draw(g);
		g.dispose();
		commands.remove(0);
	}
	
}
