package com.example.androidgridview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
 
public class StartUp extends Activity {
    // Initializing variables
    ImageView image;
    final Context context = this;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
 
       
        image = (ImageView) findViewById(R.id.kremala);
   
        //Listening to button event
        image.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	
            	Intent go = new Intent();
            	go.setClass(context, MainActivity.class);
                startActivity(go);
 
            }
        });
    }
}