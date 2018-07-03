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
import android.widget.Toast;
 
public class SettingsActivity1 extends Activity {
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
        setContentView(R.layout.settings);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        //addListenerOnSpinnerItemSelection();
        //addListenerOnSpinner1ItemSelection();
        
        addItemsOnSpinner2();
        addListenerOnSpinnerItemSelection();
        	
        	
        
       // inputLetterScore = (EditText) findViewById(R.id.passLetterScore);
       // inputSameLetterPositions = (EditText) findViewById(R.id.passSameLetterPositions);
        btnMainScreen = (Button) findViewById(R.id.btnGameScreen);
        
        
     // Spinner category selection Listener  
       // addListenerOnSpinnerItemSelection();
        //addListenerOnSpinner1ItemSelection();
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
  	List<String> list = new ArrayList<String>();
  	list.add("Low");
  	list.add("Medium");
  	list.add("High");
  	
  	
  	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
  		android.R.layout.simple_spinner_item, list);
  	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	spinner2.setAdapter(dataAdapter);
    }
    
    
    public void addListenerOnSpinnerItemSelection() {
    	spinner1 = (Spinner) findViewById(R.id.spinner1);
    	spinner1.setOnItemSelectedListener(new customOnItemSelectedListener());
    	spinner2 = (Spinner) findViewById(R.id.spinner2);
    	spinner2.setOnItemSelectedListener(new customOnItemSelectedListener());
      }
    
    
    
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

