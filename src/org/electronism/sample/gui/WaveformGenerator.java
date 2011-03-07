package org.electronism.sample.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.electronism.sample.Sample;
import org.electronism.sample.SampleRange;


public class WaveformGenerator 
{
	Sample wave;
	
	int width;
	int height;

	private Color backgroundColor = new Color(0);
	private Color centerLineColor = new Color(240,0,0);;
	private Color waveColor = new Color(200,200,200);
	private Color waveAverageColor = new Color(240,240,240);
	private Color pointColor = new Color(255,255,0);
	
	/**
	 * Show center line
	 */
	private boolean showCenterLine = true;
	
	
	/**
	 * Resolution
	 */
	double resolution = 1;
	
	/**
	 * X Offset
	 */
	int xOffset;
	/**
	 * View Padding
	 */
	int paddingLeft = 5;
	int paddingRight = 5;
	int paddingTop = 5;
	int paddingBottom = 5;
	
	
	public WaveformGenerator(Sample wave, int width, int height)
	{
		this.width = width;
		this.height = height;
		setWave(wave);
	}

	
	public void setWave(Sample wave)
	{
		this.wave = wave;
	}
	
	public Sample getWave()
	{
		return wave;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}	
	
	public void draw(Graphics g) {
		
		// backup current style
		Color c = g.getColor();
		Font f = g.getFont();
		Shape clip = g.getClip();

		// Draw Backgound
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		// g.setClip(paddingLeft, paddingRight, getWidth()-paddingLeft-paddingRight, getHeight()-paddingTop-paddingBottom);
		
		// Prepare positioning
		int defX = getInnerX();
		int defY = getInnerY();
		int defWidth = getInnerWidth();
		int defHeight = getInnerHeight();;
		int half = defHeight/2;
		

		// Draw Half line
		g.setColor(centerLineColor);
		g.drawLine(defX, defY+half, defX+defWidth, defY+half);
		

		int avgY = 0;
//		int lastAvgY = 0;
		if (wave != null)
		{
			/*
			 * Initialise positioning
			 */
			int x = 0;
			int y = 0;
			int lastY = 0;

			/*
			 * Y Ratio
			 * 255 = 100
			 * defHeight   = x
			 * 
			 * defHeight * 100 / 255 = Percent Result 
			 * Percent Result / 100 to find the multiplicator
			 */
			double yRatio = Sample.getYRatio(wave, defHeight);// (double)defHeight / (double)255;		

			
			/*
			 * Number of point to read
			 * 
			 *  That depend of the resolution (point per pixel)
			 */
			int point2read = 1;
			if (resolution > 1)
				point2read = (int)Math.ceil(resolution);
			
			// Adjust start Display
			int startOffset = 0;
			if (xOffset > 0 && point2read > 0)
				startOffset = (point2read * xOffset); 
			else if (xOffset > 0)
				startOffset = xOffset; 
			
			ArrayList<SampleRange> ranges = SampleRange.getList(wave, point2read, startOffset, point2read*defWidth);
			for(int i = 0; i < ranges.size(); i++)
			{
				SampleRange range = ranges.get(i);
				
				// set y, min, max and averageY
				y = (int) range.getAverage(1);
				int min = (int) range.getMinimum(1);
				int max = (int) range.getMaximum(1);
				avgY = (int) range.getAverage(1);
				
				// inverse display and resize with yRatio
				y = (int)((double)(y*-1) * yRatio);
				max = (int)((double)(max*-1) * yRatio);
				min = (int)((double)(min*-1) * yRatio);
				avgY = (int)((double)(avgY*-1) * yRatio);
				
				// set space between point
				int xSpace = 1;
				if (resolution < 1)
				{
					xSpace = (int)Math.floor(10-(resolution*10))*10;
					if (xSpace<10)
						xSpace = 10;
				}

				// draw lines 
				g.setColor(waveColor);
				// join 
				if (resolution < 25)
					g.drawLine(defX+x,defY+half+y, defX+x-xSpace, defY+half+lastY);
				// fill
				g.drawLine(defX+x,defY+half+min, defX+x, defY+half+max);
				
				// if resolution is negative, the draw point
				if (resolution < 1)
				{
					// draw point 
					g.setColor(pointColor);
					g.fillOval(defX+x-2,defY+half+y-2,4, 4);
				} else if (resolution > 1)
				{
					// draw average
						g.setColor(waveAverageColor);
						if (max < 0 && min > 0)
						{
							int avgmin,avgmax;
							avgmax = avgY-((max-avgY)/2); 
							avgmin = avgY-((min+avgY)/2); 
							g.drawLine(defX+x, defY+half+avgmin, defX+x, defY+half+avgmax);
						} else if (max < 1)
							g.drawLine(defX+x, defY+half+min, defX+x, defY+half+avgY);
						else if (min > 0)
							g.drawLine(defX+x,defY+half+max, defX+x, defY+half+avgY);

						// save Average
//						lastAvgY = avgY;
				}
				
				// save y
				lastY = y;
				
				// add xSpace to x
				x += xSpace;				
			}
		}
		
		// restore saved style
		g.setClip(clip);
		g.setColor(c);
		g.setFont(f);
	}

