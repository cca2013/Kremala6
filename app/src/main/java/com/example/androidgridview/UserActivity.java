package com.example.androidgridview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.example.androidgridview.Constants.FIRST_COLUMN;
import static com.example.androidgridview.Constants.SECOND_COLUMN;





import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


 
public class UserActivity extends Activity  {
    // Initializing variables
    private EditText user_name;
    private Button btnMainScreen;   
    private TextView userscore;
    private ListView listview;
    public String user;
    public int score;
    private Player player;
    
    //dATABASE INITIALIZATIONS
    private DataBaseWrapper dbHelper;
	private String[] PLAYER_TABLE_COLUMNS = { DataBaseWrapper.PLAYER_ID, DataBaseWrapper.PLAYER_NAME ,DataBaseWrapper.PLAYER_SCORE};
	private SQLiteDatabase database;
	private List<Player> players;
	 private ArrayList<HashMap<String, String>> list1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        
        
        players = new ArrayList<Player>();
        //Player p=new Player();
       // list1=new ArrayList<HashMap<String,String>>();
        
        
        dbHelper = new DataBaseWrapper(getApplicationContext());
        open();//deleteZeroScorePlayers();
        user_name = (EditText) findViewById(R.id.username);
        btnMainScreen =(Button) findViewById(R.id.btnMainScreen);
        TextView nameHeader = (TextView) findViewById(R.id.name_header);
        TextView scoreHeader = (TextView) findViewById(R.id.score_header);

        nameHeader.setText("Player");
        scoreHeader.setText("Score");
        //setUserscore((TextView) findViewById(R.id.userscore));
        listview=(ListView) findViewById(R.id.list);
        //list.setAdapter(new CustomAdapter(this,players));
       // ArrayAdapter<Player> adapter =
        	 //     new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1,players);
       // list.setAdapter(adapter);
        
        //receive reply from Game            
        final Intent fromMainScreen =getIntent();
        // Receiving the player data
		user=fromMainScreen.getStringExtra("user_name");
        score=Integer.parseInt(fromMainScreen.getStringExtra("user_score"));
        //userscore.setText(String.valueOf(score));
        
        //save player if name is not null
        
        player=addPlayer(user,score);
        getAllPlayers();
        //players=Top10Scores();
        players=Bubble(players);
        //players=selectTop10(players);
        
        ListViewAdapter adapter=new ListViewAdapter(this, players);
        listview.setAdapter(adapter);
       // populateList(user,score);
        
        adapter.notifyDataSetChanged();
        
        
       // p.setName(user);p.setScore(score);
       // players.add(p);
        //adapter.notifyDataSetChanged();
       // user_name.setText(user);
        //add player data to database
       // addPlayer(user,score);
        //get players from database to list
      //  getAllPlayers();
        
        
       // ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this,
        //        android.R.layout.simple_list_item_1, players);
        //    setListAdapter(adapter);
        
        //Listening to button event
        btnMainScreen.setOnClickListener(new View.OnClickListener() {
        	
            public void onClick(View arg0) {
             	user=user_name.getText().toString();     
             	fromMainScreen.putExtra("user_message", user);
             	setResult(Activity.RESULT_OK, fromMainScreen);
            	finish();
            }
        });

