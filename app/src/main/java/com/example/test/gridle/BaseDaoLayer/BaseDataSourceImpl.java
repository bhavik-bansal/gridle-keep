package com.example.test.gridle.BaseDaoLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bhavik on 10/14/15.
 */
public class BaseDataSourceImpl extends SQLiteOpenHelper implements  BaseDataSource {

    private static BaseDataSourceImpl baseDataSource = null;
    private SQLiteDatabase sqLiteDatabase = null;

    public BaseDataSourceImpl(Context context) {
        super(context, "gridle", null, 1);
    }

    public static BaseDataSourceImpl getInstance(Context context){
        if(baseDataSource == null) {
            baseDataSource = new BaseDataSourceImpl(context);
            baseDataSource.openDatabase();
        }
        if (!baseDataSource.getDatabase().isOpen()) {
            baseDataSource.openDatabase();
        }
        return baseDataSource;
    }

    public BaseDataSourceImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabase getDatabase(){

        if(!this.sqLiteDatabase.isOpen()){
            this.sqLiteDatabase = getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public void openDatabase() {
        sqLiteDatabase=getWritableDatabase();
    }
}
