/*
 * Chris Card
 * 9/14/12
 * 
 */
package csci498.ccard.lunchlist.apt.tutorial;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.Cursor;
import android.content.ContentValues;

public class RestaurantHelper extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME = "lunchlist.db";
	private static final int SCHEMA_VERSION = 1;

	public RestaurantHelper(Context context)
	{
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, notes TEXT, url TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// no-op, since will not be called until 2nd schema
		//version exists
	}

	public Cursor getById(String id)
	{
		String[] args = {id};

		return (getReadableDatabase().rawQuery("SELECT _id, name, address, type, notes, url FROM restaurants WHERE _ID=?", args));
	}

	public void update(String id, String name, String address, String type, String notes, String url)
	{
		ContentValues cv = new ContentValues();
		String[] args = {id};

		cv.put("name", name);
		cv.put("address", address);
		cv.put("type", type);
		cv.put("notes", notes);
		cv.put("url", url);

		getWritableDatabase().update("restaurants", cv, "_ID=?", args);
	}

	public void insert(String name, String address, String type, String notes, String url)
	{
		ContentValues cv = new ContentValues();

		cv.put("name", name);
		cv.put("address", address);
		cv.put("type", type);
		cv.put("notes", notes);
		cv.put("url", url);

		getWritableDatabase().insert("restaurants", "name", cv);
	}

	public Cursor getAll()
	{
		return (getReadableDatabase().rawQuery("SELECT _id, name, address, type, notes, url FROM restaurants ORDER BY name", null));
	}

	public String getName(Cursor c)
	{
		return (c.getString(1));
	}

	public String getAddress(Cursor c)
	{
		return (c.getString(2));
	}

	public String getType(Cursor c)
	{
		return (c.getString(3));
	}

	public String getNotes(Cursor c)
	{
		return (c.getString(4));
	}

	public String getURL(Cursor c)
	{
		return (c.getString(5));
	}
}
