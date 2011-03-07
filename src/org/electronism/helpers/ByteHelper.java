package org.electronism.helpers;

public class ByteHelper {
	/**
	 * Convert byte to String
	 * @param arr
	 * @param start
	 * @return
	 */
	public static String byte2String (byte[] arr, int start, int len) {
		return new String(arr, start, len);
	}

	
	/**
	 * Convert byte to long 8byte (64bit)
	 * @param arr
	 * @param start
	 * @return
	 */
	public static long byte2long (byte[] arr, int start) {
		long v8 = arr[start] & 0xff;
		long v7 = arr[start+1] & 0xff;
		long v6 = arr[start+2] & 0xff;
		long v5 = arr[start+3] & 0xff;
		long v4 = arr[start+4] & 0xff;
		long v3 = arr[start+5] & 0xff;
		long v2 = arr[start+6] & 0xff;
		long v1 = arr[start+7] & 0xff;
		return (long)( v1 << 56 | v2 << 48| v3 << 40 | v4 << 32 | v5 << 24 | v6 << 16 | v7 << 8 | v8 );
	}
	
	/**
	 * Convert byte to Entier 4xbyte (32bit)
	 * @param arr
	 * @param start
	 * @return
	 */
	public static int byte2int(byte[] arr, int start) {
		int v1 = arr[start] & 0xff;
		int v2 = arr[start+1] & 0xff;
		int v3 = arr[start+2] & 0xff;
		int v4 = arr[start+3] & 0xff;
		return (int)( v4 << 24 | v3 << 16 | v2 << 8 | v1 );
	}
	
	/**
	 * Convert byte to short 2xbyte (16 bit)
	 * @param arr
	 * @param start
	 * @return
	 */
	public static short byte2short(byte[] arr, int start) {
		int v2 = arr[start]&0xFF;
		int v1 = arr[start+1]&0xFF;
		return (short)( v1 << 8 | v2 );
	}
	
	
	
	public static int unsigned(byte b)
	{
		return (b & 0xff); // mask of rightmost 8 bits
	}
}
