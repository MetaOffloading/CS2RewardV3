package com.sam.webtasks.iotask2;

import com.ait.lienzo.shared.core.types.ColorName;

public class IOtask2DisplayParams {
	// size of the box, as a proportion of window size
	public static double boxSize = 0.75;

	// size of the margin inside the edges of the box, where no cirles are placed
	// expressed as proportion of the box size
	public static double margin = 0.2;

	// radius of the circles, as a proportion of the box size
	public static double circleRadius = 0.08;

	// size of labels inside circles
	public static int circleTextSize = 40;

	// colours of the target circles / borders
	public static ColorName[] circleColours = new ColorName[4];
}
