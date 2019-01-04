package com.scan.scan.ScanDevice.Base;

import android.view.KeyEvent;

/**
 * Created by ckckck on 2018/11/12.
 *
 * life is short , bugs are too many !
 */

public interface IDevice {
	void onDestory();
	void register();
	void unregister();
	boolean onKeyDown(int keyCode, KeyEvent event);
	boolean onKeyUp(int keyCode, KeyEvent event);
}
