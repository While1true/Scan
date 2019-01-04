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

public class IData95Device extends AbstractDevice {
	//是否广播模式
	public static final String KEY_OUTPUT_ACTION = "android.intent.action.BARCODEOUTPUT";
	//释放扫描按键
	public static final String KEY_UNLOCK_SCAN_ACTION = "android.intent.action.BARCODEUNLOCKSCANKEY";
	//开始扫描
	public static final String KEY_BARCODE_STARTSCAN_ACTION = "android.intent.action.BARCODESTARTSCAN";
	//停止扫描
	public static final String KEY_BARCODE_STOPSCAN_ACTION = "android.intent.action.BARCODESTOPSCAN";
	//打开与关闭扫描头
	public static final String KEY_BARCODE_ENABLESCANNER_ACTION = "android.intent.action.BARCODESCAN";

	private Intent intent ;

	public IData95Device(Activity receiver) {
		super(receiver);
		configScan();
	}
	@Override
	protected void onReceiveResult(Context context, Intent intent) {
		final String scanResult = intent.getStringExtra("value");

		/** 如果条码长度>0，解码成功。如果条码长度等于0解码失败。*/
		if (intent.getAction().equals("android.intent.action.SCANRESULT")){
			onResult(scanResult);
		}

	}

	@Override
	protected IntentFilter getReceiverFilter() {
		return new IntentFilter("android.intent.action.SCANRESULT");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_F9 || keyCode == KeyEvent.KEYCODE_F10 || keyCode == KeyEvent.KEYCODE_F11) &&
				event.getRepeatCount() == 0) {
			startScan();
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_F9 || keyCode == KeyEvent.KEYCODE_F10 || keyCode == KeyEvent.KEYCODE_F11) {
			stopScan();
			return true;
		}
		return false;
	}

	@Override
	public void startScan() {
		intent = new Intent(KEY_BARCODE_STARTSCAN_ACTION);
		mActivity.sendBroadcast(intent);
	}

	@Override
	public void stopScan(){
		intent = new Intent(KEY_BARCODE_STOPSCAN_ACTION);
		mActivity.sendBroadcast(intent);
	}

	public void configScan() {
	    open();

		/**扫描头的输出模式
		 * mode 0:扫描结果直接发送到焦点编辑框内
		 * mode 1:扫描结果以广播模式发送，应用程序需要注册action为“android.intent.action.SCANRESULT”的广播接收器，在广播机的 onReceive(Context context, Intent arg1) 方法中,通过如下语句
		 String  barocode=arg1.getStringExtra("value");
		 int barocodelen=arg1.getIntExtra("length",0);
		 分别获得 条码值,条码长度,条码类型
		 mode 2:模拟按键输出模式
		 */
		intent = new Intent(KEY_OUTPUT_ACTION);
		intent.putExtra(KEY_OUTPUT_ACTION, 1);//1:广播模式接收
		mActivity.sendBroadcast(intent);
		/******
		 *解除对扫描按键的锁定。解除后iScan无法控制扫描键，用户可自定义按键。
		 */
		intent = new Intent(KEY_UNLOCK_SCAN_ACTION);
		mActivity.sendBroadcast(intent);

	}
	//	2.打开扫描头电源
	public void open(){
		if(mActivity != null){
			Intent intent = new Intent(KEY_BARCODE_ENABLESCANNER_ACTION);
			intent.putExtra(KEY_BARCODE_ENABLESCANNER_ACTION, true);
			mActivity.sendBroadcast(intent);
		}
	}

	//2.关闭扫描头电源
	public void  close(){
		if(mActivity != null){
			Intent intent = new Intent(KEY_BARCODE_ENABLESCANNER_ACTION);
			intent.putExtra(KEY_BARCODE_ENABLESCANNER_ACTION, false);
			mActivity.sendBroadcast(intent);
		}

	}
}
