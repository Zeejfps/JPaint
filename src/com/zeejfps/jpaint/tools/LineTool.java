package com.zeejfps.jpaint.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.zeejfps.jpaint.ui.DrawingCommand;

public class LineTool extends Tool {

	private boolean drawing;
	private LineCommand command;
	private int size;
	
	public LineTool(){
		super("Line");
		drawing = false;
		command = new LineCommand(0, 0, 0, 0);
		size = 2;
	}

	public void draw(Graphics g) {
		if (drawing)
			command.draw(g);	
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (drawing) return;
		
		// Set where we want to start drawing
		drawing = true;
		command = new LineCommand(e.getX(), e.getY(), e.getX(), e.getY());
		command.size = size;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!drawing) return;
		
		drawing = false;
		context.pushCommand(command);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawing) {
			command.x2 = e.getX();
			command.y2 = e.getY();
		}
	}

	private class LineCommand implements DrawingCommand {
		
		private int x1, y1, x2, y2;
		private int size;
		
		public LineCommand(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		@Override
		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D)g.create();
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(size));
			g2d.drawLine(x1, y1, x2, y2);
			g2d.dispose();
		}
	}
	
	@Override
	public JPanel getToolPanel() {
		JPanel panel = new JPanel();
		
		JLabel sizeLbl = new JLabel("Size: ");
		JTextField sizeFld = new JTextField("" + size, 2);
		sizeFld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				size = Integer.parseInt(sizeFld.getText());
			}
		});
		
		panel.add(sizeLbl);
		panel.add(sizeFld);
		
		return panel;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

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
