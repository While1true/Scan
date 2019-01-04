package com.scan.scan.ScanDevice;

import android.app.Activity;
import android.os.Build;

import com.scan.scan.ScanDevice.Base.IDevice;
import com.scan.scan.ScanDevice.Devices.NewLandDevice;
import com.scan.scan.ScanDevice.Devices.NewLandMT60E;
import com.scan.scan.ScanDevice.Devices.NewLandN5S;


/**
 * Created by ckckck on 2018/11/12.
 *
 * life is short , bugs are too many !
 */

public class ScanDeviceManager {
	public static IDevice getSupportDevice(Activity activity){
		if(Build.MODEL.equals("MT65")|| Build.MODEL.equals("NLS-MT66")){
			return new NewLandDevice(activity);
		}else if(Build.MODEL.endsWith("NLS-MT60E(4G)")){
			return new NewLandMT60E(activity);
		}else if(Build.MODEL.equals("N5S-W")){
            return new NewLandN5S(activity);
		}

		/*else if("alps".equals(Build.MANUFACTURER.toLowerCase())){
			return new IData95Device(activity);
		}*/
		return null;
	}

}
