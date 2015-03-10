package com.clarion;

import com.clarion.R;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


public class Frag extends Fragment {

	View v;
	int type;
	
	public Frag(int x) {
		// TODO Auto-generated constructor stub
		type = x;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(type==0)
		{
			v = inflater.inflate(R.layout.where, null);			
		}
		else if(type==1)
			v = inflater.inflate(R.layout.when, null);
		else if(type==2)
			v = inflater.inflate(R.layout.what, null);
		return v;
	}
}
