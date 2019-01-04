package com.scan.scan.ScanDevice.Base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by ckckck on 2018/11/12.
 *
 * life is short , bugs are too many !
 */

public abstract class AbstractDevice implements IDevice {
	 protected Activity mActivity;
	 private BroadcastReceiver mReceiver;
     public AbstractDevice(final Activity receiver){
		 init(receiver);
	 }

	private void init(Activity receiver) {
		mActivity=receiver;
		mReceiver=new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				onReceiveResult(context,intent);
			}
		};
	}


	public void register(){
		 IntentFilter mFilter= getReceiverFilter();
		 mActivity.getApplication().registerReceiver(mReceiver,mFilter);
	 }
	public void unregister(){
			 mActivity.getApplication().unregisterReceiver(mReceiver);
	 }

	protected abstract void onReceiveResult(Context context, Intent intent);
	protected abstract IntentFilter getReceiverFilter();
	protected abstract void startScan();
	protected abstract void stopScan();
	protected void onResult(String result){
		 ResultReflectUtil.onResult(mActivity,result);
	}
	public void onDestory(){
		 mActivity=null;
		 mReceiver=null;
	 }

}
