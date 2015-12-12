package com.zeejfps.jpaint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import com.zeejfps.jpaint.tools.CircleTool;
import com.zeejfps.jpaint.tools.LineTool;
import com.zeejfps.jpaint.tools.PencilTool;
import com.zeejfps.jpaint.tools.RectTool;
import com.zeejfps.jpaint.ui.ToolOptionPanel;
import com.zeejfps.jpaint.ui.WorkPanel;

public class Application implements Runnable {
	
	public static final int WIDTH = 960, HEIGHT = 540;
	public static final String TITLE = "JPaint v0.0.4";
	
	private volatile boolean running;
	private Thread appThread;
	
	private WorkPanel workPanel;
	private ToolOptionPanel toolOptionsPanel;
	private JFrame window;
	
	public Application(String[] args) {
		appThread = new Thread(this);
		appThread.start();
	}

	@Override
	public void run() {
		if (running) return;
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					createAndShowGUI();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}

		running = true;
		while(running) {
			workPanel.getDrawingPanel().repaint();
		}
	}
	
	private void createAndShowGUI() {		
		
		Container contentPane = createContentPane();
		JMenuBar menuBar = createMenuBar();
		
		window = new JFrame(TITLE);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setJMenuBar(menuBar);
		window.setContentPane(contentPane);
		window.setMinimumSize(new Dimension(640, 480));
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		
		// Create the files menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMargin(new Insets(1, 4, 1, 4));

		// Create all the menu items
		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		newMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JMenuItem openFileMenuItem = new JMenuItem("Open...");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveAsMenuItem = new JMenuItem("Save as...");

		// Add menu items to file menu
		fileMenu.add(newMenuItem);
		fileMenu.add(openFileMenuItem);	
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		
		// Create the edit menu
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMargin(new Insets(1, 4, 1, 4));
		
		// Create the menu items
		JMenuItem undoMenuItem = new JMenuItem("Undo");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		undoMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				workPanel.getDrawingPanel().getFrame().undo();
			}
		});
		JMenuItem redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		redoMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				workPanel.getDrawingPanel().getFrame().redo();
			}
		});
		
		// Add menu items to file menu
		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
	
		JMenu windowsMenu = new JMenu("Windows");
		
		JMenuItem minimizeAllMenuItem = new JMenuItem("Minimize all...");
		JMenuItem maximizeAllMenuItem = new JMenuItem("Maximize all...");
		JMenuItem closeAllMenuItem = new JMenuItem("Close all...");
		
		windowsMenu.add(minimizeAllMenuItem);
		windowsMenu.add(maximizeAllMenuItem);
		windowsMenu.addSeparator();
		windowsMenu.add(closeAllMenuItem);
		
		// Add to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(windowsMenu);
		
		return menuBar;
	}
	
	private Container createContentPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		toolOptionsPanel = new ToolOptionPanel();
		panel.add(toolOptionsPanel, BorderLayout.NORTH);
		
		JPanel toolsPanel = createToolsPanel();
		panel.add(toolsPanel, BorderLayout.WEST);
		
		workPanel = new WorkPanel();
		panel.add(workPanel, BorderLayout.CENTER);
		
		LineTool startTool = new LineTool();
		workPanel.getDrawingPanel().setTool(startTool);
		toolOptionsPanel.setTool(startTool);
	
		return panel;
	}
	
	private JPanel createToolsPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createTitledBorder("Tools")));

		JPanel toolGrid = new JPanel(new GridLayout(4, 1));
		
		JToggleButton lineTool = createToolButton("LineIcon.png");
		lineTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LineTool t = new LineTool();
				workPanel.getDrawingPanel().setTool(t);
				toolOptionsPanel.setTool(t);
			}
		});
		lineTool.setSelected(true);
		
		JToggleButton circleTool = createToolButton("CircleIcon.png");
		circleTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CircleTool c = new CircleTool();
				workPanel.getDrawingPanel().setTool(c);
				toolOptionsPanel.setTool(c);
			}
		});
		
		JToggleButton rectTool = createToolButton("RectIcon.png");
		rectTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				workPanel.getDrawingPanel().setTool(new RectTool());
			}
		});
		
		JToggleButton freeTool = createToolButton("PencilIcon.png");
		freeTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				workPanel.getDrawingPanel().setTool(new PencilTool());
			}
		});
		
		toolGrid.add(lineTool);
		toolGrid.add(circleTool);
		toolGrid.add(rectTool);
		toolGrid.add(freeTool);
		
		ButtonGroup group = new ButtonGroup();
		group.add(lineTool);
		group.add(circleTool);
		group.add(rectTool);
		group.add(freeTool);
		
		panel.add(toolGrid, BorderLayout.NORTH);
		return panel;
	}
	
	private JToggleButton createToolButton(String iconName) {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(iconName));
		ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		
		JToggleButton button = new JToggleButton(scaledIcon);
		button.setPreferredSize(new Dimension(35, 35));
		button.setFocusable(false);
		return button;
	}
	
	private JPanel createDebugPanel() {
		JPanel panel = new JPanel();
		return panel;
	}
	
	public static void main(String[] args) {
		try {
	        UIManager.setLookAndFeel(
	            UIManager.getCrossPlatformLookAndFeelClassName());
	    } 
	    catch (Exception e) {}
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		new Application(args);	
	}
	
}
