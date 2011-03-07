package org.electronism.sample;

import org.electronism.helpers.ByteHelper;

public class SampleData {

	/**
	 * Indicateur de zone : «data» soit 4 caractères ce qui nous donne en hexadécimal : $64,$61,$74,$61. Cet indicateur nous prévient que les informations qui vont suivre sont les données proprement dites.
	 */
	char[] dataZone = {'d','a','t','a'};
	
	/**
	 * Taille des datas sous la forme d'un entier long (soit 4 octets). Cette taille est le nombre total d'octets des datas
	 */
	int dataSize = 0;

	/**
	 * raw data
	 */
	byte[] data = new byte[0];
	

	public SampleData(byte[] d) {
		if (d == null)
			throw new NullPointerException();
		
		this.dataSize = d.length;
		this.data = d;
		
	}

	public SampleData()
	{
		this(new byte[0]);
	}
	
	/**
	 * Get data
	 * @return
	 */
	public byte[] getData()
	{
		return data;
	}
	
	/**
	 * Get data size
	 * @return
	 */
	public int getDataSize()
	{
		return dataSize;
	}
	
	/**
	 * get a byte
	 */
	public byte getByte(int offset)
	{
		checkOffset(offset);
		return data[offset];
	}
	
	/**
	 * get 8 bit point
	 */
	public int get8bitPoint(int offset)
	{
		checkOffset(offset);
		return ByteHelper.unsigned(data[offset])-127;
	}
	
	/**
	 * put 8 bit point
	 * @param offset
	 * @param d
	 */
	public void put8bitPoint(int offset, byte d)
	{
		checkOffset(offset);
		data[offset] = d;
	}
	

	/**
	 * get a 16 bit point
	 */
	public short get16bitPoint(int offset)
	{
		checkOffset(offset);
		return ByteHelper.byte2short(data, offset);
	}
	
	/**
	 * put a 16 bit point
	 * @param offset
	 * @param d
	 */
	public void put16bitPoint(int offset, short d)
	{
		checkOffset(offset);
		byte v2 = (byte) ((d >>8) & 0x00FF);
		byte v1 = (byte) d;
		data[offset] = v1;
		data[offset+1] = v2;
	}
	
	/**
	 * get a 24 bit point
	 */
	public int get24bitPoint(int offset)
	{
		checkOffset(offset);
		int v3 = data[offset]&0xFF;
		int v2 = data[offset+1]&0xFF;
		int v1 = data[offset+2]&0xFF;
		return (short)( v1 << 16 | v2 << 8 | v3 );
	}
	
	/**
	 * put a 24 bit point
	 * @param offset
	 * @param d
	 */
	public void put24bitPoint(int offset, int d)
	{
		checkOffset(offset);
		byte v3 = (byte) ((d >>16) & 0x00FF);
		byte v2 = (byte) ((d >>8) & 0x00FF);
		byte v1 = (byte) d;
		data[offset] = v1;
		data[offset+1] = v2;
		data[offset+1] = v3;
	}
	
	
	/**
	 * put a 32 bit Point
	 * @param offset
	 * @param d
	 */
	public void put32bitPoint(int offset, long d)
	{
		checkOffset(offset);
		byte v8 = (byte) ((d >> 56) & 0x00FF);
		byte v7 = (byte) ((d >> 48) & 0x00FF);
		byte v6 = (byte) ((d >> 40) & 0x00FF);
		byte v5 = (byte) ((d >> 32) & 0x00FF);
		byte v4 = (byte) ((d >> 24) & 0x00FF);
		byte v3 = (byte) ((d >> 16) & 0x00FF);
		byte v2 = (byte) ((d >> 8) & 0x00FF);
		byte v1 = (byte) (d);
		data[offset] = v1;
		data[offset+1] = v2;
		data[offset+2] = v3;
		data[offset+3] = v4;
		data[offset+4] = v5;
		data[offset+5] = v6;
		data[offset+6] = v7;
		data[offset+7] = v8;
		
	}
	
	
	/**
	 * get a 32 bit Point
	 */
	public int get32bitPoint(int offset)
	{
		checkOffset(offset);
		return ByteHelper.byte2int(data, offset);
	}
	
	/**
	 * put a int
	 * @param offset
	 * @param d
	 */
	public void putInt(int offset, int d)
	{
		checkOffset(offset);
		byte v4 = (byte) ((d >> 24) & 0x00FF);
		byte v3 = (byte) ((d >> 16) & 0x00FF);
		byte v2 = (byte) ((d >>8) & 0x00FF);
		byte v1 = (byte) (d);
		data[offset] = v1;
		data[offset+1] = v2;
		data[offset+2] = v3;
		data[offset+3] = v4;
	}

	
	/**
	 * check if offset is in the range
	 * @param offset
	 */
	private void checkOffset(int offset)
	{
		if (offset < 0 || offset >= dataSize)
			throw new ArrayIndexOutOfBoundsException();
	}
}
