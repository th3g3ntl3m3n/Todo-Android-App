package com.example.ram.to_doapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDataHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "noteDatabase";
    private static final int DATABASE_VER = 1;
    private static final String IDNOTE = "_id";
    private static final String NOTE = "_note";
    private static final String TABLE_NAME = "notes";
    NoteDataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "CREATE TABLE " + TABLE_NAME
                +"( "+IDNOTE+" INTEGER PRIMARY KEY, "
                + NOTE +" VARCHAR(255) " + ");";
        sqLiteDatabase.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //All CRUD Operations
    public void addNote(Notepad myNote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE, myNote.getSubject());

        db.insert(TABLE_NAME, null,cv);
        db.close();
    }

    public Notepad getNote(int id){
        return null;
    }

    public List<Notepad> getAllNotes(){
        List<Notepad> listNote = new ArrayList<Notepad>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Notepad note = new Notepad();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setSubject(cursor.getString(1));
                System.out.println("Note id " + note.getId() + " note subject " + note.getSubject());
                listNote.add(note);
            } while(cursor.moveToNext());
        }
        //cursor.close();
        return listNote;
    }

    public void deleteNote(Notepad note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, IDNOTE + " = ?", new String[] {String.valueOf(note.getId())});
        sqLiteDatabase.close();
    }

    public int updateNote(Notepad note){
        return 0;
    }

    public int getNoteCount(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
