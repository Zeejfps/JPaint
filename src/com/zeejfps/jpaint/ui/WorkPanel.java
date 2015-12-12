package com.zeejfps.jpaint.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.DesktopPaneUI;

import com.zeejfps.jpaint.tools.CircleTool;
import com.zeejfps.jpaint.tools.LineTool;

public class WorkPanel extends JPanel implements MouseMotionListener {

	private DrawingPanel drawPanel;
	
	private int x, y;
	private JLabel mousePosLbl;
	
	public WorkPanel() {
		super(new BorderLayout());

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setFocusable(false);
		add(desktopPane, BorderLayout.CENTER);
		
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

		JPanel p = new JPanel(null);
		p.setPreferredSize(new Dimension(drawPanel.getPreferredSize().width + 25, drawPanel.getPreferredSize().height + 25));
		p.add(drawPanel);
		
		JPanel container = new JPanel(new BorderLayout());
		container.add(p, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JInternalFrame f = new JInternalFrame("Test", true, true, true, true);
		f.setContentPane(scrollPane);
		f.pack();
		f.setVisible(true);
		
		desktopPane.add(f);
		try {
			f.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		f.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				int x = container.getWidth()/2 - drawPanel.getPreferredSize().width/2;
				int y = container.getHeight()/2 - drawPanel.getPreferredSize().height/2;
				drawPanel.setBounds(x, y, drawPanel.getPreferredSize().width, drawPanel.getPreferredSize().height);	
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
