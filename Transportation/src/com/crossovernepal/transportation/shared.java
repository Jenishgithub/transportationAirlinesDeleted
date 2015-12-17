package com.crossovernepal.transportation;

import android.util.Log;

public class shared {
	public static String fromString = "";
	public static String toString = "";
	public static String through1String = "";
	public static String through2String = "";
	public static String through3String = "";
	public static String fromPrice = "";
	public static String toPrice = "";

	public static String vehicle = "all";

	// airlines classes yeti budha sita tara agni

	public static void print() {
		Log.d("test", "fromString=" + fromString);
		Log.d("test", "toString=" + toString);
		Log.d("test", "through1String=" + through1String);
		Log.d("test", "through2String=" + through2String);
		Log.d("test", "through3String=" + through3String);
		Log.d("test", "fromPrice=" + fromPrice);
		Log.d("test", "toPrice=" + toPrice);

		Log.d("test", "vehicle = " + vehicle);

	}
}
