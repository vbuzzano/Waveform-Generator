package org.electronism.sample.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.electronism.sample.Sample;
import org.electronism.sample.SampleRange;


public class WaveView extends JPanel 
{
	Sample wave;
	WaveformGenerator generator;
	
	public WaveView()
	{
		generator = new WaveformGenerator(wave, getWidth(), getHeight());
		resolutionChangeListeners = new ArrayList<ResolutionChangeListener>() ;
		setDoubleBuffered(true);
		setCursor(new Cursor(Cursor.TEXT_CURSOR));
		
	}

	public WaveView(Sample wave)
	{
		this();
		setWave(wave);
	}

	
	public void setWave(Sample wave)
	{
		generator.setSample(wave);
		this.wave = wave;
		zoomToFull();
	}
	
	public Sample getWave()
	{
		return wave;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		generator.setWidth(getWidth());
		generator.setHeight(getHeight());
		generator.draw(g);	
	}

	/**
	 * @return the inner X
	 */
	public int getInnerX()
	{
		return generator.getPaddingLeft();
	}
	
	/**
	 * @return the inner Y
	 */
	public int getInnerY()
	{
		return generator.getPaddingRight();
	}
	
	/**
	 * @return the inner width
	 */
	public int getInnerWidth()
	{
		return generator.getInnerWidth();
	}
	
	/**
	 * 
	 * @return the inner Height
	 */
	public int getInnerHeight()
	{
		return generator.getInnerHeight();
	}
	
	
	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return generator.getBackgroundColor();
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(Color backgroundColor) {
		generator.setBackgroundColor(backgroundColor);
	}

	/**
	 * @return the centerLineColor
	 */
	public Color getCenterLineColor() {
		return generator.getCenterLineColor();
	}

	/**
	 * @param centerLineColor the centerLineColor to set
	 */
	public void setCenterLineColor(Color centerLineColor) {
		generator.setCenterLineColor(centerLineColor);
	}

	/**
	 * @return the waveColor
	 */
	public Color getWaveColor() {
		return generator.getWaveColor();
	}

	/**
	 * @param waveColor the waveColor to set
	 */
	public void setWaveColor(Color waveColor) {
		generator.setWaveColor(waveColor);
	}

	/**
	 * @return the waveAverageColor
	 */
	public Color getWaveAverageColor() {
		return generator.getWaveAverageColor();
	}

	/**
	 * @param waveAverageColor the waveAverageColor to set
	 */
	public void setWaveAverageColor(Color waveAverageColor) {
		generator.setWaveAverageColor(waveAverageColor);
	}

	/**
	 * @return the pointColor
	 */
	public Color getPointColor() {
		return generator.getPointColor();
	}

	/**
	 * @param pointColor the pointColor to set
	 */
	public void setPointColor(Color pointColor) {
		generator.setPointColor(pointColor);
	}

	/**
	 * @param zoom the zoom to set
	 */
	public void zoomToFull()
	{
		generator.zoomToFull();
	}

	/**
	 * @param zoom to view 
	 */
	public void zoom(int percent) {
		generator.zoom(percent);
	}
	
	/**
	 * @return the resolution
	 */
	public double getResolution() {
		return generator.getResolution();
	}

	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(double newResolution) {
		generator.setResolution(newResolution);
		fireResolutionChange(newResolution);
		repaint();
	}

	/**
	 * @return the xOffset
	 */
	public int getXOffset() {
		return generator.getXOffset();
	}

	/**
	 * @param offset the xOffset to set
	 */
	public void setXOffset(int offset) {
		generator.setXOffset(offset);
		repaint();
	}

	/**
	 * @return the paddingLeft
	 */
	public int getPaddingLeft() {
		return generator.getPaddingLeft();
	}

	/**
	 * @param paddingLeft the paddingLeft to set
	 */
	public void setPaddingLeft(int paddingLeft) {
		generator.setPaddingLeft(paddingLeft);
	}

	/**
	 * @return the paddingRight
	 */
	public int getPaddingRight() {
		return generator.getPaddingRight();
	}

	/**
	 * @param paddingRight the paddingRight to set
	 */
	public void setPaddingRight(int paddingRight) {
		generator.setPaddingRight(paddingRight);
	}

	/**
	 * @return the paddingTop
	 */
	public int getPaddingTop() {
		return generator.getPaddingTop();
	}

	/**
	 * @param paddingTop the paddingTop to set
	 */
	public void setPaddingTop(int paddingTop) {
		generator.setPaddingTop(paddingTop);
	}

	/**
	 * @return the paddingBottom
	 */
	public int getPaddingBottom() {
		return generator.getPaddingBottom();
	}

	/**
	 * @param paddingBottom the paddingBottom to set
	 */
	public void setPaddingBottom(int paddingBottom) {
		generator.setPaddingBottom(paddingBottom);
	}

	/**
	 * @return the showCenterLine
	 */
	public boolean isShowCenterLine() {
		return generator.isShowCenterLine();
	}

	/**
	 * @param showCenterLine the showCenterLine to set
	 */
	public void setShowCenterLine(boolean showCenterLine) {
		generator.setShowCenterLine(showCenterLine);
	}
	

	// ######################## LISTENER ##############################################

	private List<ResolutionChangeListener> resolutionChangeListeners;
	
    /**
     * Adds the specified resolution change listener to receive component events from
     * this component.
     * If listener <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     *
     * @param    l   the component listener
     */
    public synchronized void addResolutionChangeListener(ResolutionChangeListener l) {
        if (l == null) 
            return;
        
        resolutionChangeListeners.add(l);
    }
	
    /**
     * Remove a listener
     * @param listener
     */
    public void removeNoteBufferListener(ResolutionChangeListener l) {
		resolutionChangeListeners.remove(l);
	}
    
    /**
     * Fire Resolution Change
     * @param url
     */
	private void fireResolutionChange(double resolution) {
		if (resolutionChangeListeners != null) {
			Iterator<ResolutionChangeListener> iterator = resolutionChangeListeners.iterator();
			while (iterator.hasNext()) {
				ResolutionChangeListener listener = iterator.next();
				listener.change(new ResolutionChangeEvent(this, resolution));
		
			}
		}		
	}
}
