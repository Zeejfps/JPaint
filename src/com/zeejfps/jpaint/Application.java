package com.zeejfps.jpaint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.zeejfps.jpaint.tools.*;
import com.zeejfps.jpaint.ui.EditFrame;
import com.zeejfps.jpaint.ui.ToolOptionPanel;

public class Application implements Runnable {
	
	public static final int WIDTH = 1080, HEIGHT = 720;
	public static final String TITLE = "JPixel v0.0.8";
	
	public static final int MAX_UNDOS = 25;
	
	private volatile boolean running;
	private Thread appThread;
	
	private ToolOptionPanel toolOptionsPanel;
	private JFrame window;
	private ContextPanel currContext;
	private Tool currTool;
	private JLabel mousePosLbl;
	private JDesktopPane desktopPane;
	
	private LineTool lineTool;
	private RectTool rectTool;
	private CircleTool circleTool;
	private PencilTool pencilTool;
	
	public Application(String[] args) {
		
		lineTool = new LineTool();
		rectTool = new RectTool();
		circleTool = new CircleTool();
		pencilTool = new PencilTool();
		
		currTool = lineTool;

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
			//desktopPane.repaint();
			if (currContext != null && currContext.isVisible()) {
				currContext.draw();
				//System.out.println(Thread.currentThread());
					//currTool.draw(gg);				
			}
			try {
				Thread.sleep((long) 0.06);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				
				JPanel sizePanel = new JPanel(new GridLayout(2, 2));
				sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
				
				JLabel widthLbl = new JLabel("Width");
				JLabel heightLbl = new JLabel("Height");
				
				JTextField widthFld = new JTextField();
				JTextField heightFld = new JTextField();
				
				sizePanel.add(widthLbl);
				sizePanel.add(widthFld);
				
				sizePanel.add(heightLbl);
				sizePanel.add(heightFld);
				
				Object[] options = {"Create", "Cancel"};
				int n = JOptionPane.showOptionDialog(window, sizePanel, "New Sprite", 
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (n == 0) {
					int width = Integer.parseInt(widthFld.getText());
					int height = Integer.parseInt(heightFld.getText());
					createDocument(width, height);
				}
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
		ActionListener l = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//workPanel.getDrawingPanel().getFrame().undo();
				System.out.println("Action performed");
				undoMenuItem.setAccelerator(null);
				new SwingWorker<Void, Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						currContext.getContext().undo();
						return null;
					}
					
					@Override
					protected void done() {
						System.out.println("Done");
						undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
					}
				}.execute();
						
			}
			
		};
		undoMenuItem.addActionListener(l);
		JMenuItem redoMenuItem = new JMenuItem("Redo");
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		redoMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//workPanel.getDrawingPanel().getFrame().redo();
				//currContext.redo();
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
		
		JPanel workAreaPanel = createWorkArea();
		panel.add(workAreaPanel, BorderLayout.CENTER);

		JPanel sidePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		sidePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		panel.add(sidePanel, BorderLayout.EAST);
		
		JPanel testPanel = new JPanel(new BorderLayout());
		testPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		JLabel titleLbl = new JLabel("Layers");
		topPanel.add(titleLbl);
		testPanel.add(topPanel, BorderLayout.NORTH);
		testPanel.setPreferredSize(new Dimension(200, 150));
		
		String[] test = {"Layer", "Other Layer"};
		JList<String> list = new JList<>(test);
		testPanel.add(list, BorderLayout.CENTER);
		
		sidePanel.add(testPanel);
		
		toolOptionsPanel.setTool(lineTool);
	
		return panel;
	}
	
	private JPanel createToolsPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createTitledBorder("Tools")));
		panel.setPreferredSize(new Dimension(55, 0));

		JPanel toolGrid = new JPanel(new GridLayout(4, 1));
		
		JToggleButton lineToolBtn = createToolButton("LineIcon.png");
		lineToolBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(lineTool);
			}
		});
		lineToolBtn.setSelected(true);
		
		JToggleButton circleToolBtn = createToolButton("CircleIcon.png");
		circleToolBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(circleTool);
			}
		});
		
		JToggleButton rectToolBtn = createToolButton("RectIcon.png");
		rectToolBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(rectTool);
			}
		});
		
		JToggleButton pencilToolBtn = createToolButton("PencilIcon.png");
		pencilToolBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setTool(pencilTool);
			}
		});
		
		toolGrid.add(lineToolBtn);
		toolGrid.add(circleToolBtn);
		toolGrid.add(rectToolBtn);
		toolGrid.add(pencilToolBtn);
		
		ButtonGroup group = new ButtonGroup();
		group.add(lineToolBtn);
		group.add(circleToolBtn);
		group.add(rectToolBtn);
		group.add(pencilToolBtn);
		
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

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setFocusable(false);
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		
		JPanel debugPanel = new JPanel(new BorderLayout());
		debugPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		mainPanel.add(debugPanel, BorderLayout.SOUTH);
		
		JPanel mousePosPanel = new JPanel(new BorderLayout());
		mousePosPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		debugPanel.add(mousePosPanel, BorderLayout.EAST);
		
		mousePosLbl = new JLabel(0 + ", " + 0);
		mousePosPanel.add(mousePosLbl, BorderLayout.CENTER);	
		
		return mainPanel;
	}
	
	private void createDocument(int width, int height) {
		
		Context c = new Context(width, height);
		currContext = new ContextPanel(c);
		/*
		currContext.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosLbl.setText(e.getX() + ", " + e.getY());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mousePosLbl.setText(e.getX() + ", " + e.getY());
			}
		});
		*/
		EditFrame f = new EditFrame("Test", currContext);
		f.addInternalFrameListener(new InternalFrameListener() {
			
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {}
					
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				
				currContext.removeKeyListener(currTool);
				currContext.removeMouseListener(currTool);
				currContext.removeMouseMotionListener(currTool);
				//currContext = null;
				System.out.println("Deactivate");
			}
		
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				
				currContext =  f.getContextPanel();
				setTool(currTool);
				System.out.println("Activated");
			}
		});
		desktopPane.add(f);
		desktopPane.repaint();
		desktopPane.revalidate();
		try {
			f.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} 
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
