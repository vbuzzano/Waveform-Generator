package org.electronism.sample.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class WaveVerticalRule extends JPanel {

    private static final long serialVersionUID = 1L;

    Font font = new Font("Arial", 0, 10);
	
	static Color COLOR_BACKGROUND = Color.GRAY;
	static Color COLOR_LIGHT_BORDER = Color.LIGHT_GRAY;
	static Color COLOR_BLACK_BORDER = Color.LIGHT_GRAY;

	// horizontal Border
	static int hBorder = 2;
	static int lineWith = 5;
	
	public WaveVerticalRule()
	{
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// backup current color and font
		Color c = g.getColor();
		Font f = g.getFont();
		Shape clip = g.getClip();
		
		// set Default font
		g.setFont(font);

		// Draw Rule 
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(COLOR_BLACK_BORDER);
		g.drawLine(getWidth(), 0, getWidth(), getHeight());
		g.drawLine(getWidth(), getHeight(), 0, getHeight());
		g.setColor(COLOR_LIGHT_BORDER);
		g.drawLine(0, getHeight(), 0, 0);
		g.drawLine(0, 0, 0, getWidth());
		
		// get back the backuped color
		g.setColor(c);
		g.setFont(f);
		g.setClip(clip);
	}


	public Font getFont() {
		return font;
	}


	public void setFont(Font font) {
		this.font = font;
		updateSize();
	}
	
	
	public void updateSize()
	{
		SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
				Graphics g = getGraphics();
				if (g != null)
				{
					FontMetrics fm = g.getFontMetrics(font);
					int width = (int)Math.ceil(fm.getStringBounds("-9.9", g).getWidth())+(hBorder*2)+lineWith+2;
					Dimension dim = new Dimension(width, 10);
					setSize(dim);
					setMinimumSize(dim);
					setPreferredSize(dim);
				
				}
			}
		
		});
	}
}
