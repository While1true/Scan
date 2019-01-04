package com.scan.scan;


import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;

import com.android.Scanner;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/5/5.
 */

public class CoreApp extends Application {

    protected static Map<String, String> lastScanPrintSnMap = new HashMap<>();//缓存上次扫描到的托单号
	private static Scanner mScanner;//扫描枪实例
	private static SoundPool soundpool;
	private static Map<Integer, Integer> spMap;//储存声音文件id


	public static Scanner getmScanner() {
		return mScanner;
	}

	public static SoundPool getSoundpool() {
		return soundpool;
	}

	public static Map<Integer, Integer> getSpMap() {
		return spMap;
	}

    public static Map<String, String> getLastScanPrintSnMap() {
        return lastScanPrintSnMap;
    }

	@Override
	public void onCreate() {
		super.onCreate();
		initScanner();
    }

	private void initScanner() {
		mScanner = new Scanner();
		soundpool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		spMap = new HashMap<>();
		spMap.put(1, soundpool.load(this, R.raw.beep, 1));
		spMap.put(2, soundpool.load(this, R.raw.warn, 1));
		spMap.put(3, soundpool.load(this, R.raw.error, 1));
        spMap.put(4, soundpool.load(this, R.raw.change, 1));
		CoreApp.getmScanner().open();
    }


}
