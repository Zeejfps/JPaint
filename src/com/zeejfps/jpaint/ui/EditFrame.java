package com.zeejfps.jpaint.ui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.zeejfps.jpaint.Context;

@SuppressWarnings("serial")
public class EditFrame extends JInternalFrame {

	private final JPanel container;
	private final Context context;
	
	public EditFrame(String title, Context context) {
		super(title, true, true, true, true);
		this.context = context;

		container = new JPanel(null);
		container.add(context);
		container.setPreferredSize(new Dimension(context.getWidth() + 35, context.getHeight() + 35));
		
		JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);	
		setContentPane(scrollPane);
		pack();
		setVisible(true);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				int x = container.getWidth()/2 - context.getWidth()/2;
				int y = container.getHeight()/2 - context.getHeight()/2;
				context.setBounds(x, y, context.getWidth(), context.getHeight());
				scrollPane.repaint();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
	
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
	
			}
		});
	}
	
	public Context getContext() {
		return context;
	}

}
