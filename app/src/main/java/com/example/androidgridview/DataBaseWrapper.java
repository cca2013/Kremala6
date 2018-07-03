package com.example.androidgridview;



	
	import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

	public class DataBaseWrapper extends SQLiteOpenHelper {

		public static final String PLAYERS = "kremala";
		public static final String PLAYER_ID = "_id";
		public static final String PLAYER_NAME = "_name";
		public static final String PLAYER_SCORE = "_score";

		private static final String DATABASE_NAME = "kremala.db";
		private static final int DATABASE_VERSION = 1;

		// creation SQLite statement
		private static final String DATABASE_CREATE = "create table " + PLAYERS
				+ "(" + PLAYER_ID + " integer primary key autoincrement, "
				+ PLAYER_NAME + " text not null, "+PLAYER_SCORE+" integer);";


		public DataBaseWrapper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// you should do some logging in here
			// ..

			db.execSQL("DROP TABLE IF EXISTS " + PLAYERS);
			onCreate(db);
		}

	}

