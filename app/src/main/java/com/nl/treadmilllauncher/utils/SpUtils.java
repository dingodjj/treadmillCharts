package com.nl.treadmilllauncher.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtils {

	private static final String name = "TREADMILL_LAUNCHER_SP";

	public static void save(String key, String value, Context context) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void save(String key, int value, Context context) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}

	public static String load(String key, String defaultValue, Context context) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	public static int load(String key, int defaultValue, Context context) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static boolean contains(String key, Context context) {
		SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.contains(key);
	}
}
