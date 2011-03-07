package org.electronism.sample;

import java.util.ArrayList;
import java.util.List;

public class SampleRange {

	/**
	 * Sample Point list
	 */
	private List<SamplePoint> points;
	
	/**
	 * Average point
	 */
	private long[] average;
	
	/**
	 * Minimum point
	 */
	private long[] min;
	
	/**
	 * Maximum point
	 */
	private long[] max;
	
	/**
	 * Nb of channel
	 */
	private int nChannel;
	
	/**
	 * bit per sample
	 */
	private int nBitsPerSample;
	
	/**
	 * 
	 * @param wave
	 * @param offset
	 * @param length
	 */
	public SampleRange(Sample wave, int offset, int length) {
		if (length < 1)
			throw new IllegalArgumentException("length >= 1");
	
		if (wave == null)
			throw new NullPointerException();
		
		nChannel = wave.getNChannel();
		nBitsPerSample = wave.getNBitsPerSample();
		
		average = new long[nChannel];
		min = new long[nChannel];
		max = new long[nChannel];
		
		points = new ArrayList<SamplePoint>();
		
		int count = 0;
		for(int i = 0; i < length; i++)
		{
			try {
				SamplePoint point = new SamplePoint(wave, offset+i);
				for(int c = 1; c <= nChannel; c++) 
				{
					long value = point.getValue(c);
					average[c-1] += value;
					if (count == 0)
						min[c-1] = max[c-1] = value;
					if (value < min[c-1] )
						min[c-1] = value;
					if (value > max[c-1])
						max[c-1] = value;
				}
				points.add(point);
				count++;

			} catch(ArrayIndexOutOfBoundsException e)
			{
				break;
			}
		
		}

		if (count > 0)
			for(int c = 1; c <= nChannel; c++) 
				average[c-1] = average[c-1] / count;
		
	}
	
	
	/**
	 * Get Average point for a channel
	 * @param channel
	 * @return
	 */
	public long getAverage(int channel)
	{
		if (channel < 1 || channel > average.length)
			throw new ArrayIndexOutOfBoundsException();
		
		return average[channel-1];
	}

	/**
	 * Get minimum point for a channel
	 * @param channel
	 * @return
	 */
	public long getMinimum(int channel)
	{
		if (channel < 1 || channel > average.length)
			throw new ArrayIndexOutOfBoundsException();
		
		return min[channel-1];
	}

	/**
	 * get Maximum point for a channel
	 * @param channel
	 * @return
	 */
	public long getMaximum(int channel)
	{
		if (channel < 1 || channel > average.length)
			throw new ArrayIndexOutOfBoundsException();
		
		return max[channel-1];
	}


	/**
	 * Get a list of SampleRange
	 * @param wave
	 * @param resolution (point per range)
	 * @param start
	 * @param length
	 * @return
	 */
	public static ArrayList<SampleRange> getList(Sample wave, int resolution, int start, int length) {
		ArrayList<SampleRange> list = new ArrayList<SampleRange>();
		int len = wave.getNbOfPoints();		
		for(int offset = start; offset < len; offset += resolution)
		{
			// if we are out of the display we stop reading
			if (offset >= start+length)
				break;
		
			list.add(new SampleRange(wave, offset, resolution));
		}
		
		return list;

	}

}
