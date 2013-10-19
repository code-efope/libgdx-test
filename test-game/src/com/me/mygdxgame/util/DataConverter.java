/**
 * 
 */
package com.me.mygdxgame.util;

/**
 * @author deDokter
 *
 */
public class DataConverter
{
	public static int ByteArrayToInt(byte[] array, int start)
	{
		if (array.length < (start + 4))
			return 0;
		else
			return (array[start] << 24) + (array[start + 1] << 16) + (array[start + 2] << 8) + array[start + 3];
	}
}
