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
 
public class CopyOfSettingsActivity1 extends Activity {
    // Initializing variables
    EditText inputLetterScore;
    EditText inputSameLetterPositions;
    Button btnMainScreen;
    Spinner spinner1,spinner2;
    private String category_selected="General";
    private String level_selected="Low";
    Intent MainScreen;
    //String inputletterscore="240";
    //String inputsamepos="5";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings1);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        addListenerOnSpinner1ItemSelection();
       // spinner2 = (Spinner) findViewById(R.id.spinner2);
        //addListenerOnSpinner2ItemSelection();
        
        List<String> list = new ArrayList<String>();
        list.add("General");
        list.add("Geography");
        list.add("Animals");
        list.add("History");
        list.add("Science");
        
        List<String> level = new ArrayList<String>();
        level.add("Low");
        level.add("Medium");
        level.add("High");
     
        
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_spinner_item,list);
        
       // ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>
       // (this, android.R.layout.simple_spinner_item,level);
        
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinner1.setAdapter(categoryAdapter);
        	
      //  levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	//spinner2.setAdapter(levelAdapter);
        	
        	
        	
        
       // inputLetterScore = (EditText) findViewById(R.id.passLetterScore);
       // inputSameLetterPositions = (EditText) findViewById(R.id.passSameLetterPositions);
        btnMainScreen = (Button) findViewById(R.id.btnGameScreen);
        
        
     // Spinner category selection Listener  
        addListenerOnSpinner1ItemSelection();
        //Spinner level selection Listener
        //addListenerOnSpinner2ItemSelection();
        
       
        MainScreen =getIntent();
       
       
        //Listening to button event
        btnMainScreen.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View view) {
            	
               //  if (!inputLetterScore.getEditableText().toString().isEmpty())
                // { 
                //	 inputletterscore=inputLetterScore.getEditableText().toString();
                	 //MainScreen.putExtra("letter_score", inputletterscore);
                // }
               //  else
                	// MainScreen.putExtra("letter_score", "240");
                 
                // if (!inputSameLetterPositions.getEditableText().toString().isEmpty())
               //  {
                	// inputsamepos=inputSameLetterPositions.getEditableText().toString();   
                 	// MainScreen.putExtra("letter_same_positions", inputsamepos);
                // }
               //  else
                	// MainScreen.putExtra("letter_same_positions", "5");
              	
                   //  MainScreen.putExtra("category_selected", category_selected);
                  //   MainScreen.putExtra("level_selected", level_selected);
            	 
            	setResult(Activity.RESULT_OK, MainScreen);
            	finish();
            	
            }
        });
    }
    
    public void addListenerOnSpinner1ItemSelection() {
    	 
    	spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	category_selected= parentView.getItemAtPosition(position).toString();
    	    	
    	    }

    	    @Override
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	    	
    	    	    	    	category_selected="General";    	     
    	    }

    	});

    	
      }
    
  
    
    
}