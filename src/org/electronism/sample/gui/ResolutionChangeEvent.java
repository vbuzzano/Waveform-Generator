package org.electronism.sample.gui;


import java.util.EventObject;

/**
 * @author Vincent Buzano <vincent.buzzano@gmail.com>
 */

public class ResolutionChangeEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    private double resolution = 1;

    /**
     * Timestamp of when this event occurred. Because an ActionEvent is a high-
     * level, semantic event, the timestamp is typically the same as an
     * underlying InputEvent.
     *
     * @serial
     * @see #getWhen
     */
    long when;

    /**
     * Constructs an <code>ResolutionChangeEvent</code> object.
     *
     * @param source  the object that originated the event
     * @param resolution the new resolution 
     *                
     * @throws IllegalArgumentException if <code>source</code> is null
     */
    public ResolutionChangeEvent(Object source, double resolution) {
        super(source);
        this.resolution = resolution;
        this.when = System.currentTimeMillis();
    }

        
    /**
     * Returns the new resolution 
     * @return the new resolution
     */
    public double getResolution() {
        return resolution;
    }

    /**
     * Returns the timestamp of when this event occurred. Because an
     * ActionEvent is a high-level, semantic event, the timestamp is typically
     * the same as an underlying InputEvent.
     *
     * @return this event's timestamp
     */
    public long getWhen() {
        return when;
    }
}
