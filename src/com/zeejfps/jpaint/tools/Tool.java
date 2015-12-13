package com.zeejfps.jpaint.tools;

import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.zeejfps.jpaint.Context;

public abstract class Tool implements MouseInputListener, KeyListener {

	protected Context context;
	
	private String name;
	
	public Tool(String name) {
		this.name = name;
	}
	
	public abstract void draw(Graphics g);
	
	public abstract JPanel getToolPanel();
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public String getName() {
		return name;
	}
	
}
