/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
