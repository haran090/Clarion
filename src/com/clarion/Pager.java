package com.clarion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Pager extends FragmentStatePagerAdapter {


	Fragment[] all = null;

	public Pager(FragmentManager fm) {
		super(fm);
		all = new Fragment[3];
		for(int i=0;i<3;i++)
			all[i] = null;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		if(all[arg0]==null)
		{
			Fragment temp = new Frag(arg0);
			all[arg0]=temp;
		}
		return all[arg0];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
