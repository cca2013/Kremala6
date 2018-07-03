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




public class MainActivity1 extends Activity implements OnClickListener{

	private Button playBtn, helpBtn, highBtn;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);  
		
		playBtn = (Button)findViewById(R.id.play_btn);
		helpBtn = (Button)findViewById(R.id.help_btn);
		highBtn = (Button)findViewById(R.id.quit_btn);
			
		playBtn.setOnClickListener(this);
		helpBtn.setOnClickListener(this);
		highBtn.setOnClickListener(this);
		
		
		
		}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		if(view.getId()==R.id.play_btn){
		    //play button
			
			Intent intent=new Intent(getApplicationContext(),MainActivity.class);
			startActivity(intent);
		}
		else if(view.getId()==R.id.help_btn){
		    //help button
			Intent intent=new Intent(getApplicationContext(),ActivityHow.class);
			startActivity(intent);
			finish();
		}
		else if(view.getId()==R.id.quit_btn){
		    //exit button
			
			System.exit(0);
		}
		
	}

}