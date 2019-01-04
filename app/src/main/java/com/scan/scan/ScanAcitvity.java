package com.scan.scan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.Scanner;
import com.scan.scan.ScanDevice.Base.IDevice;
import com.scan.scan.ScanDevice.ScanDeviceManager;

/**
 * 扫描功能基类
 * Created by WJX on 2018/5/10.
 */

public abstract class ScanAcitvity extends AppCompatActivity implements Scanner.DecodeCallback {

	//计划内扫描提示音切换
	private final static int TYPE_SOUND_1 = 1;
	private final static int TYPE_SOUND_2 = 2;
	private Integer mScanType = 0; //区分扫描区域
	private int defaultSoundType = TYPE_SOUND_1;
	IDevice mIDevice;

	//正确提示音1
	protected void playBeepSound() {
		CoreApp.getSoundpool().play(CoreApp.getSpMap().get(1), 1, 1, 1, 0, 1);
	}

	//警告提示音
	protected void playWarnSound() {
		CoreApp.getSoundpool().play(CoreApp.getSpMap().get(2), 1, 1, 1, 0, 1);
	}

	//错误提示音
	protected void playErrorSound() {
		CoreApp.getSoundpool().play(CoreApp.getSpMap().get(3), 1, 1, 1, 0, 1);
	}

	//正确提示音2
	protected void playChangeSound() {
		CoreApp.getSoundpool().play(CoreApp.getSpMap().get(4), 1, 1, 1, 0, 1);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mIDevice= ScanDeviceManager.getSupportDevice(this);
	}

	/**
	 * 同一个印刷号下，播放一种声音
	 *
	 * @param printsn
	 */
	protected void selectSoundByPrintsn(String printsn) {
		if (TextUtils.isEmpty(printsn)) return;

		if (printsn.equals(CoreApp.getLastScanPrintSnMap().get("xxxx"))) {
			playNormalSound();
		} else {
			CoreApp.getLastScanPrintSnMap().clear();//清除上次记录
			CoreApp.getLastScanPrintSnMap().put("xxxx", printsn);//保存本次扫描到的印刷号
			changeBeepSound();
			playNormalSound();
		}
	}

	private void playNormalSound() {
		if (1 == defaultSoundType) {
			playBeepSound();
		} else {
			playChangeSound();
		}
	}

	protected void changeBeepSound() {
		if (1 == defaultSoundType) {
			defaultSoundType = TYPE_SOUND_2;
		} else {
			defaultSoundType = TYPE_SOUND_1;
		}
	}

	public void setScanType(Integer scanType) {
		mScanType = scanType;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mIDevice!=null){
			mIDevice.register();
			return;
		}
		CoreApp.getmScanner().setDecodeCallBack(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mIDevice!=null){
			mIDevice.unregister();
			return;
		}
		CoreApp.getmScanner().setDecodeCallBack(null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CoreApp.getLastScanPrintSnMap().clear();
		if(mIDevice!=null){
			mIDevice.onDestory();
			mIDevice=null;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(mIDevice!=null&&mIDevice.onKeyDown(keyCode,event)){
			return true;
		}
		if ((keyCode == KeyEvent.KEYCODE_F9 || keyCode == KeyEvent.KEYCODE_F10 || keyCode == KeyEvent.KEYCODE_F11) &&
				event.getRepeatCount() == 0) {
			CoreApp.getmScanner().StartScan();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(mIDevice!=null&&mIDevice.onKeyUp(keyCode,event)){
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_F9 || keyCode == KeyEvent.KEYCODE_F10 || keyCode == KeyEvent.KEYCODE_F11) {
			CoreApp.getmScanner().StopScan();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onDecodeComplete(final String barcode) {
		getScanCode(barcode, mScanType);

	}

	/**
	 * 获得扫描结果
	 */
	protected abstract void getScanCode(String code, Integer scanType);

}
