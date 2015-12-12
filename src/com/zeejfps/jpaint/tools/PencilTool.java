package com.zeejfps.jpaint.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.zeejfps.jpaint.ui.DrawingCommand;

public class PencilTool extends Tool {

	private boolean drawing;
	private PencilCommand command;
	
	public PencilTool() {
		super("Pencil");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (drawing) return;
		
		drawing = true;
		command = new PencilCommand();
		command.points.add(new Point(e.getX(), e.getY()));
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (!drawing) return;
		
		drawing = false;
		frame.addCommand(command);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawing) {
			command.points.add(new Point(e.getX(), e.getY()));
		}
	}
	
	@Override
	public void draw(Graphics g) {
		if (drawing) {
			command.draw(g);
		}
	}

	@Override
	public JPanel getToolPanel() {
		return new JPanel();
	}
	
	private class PencilCommand implements DrawingCommand {
		
		private ArrayList<Point> points = new ArrayList<>();

		@Override
		public void draw(Graphics g) {
			for(Point p : points) {
				g.fillRect(p.x-2, p.y-2, 4, 4);
			}
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}


}