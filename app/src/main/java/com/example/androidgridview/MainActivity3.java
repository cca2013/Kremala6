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

public class MainActivity3 extends Activity {
	
	
	private int max_wrong_letters=4;
	private int letter_score=240;
	private int same_letter_positions=5;
	private GridView grid;
	private GridView gridview;
	private int count=0;
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
	private int timescharfound=0;
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
	public String level="Low";
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
	private String LETTER_SCORE=String.valueOf(letter_score);
	private String LEVEL=String.valueOf(level);
	//string messages
	static final String timeup="Sorry, time up, got 0 points for this word.";
	static final String nopoints= "Sorry, no points left. ";
	private String bravo=	"Bravo ";
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
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);  
		bravo=bravo+user+" Won"+String.format("%1d", getPlayer_score())+"  points";

		//Intent intent= new Intent();
		//user=intent.getStringExtra(intent.getStringExtra("user_message"));

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
		
		createNewSecret();
		
		//set hangman image
		kremala = (ImageView)findViewById(R.id.kremala1);
	
		
		 //set the INPUT grid
		 gridview = (GridView) findViewById(R.id.gridView);
		 //final MyAdapter adapter1 = new MyAdapter(MainActivity.this, letters);
		
		 final ArrayAdapter<Character> ad = new ArrayAdapter<Character>(getApplicationContext(),R.layout.myytextview,letters);
		 
		 gridview.setAdapter(ad);
		 
		 //set the RESULT grid
		 grid = (GridView) findViewById(R.id.grid);
		// adapter1=new MyAdapter(getApplicationContext(),texts);
		 //adapter1 = new MyAdapter(MainActivity.this, texts);
		 
		 adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, texts);
		 grid.setAdapter(adapter);
		// runOnUiThread(new Runnable() {
			//    public void run() {
			    	
			    //    adapter1.notifyDataSetChanged();
			  //  }
			//});
		 //get the secret word- version 1
		// key_word = (EditText) findViewById(R.id.txtPassword);
		 //set up a secret word reset button
		 
		 
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
				 char_in=((TextView) v).getText().charAt(0);
				//convert the input to string
				  //key_string=key_word.getEditableText().toString();
				  key_string=secret.toString();
				  startCounting(getStart_count(),getStartTime(),getCustomHandler(),getUpdateTimerThread());				
				   advanceCharCounter();

				  
//check  for a time up
if (clock_timer(getClock_secs()))
				  {		toastText(timeup,30, getToastLayout());					
   			 			                   	    
               	    	failed(true);
          	    }			
