package com.example.test.gridle.BaseDaoLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.test.gridle.NotesData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by bhavik on 10/14/15.
 */
public class BaseDao {
    BaseDataSourceImpl baseDataSource;
    private String TAG = "BaseDao";
    public BaseDao(Context context)
    {
        baseDataSource  = BaseDataSourceImpl.getInstance(context);
    }

    public void createSchema(String schema ){
        baseDataSource.getDatabase().execSQL(schema);

    }

public void insertUpdateNote(NotesData data){
        Cursor cursor = baseDataSource.getDatabase().query("notes_data",null,"id = ?",new String[]{data.getId()},null,null,null);
         if(!cursor.moveToFirst()) {
             ContentValues row = new ContentValues();
             row.put("title", data.getTitle());
             row.put("label", data.getLabel());
             row.put("note", data.getNote());
             row.put("date", data.getDate());
             row.put("soft_delete","1");
             baseDataSource.getDatabase().insert("notes_data", null, row);
         }else{
          ContentValues row = new ContentValues();
           row.put("id", data.getId());
             row.put("title", data.getTitle());
             row.put("label", data.getLabel());
             row.put("note", data.getNote());
             row.put("date", data.getDate());
             row.put("soft_delete","1");
         baseDataSource.getDatabase().update("notes_data", row, "id=?", new String[]{data.getId()});
         }
        cursor.close();
    }
    public List<NotesData> getNotesData(){
       List<NotesData> meta_data =  new ArrayList<>();
        NotesData data;
                    Cursor curData = baseDataSource.getDatabase().query("notes_data", null, "soft_delete = ?",new String[]{"1"}, null, null, null);
                    if (curData.moveToFirst()) {
                        do {
                            data = new NotesData();
                            data.setId(curData.getString(curData.getColumnIndex("id")));
                            data.setTitle(curData.getString(curData.getColumnIndex("title")));
                            data.setLabel(curData.getString(curData.getColumnIndex("label")));
                            data.setNote(curData.getString(curData.getColumnIndex("note")));
                            data.setDate(curData.getString(curData.getColumnIndex("date")));
                            meta_data.add(data);
                        }while (curData.moveToNext());
                    }
        curData.close();
        return meta_data;
    }
public NotesData getNotes(String id){
     Cursor cursor = baseDataSource.getDatabase().query("notes_data",null,"id = ?",new String[]{id},null,null,null);
                cursor.moveToFirst();
                NotesData  notesData=new NotesData();
                notesData.setId(cursor.getString(cursor.getColumnIndex("id")));
                notesData.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                notesData.setNote(cursor.getString(cursor.getColumnIndex("note")));
    cursor.close();
        return notesData;
        }
    public void removeTables(){
        baseDataSource.getDatabase().delete("notes_data",null,null);
    }
    public void remove(String id) {
        ContentValues row = new ContentValues();
        row.put("soft_delete","0");
        baseDataSource.getDatabase().update("notes_data",row,"id = ?",new String[]{id});
    }
}
