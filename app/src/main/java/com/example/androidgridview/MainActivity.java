package com.example.androidgridview;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
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

public class MainActivity extends Activity {
	
	
	private int max_wrong_letters=4;
	private int letter_score=240;
	private int same_letter_positions=5;
	

	private GridView grid;
	private GridView gridview;
	private int count=0;
	private String level="Low";
	private int wrong_guess=0;
	private ImageView start_image = null;
	private ImageView kremala = null;
	private EditText key_word=null;
	private String key_string=null;
	private Character char_in=null;
	private Character[] texts1;
	private Character[] firstlast;
	public int[] times_pos;
	private Button button;
	private Button btnSettings;
	private Button quit;
	private Button userScreen;
	private TextView secret_field=null;
	private TextView text1;
	private String secret;
	private ArrayAdapter adapter=null;
	private MyAdapter adapter1;
	private  ArrayList<Character> mitems;
	private SoundPool soundPool;
	
	private int tries=4;
	private int word_score=0;
	private int word_points=0;
	private int clock_secs=0;
	
	private int player_score=0;
	private Boolean stop_timer=false;
	public String category="General";
	final int[] hangman_images= {R.drawable.kremala001, R.drawable.kremala002,R.drawable.kremala003,R.drawable.kremala004,R.drawable.kremala005};
	LinearLayout toastLayout ;
	List<Character> texts2 = new ArrayList<Character>(20);
	//timer BUTTONS
	//private Button startButton;
	//private Button pauseButton;
	//private Button resetTimerButton;
	private TextView timerValue;  
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	Intent settings,user_settings;
	Boolean failed=false;

	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	private int start_count=0;
	private String user="no name user";
	public final static String USER_NAME=null;
	public final static String USER_SCORE=null;
	
	
	static final Character[] letters = new Character[] { 
			'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	 static   Character[] texts = new Character[]{'-','-','-', '-', '-', '-', '-', '-','-', '-','-','-','-','-','-'};
	 
	 static final String[] words = new String[] { 
			"HELLO","KOSTAS","GREEK","RAMONA","KATERINA","GOOGLE","NIKOS","GEORGE","ZOI","CAR","BINGO","LOVE","PHYSICS","ROMANIA"};
	 static final String[] animals = new String[] { 
			"LION","TIGER","WOLF","ELEPHANT","LEOPARD","BUFFALO"};
	Helper helper=new Helper();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);  
	  	

        times_pos=new int[same_letter_positions];

        //startActivity(settings);
        userScreen=(Button)findViewById(R.id.btnUserScreen);
        userScreen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	//move to settings activity with user_name and player_score values	    	
            	user_settings = new Intent(getApplicationContext(), UserActivity.class);
            	user_settings.putExtra("user_name", user);
            	user_settings.putExtra("user_score",String.valueOf(player_score));
            	
