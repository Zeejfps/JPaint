package com.zeejfps.jstickz.ui;

import java.awt.Insets;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
class UIMenuBar extends JMenuBar {

	private final JMenuItem 
		newMenuItem, 
		openFileMenuItem,
		saveMenuItem,
		saveAsMenuItem,
		undoMenuItem,
		redoMenuItem;

	public UIMenuBar() {

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMargin(new Insets(1, 5, 1, 5));

		newMenuItem = new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		openFileMenuItem = new JMenuItem("Open...");
		//openFileItem.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveAsMenuItem = new JMenuItem("Save as...");
		//saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
		
		fileMenu.add(newMenuItem);
		fileMenu.add(openFileMenuItem);	
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMargin(new Insets(1, 5, 1, 5));
		
		undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
	
		add(fileMenu);
		add(editMenu);
	}
	
	public JMenuItem getNewMenuItem() {
		return newMenuItem;
	}

	public JMenuItem getOpenFileMenuItem() {
		return openFileMenuItem;
	}
	
	public JMenuItem getSaveMenuItem() {
		return saveMenuItem;
	}
	
	public JMenuItem getSaveAsMenuItem() {
		return saveAsMenuItem;
	}
	
	public JMenuItem getUndoMenuItem() {
		return undoMenuItem;
	}
	
	public JMenuItem getRedoMenuItem() {
		return redoMenuItem;
	}
	
}
