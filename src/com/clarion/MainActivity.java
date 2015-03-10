package com.clarion;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.clarion.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.internal.lt;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends Activity{

	private Pager mAdapter;
	String[] tabs = {"Where?","When?","What?"};
	private GoogleMap map;
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private UiLifecycleHelper uiHelper;
	private LocationManager locationManager;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//printKeyHash(this);
		setContentView(R.layout.where);

		
		uiHelper = new UiLifecycleHelper(this, callback);
		//uiHelper.onCreate(savedInstanceState);
		
		/*
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("public_profile","user_friends","email"));
 		*/
		
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();	
		locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);

        mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
     
        if(mLastLocation==null)
        	mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
        if(mLastLocation!=null)
        {
        	LatLng loc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        
        	Log.e("Last Location", loc.toString());
        	map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 0));
        	map.animateCamera(CameraUpdateFactory.zoomTo(17),1500,null);
        }
	}

	

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {

		if(state.isOpened())
		{
			makeMeRequest(session);
		}
	}
	
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {

			onSessionStateChange(session, state, exception);
		}
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {
			@Override
			public void onCompleted(final GraphUser user, Response response) {
				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						Toast.makeText(getBaseContext(), "Hello "+user.getName()+"!", Toast.LENGTH_LONG).show();
						ParseClass p = new ParseClass(getBaseContext());
						p.Signup(user,user.getProperty("email").toString());
					}
				}
				if (response.getError() != null) {

					Toast.makeText(getBaseContext(), "Error with connection", Toast.LENGTH_LONG).show();
				}
			}

		

		});

		Bundle params = request.getParameters();
		params.putString("fields", "email,name");
		request.setParameters(params);
		request.executeAsync();
	} 

	
	public static String printKeyHash(Activity context) {
		PackageInfo packageInfo;
		String key = null;
		try {

			//getting application package name, as defined in manifest
			String packageName = context.getApplicationContext().getPackageName();

			//Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);
			
			Log.e("Package Name=", context.getApplicationContext().getPackageName());
			
			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));
				Log.e("Key Hash1=", key);
				key = new String(Base64.encode(md.digest(),Base64.DEFAULT));
				Log.e("Key Hash2=", key);

			}
		} catch (NameNotFoundException e1) {
			Log.e("Name not found", e1.toString());
		}

		catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}


	
}
