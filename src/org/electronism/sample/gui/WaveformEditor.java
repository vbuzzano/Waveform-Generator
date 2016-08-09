package org.electronism.sample.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.electronism.helpers.ByteHelper;
import org.electronism.sample.Sample;


public class WaveformEditor extends JPanel 
{
    private static final long serialVersionUID = 1L;

    WaveEditor editor;
	Sample wave;
	Font font = new Font("Arial", 0, 10);

	int rulesOffset = 25;
	int scrollbarOffset = 0;
	
	static Color BACKGROUND = new Color(0);
	static Color BACKGROUND_LINE = new Color(240,0,0);
	static Color WAVE_LINE = new Color(200,200,200);
	static Color WAVE_AVERAGE_LINE = new Color(240,240,240);
	static Color WAVE_POINT = new Color(255,255,0);
	
	
	
	int resolution = 10;
	
	public void incResolution()
	{
		resolution += 5;
		editor.updateScrollbar();
		repaint();
	}
	
	public void decResolution()
	{
		resolution -= 5;
		editor.updateScrollbar();
		repaint();
	}
	
	public int getResolution()
	{
		return resolution;
	}
	
	
	public WaveformEditor(WaveEditor editor)
	{
		this.editor = editor;
		
	}
	
	
	public void setWave(Sample wave)
	{
		this.wave = wave;
		repaint();
	}
	
	public Sample getWave()
	{
		return wave;
	}
	
	public int getRulesOffset()
	{
		return rulesOffset;
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Rectangle2D rct;
		
		// backup current color and font
		Color c = g.getColor();
		Font f = g.getFont();
		Shape clip = g.getClip();
		
		// set Default font
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics(font);
		
		// Draw Backgound
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Draw Left R�gle 
		g.setColor(Color.GRAY);
		g.fillRect(0,rulesOffset, rulesOffset, getHeight());
		g.setColor(Color.DARK_GRAY);
		g.drawLine(rulesOffset, rulesOffset, rulesOffset, getHeight());
		g.drawLine(rulesOffset, getHeight(), 0, getHeight());
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, getHeight(), 0, rulesOffset);
		g.drawLine(0, rulesOffset, rulesOffset, rulesOffset);
		
		// Draw Top R�gle 
		g.setColor(Color.GRAY);
		g.fillRect(rulesOffset,0, getWidth(), rulesOffset);
		g.setColor(Color.DARK_GRAY);
		g.drawLine(getWidth(), 0, getWidth(), rulesOffset);
		g.drawLine(getWidth(), rulesOffset, rulesOffset, rulesOffset);
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(rulesOffset, rulesOffset, rulesOffset, 0);
		g.drawLine(rulesOffset, 0, getWidth(), 0);

		// Draw Resolution
		int rmw, aw, ah;
		rmw = (int)Math.ceil(fm.getStringBounds("1000", g).getWidth());
		rct = fm.getStringBounds(""+resolution, g);
		aw = (int)Math.ceil(rct.getWidth());
		ah = (int)Math.ceil(rct.getHeight());
		g.drawString(""+resolution, rmw-aw, ah);
		
		int verticalBorder = 2;
		
		// Draw Half line
		int half = (getHeight()-(verticalBorder*2)-rulesOffset)/2;
		rct = fm.getStringBounds("-0.9", g);
		if (half > (rct.getHeight()*2))
		{
			g.setColor(BACKGROUND_LINE);
			g.drawLine(rulesOffset+1, half+rulesOffset+verticalBorder, getWidth(), half+rulesOffset+verticalBorder);
			
			// Draw 0 line
			g.drawString("0.0", 3, half+rulesOffset+verticalBorder+3);

			if (half > rct.getHeight()*5)
			{
				// Draw 1 Line
				int sy = rulesOffset+(int)rct.getHeight()+(verticalBorder-(int)rct.getHeight())+2;
				if (sy-rct.getHeight() < rulesOffset)
					sy = rulesOffset+(int)rct.getHeight()+2;
				
				g.drawLine(rulesOffset+1, rulesOffset+verticalBorder, getWidth(), rulesOffset+verticalBorder);
				g.drawString("1.0", 3, sy);
						
				// Draw -1 Line
				sy = getHeight()-verticalBorder+2;
				if (sy+rct.getHeight() > getHeight());
					sy = getHeight()-4;
				
				g.drawLine(rulesOffset+1, getHeight()-verticalBorder, getWidth(), getHeight()-verticalBorder);
				g.drawString("-1.0", 3, sy);
			
				if (half > rct.getHeight()*10)
				{
					// Draw 0.5 Line
					g.drawString("0.5", 3, rulesOffset+verticalBorder+(half/2)+2);
					g.drawLine(rulesOffset+1, rulesOffset+verticalBorder+(half/2), getWidth(), rulesOffset+verticalBorder+(half/2));
							
					// Draw -1 Line
					g.drawString("-0.5", 3, getHeight()-verticalBorder-2-(half/2));
					g.drawLine(rulesOffset+1, getHeight()-verticalBorder-2-(half/2), getWidth(), getHeight()-verticalBorder-2-(half/2));
				}
			}		
		}
		
