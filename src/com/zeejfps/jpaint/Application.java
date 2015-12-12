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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
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
import com.zeejfps.jpaint.tools.Tool;
import com.zeejfps.jpaint.ui.EditFrame;
import com.zeejfps.jpaint.ui.ToolOptionPanel;

public class Application implements Runnable {
	
	public static final int WIDTH = 960, HEIGHT = 540;
	public static final String TITLE = "JPaint v0.0.5";
	
	public static final int MAX_UNDOS = 25;
	
	private volatile boolean running;
	private Thread appThread;
	
	private ToolOptionPanel toolOptionsPanel;
	private JFrame window;
	private Context currContext;
	private Tool currTool;
	private JLabel mousePosLbl;
	
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
		setTool(new LineTool());
		while(running) {
			//workPanel.getDrawingPanel().repaint();
			currContext.repaint();
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
				//workPanel.getDrawingPanel().getFrame().undo();
				currContext.undo();
			}
		});
		JMenuItem redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		redoMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//workPanel.getDrawingPanel().getFrame().redo();
				currContext.redo();
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
		
		//workPanel = new WorkPanel();
		//panel.add(workPanel, BorderLayout.CENTER);
		
		JPanel workAreaPanel = createWorkArea();
		panel.add(workAreaPanel, BorderLayout.CENTER);
		
		LineTool startTool = new LineTool();
		//workPanel.getDrawingPanel().setTool(startTool);
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
				//workPanel.getDrawingPanel().setTool(t);
				//toolOptionsPanel.setTool(t);
				setTool(t);
			}
		});
		lineTool.setSelected(true);
		
		JToggleButton circleTool = createToolButton("CircleIcon.png");
		circleTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CircleTool c = new CircleTool();
				//workPanel.getDrawingPanel().setTool(c);
				//toolOptionsPanel.setTool(c);
				setTool(c);
			}
		});
		
		JToggleButton rectTool = createToolButton("RectIcon.png");
		rectTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//workPanel.getDrawingPanel().setTool(new RectTool());
				setTool(new RectTool());
			}
		});
		
		JToggleButton freeTool = createToolButton("PencilIcon.png");
		freeTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//workPanel.getDrawingPanel().setTool(new PencilTool());
				setTool(new PencilTool());
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
	
	private JPanel createWorkArea() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setFocusable(false);
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		
		JPanel debugPanel = new JPanel(new BorderLayout());
		mainPanel.add(debugPanel, BorderLayout.SOUTH);
		
		JPanel mousePosPanel = new JPanel(new BorderLayout());
		mousePosPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		debugPanel.add(mousePosPanel, BorderLayout.EAST);
		
		mousePosLbl = new JLabel();
		mousePosPanel.add(mousePosLbl, BorderLayout.CENTER);	
		
		Context c = new Context(300, 300, this);
		currContext = c;
		c.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosLbl.setText(e.getX() + ", " + e.getY());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mousePosLbl.setText(e.getX() + ", " + e.getY());
			}
		});
		EditFrame f = new EditFrame("Test", c);
		desktopPane.add(f);
		
		try {
			f.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		return mainPanel;
	}
	
	public void setTool(Tool t) {
		if (currTool != null) {
			currContext.removeKeyListener(currTool);
			currContext.removeMouseListener(currTool);
			currContext.removeMouseMotionListener(currTool);
		}
		
		currTool = t;
		currTool.setContext(currContext);
		currContext.addMouseListener(currTool);
		currContext.addMouseMotionListener(currTool);
	}
	
	public Tool getCurrentTool() {
		return currTool;
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
