/**
 * 
 */
package com.me.mygdxgame.util;

import com.badlogic.gdx.Gdx;

/**
 * @author deDokter
 *
 */
public class FrameLogger
{
	private static long _fillTime = 0, _endTime = 0, _totalTime = 0;
	private static long _numRounds = 0;

	public static void logMsg(int instancesSize, long startTime, long fillTime, long endTime)
	{
		_numRounds++;
		_fillTime += fillTime - startTime;
		_endTime += endTime - fillTime;
		_totalTime = endTime - startTime;

		Gdx.app.log("FrameLogger", "instances: " + instancesSize + " fill: " + (_fillTime / (1000 * _numRounds)) + " end: " + (_endTime / (1000 * _numRounds)) + " total: " + _totalTime / (1000 * _numRounds));
	}
}
