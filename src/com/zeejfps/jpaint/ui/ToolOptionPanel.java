package com.zeejfps.jpaint.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.zeejfps.jpaint.tools.Tool;

public class ToolOptionPanel extends JPanel {

	private Tool currTool;
	private JLabel toolLbl;
	private JPanel toolPanel;
	
	public ToolOptionPanel() {
		
		super(new BorderLayout());
		setBorder(BorderFactory.createRaisedBevelBorder());
		
		JPanel labelPanel = new JPanel(new BorderLayout(10, 1));
		
		toolLbl = new JLabel("Tool: ");
		labelPanel.add(toolLbl, BorderLayout.WEST);
		labelPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		
		add(labelPanel, BorderLayout.WEST);
	}
	
	public void setTool(Tool t) {
		if (toolPanel != null) 
			remove(toolPanel);
		
		currTool = t;
		
		toolLbl.setText("Tool: " + currTool.getName());
		JPanel panel = new JPanel(new BorderLayout());
		
		toolPanel = currTool.getToolPanel();
		panel.add(toolPanel, BorderLayout.WEST);
		
		add(panel, BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
}
