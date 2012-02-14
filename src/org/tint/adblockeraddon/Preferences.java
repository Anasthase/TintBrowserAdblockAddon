package org.tint.adblockeraddon;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Preferences extends ListActivity {

	private static final int MENU_ADD = Menu.FIRST;
	private static final int MENU_CLEAR = Menu.FIRST + 1;
	private static final int MENU_RESET_TO_DEFAULT = Menu.FIRST + 2;
	
	private WhiteListAdaper mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(R.string.AdblockerPreferenceActivityTitle);
		getActionBar().setSubtitle(R.string.AdblockerPreferenceActivitySubtitle);
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mAdapter = new WhiteListAdaper(this, R.layout.adblocker_white_list_row, Controller.getInstance().getAdblockerWhiteList(this));
		getListView().setAdapter(mAdapter);
        getListView().setOnItemClickListener(mAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_ADD, 0, R.string.AdblockerWhiteListMenuAdd);
		menu.add(0, MENU_CLEAR, 0, R.string.AdblockerWhiteListMenuClear);
		menu.add(0, MENU_RESET_TO_DEFAULT, 0, R.string.AdblockerWhiteListMenuReset);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
    	case MENU_ADD:
    		addToWhiteList();
    		return true;
    		
    	case MENU_CLEAR:    		
    		clearWhiteList();
            return true;
            
    	case MENU_RESET_TO_DEFAULT:
    		resetToDefaultValues();
    		return true;
        default: return true;
    	}
	}
	
	private void doAddToWhiteList(String value) {
		Controller.getInstance().addToAdblockerWhiteList(this, value);
		mAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Build and show a dialog for user input. Add user input to the white list.
	 */
	private void addToWhiteList() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);    
    	builder.setCancelable(true);
    	builder.setIcon(android.R.drawable.ic_input_add);
    	builder.setTitle(getResources().getString(R.string.AdblockerWhiteListDialogAddTitle));
    	
    	builder.setInverseBackgroundForced(true);
    	
    	// Set an EditText view to get user input 
    	final EditText input = new EditText(this);
    	input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
    	builder.setView(input);
    	
    	builder.setInverseBackgroundForced(true);
    	builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    			doAddToWhiteList(input.getText().toString());
    		}
    	});
    	builder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    		}
    	});
    	AlertDialog alert = builder.create();
    	alert.show();

	}
	
	/**
	 * Clear the white list.
	 */
	private void doClearWhiteList() {		
		Controller.getInstance().clearAdblockerWhiteList(this);
		mAdapter.notifyDataSetChanged();
	}
	
	private void doResetToDefaultValues() {
		Controller.getInstance().loadAdblockerWhiteListDefaultValues(this);
		mAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Display a confirmation dialog and clear the white list.
	 */
	private void clearWhiteList() {
		showYesNoDialog(
				R.string.AdblockerWhiteListDialogClearTitle,
				R.string.CannotBeUndone,
				android.R.drawable.ic_dialog_alert,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						doClearWhiteList();
					}
		});		
    }
	
	private void resetToDefaultValues() {
		showYesNoDialog(
				R.string.AdblockerWhiteListDialogResetTitle,
				R.string.CannotBeUndone,
				android.R.drawable.ic_dialog_alert,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						doResetToDefaultValues();
					}
		});
	}
	
	private void showYesNoDialog(int title, int message, int icon, DialogInterface.OnClickListener onYes) {
		showYesNoDialog(title, getString(message), icon, onYes);
	}
	
	private void showYesNoDialog(int title, String message, int icon, DialogInterface.OnClickListener onYes) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(true);
    	builder.setIcon(icon);
    	builder.setTitle(getResources().getString(title));
    	builder.setMessage(message);

    	builder.setInverseBackgroundForced(true);
    	builder.setPositiveButton(getResources().getString(R.string.Yes), onYes);
    	builder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    		}
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
	}

	class WhiteListAdaper extends ArrayAdapter<String> implements OnItemClickListener {
		
		private int mResource;
        private LayoutInflater mInflater;
        private List<String> mData;

		public WhiteListAdaper(Context context, int resource, List<String> data) {
			super(context, resource);
			
			mResource = resource;
			mData = data;
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			
			if (convertView == null) {
                view = mInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }
			
			TextView v = (TextView) view.findViewById(R.id.AdblockerListUrlValue);
			v.setText(mData.get(position));
			
			return view;
		}

		@Override
		public int getCount() {
			if (mData != null) {
				return mData.size();
			} else {
				return 0;
			}
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
			showYesNoDialog(
					R.string.AdblockerWhiteListDialogRemoveItemTitle,
					mData.get(position),
					android.R.drawable.ic_dialog_alert,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Controller.getInstance().removeFromAdblockerWhiteList(Preferences.this, position);
							notifyDataSetChanged();
						}
			});
		}
		
	}

}
