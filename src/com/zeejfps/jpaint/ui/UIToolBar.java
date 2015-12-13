package com.zeejfps.jstickz.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

class UIToolBar extends JPanel {

	public UIToolBar() {
		super(new BorderLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
		
		JToggleButton b1 = new JToggleButton("b1");
		b1.setFocusable(false);
		JToggleButton b2 = new JToggleButton("b2");
		b2.setFocusable(false);
		JToggleButton b3 = new JToggleButton("b3");
		b3.setFocusable(false);
		JToggleButton b4 = new JToggleButton("b4");
		b4.setFocusable(false);
		
		ButtonGroup g = new ButtonGroup();
		g.add(b1);
		g.add(b2);
		g.add(b3);
		g.add(b4);
		
		buttonPanel.add(b1);
		buttonPanel.add(b2);
		buttonPanel.add(b3);
		buttonPanel.add(b4);
		
		add(buttonPanel, BorderLayout.NORTH);
	}
	
}
