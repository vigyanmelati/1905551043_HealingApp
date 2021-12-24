package id.vigyan.healingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBmain extends SQLiteOpenHelper {
    public static final String db_name="db_healing";
    public static final String table_name="tb_registrasi_pasien";
    public static final int VER=2;

    public static final String row_id ="id";
    public static final String row_nik ="NIK";
    public static final String row_nama="Nama";
    public static final String row_jk="Jenis_Kelamin";
    public static final String row_keluhan="Keluhan";
    public static final String row_persentase="Persentase_Kecemasan";

    private SQLiteDatabase db;

    public DBmain(Context context) {
        super(context, db_name, null, VER);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " + table_name + "("
                + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nik + " TEXT,"
                + row_nama + " TEXT,"
                + row_jk + " TEXT,"
                + row_keluhan + " TEXT,"
                + row_persentase + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ table_name);
        onCreate(db);
    }

    public void insertData(ContentValues values){
        db.insert(table_name,null,values);
    }

    public void updateData(ContentValues values, long id){
        db.update(table_name,values,row_id + "=" + id, null);
    }

    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    public Cursor getAllData(){
        return db.query(table_name,null,null,null,null, null,null);
    }

    public Cursor getData(long id){
        return db.rawQuery("SELECT*FROM " + table_name + " WHERE " + row_id + "=" + id, null);
    }
}