            	startActivityForResult(user_settings,200);		    			    	
            }
        });
                
        quit=(Button)findViewById(R.id.btnQuit);
        quit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub                
                System.exit(0);
            }
        });
              
        btnSettings = (Button) findViewById(R.id.btnSettingsScreen);
        btnSettings.setOnClickListener(new OnClickListener(){
		    public void onClick(View view) {
		    	//go to settings screen
		    	settings = new Intent(getApplicationContext(), SettingsActivity.class);
		    	startActivityForResult(settings,1);
	          }
		   });

		//set timer components
		
		timerValue = (TextView) findViewById(R.id.timerValue);
		helper.createNewSecret();
		
		//set the hang man image
		kremala = (ImageView)findViewById(R.id.kremala1);
		
		 //set the INPUT grid
		 gridview = (GridView) findViewById(R.id.gridView);
		 //final MyAdapter adapter1 = new MyAdapter(MainActivity.this, letters);
		
		 final ArrayAdapter<Character> ad = new ArrayAdapter<Character>(getApplicationContext(),R.layout.myytextview,letters);
		 
		 gridview.setAdapter(ad);
		 
		 //set the RESULT grid
		 grid = (GridView) findViewById(R.id.grid);

		 
		 adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, texts);
		 grid.setAdapter(adapter);
		 
		 button=(Button)findViewById(R.id.new_word_button);
		  button.setOnClickListener(new OnClickListener(){
			    public void onClick(View view) {			
			       reset_word();
 	
		          }
			    });
		 
			
		  //alpha beta grid listener
		 gridview.setOnItemClickListener(new OnItemClickListener() {
			 	public void onItemClick(AdapterView parent, View v, int position, long id) {
  
			    //get the input character as a Character
				Character char_in=((TextView) v).getText().charAt(0);
				helper.setChar_in(char_in);

				//convert the input to string
				  //key_string=key_word.getEditableText().toString();
				  helper.setKey_string(helper.getSecret().toString());
				  
				if (helper.getStart_count()==0)
					
				{
						helper.setStartTime(SystemClock.uptimeMillis());					
					    helper.getCustomHandler().postDelayed(helper.getUpdateTimerThread(), 0);
				}

				helper.setStart_count(helper.getStart_count()+1);
				  //check if letter belongs to secret word
				  helper.setTimescharfound(helper.belongs_2secret(char_in));
				  
//check  for a time up
if (helper.clock_timer(clock_secs))

{			
	helper.toastText(helper.timeup,30, helper.toastLayout,getApplicationContext());   			 			               	    
    helper.setFailed(true);
               	    	
}			
else {
//time is fine
	 //check for more guess chances available 
				  //check if letter was found and how many times within secret word
                  if (helper.char_found(helper.getChar_in()))
                  {   //exclude already found letters include starting and ending letters.  
                	 if (helper.char_not_already_found(helper.getTexts(),helper.getChar_in(),helper.getSecret()))                	 
                	  {
                		  //check if there are not any remaining points
                		 if (helper.no_points(helper.getWord_score(), helper.getWrong_guess()))                		     
                		     {
                			    //animate kremala to hanged man
                			 	helper.setWrong_guess(helper.getMax_wrong_letters());                		    	
                		    	helper.animateKremala(helper.getWrong_guess());
                		    	 //toast no points
                		    	 helper.toastText(helper.nopoints,30, helper.toastLayout,getApplicationContext());   			 			               	    
                		    	 //finish up with a fail
                		    	 helper.setFailed(true);
                		     }
                		  	 else  
                		     {
                			  //if there are remaining points
                		  		helper.show_in_position(helper.getTexts(),helper.getTimes_pos(),helper.getTimescharfound(),helper.getChar_in(),helper.getAdapter());
                		     }                 	                  		 
                  		}
                  		else //letter was already found 
                  		{Toast.makeText(getApplicationContext(),
                       		  ((TextView) v).getText(), Toast.LENGTH_SHORT).show();}
						}//end letter already found
                  
                  
                  else
                  { //letter not found in secret

                	  //check 
                	  if (helper.wordScoreIsAlive())
                	    	//Points enough for this try, deduct points for this try from word_score
                	    	{   helper.advance_wrong_guess();
                	    	    helper.reduceWordScore();
                	    		//animate step kremala
                	    	    helper.animateKremala(helper.getWrong_guess());
                	    		
                	    	}
                	    else if (helper.getFromPlayerScore(helper.getWord_score(), helper.getWord_points(), helper.getPlayer_score(), helper.getTries()))
                	    		helper.reducePlayerScore();
                	    	
                	    else //player_score not enough
                	    	//else  hang man due to player quota run out.
                	    	{   
                	    		helper.setWrong_guess(helper.getMax_wrong_letters());
                	    		helper.toastText(helper.nopoints,30, helper.toastLayout,getApplicationContext());   			 			               	    
                        	    helper.setFailed(true);
			    		
                	    	}  //end of word_score<word_points/tries check           	      
                	    
                	 }//end of the clock timer clause

				  //if success and points are enough

                	  if (helper.word_found(helper.getKey_string()))
                	  {
                	  //initialize things for found	
                	  helper.initFound();
                	  helper.toastText(helper.bravo,30, helper.toastLayout,getApplicationContext());   			 			               	    
                	  helper.setFailed(true);		
     				  helper.playWinner(R.raw.aha,getApplicationContext());
     							
	                 } //end of success
                	
		  	 
	  
                  
       }//end of  time is off

}//end of onItemClickListener
			
});//onItemClick

}//onCreate

	
	
	
	
	//methods 
	private int count_points()
	{    int anim;
		 stopTimer();
	 	 resetTimer();
	 	 //initialize counters
		 wrong_guess=0;
		 player_score+=word_score;
		 word_score=0;
		 word_points=0;
		 anim=animateKremala(wrong_guess);
		 return anim;
		
	}
	
	private int reset_player()
	{    int anim;
		 stopTimer();
	 	 resetTimer();
	 	 resetFound();
	 	 //initialize counters
		 wrong_guess=0;		 
		 
		 player_score=0;
		 createNewSecret();
	     adapter.notifyDataSetChanged(); 
		 anim=animateKremala(wrong_guess);
		 return anim;
		
	}
	private int animateKremala(int guess)
	{
		kremala.setImageResource(hangman_images[guess]);	
		return guess;	
	}
	private Boolean failed(Boolean t)
	{    	if (t){
	
 	 	 //initialize counters
 		 wrong_guess=4;
 		 word_score=0;
 		 word_points=0;
 		 //hand the man
 		 animateKremala(wrong_guess);
 		 wrong_guess=0;
 		 
         reset_word();
 		 failed=true;
		}
	else
		failed=false;
	
	
 		 return t;
	}
	
	
	private void initFound()
	{   
		count_points();
		failed=false;			
		
	}
	
	private void reset_word(){
		 	resetFound();
	        count_points();
	    	createNewSecret();
	    	adapter.notifyDataSetChanged(); 	
	}
	private void advance_wrong_guess()
	{
		if (wrong_guess<4)
			wrong_guess++;
		
	}
	
	 private  void createNewSecret(){
		  int ran;
		  if (category.equals("Animals"))
		  {
			    ran=randomWordIndex(5);
				secret=animals[ran];
		  }  
			  
		  else	  
		  {
			  ran=randomWordIndex(13);
			  secret=words[ran];
			
		  }
			Character[] texts3 = new Character[secret.length()];
			for (int i=0;i<texts3.length;i++)
			    texts3[i]='-';
		   
		   
			firstlast=firstlast(secret);
			//texts1 = Arrays.copyOfRange(texts, 0, secret.length());
			//texts=texts1;
			//texts=Arrays.copyOfRange(texts, 0, secret.length());
			//texts=cutArray(secret,texts);
			texts[0]=firstlast[0];
			texts[secret.length()-1]=firstlast[1];
			
			word_points=letter_score*secret.length();
			clock_secs=word_points;
			
			word_score=clock_secs;
	
	  }
	//result receiving from intents
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            String letterScore=null;
            String sameLetter=null;
            String player_name=null;
            
    if (requestCode==1)
	    {
	        if(resultCode == RESULT_OK){
	        	
                helper.setCategory(data.getExtras().getString("category_selected").toString());  
                helper.setLevel(data.getExtras().getString("level_selected").toString());           
                              
                 reset_word();     
   	  
	        }
	        if (resultCode == RESULT_CANCELED) {
	        	
            	
	        	
	        }
	      
	    }
    if (requestCode==200) 	
    	       
    		    {
    		        if(resultCode == RESULT_OK){
    		        	  player_name = data.getExtras().get("user_message").toString();
    		        	  
    		        	  //make user's name global
    		        	  user=player_name;
    		        	  //reset game for a new player  
    	                  
    	                  reset_player();
    	                  
    		        }
    		       if (resultCode == RESULT_CANCELED) {
    		            
    		        }
    		    }
  

        
	}//onActivityResult
		

	
	
	private void stopTimer()
	{
		 timeSwapBuff += timeInMilliseconds;
		 customHandler.removeCallbacks(updateTimerThread);
		
	}
	private void resetTimer()
	{
		 timerValue.setText("" + 0 + ":"+ String.format("%02d", 0));
		 timeInMilliseconds = 0L;
		 timeSwapBuff = 0L;
		 updatedTime = 0L;
		 start_count=0;
		 
		
	}
	public Boolean word_found(String str)
	{
		Boolean found=false;
		String clean_str=new String();
		clean_str=clean_display_str();   
         if (str.equals(clean_str.toString()))
              found=true;
		return found;
	}
	
	public void playWinner(int p)
	{
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(),p);
		mp.start();
		
		
	}
	public String clean_display_str()
	{
		String str=new String();
		StringBuilder sb = new StringBuilder(texts.length);
		for (Character c : texts){
			if (!c.equals('-'))
			sb.append(c);
		}
		str = sb.toString();
		return str;
	}
	
	
	public int non_empty_pos(int[] times)
	{
		int result=0;
		for (int p=0;p<times.length;p++)
		{
			if (times[p]!=0)
			{
				result++;
			}
			
			
		}
		
		return result;
	}
	  public Boolean letter_already_found(Character b)
	  {    
		   Boolean exist=false;
		   
		   	for (int i=0;i<texts.length;i++){
		   		
		   		if (b.equals(texts[i]))
		   			
		   		{
		   			exist=true;
		   		}
		   	}
		   return exist;
	  }
	  
	  
	  public int belongs_2secret(Character b)
	  {    
		  int times=0;
		  times_pos=new int[10];
		  times_pos[0]=0;times_pos[1]=0;times_pos[2]=0;times_pos[3]=0;times_pos[4]=0;
		   
		   	for (int i=0;i<key_string.length();i++)
		   	{		   		
		   		if (b.equals(key_string.charAt(i)))
		   			
		   			{
		   				times_pos[times]=i;times++;	   						   				
		   			}
		   	}
		   	
		   return times;
	  }
	  
	  private int randomWordIndex(int size)
		{
		int number=0;	
		number =0 + (int)(Math.random() * ((size - 0) + 1));
		return number;	
		}
		
		
	 
	  
  

	  
	  private Character[] cutArray(String sec,Character[] text)
	  {
		  for (int i=0;i<text.length;i++)
		  {
			if (i==0)
			{
		      text[i]=sec.charAt(i);		
			}  
			
			  if (i==sec.length()-1)
			  {
				   text[i]=sec.charAt(i);
				  
			  }
			
			  if (i>sec.length()-1)  
			  {
				  
				  text[i]=null;
				  
			  }
			  
		  }
		  
		  
		 return text; 
	  }
	  
		private String createSecret(String word)
		{

			for (int s=1;s<word.length()-1;s++)
			{     
				word=word.substring(0,s)+'-'+word.substring(s+1);
			}
			return word;		
		}
				
		private Character[] firstlast(String word)
		{
			Character[] a=new Character[2];
			for (int i=0;i<word.length();i++)
			{     
				if (i==0 )
					a[0]=word.charAt(i);
				else if (i==word.length()-1)
					a[1]=word.charAt(i);			
			}
					
			return a;
		}
	  
		private void resetFound()
		{
			texts[0]='-';texts[1]='-';texts[2]='-';texts[3]='-';texts[4]='-';texts[5]='-';
			texts[6]='-';texts[7]='-';texts[8]='-';texts[9]='-';texts[10]='-';texts[11]='-';
			texts[12]='-';texts[13]='-';texts[14]='-';	
		}
		
		
		
	  private Runnable updateTimerThread = new Runnable() {

			public void run() {
				int secs=0;
				
            	  
				timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
				updatedTime = timeSwapBuff + timeInMilliseconds;
				
				secs = (int) (updatedTime / 1000);
				
				int mins = secs / 60;
				
				clock_secs-=mins;
				secs = secs % 60;
				//int milliseconds = (int) (updatedTime % 1000);
				timerValue.setText("" + mins + ":"
						+ String.format("%02d", secs));

				
				customHandler.postDelayed(this, 0);
				
           
			}

		};
		
		private void init_player()
		{
			player_score=0;
			
			
			
			
		}

}