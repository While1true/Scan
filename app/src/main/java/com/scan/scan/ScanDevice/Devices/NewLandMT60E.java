package com.scan.scan.ScanDevice.Devices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.scan.scan.ScanDevice.Base.AbstractDevice;


/**
 * Created by ck on 2018/12/6.
 */

public class NewLandMT60E extends AbstractDevice {
    public NewLandMT60E(Activity receiver) {
        super(receiver);
        startScan();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    protected void onReceiveResult(Context context, Intent intent) {
        String scanResult=intent.getStringExtra("EXTRA_SCAN_DATA");
        final String scanStatus = intent.getStringExtra("EXTRA_SCAN_STATE");
        if(!TextUtils.isEmpty(scanResult)){
            //成功
            onResult(scanResult);
        }else{
            //失败 如超时等
        }

    }

    @Override
    protected IntentFilter getReceiverFilter() {
        return new IntentFilter("ACTION_BAR_SCAN");
    }

    @Override
    protected void startScan() {
        Intent intent = new Intent("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_POWER", 1);
        intent.putExtra("EXTRA_SCAN_MODE", 3);
        intent.putExtra("EXTRA_SCAN_AUTOENT", 0);
        intent.putExtra("EXTRA_SCAN_NOTY_SND", 0);
        mActivity.sendBroadcast(intent);
    }

    @Override
    protected void stopScan() {
        Intent intent = new Intent("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_POWER", 0);
        mActivity.sendBroadcast(intent);
    }
}
