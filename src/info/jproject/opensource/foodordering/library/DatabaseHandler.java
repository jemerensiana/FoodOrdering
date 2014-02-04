package info.jproject.opensource.foodordering.library;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "android_api";
    private static final String TABLE_LOGIN = "login";
    private static DatabaseHandler db = null;
    private static SQLiteDatabase myWritable, myReadable;
    
    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ANUMBER = "anumber";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CREATED_AT = "created_at";
 
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHandler getDatabaseHandler(Context context) {
		if(db == null) {
			db = new DatabaseHandler(context.getApplicationContext());
		}
    	return db;
    }
    
    public SQLiteDatabase getMyWritable() {
    	if((myWritable == null) || (!myWritable.isOpen())) {
    		myWritable = this.getWritableDatabase();
    	}
		return myWritable;
    }
    
    public SQLiteDatabase getMyReadable() {
    	if((myReadable == null) || (!myReadable.isOpen())) {
    		myReadable = this.getReadableDatabase();
    	}
    	return myReadable;
    }
    
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ANUMBER + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String anumber, String name, String email, String created_at) {
        SQLiteDatabase db = getMyWritable();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ANUMBER, anumber); //A# number
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
 
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        //db.close(); // Closing database connection
    }
 
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = getMyReadable();
        Cursor cursor = null;
        
        try {
	        cursor = db.rawQuery(selectQuery, null);
	        // Move to first row
	        cursor.moveToFirst();
	        if(cursor.getCount() > 0){
	        	user.put("anumber", cursor.getString(1));
	            user.put("name", cursor.getString(2));
	            user.put("email", cursor.getString(3));
	            user.put("created_at", cursor.getString(5));
	        }
        } catch (CursorIndexOutOfBoundsException e) {
        	e.printStackTrace();
        	Log.e("CursorOutOfBounds-HashMap", e.toString());
        }
        cursor.close();
        //db.close();
        // return user
        return user;
    }
    
    public String getUserName() {
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = getMyReadable();
        String name = null;
        Cursor cursor = null;
        
        try {
	        cursor = db.rawQuery(selectQuery, null);
	        if(cursor.getCount() == 0){
	        	name = "None";
	        } else {
		        // Move to first row
		        cursor.moveToFirst();
		        name = cursor.getString(2);
	        }
        } catch (CursorIndexOutOfBoundsException e) {
        	e.printStackTrace();
        	Log.e("CursorOutOfBounds-getUserName", e.toString());
        }
        
        cursor.close();
        //db.close();
        //Log.e("name", name);
        // return user
        return name;
    }
    
    public String getAnumber() {
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = getMyReadable();
        Cursor cursor = null;
        String anumber = null;
        
        try{
	        cursor = db.rawQuery(selectQuery, null);
	        if(cursor.getCount() == 0) {
	        	anumber = "None";
	        } else {
		        // Move to first row
		        cursor.moveToFirst();
		        anumber = cursor.getString(1);
	        }
        } catch (CursorIndexOutOfBoundsException e) {
        	e.printStackTrace();
        	Log.e("CursorOutOfBounds-getAnumber", e.toString());
        }
        
        cursor.close();
        //db.close();
        //Log.e("anumber", anumber);
        // return user
        return anumber;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = getMyReadable();
        Cursor cursor=null;
        int rowCount = 0;
        
        try {
        	cursor = db.rawQuery(countQuery, null);;
        	rowCount = cursor.getCount();
        } catch (CursorIndexOutOfBoundsException e) {
        	e.printStackTrace();
        	Log.e("CursorOutOfBounds-getRowCount", e.toString());
        }
        
        //db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
 
    /**
     * Re crate database
     * Delete all tables and create them again
     * @return 
     * */
    public boolean resetTables(){
        SQLiteDatabase db = getMyWritable();
        //String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        //Cursor cursor = null; 
        //int rowCount = 0;
        
        try{
        //cursor = db.rawQuery(countQuery, null);
        //rowCount = cursor.getCount();
        //Log.e("resetTables-before", Integer.toString(rowCount));
        
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
    
        //cursor = db.rawQuery(countQuery, null);
        //rowCount = cursor.getCount();
        
        //Log.e("resetTables-after", Integer.toString(rowCount));
        } catch (CursorIndexOutOfBoundsException e) {
        	e.printStackTrace();
        	Log.e("CursorOutOfBounds-resetTables", e.toString());
        } 
                
        //db.close();
        //cursor.close();
		return true;
    }
 
}