	/**
	 * @return the inner X
	 */
	public int getInnerX()
	{
		return paddingLeft;
	}
	
	/**
	 * @return the inner Y
	 */
	public int getInnerY()
	{
		return paddingRight;
	}
	
	/**
	 * @return the inner width
	 */
	public int getInnerWidth()
	{
		return getWidth()-paddingLeft-paddingRight;
	}
	
	/**
	 * 
	 * @return the inner Height
	 */
	public int getInnerHeight()
	{
		return getHeight()-paddingTop-paddingBottom;
	}
	
	
	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the centerLineColor
	 */
	public Color getCenterLineColor() {
		return centerLineColor;
	}

	/**
	 * @param centerLineColor the centerLineColor to set
	 */
	public void setCenterLineColor(Color centerLineColor) {
		this.centerLineColor = centerLineColor;
	}

	/**
	 * @return the waveColor
	 */
	public Color getWaveColor() {
		return waveColor;
	}

	/**
	 * @param waveColor the waveColor to set
	 */
	public void setWaveColor(Color waveColor) {
		this.waveColor = waveColor;
	}

	/**
	 * @return the waveAverageColor
	 */
	public Color getWaveAverageColor() {
		return waveAverageColor;
	}

	/**
	 * @param waveAverageColor the waveAverageColor to set
	 */
	public void setWaveAverageColor(Color waveAverageColor) {
		this.waveAverageColor = waveAverageColor;
	}

	/**
	 * @return the pointColor
	 */
	public Color getPointColor() {
		return pointColor;
	}

	/**
	 * @param pointColor the pointColor to set
	 */
	public void setPointColor(Color pointColor) {
		this.pointColor = pointColor;
	}

	/**
	 * @param zoom the zoom to set
	 */
	public void zoomToFull()
	{
		zoomTo(100);
	}

	/**
	 * @param zoom to view 
	 */
	public void zoom(int percent) {

		double res = this.resolution;
		res = resolution + (resolution*((double)percent/100));
	
		setResolution(res);
	}
	
	private void zoomTo(double percent)
	{
		// Find the new resolution
		// Number of point / Display Width / zoom
		double res = (wave.getNbOfPoints() / (getInnerWidth()-0.1));
		if (res <= 1)
			res = Math.round(res - 0.2);
		else if (res <= 10)
			res -= 2;
		else
			res = res / (percent/100);
		
		setResolution(res);
	}

	/**
	 * @return the resolution
	 */
	public double getResolution() {
		return resolution;
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(double newResolution) {
		this.resolution = newResolution;
		if (this.resolution < 0.1)
			this.resolution = 0.1;
		if (this.resolution > wave.getData().getDataSize()/30)
			this.resolution = wave.getData().getDataSize() /30;
	}

	/**
	 * @return the xOffset
	 */
	public int getXOffset() {
		return xOffset;
	}

	/**
	 * @param offset the xOffset to set
	 */
	public void setXOffset(int offset) {
		xOffset = offset;
	}

	/**
	 * @return the paddingLeft
	 */
	public int getPaddingLeft() {
		return paddingLeft;
	}

	/**
	 * @param paddingLeft the paddingLeft to set
	 */
	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	/**
	 * @return the paddingRight
	 */
	public int getPaddingRight() {
		return paddingRight;
	}

	/**
	 * @param paddingRight the paddingRight to set
	 */
	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	/**
	 * @return the paddingTop
	 */
	public int getPaddingTop() {
		return paddingTop;
	}

	/**
	 * @param paddingTop the paddingTop to set
	 */
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	/**
	 * @return the paddingBottom
	 */
	public int getPaddingBottom() {
		return paddingBottom;
	}

	/**
	 * @param paddingBottom the paddingBottom to set
	 */
	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * @return the showCenterLine
	 */
	public boolean isShowCenterLine() {
		return showCenterLine;
	}

	/**
	 * @param showCenterLine the showCenterLine to set
	 */
	public void setShowCenterLine(boolean showCenterLine) {
		this.showCenterLine = showCenterLine;
	}


	public void setSample(Sample sample) {
		this.wave = sample;
		
	}
}
