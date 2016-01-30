package com.example.test.gridle.BaseDaoLayer;

import android.content.Context;

/**
 * Created by bhavik on 10/15/15.
 */
public class InitDB {
    private String notes_table="CREATE TABLE IF NOT EXISTS "+
            "notes_data"+
            "("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "title TEXT,"+
            "label TEXT,"+
            "soft_delete TEXT,"+
            "note TEXT,"+
            "date TEXT)";

                public InitDB(Context context){
                        BaseDao baseDao = new BaseDao(context);
                        baseDao.createSchema(notes_table);
                }

}