		g.setClip(rulesOffset, rulesOffset, getWidth(), getHeight()-rulesOffset);

		int avg = 0;
		if (wave != null)
		{
			byte[] data = wave.getData().getData();
			int x = 0;
			int y = 0, y2 = 0;
			
			double yRatio = ((double)(getHeight()-(verticalBorder*2)-rulesOffset)*100/(double)255)/(double)100;
			
			
			int point2read = 1;
			if (resolution > 1)
				point2read = resolution;
			
			int sboffset = 0;
			if (scrollbarOffset > 0 && resolution > 0)
				sboffset = (resolution*scrollbarOffset); 
			if (scrollbarOffset > 0)
				sboffset = scrollbarOffset; 
			
			for(int i = sboffset; i < data.length; i += point2read)
			{
				if (x > getWidth())
					break;
				
				y2 = y;

				// on regroupe les points entre eux selon la r�solution
				int min = 0, max = 0, count=0; 
				avg = 0;
				for(int j = 0; j<point2read && i+j<data.length; j++)
				{
					if (wave.getNBitsPerSample() == 1) // 8 bits
						y = ByteHelper.unsigned(data[i+j])-127;
					else
						y = data[i+j];
					
					if (count == 0)
						min = max = y;
					if (y < min )
						min = y;
					if (y > max)
						max = y;
					avg += y;
					count++;
				}
				
				// average
				if (count > 1)
					avg = avg / count;
				
				// find the grouped y
				if (max <= 0)
					y = min;
				else if (min >= 0)
					y = max;
				else
				{
					if ((min*-1) > max)
						y = min;
					else
						y = max;
				}
				
				// inverse display and resize with yRatio
				y = (int)((double)(y*-1) * yRatio);
				max = (int)((double)(max*-1) * yRatio);
				min = (int)((double)(min*-1) * yRatio);
				avg = (int)((double)(avg*-1) * yRatio);
				
				
				// Draw Wave
				
				// set space between point
				int xSpace = 1;
				if (resolution < 0)
					xSpace = resolution*-1;

				// draw lines 
				g.setColor(WAVE_LINE);
				// join 
				if (resolution < 25)
					g.drawLine(rulesOffset+x,rulesOffset+verticalBorder+half+y, rulesOffset+x-xSpace, rulesOffset+verticalBorder+half+y2);
				// fill
				g.drawLine(rulesOffset+x,rulesOffset+verticalBorder+half+min, rulesOffset+x, rulesOffset+verticalBorder+half+max);


				
				// if resolution is negative, the draw point
				if (resolution < 0)
				{
					// draw point 
					g.setColor(WAVE_POINT);
					g.fillOval(rulesOffset+x-2,rulesOffset+verticalBorder+half+y-2,4, 4);
				} else if (resolution > 0)
				{
					// draw average
						g.setColor(WAVE_AVERAGE_LINE);
						if (max < 0 && min > 0)
						{
							int avgmin,avgmax;
							avgmax = avg-((max-avg)/2); 
							avgmin = avg-((min+avg)/2); 
							g.drawLine(rulesOffset+x, rulesOffset+verticalBorder+half+avgmin, rulesOffset+x, rulesOffset+verticalBorder+half+avgmax);
						} else if (max < 1)
							g.drawLine(rulesOffset+x, rulesOffset+verticalBorder+half+min, rulesOffset+x, rulesOffset+verticalBorder+half+avg);
						else if (min > 0)
							g.drawLine(rulesOffset+x,rulesOffset+verticalBorder+half+max, rulesOffset+x, rulesOffset+verticalBorder+half+avg);

				}
				
				// add xSpace to x
				x += xSpace;


				
				
			}
			
		}

		g.setClip(clip);
		
		// get back the backuped color
		g.setColor(c);
		g.setFont(f);
	}

	public void setScrollBarOffset(int value) {
		if (value < 0)
			value = 0;
		scrollbarOffset = value;
		repaint();
		
	}
}
