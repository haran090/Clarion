package com.clarion;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.facebook.model.GraphUser;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ParseClass {

	Context context;
	
	public ParseClass(Context con) {
		// TODO Auto-generated constructor stub
		Parse.initialize(con, "hNpMHpXTPCftzis8MzSXvX8XyiQ7UvuQtN9431BD", "MUFNdoQ9TGmWcSvyhjDfs7yollLnyY3QFhfr3Zs6");
		context = con;
	}
	
	void Signup(GraphUser fuser,String email)
	{
		Log.e("ParseSignup",fuser.getName());
		ParseUser user = new ParseUser();
		user.setUsername(fuser.getName());
		user.setPassword("password");
		user.setEmail(email);
		//user.put("Facebook ID", fuser.getId());
		
		user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				if(e!=null)
				{
					Log.e("ParseError",e.toString());
					Toast.makeText(context, "Could not finish sign up!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
