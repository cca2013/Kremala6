package com.example.androidgridview;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;




public class ActivityHow extends Activity {

	private Button menuBtn;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how);  
		
		
		 menuBtn=(Button)findViewById(R.id.btnMenu);
		 menuBtn.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	               
	            	Intent intent=new Intent(getApplicationContext(),MainActivity1.class);
	    			startActivity(intent);
	    			finish();
			    	
	            }
	        });	
	

}
}