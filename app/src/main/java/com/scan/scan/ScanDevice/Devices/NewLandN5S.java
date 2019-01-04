package com.scan.scan.ScanDevice.Devices;

import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.scan.scan.ScanDevice.Base.IDevice;
import com.scan.scan.ScanDevice.Base.ResultReflectUtil;
import com.zltd.industry.ScannerManager;


/**
 * Created by ck on 2018/12/6.
 */

public class NewLandN5S implements IDevice, ScannerManager.IScannerStatusListener {
    private ScannerManager scannerManager;
    protected Activity activity;
    public NewLandN5S(Activity activity) {
        this.activity=activity;
        scannerManager = ScannerManager.getInstance();
        scannerManager.scannerEnable(true);
        scannerManager.setScanEngineType(ScannerManager.SCAN_ENGINE_NEWLAND);
        scannerManager.setDataTransferType(ScannerManager.TRANSFER_BY_API);
        scannerManager.setScanMode(ScannerManager.SCAN_KEY_HOLD_MODE);
        scannerManager.setScannerSoundEnable(false);
    }

    @Override
    public void onDestory() {
        activity=null;
        scannerManager=null;
    }

    @Override
    public void register() {
        //2.连接扫描服务
        int res = scannerManager.connectDecoderSRV();
        scannerManager.addScannerStatusListener(this);
    }

    @Override
    public void unregister() {
        scannerManager.removeScannerStatusListener(this);
        scannerManager.disconnectDecoderSRV();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BUTTON_A: {
                scannerManager.dispatchScanKeyEvent(event);
//                scannerManager.startKeyHoldScan();
                return true;

            }
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BUTTON_A: {
                scannerManager.dispatchScanKeyEvent(event);
//                scannerManager.stopKeyHoldScan();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onScannerStatusChanage(int i) {

    }

    @Override
    public void onScannerResultChanage(byte[] bytes) {
        String result = new String(bytes);
        if(!TextUtils.isEmpty(result)){
            ResultReflectUtil.onResult(activity,result);
        }else {

        }
    }
}
