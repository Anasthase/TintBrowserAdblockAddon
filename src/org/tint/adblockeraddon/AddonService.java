package org.tint.adblockeraddon;

import org.tint.addons.framework.IAddon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AddonService extends Service {
	
	private IAddon.Stub mAddon = new Addon(this);
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mAddon;
	}

}
