package com.zeejfps.jpaint.tools;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.zeejfps.jpaint.ui.DrawingCommand;

public class CircleTool extends Tool {

	private boolean drawing;
	private CircleCommand command;
	
	public CircleTool() {
		super("Circle");
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(drawing) return;
		
		drawing = true;
		command = new CircleCommand(e.getX(), e.getY(), 0, 0, e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!drawing) return;
		
		drawing = false;
		frame.addCommand(command);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (drawing) {
			command.height = e.getY() - command.yPiv;
			command.width = e.getX() - command.xPiv;
			
			if (command.width < 0 &&command.height < 0) {
				command.x = e.getX();
				command.y = e.getY();
				command.width = -command.width;
				command.height = -command.height;
			}
			else if (command.width < 0) {	
				command.x = e.getX();
				command.y = e.getY() - command.height;
				command.width = command.xPiv - command.x;
			} 
			else if (command.height < 0) {
				command.x = e.getX() - command.width;
				command.y = e.getY();
				command.height = command.yPiv - command.y;
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
	
	private class CircleCommand implements DrawingCommand {

		private int x, y, width, height, xPiv, yPiv;
		
		public CircleCommand(int x, int y, int width, int height, int xPiv, int yPiv) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.xPiv = xPiv;
			this.yPiv = yPiv;
		}
		
		@Override
		public void draw(Graphics g) {
			g.drawOval(x, y, width, height);
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
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}


}
