/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package org.tint.adblockeraddon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class Controller {
	
	private final static String ADBLOCKER_WHITE_LIST_FILE = "adblocker-whitelist";
	
	/**
	 * Holder for singleton implementation.
	 */
	private static final class ControllerHolder {
		private static final Controller INSTANCE = new Controller();
		/**
		 * Private Constructor.
		 */
		private ControllerHolder() { }
	}
	
	/**
	 * Get the unique instance of the Controller.
	 * @return The instance of the Controller
	 */
	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
	
	/**
	 * Private Constructor.
	 */
	private Controller() {
		mAdblockerWhiteList = null;		
	}
	
	private List<String> mAdblockerWhiteList;
	
	public List<String> getAdblockerWhiteList(Context context) {
		if (mAdblockerWhiteList == null) {
			loadAdblockerWhiteList(context);
		}
		
		return mAdblockerWhiteList;
	}
	
	public void addToAdblockerWhiteList(Context context, String value) {
		mAdblockerWhiteList.add(value);
		saveAdblockerWhiteList(context);
	}
	
	public void removeFromAdblockerWhiteList(Context context, int index) {
		mAdblockerWhiteList.remove(index);
		saveAdblockerWhiteList(context);
	}
	
	public void clearAdblockerWhiteList(Context context) {
		mAdblockerWhiteList.clear();
		saveAdblockerWhiteList(context);
	}
	
	public void loadAdblockerWhiteListDefaultValues(Context context) {
		loadAdblockerWhiteListDefault();
		saveAdblockerWhiteList(context);
	}
	
	private void saveAdblockerWhiteList(Context context) {
		try {
			FileOutputStream fos = context.openFileOutput(ADBLOCKER_WHITE_LIST_FILE, Activity.MODE_PRIVATE);			
			
			if (fos != null) {
				for (String s : mAdblockerWhiteList) {
					fos.write((s + "\n").getBytes());
				}
				
				fos.close();
			}
			
		} catch (FileNotFoundException e) {
			Log.w("saveAdblockerWhiteList", "Unable to save AdBlocker white list: " + e.getMessage());
		} catch (IOException e) {
			Log.w("saveAdblockerWhiteList", "Unable to save AdBlocker white list: " + e.getMessage());
		}
	}
	
	private void loadAdblockerWhiteList(Context context) {
		
		mAdblockerWhiteList = new ArrayList<String>();
		
		try {
			FileInputStream fis = context.openFileInput(ADBLOCKER_WHITE_LIST_FILE);
			
			if (fis != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.length() > 0) {
						mAdblockerWhiteList.add(line);
					}
				}

				fis.close();
			} else {
				loadAdblockerWhiteListDefault();
			}

		} catch (FileNotFoundException e) {
			loadAdblockerWhiteListDefault();
		} catch (IOException e) {
			loadAdblockerWhiteListDefault();
		}
	}
	
	private void loadAdblockerWhiteListDefault() {
		mAdblockerWhiteList.clear();
		mAdblockerWhiteList.add("google.com/reader");
		mAdblockerWhiteList.add("mail.google.com");
	}

}
