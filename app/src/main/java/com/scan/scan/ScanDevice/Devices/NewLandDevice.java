package com.scan.scan.ScanDevice.Devices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;

import com.scan.scan.ScanDevice.Base.AbstractDevice;


/**
 * Created by ckckck on 2018/11/12.
 *
 * life is short , bugs are too many !
 */

public class NewLandDevice extends AbstractDevice {

	public NewLandDevice(Activity receiver) {
		super(receiver);
		configScan();
	}

	@Override
	protected void onReceiveResult(Context context, Intent intent) {
		final String scanResult_1 = intent.getStringExtra("SCAN_BARCODE1");
		final String scanResult_2 = intent.getStringExtra("SCAN_BARCODE2");
		final int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1); // -1:unknown
		final String scanStatus = intent.getStringExtra("SCAN_STATE");
		if ("ok".equals(scanStatus)) {
			onResult(scanResult_1);
		} else {
		}
	}

	@Override
	protected IntentFilter getReceiverFilter() {
		return new IntentFilter("nlscan.action.SCANNER_RESULT");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	public void startScan() {
		Intent intent = new Intent("nlscan.action.SCANNER_TRIG");
		intent.putExtra("SCAN_TIMEOUT", 9);//单位为秒，值为 int 类型，且不超过 9 秒
		intent.putExtra("SCAN_TYPE ", 1);//扫码类型：双码
		mActivity.sendBroadcast(intent);
	}

	@Override
	protected void stopScan() {

	}

	public void configScan() {
		Intent intent = new Intent("ACTION_BAR_SCANCFG");
		intent.putExtra("EXTRA_SCAN_MODE", 3);
		intent.putExtra("EXTRA_SCAN_AUTOENT", 1);
		intent.putExtra("EXTRA_SCAN_NOTY_SND", 0);
		intent.putExtra("EXTRA_SCAN_NOTY_VIB", 0);
		intent.putExtra("EXTRA_TRIG_MODE", 0);
		intent.putExtra("EXTRA_SCAN_POWER INT", 1);
		mActivity.sendBroadcast(intent);
	}


}
