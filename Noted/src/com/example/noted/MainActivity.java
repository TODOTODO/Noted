package com.example.noted;
/**
 * 主界面
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.R.integer;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//数据库
	public static SQLiteDatabase db;
    // 存储数据的数组列表  
	public static ArrayList<HashMap<String, Object>> listData;  
    // 适配器  
	public static SimpleAdapter listItemAdapter;
    
    public static String content;
	
    public SQLiteDatabase getmDb() {
		return db;
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	db = openOrCreateDatabase("users.db",SQLiteDatabase.CREATE_IF_NECESSARY, null);  
    	createTable(db, "notes");  
    	getAllData("notes");
    	for(int i =0;i<900;i++){
    		delete(db, "notes",i);
    	}
    	
		//初始化列表
        ListView list = (ListView) findViewById(R.id.list_items);  
        listItemAdapter = new SimpleAdapter(MainActivity.this,  
                listData,// 数据源  
                R.layout.item,// ListItem的XML实现  
                new String[] {"content"},  
                new int[] { R.id.content});  
        list.setAdapter(listItemAdapter);  
        
        //点击笔记查看笔记
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				int posi = position;
				 content = (String)listData.get(position).get("content");
//				Toast.makeText(MainActivity.this, "你点击了第"+position+"项的"+content,Toast.LENGTH_SHORT).show();
				 Intent intent = new Intent(MainActivity.this,SeeActivity.class);  
//	             Bundle bundle = new Bundle();  
//	             bundle.putInt("position", posi);  
//	             intent.putExtras(bundle);  
	             startActivity(intent);  
			}
		
		});
        
        //点击按钮进入笔记编辑界面
		Button button_add = (Button)findViewById(R.id.add);
		button_add.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, EditActivity.class);
				startActivity(intent);
			}
		});
	}
	
	// 创建一个数据库  
    public void createTable(SQLiteDatabase mDb, String table) {  
        try {  
            mDb.execSQL("create table if not exists "  
                    + table  
                    + " (id integer primary key autoincrement, "  
                    + "content text not null);");  
        } catch (SQLException e) {  
            Toast.makeText(getApplicationContext(), "数据表创建失败",  
                    Toast.LENGTH_LONG).show();  
        }  
    }  

    // 查询数据  
    public void getAllData(String table) {  
        Cursor c = db.rawQuery("select * from " + table, null);  
        int columnsSize = c.getColumnCount();  
        listData = new ArrayList<HashMap<String, Object>>();  
        // 获取表的内容  
        while (c.moveToNext()) {  
            HashMap<String, Object> map = new HashMap<String, Object>();  
            for (int i = 0; i < columnsSize; i++) {  
                map.put("id", c.getString(0));  
                map.put("content", c.getString(1));  
//                map.put("image", c.getString(2));  
            }  
            listData.add(map);  
        }  
    }  
    // 插入数据 
    public void InsertData(SQLiteDatabase mDb, String table,String content)
    {
	    	ContentValues values = new ContentValues();  
            values.put("content",content);  
            mDb.insert("notes", null, values);
    }
    // 删除数据  
    public boolean delete(SQLiteDatabase mDb, String table, int id) {  
        String whereClause = "id=?";  
        String[] whereArgs = new String[] { String.valueOf(id) };  
        try {  
            mDb.delete("notes", whereClause, whereArgs);  
        } catch (SQLException e) {  
            Toast.makeText(getApplicationContext(), "删除数据库失败",  
                    Toast.LENGTH_LONG).show();  
            return false;  
        }  
        return true;  
    }  
}