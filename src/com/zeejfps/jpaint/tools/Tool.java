package com.zeejfps.jpaint.tools;

import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.zeejfps.jpaint.ui.Frame;

public abstract class Tool implements MouseInputListener, KeyListener{

	protected Frame frame;
	
	private String name;
	
	public Tool(String name) {
		this.name = name;
	}
	
	public abstract void draw(Graphics g);
	
	public abstract JPanel getToolPanel();
	
	public void setFrame(Frame f){
		this.frame = f;
	}
	
	public String getName() {
		return name;
	}
	
}
