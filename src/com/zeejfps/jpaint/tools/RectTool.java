package com.zeejfps.jpaint.tools;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.zeejfps.jpaint.ui.DrawingCommand;

public class RectTool extends Tool {

	private int xPiv, yPiv;
	private boolean drawing;
	private RectCommand command;
	
	public RectTool() {
		super("Rect");
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if (drawing) return;
		
		drawing = true;
		command = new RectCommand(e.getX(), e.getY(), 0, 0);
		xPiv = e.getX();
		yPiv = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(!drawing) return;
		drawing = false;
		frame.addCommand(command);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawing) {
			command.height = e.getY() - yPiv;
			command.width = e.getX() - xPiv;
			
			if (command.width < 0 && command.height < 0) {
				command.x = e.getX();
				command.y = e.getY();
				command.width = -command.width;
				command.height = -command.height;
			}
			else if (command.width < 0) {	
				command.x = e.getX();
				command.y = e.getY() - command.height;
				command.width = xPiv - command.x;
			} 
			else if (command.height < 0) {
				command.x = e.getX() - command.width;
				command.y = e.getY();
				command.height = yPiv - command.y;
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (drawing)
			command.draw(g);
	}

	@Override
	public JPanel getToolPanel() {
		return new JPanel();
	}
	
	private class RectCommand implements DrawingCommand {

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
	
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