    	//category_selected=String.valueOf(spinner1.getSelectedItemPosition());
  	
      }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment 
    {

        public PlaceholderFragment() { }

      //  @Override
      //  public View onCreateView(LayoutInflater inflater, ViewGroup container,
              //    Bundle savedInstanceState) {
           //   View rootView = inflater.inflate(R.layout.fragment_display_message,
              //        container, false);
             // return rootView;
      //  }
    
    }
     
    public List getAllPlayers() {
		
		Cursor cursor = database.query(DataBaseWrapper.PLAYERS,
				PLAYER_TABLE_COLUMNS, null, null, null, null, null);
       int count=0;
		cursor.moveToFirst();
		while (!cursor.isAfterLast() ) {
			count++;
			Player player = parsePlayer(cursor);
			if (player.getScore()!=0)
			players.add(player);
			cursor.moveToNext();
		}
    
		cursor.close();
		return players;
	}
    
    
    
    private List<Player> Bubble(List<Player> p){
    	int n=p.size();
    	
        for (int c = 0; c < ( n - 1 ); c++) 
        {
            for (int d = 0; d < n - c - 1; d++) {
              if (p.get(d).getScore() < p.get(d+1).getScore()) /* For descending order use < */
              {
            	 p=swapPlayers(p,d,d+1);             	  
              }
            }
          }

    	return p;
    }
    
    
    private List<Player> selectTop10(List<Player> p)
    {
    	int last=p.size()-1;
    	
    	for (int i=0;i<last;i++)
    	{
    		if (i>=10)
    		{
    			p.remove(i);
    			
    		}
    		
    	}
    		    	
    	return p;
    }
    
    private List<Player> swapPlayers(List<Player> p, int i,int j)
    {	
    	Player swap=new Player();
    	swap=p.get(i);
    	p.set(i,p.get(j));
    	p.set(j, swap);	
   return p; 	
    }
    
    
    //new Bubble sort
	private List<Player> Top10Scores()
	{
		int max_score=0;int min_score=0;			
		int count=0;
				
		List <Player> top10_players=new ArrayList<Player>();
		Player player=new Player();
		
	//rerun players to get the next top score after removing the previously found	  
	while (count<10){
		//run players to get the top score	
		for (int i=0;i<players.size();i++)
    	{   
			player=players.get(i);    		
			int score=player.getScore();
    		//compare score
    		if (score>max_score)
    		{	player=players.get(i); 
    			//swap
    			min_score=max_score;max_score=score;
    			//remove this top score from players
    	    	    players.get(i).setScore(0);
    			
   		    
    			
    		}
    		
    	}
		
		top10_players.add(player);
		
		count++;

	}    
	
		return top10_players;
	}
		
		
	
    
    
	public void PlayerOperations(Context context) {
		dbHelper = new DataBaseWrapper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Player addPlayer(String name,int score) {

		ContentValues values = new ContentValues();

		values.put(DataBaseWrapper.PLAYER_NAME, name);
		values.put(DataBaseWrapper.PLAYER_SCORE, score);

		long playerId = database.insert(DataBaseWrapper.PLAYERS, null, values);

		// now that the student is created return it ...
		Cursor cursor = database.query(DataBaseWrapper.PLAYERS,
				PLAYER_TABLE_COLUMNS, DataBaseWrapper.PLAYER_ID + " = "
						+ playerId, null, null, null, null);

		cursor.moveToFirst();

		Player newComment = parsePlayer(cursor);
		cursor.close();
		return newComment;
	}
	
	public void deletePlayer(Player comment) {
		long id = comment.getId();
		//System.out.println("Player deleted with id: " + id);
		database.delete(DataBaseWrapper.PLAYERS, DataBaseWrapper.PLAYER_ID
				+ " = " + id, null);
	}
	public void deleteLastPlayer() {
		
		Cursor cursor = database.query(DataBaseWrapper.PLAYERS,
				PLAYER_TABLE_COLUMNS, null, null, null, null, null);
		cursor.moveToLast();
		Player lastPlayer = parsePlayer(cursor);
		
		//System.out.println("Player deleted with id: " + id);
		database.delete(DataBaseWrapper.PLAYERS, DataBaseWrapper.PLAYER_ID
				+ " = " + lastPlayer.getId(), null);
		
	}
	
public void deleteZeroScorePlayers() {
		
		Cursor cursor = database.query(DataBaseWrapper.PLAYERS,
				PLAYER_TABLE_COLUMNS, null, null, null, null, null);
		cursor.moveToFirst();
		while (cursor.isAfterLast())
		{
			Player thisPlayer = parsePlayer(cursor);
			if (thisPlayer.getScore()==0)
				database.delete(DataBaseWrapper.PLAYERS, DataBaseWrapper.PLAYER_ID
						+ " = " + thisPlayer.getId(), null);
			
		}
				
		
	}
	
	
	private Player parsePlayer(Cursor cursor) {
		Player player = new Player();
		player.setId((cursor.getInt(0)));
		player.setName(cursor.getString(1));
		player.setScore(cursor.getInt(2));
		return player;
	}


	public TextView getUserscore() {
		return userscore;
	}


	public void setUserscore(TextView userscore) {
		this.userscore = userscore;
	}
	
	private void populateList(String name, int score) {
        // TODO Auto-generated method stub
         
       // list1=new ArrayList<HashMap<String,String>>();
         
        HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(FIRST_COLUMN, name);
            temp.put(SECOND_COLUMN, String.valueOf(score));
           
        list1.add(temp);
         
     
             
    }



}