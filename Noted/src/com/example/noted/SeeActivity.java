package com.example.noted;
/**
 * 查看界面
 */
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SeeActivity extends Activity {

	protected Button button_back;
	protected Button button_del;
	protected Button button_edit;
	protected TextView textView;
	protected ImageView imageView;
	protected Bitmap bm;
	int i=1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see);
		

		//得到控件
		button_back = (Button)findViewById(R.id.backsee);
		button_del = (Button)findViewById(R.id.delsee);
		button_edit = (Button)findViewById(R.id.editsee);
		textView = (TextView)findViewById(R.id.wenzi);
		
		EditActivity editActivity = new EditActivity();
		bm = editActivity.getbm();
		
		//显示内容
		textView.setText(MainActivity.content);
		
		//返回按钮
		button_back.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				SeeActivity.this.finish();
			}
		});
		//编辑按钮
		button_edit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				//删除掉
				//到编辑页面
				Intent intent_ = new Intent();
				intent_.setClass(SeeActivity.this, EditActivity.class);
				startActivity(intent_);
				SeeActivity.this.finish();
			}
		});
		//删除按钮
		button_del.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				//删除掉
				 Dialog dialog = new AlertDialog.Builder(SeeActivity.this)
                 .setTitle("删除")
                 .setMessage("确定要删除吗？")
                 .setPositiveButton("确定",
                		 new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog,int which)
                     {
                    	
//                    	 int position = Integer.parseInt("2");  //确定item所在位置
//                    	 MainActivity.listData.remove(i);  //移除该item
//                    	 MainActivity.listItemAdapter.notifyDataSetChanged();  //刷新listview
                    	Toast.makeText(SeeActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    	Intent intent_ = new Intent();
         				intent_.setClass(SeeActivity.this, MainActivity.class);
         				startActivity(intent_);
         				SeeActivity.this.finish();
                     }
                })
             .setNegativeButton("取消",
            		 new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog,int which)
                 {
                	 dialog.cancel();
                 }
            })
            .show();
			}
		});
	}
}
