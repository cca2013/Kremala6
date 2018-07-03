package com.example.androidgridview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
 
public class SettingsActivity extends Activity {
    // Initializing variables
   
    Button btnMainScreen;
    Spinner spinner1,spinner2;
    private String category_selected="General";
    private String level_selected="Low";
    Intent MainScreen;
   
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        MainScreen =getIntent();
        
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("General");
        list.add("Geography");
        list.add("Animals");
        list.add("History");
        list.add("Science");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_spinner_item,list);
        
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(categoryAdapter);  	
        spinner1.setOnItemSelectedListener(new customOnItemSelectedListener());
        	
         spinner2 = (Spinner) findViewById(R.id.spinner2);
         //set up adapter+listener for spinner2 
         addItemsOnSpinner2();

        //set the back button
        btnMainScreen = (Button) findViewById(R.id.btnGameScreen);
        //Listening to button event
        btnMainScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                     MainScreen.putExtra("category_selected", category_selected);
                     MainScreen.putExtra("level_selected", level_selected);
            	setResult(Activity.RESULT_OK, MainScreen);
            	finish();
            }
        });
    }
 // add items into spinner dynamically
    public void addItemsOnSpinner2() {
   
  	spinner2 = (Spinner) findViewById(R.id.spinner2);
  	List<String> level = new ArrayList<String>();
  	level.add("Low");
  	level.add("Medium");
  	level.add("High");

  	ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(this,
  		android.R.layout.simple_spinner_item, level);
  	levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	spinner2.setAdapter(levelAdapter);
  	spinner2.setOnItemSelectedListener(new customOnItemSelectedListener());
    }
    //spinner listener class
    public class customOnItemSelectedListener implements OnItemSelectedListener {
      	 
    	  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
    	
    		
    		 Spinner spinner = (Spinner) parent;
    	     if(spinner.getId() == R.id.spinner1)
    	     {
    	       //do this   
    	    	 category_selected= parent.getItemAtPosition(pos).toString();                
    	     }
    	     else if(spinner.getId() == R.id.spinner2)
    	     {
    	       //do this
    	    	level_selected= parent.getItemAtPosition(pos).toString(); 
    	     }
    	  }
    	 
    	  @Override
    	  public void onNothingSelected(AdapterView<?> arg0) {
    		// TODO Auto-generated method stub
    	  }
    	 
    	} 
}