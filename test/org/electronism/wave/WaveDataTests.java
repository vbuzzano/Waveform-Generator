package org.electronism.wave;
import org.electronism.sample.SampleData;

import junit.framework.TestCase;


public class WaveDataTests extends TestCase {

	public void testWaveData()
	{
		// test simple constructor
		SampleData data = new SampleData();
		assertNotNull(data);
		
		// test advance constructor$
		byte[] dataByte = new byte[100];
		data = new SampleData(dataByte);
		assertNotNull(data);
		
		// test getDataSize
		assertEquals((long)100, data.getDataSize());
		
		// test getData
		assertEquals(dataByte, data.getData());
		
		// put byte && get byte
		data.put8bitPoint(10, (byte) 23);
		assertEquals((byte) 23, data.getByte(10)); 
		
		// put byte && get unsigned byte
		data.put8bitPoint(10, (byte) 200);
		assertEquals(200, data.get8bitPoint(10)); 
		
		// put short && get short
		data.put16bitPoint(20, (short) 2709);
		assertEquals((short) 2709, data.get16bitPoint(20));
		
		// put int && get int
		data.putInt(30, (int)50284);
		assertEquals((int) 50284, data.get32bitPoint(30));
		
		// put long && get long
		long l = 9223372036854775807L;
		data.put32bitPoint(0, l);
		assertEquals(l, data.get32bitPoint(0));
	
	
	}
}
