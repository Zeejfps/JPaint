package com.zeejfps.jpaint;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ContextPanel extends JPanel {

	private Context context;
	private float scale;
	
	public ContextPanel(Context context) {
		this.context = context;
		scale = 2f;
		context.clear(0xffff00ff);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int)(context.getWidth()*scale), (int)(context.getHeight()*scale));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		draw(g);
	}
	
	public void zoom(float amount) {
		scale += amount;
		if (scale <= 0) {
			scale = 0.1f;
		}
		revalidate();
	}
	
	private void draw(Graphics g) {
		//context.clear(0xffff00ff);
		if (g != null) {
			Image img = context.getBuffer().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
		
		
	}
	
	public void draw() {
		Graphics g = getGraphics();
		if (g != null) {
			draw(g);
			g.dispose();
		}
	}
	
	public float getZoom() {
		return scale;
	}
	
	@Override
	public int getWidth() {
		return (int)(context.getWidth() * scale);
	}
	
	@Override
	public int getHeight() {
		return (int)(context.getHeight() * scale);
	}
	
	public Context getContext() {
		return context;
	}

}
