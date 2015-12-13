package com.zeejfps.jstickz.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.zeejfps.jstickz.tools.CircleTool;
import com.zeejfps.jstickz.tools.LineTool;

public class WorkPanel extends JPanel implements MouseMotionListener {

	private DrawingPanel drawPanel;
	
	private int x, y;
	private JLabel mousePosLbl;
	
	public WorkPanel() {
		super(new BorderLayout());
		setBackground(Color.WHITE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFocusable(false);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel debugPanel = new JPanel(new BorderLayout());
		add(debugPanel, BorderLayout.SOUTH);
		
		JPanel mousePosPanel = new JPanel(new BorderLayout());
		mousePosPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		debugPanel.add(mousePosPanel, BorderLayout.EAST);
		
		mousePosLbl = new JLabel();
		updateMousePosLabel();
		mousePosPanel.add(mousePosLbl, BorderLayout.CENTER);	
		
		drawPanel = new DrawingPanel();
		drawPanel.addMouseMotionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(drawPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tabbedPane.addTab("Test", scrollPane);
		tabbedPane.setTabComponentAt(0, new CustomTab(tabbedPane));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		updateMousePosLabel();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		updateMousePosLabel();
	}
	
	private void updateMousePosLabel() {
		mousePosLbl.setText(x + ", " + y);
	}
	
	public DrawingPanel getDrawingPanel() {
		return drawPanel;
	}
	
}
