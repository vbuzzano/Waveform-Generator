package org.electronism.sample;

import java.awt.Color;

public class WaveformOption {

    private Color backgroundColor = new Color(0, 0, 0, 0);
    private Color centerLineColor = new Color(240,0,0);
    private Color waveColor = new Color(150,150,150);
    private Color waveAverageColor = new Color(50,50,50);
    private Color pointColor = new Color(255,255,0);

    private boolean showCenterLine = true;
    
    double zoom = 100;

    int width = 800;
    int height = 250;

    int paddingLeft = 5;
    int paddingRight = 5;
    int paddingTop = 5;
    int paddingBottom = 5;
    
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
    /**
     * @return the zoom resolution
     */
    public double getZoom() {
        return zoom;
    }
    /**
     * @param zoom the zoom resolution to set
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
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
}
