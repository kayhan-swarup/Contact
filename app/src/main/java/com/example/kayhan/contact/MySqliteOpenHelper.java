package com.example.kayhan.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;

/**
 * Created by Kayhan on 4/8/2015.
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "MY_CONTACT_DB";
    String [] CONTACT_COLUMNS = {"_id","NAME","NUMBER"};
    public Context mContext;


    public String CREATE_TABLE_CONTACT = "CREATE TABLE CONTACT(_id INTEGER PRIMARY KEY,"
            +"NAME TEXT, NUMBER TEXT)";
    public String CONTACT_TABLE = "CONTACT";




    public MySqliteOpenHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.mContext = context;
//        addPhoneContact();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("IF EXISTS DROP TABLE CONTACT");
        this.onCreate(db);
    }
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CONTACT_COLUMNS[1],contact.name);
        values.put(CONTACT_COLUMNS[2],contact.number);

        db.insert(CONTACT_TABLE, null, values);
        db.close();

    }

    public Cursor getAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM "+CONTACT_TABLE,null);

        return cursor;

    }

    public void deleteContact(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACT_TABLE,CONTACT_COLUMNS[1]+" = ?",
                new String[]{name});
        db.close();
    }


    public boolean isEmpty(){
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(_id) FROM CONTACT", null);
        int size = cursor.getInt(0);
        return size==0;
    }

    public void addPhoneContact(){
        Cursor cursor = null;
        if(isEmpty())
            cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact contact = new Contact(name,number);
                    addContact(contact);
                }while (cursor.moveToNext());
            }
        }

    }


}
