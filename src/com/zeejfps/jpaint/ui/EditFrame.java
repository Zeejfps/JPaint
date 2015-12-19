package com.zeejfps.jpaint.ui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.zeejfps.jpaint.Context;
import com.zeejfps.jpaint.ContextPanel;
import com.zeejfps.jpaint.OldContext;

@SuppressWarnings("serial")
public class EditFrame extends JInternalFrame {

	private final JPanel container;
	private final ContextPanel contextPanel;
	
	public EditFrame(String title, ContextPanel contextPanel) {
		super(title, true, true, true, true);
		this.contextPanel = contextPanel;

		container = new JPanel(null) {
			@Override
			public Dimension getPreferredSize() {
				return contextPanel.getPreferredSize();
			}
		};
		container.add(contextPanel);

		JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);	
		setContentPane(scrollPane);
		pack();
		setVisible(true);
		
		container.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				int x = (container.getWidth() - contextPanel.getWidth()) / 2;
				int y = (container.getHeight()- contextPanel.getHeight()) / 2;
				contextPanel.setBounds(x, y, contextPanel.getWidth(), contextPanel.getHeight());
				scrollPane.revalidate();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
	
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
	
			}
		});

		container.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				contextPanel.zoom(e.getUnitsToScroll() * 0.05f * contextPanel.getZoom());
				int x = (container.getWidth() - contextPanel.getWidth()) / 2;
				int y = (container.getHeight()- contextPanel.getHeight()) / 2;
				contextPanel.setBounds(x, y, contextPanel.getWidth(), contextPanel.getHeight());
				scrollPane.revalidate();
			}
		});
		
	}
	
	public ContextPanel getContextPanel() {
		return contextPanel;
	}

}
