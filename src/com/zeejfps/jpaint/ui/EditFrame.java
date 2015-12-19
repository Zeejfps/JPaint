package com.zeejfps.jpaint.ui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.zeejfps.jpaint.ContextPanel;

@SuppressWarnings("serial")
public class EditFrame extends JInternalFrame {

	private final JPanel container;
	private final ContextPanel contextPanel;
	
	public EditFrame(String title, ContextPanel contextPanel) {
		super(title, true, true, true, true);
		this.contextPanel = contextPanel;

		container = new JPanel(new GridBagLayout()) {
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
		
		container.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				contextPanel.zoom(e.getUnitsToScroll() * 0.05f * contextPanel.getZoom());
				//scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()/2);
				//scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum()/2);
			}
		});
		
	}
	
	public ContextPanel getContextPanel() {
		return contextPanel;
	}

}
