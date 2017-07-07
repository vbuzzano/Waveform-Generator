package org.electronism.sample.gui.javafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.electronism.sample.Generator;
import org.electronism.sample.Sample;
import org.electronism.sample.SampleRange;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Piotr Zmilczak on 2017-07-06.
 */
public class WaveformGenerator {

    private Sample wave;
    private GraphicsContext graphicsContext;

    private Color backgroundColor = Color.gray(1);
    private Color waveColor = Color.color(0.1098, 1, 0.1686);
    private Color centerLineColor = Color.color(0,0,0);;
    private Color waveAverageColor = Color.color(0.0667, 0.5216, 0.0196);
    private Color pointColor = Color.color(1, 0.0471, 0);

    private boolean showCenterLine = true;

    private int width;
    private int height;

    private int paddingLeft = 5;
    private int paddingRight = 5;
    private int paddingTop = 5;
    private int paddingBottom = 5;


    double resolution = 1;

    int xOffset = 0;

    public WaveformGenerator(Sample wave, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.wave = wave;
        this.graphicsContext = null;
        this.zoomToFull();
    }

    public WaveformGenerator(File file, GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
        this.width = (int) graphicsContext.getCanvas().getWidth();
        this.height = (int) graphicsContext.getCanvas().getHeight();
        Generator generator = new Generator();
        wave = null;
        try {
            wave = generator.loadStandardizedSample(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        this.zoomToFull();
    }
    public void draw(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
        this.draw();
    }

    public void draw(){
        if(wave != null && graphicsContext != null){

            // Draw background
            graphicsContext.setFill(backgroundColor);
            graphicsContext.fillRect(0, 0, getWidth(), getHeight());

            // Prepare positioning
            int defX = getInnerX();
            int defY = getInnerY();
            int defWidth = getInnerWidth();
            int defHeight = getInnerHeight();
            int half = defHeight/2;

            // Draw line in the vertical middle of available space to draw
            graphicsContext.setStroke(backgroundColor);
            graphicsContext.strokeLine(defX, defY + half, defX + defWidth, defY + half);

            int avgY = 0;

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
            for (SampleRange range : ranges) {

                // set y, min, max and averageY
                y = (int) range.getAverage(1);
                int min = (int) range.getMinimum(1);
                int max = (int) range.getMaximum(1);
                avgY = (int) range.getAverage(1);

                // inverse display and resize with yRatio
                y = (int) ((double) (y * -1) * yRatio);
                max = (int) ((double) (max * -1) * yRatio);
                min = (int) ((double) (min * -1) * yRatio);
                avgY = (int) ((double) (avgY * -1) * yRatio);

                // set space between point
                int xSpace = 1;
                if (resolution < 1) {
                    xSpace = (int) Math.floor(10 - (resolution * 10)) * 10;
                    if (xSpace < 10)
                        xSpace = 10;
                }
                graphicsContext.setFill(waveColor);
                if (resolution < 25)
                    graphicsContext.strokeLine(defX + x, defY + half + y, defX + x - xSpace, defY + half + lastY);
                // fill
                graphicsContext.strokeLine(defX + x, defY + half + min, defX + x, defY + half + max);
                if (resolution < 1) {
                    // draw point
                    graphicsContext.setStroke(pointColor);
                    graphicsContext.fillOval(defX + x - 2, defY + half + y - 2, 4, 4);
                } else if (resolution > 1) {
                    // draw average
                    graphicsContext.setStroke(waveAverageColor);
                    if (max < 0 && min > 0) {
                        int avgmin, avgmax;
                        avgmin = avgY - ((min + avgY) / 2);
                        avgmax = avgY - ((max - avgY) / 2);
                        graphicsContext.strokeLine(defX + x, defY + half + avgmin, defX + x, defY + half + avgmax);
                    } else if (max < 1)
                        graphicsContext.strokeLine(defX + x, defY + half + min, defX + x, defY + half + avgY);
                    else if (min > 0)
                        graphicsContext.strokeLine(defX + x, defY + half + max, defX + x, defY + half + avgY);
                }
                lastY = y;
                x += xSpace;
            }
        }
    }

    public void zoomToFull()
    {
        zoomTo(100);
    }

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

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double newResolution) {
        this.resolution = newResolution;
        if (this.resolution < 0.1)
            this.resolution = 0.1;
        if (this.resolution > wave.getData().getDataSize()/30)
            this.resolution = wave.getData().getDataSize() /30;
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

    public int getInnerX()
    {
        return paddingLeft;
    }

    public int getInnerY()
    {
        return paddingRight;
    }

    public int getInnerWidth()
    {
        return getWidth()-paddingLeft-paddingRight;
    }

    public int getInnerHeight()
    {
        return getHeight()-paddingTop-paddingBottom;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getCenterLineColor() {
        return centerLineColor;
    }

    public void setCenterLineColor(Color centerLineColor) {
        this.centerLineColor = centerLineColor;
    }

    public Color getWaveColor() {
        return waveColor;
    }

    public void setWaveColor(Color waveColor) {
        this.waveColor = waveColor;
    }

    public Color getWaveAverageColor() {
        return waveAverageColor;
    }

    public void setWaveAverageColor(Color waveAverageColor) {
        this.waveAverageColor = waveAverageColor;
    }

    public Color getPointColor() {
        return pointColor;
    }

    public void setPointColor(Color pointColor) {
        this.pointColor = pointColor;
    }

    public int getXOffset() { return xOffset; }

    public void setXOffset(int offset) { xOffset = offset; }

    public int getPaddingLeft() { return paddingLeft; }

    public void setPaddingLeft(int paddingLeft) { this.paddingLeft = paddingLeft; }

    public int getPaddingRight() { return paddingRight; }

    public void setPaddingRight(int paddingRight) { this.paddingRight = paddingRight; }

    public int getPaddingTop() { return paddingTop;}

    public void setPaddingTop(int paddingTop) { this.paddingTop = paddingTop; }

    public int getPaddingBottom() { return paddingBottom; }

    public void setPaddingBottom(int paddingBottom) { this.paddingBottom = paddingBottom; }

    public boolean isShowCenterLine() { return showCenterLine;}

    public void setShowCenterLine(boolean showCenterLine) {this.showCenterLine = showCenterLine; }
}
