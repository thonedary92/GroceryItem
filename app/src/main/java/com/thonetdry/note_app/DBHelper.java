package com.thonetdry.note_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "GOCERYlIST";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "gocery";
    private static final String DB_COL = "gocery_item";

    public DBHelper (Context context){
        super (context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %S (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);",DB_TABLE,DB_COL);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s",DB_TABLE);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void insertNewTask(String item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COL,item);

        sqLiteDatabase.insertWithOnConflict(DB_TABLE,null,contentValues,sqLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }
    public void deleteTask(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COL+" = ?",new String[] {item});
        db.close();
    }

    public ArrayList<String> getGroceryItem(){
        ArrayList<String> groceryItem = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[] {DB_COL},null,null,null,null,null);

        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COL);
            groceryItem.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return groceryItem;
    }
}
