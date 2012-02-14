package org.tint.addons;

import org.tint.adblockeraddon.R;
import org.tint.addons.framework.IAddon;

import android.app.Service;
import android.os.RemoteException;

public abstract class BaseAddon extends IAddon.Stub {
	
	protected Service mService;
	
	public BaseAddon(Service service) {
		super();
		mService = service;
	}
	
	@Override
	public String getName() throws RemoteException {			
		return mService.getString(R.string.AddonName);
	}
	
	@Override
	public String getShortDescription() throws RemoteException {
		return mService.getString(R.string.AddonShortDescription);
	}
	
	@Override
	public String getDescription() throws RemoteException {		
		return mService.getString(R.string.AddonDescription);
	}	

	@Override
	public String getEMail() throws RemoteException {
		return mService.getString(R.string.AddonContactEMail);
	}

	@Override
	public String getWebsite() throws RemoteException {
		return mService.getString(R.string.AddonContactWebsite);
	}

}