else {
//time is fine
	 //check for more guess chances available 
				
        	  
				  //check if letter was found and how many times within secret word
                  if (char_found(getChar_in()))
                  {   //exclude already found letters include starting and ending letters.  
                	  if (char_not_already_found(texts,getChar_in(),getSecret()))
               
                	  {
                		  //check if there are not any remaining points
                		  if (no_points(getWord_score(),getWrong_guess()))
                		     {
                			    //set kremala to max
                		    	 setWrong_guess(getMax_wrong_letters());
                		    	 animateKremala(wrong_guess);
                		    	 //toast no points
                		    	 toastText(nopoints,30,getToastLayout());
                		    	 //finish up with a fail
                		    	 failed(true);
                		     }
                		  	 else  
                		     {
                			  //if there are remaining points place in position character
                		  		show_in_position(texts, getTimes_pos(),getTimescharfound(),getChar_in(),getAdapter());
                		     }                 	                  		 
                  		}
                  		else //letter was already found 
                  		{Toast.makeText(getApplicationContext(),
                       		  ((TextView) v).getText(), Toast.LENGTH_SHORT).show();}
						}//end letter already found
                  
                  
                  else
                  { //letter not found in secret

                	  //check 
                	    if(enough_score(getWord_score(),getWord_points(),getTries()))
                	    	//Points enough for this try, deduct points for this try from word_score
                	    	{   advance_wrong_guess();reduceWordScore();
                	    		//animate step kremala                	    	
                	    		animateKremala(wrong_guess);
                	    	}
                	    else if (getFromPlayerScore(getWord_score(),getWord_points(),getPlayer_score(),getTries()))
                	    		reducePlayerScore();
                	    else //player_score not enough
                	    	//else  hang man due to player quota run out.
                	    	{   setWrong_guess(getMax_wrong_letters());
                	    		toastText(nopoints,30,getToastLayout());
                	    		 
                	    		
    							//settle things down for a fail
    							failed(true);    			    		
                	    	}  //end of word_score<word_points/tries check           	      
                	    
                	 }//end of the clock timer clause
                	      
                	 
	               
				  //if success and points are enough
                  
                  if (word_found(key_string) )
                  {
                	  //initialize things for found	
                	  	initFound();
                	 	toastText(LEVEL,30,getToastLayout());                	            		     		
     					playWinner(R.raw.aha);	 
                	    failed(false);
	                 } //end of success
 
       }//end of  time is off

}//end of onItemClickListener
			
});//onItemClick

}//onCreate

	//new method structure
	//Boolean clock_timer(int time)
	//Boolean char_found(Character character)
	//Boolean char_not_already_found(Character[] texts,Character a)
	//Boolean no_points(int word_score,int wrong_guess)
	//Boolean failed(Boolean t)
	//Boolean getFromPlayerScore(int wordscore,int wordpoints,int playerscore,int tr)
	
	//void show_in_position(Character[] ab,int[] positions,int times,Character char_in)
	//void setWrong_guess(int wrong_guess)
	//void setTries(int tries)
	//void initFound()
	//void reset_word()
	//void reset_word()
	//void setMax_wrong_letters(int max_wrong_letters)
	//void setWord_score(int word_score)
	//void setWord_points(int word_points)
	//void setClock_secs(int clock_secs)
	//void setPlayer_score(int player_score) 
	//void setChar_in(Character char_in)
	//void setStartTime(long startTime)
	//void setStart_count(int start_count) 
	//void setCustomHandler(Handler customHandler)
	// void setUpdateTimerThread(Runnable updateTimerThread) 
	//void setSecret(String secret) 
	//void setTimescharfound(int timescharfound)
	//void setTimes_pos(int[] times_pos)
	//void setCategory(String category)
	//void setLevel(String level)
	
	
	//int getWrong_guess()
	//int animateKremala(int guess)
	//int getTries()
	//int belongs_2secret(Character b)
	//int reset_player()
	//int getMax_wrong_letters()
	//int getWord_score()
	//int getWord_points()
	//int getClock_secs() 
	//int getPlayer_score()
	//int getStart_count()
	//int getTimescharfound()
	//int[] getTimes_pos() 
	
	
	//long getStartTime()
	
	//Handler getCustomHandler()
	
	// Runnable getUpdateTimerThread() 
	
	//String createSecret(String word)
	// String getSecret()
	//String getCategory()
	//String getLevel()
	
	
	//Character getChar_in() 
	
	//setters getters
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;}
	
	
	public int[] getTimes_pos() {
		return times_pos;
	}

	public void setTimes_pos(int[] times_pos) {
		this.times_pos = times_pos;
	}
	public int getTimescharfound() {
		return timescharfound;
	}

	public void setTimescharfound(int timescharfound) {
		this.timescharfound = timescharfound;
	}
	
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Handler getCustomHandler() {
		return customHandler;
	}

	public void setCustomHandler(Handler customHandler) {
		this.customHandler = customHandler;
	}

	public int getStart_count() {
		return start_count;
	}

	public void setStart_count(int start_count) {
		this.start_count = start_count;
	}

	public Runnable getUpdateTimerThread() {
		return updateTimerThread;
	}

	public void setUpdateTimerThread(Runnable updateTimerThread) {
		this.updateTimerThread = updateTimerThread;
	}
	public Character getChar_in() {
		return char_in;
	}

	public void setChar_in(Character char_in) {
		this.char_in = char_in;
	}
	public int getPlayer_score() {
		return player_score;
	}

	public void setPlayer_score(int player_score) {
		this.player_score = player_score;
	}
	public ArrayAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ArrayAdapter adapter) {
		this.adapter = adapter;
	}
	public int getWord_score() {
		return word_score;
	}

	public void setWord_score(int word_score) {
		this.word_score = word_score;
	}

	public int getWord_points() {
		return word_points;
	}

	public void setWord_points(int word_points) {
		this.word_points = word_points;
	}

	public int getClock_secs() {
		return clock_secs;
	}

	public void setClock_secs(int clock_secs) {
		this.clock_secs = clock_secs;
	}
	
	
	public int getMax_wrong_letters() {
		return max_wrong_letters;
	}

	public void setMax_wrong_letters(int max_wrong_letters) {
		this.max_wrong_letters = max_wrong_letters;
	}
	public LinearLayout getToastLayout() {
		return toastLayout;
	}

	public void setToastLayout(LinearLayout toastLayout) {
		this.toastLayout = toastLayout;
	}
	
	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}
	public int getWrong_guess() {
		return wrong_guess;
	}

	public void setWrong_guess(int wrong_guess) {
		this.wrong_guess = wrong_guess;
	}
	
	//--------------------------------------------
	//setting environment
	private Boolean clock_timer(int time)
	{
		if (time<=0)
		 return true;
			return false;
		
	}
	private void reset_word(){
	 	resetFound();
        count_points();
    	createNewSecret();
    	adapter.notifyDataSetChanged(); 	
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
	
	private void initFound()
	{   
		count_points();
		failed=false;			
		
	}
	
	
	private Boolean failed(Boolean t)
	{    	if (t){
	
 	 	 //initialize counters
 		this.setWrong_guess(this.getMax_wrong_letters());
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
	private void startCounting(int start,Long starttime,Handler custom,Runnable thread)
	{
		
		if (start==0)
			
		{
				starttime = SystemClock.uptimeMillis();
				custom.postDelayed(thread, 0);					
		}
		
		
	}
	
	private void reducePlayerScore()
	{
		
		this.setPlayer_score(this.getWord_points()/this.getTries()-1);
		
	}
	private void reduceWordScore()
	{
		
		this.setWord_score(this.getWord_score()/this.getTries()-1);
		
	}
	
	private void advanceCharCounter()
	{
		
		this.setStart_count(this.getStart_count()+1);
	}
	
	//algorithms
	
	private Boolean getFromPlayerScore(int wordscore,int wordpoints,int playerscore,int tr)
	{
		if (wordscore<wordpoints/tr && playerscore>=wordpoints/tr)
			return true;
		return false;
		
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

	
	private Boolean enough_score(int wordscore,int wordpoints,int tries)
	{
		 if(wordscore>=wordpoints/tries)
			 return true;
		return false;
		
	}
	
	
	private void toastText(String text,int size, LinearLayout message)
	{
		
		 Toast makeText = Toast.makeText(getApplicationContext(),
    			 text, Toast.LENGTH_LONG);
    	 message = (LinearLayout)makeText.getView();
    	 TextView toastTV = (TextView) message.getChildAt(0);
    	 toastTV.setTextSize(size);
    	 makeText.show();  
	}
	
	
	private int animateKremala(int guess)
	{
		kremala.setImageResource(hangman_images[guess]);	
		return guess;	
	}
	
	
	private Boolean char_not_already_found(Character[] text,Character a,String s)
	{
		if (!letter_already_found(a) || text[0]==a || texts[s.length()-1]==a)
			return true;
		return false;
	}
	

	
	private Boolean char_found(Character character)
	{
		int times=belongs_2secret(character);
		
		if (times>0)
			return true;
		return false;
	}
	
	private Boolean no_points(int word_score,int wrong_guess)
	{
		
		if (word_score<0 || wrong_guess>this.getMax_wrong_letters())
			return true;
		return false;
	}
	
	private void show_in_position(Character[] ab,int[] positions,int times,Character char_in,ArrayAdapter ad){
		
		  for (int k=0;k<times;k++)
		  {
			 ab[positions[k]]=char_in;
			 ad.notifyDataSetChanged(); 
		
		  }
	}
	private void advance_wrong_guess()
	{
		if (wrong_guess<4)
			wrong_guess++;
		
	}
	
	//-----------------------------------------------------------------------
	
	//old methods 
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
	

	//result receiving from intents
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            
            String player_name=null;
            
    if (requestCode==1)
	    {
	        if(resultCode == RESULT_OK){	  
                  String cat=data.getExtras().getString("category_selected").toString();  
                  String lev=data.getExtras().getString("level_selected").toString();
                  String score=data.getExtras().getString("letter_score").toString();
                  String pos=data.getExtras().getString("letter_same_positions").toString();
                  letter_score=Integer.parseInt(score);
                  same_letter_positions=Integer.parseInt(pos);
                  
                  setCategory(cat);setLevel(lev);
                  
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
	  
		
		private void resetFound()
		{
			texts[0]='-';texts[1]='-';texts[2]='-';texts[3]='-';texts[4]='-';texts[5]='-';
			texts[6]='-';texts[7]='-';texts[8]='-';texts[9]='-';texts[10]='-';texts[11]='-';
			texts[12]='-';texts[13]='-';texts[14]='-';	
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