package com.example.noted;
/**
 * 编辑界面
 */

import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends Activity {

	

	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	
	public Bitmap bm = null;
	
	protected Button button_save;

	protected Button button_back;
	
	protected Button button_record;

	protected Button button_image;
	
	protected EditText edit;
	
	protected ImageView imageView1;
	
	protected ImageView imageView2;
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		button_back = (Button)findViewById(R.id.back);
		button_save = (Button)findViewById(R.id.save);
		button_record = (Button)findViewById(R.id.addrecord);
		button_image = (Button)findViewById(R.id.addimage);
		edit = (EditText) findViewById(R.id.edit);
		imageView1 = (ImageView) findViewById(R.id.image);
		imageView2 = (ImageView) findViewById(R.id.tupian);
		//返回按钮监听
		button_back.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(EditActivity.this, MainActivity.class);
				startActivity(intent);
				EditActivity.this.finish();
			}
		});
		
		//保存按钮监听
		button_save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new MainActivity().InsertData(MainActivity.db, "notes",edit.getText().toString());
				Toast.makeText(EditActivity.this, "保存成功", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(EditActivity.this, MainActivity.class);
				startActivity(intent);
				EditActivity.this.finish();
			}
		});
		
		//录音按钮监听
		button_record.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(EditActivity.this, RecordActivity.class);
				startActivity(intent);
				EditActivity.this.finish();
			}
	   });
		//图片添加
		button_image.setOnClickListener(new OnClickListener() {
			 public void onClick(View v) {
				 Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				 getAlbum.setType(IMAGE_TYPE);
				 startActivityForResult(getAlbum, IMAGE_CODE);
			 }
		});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode != RESULT_OK) {
			return;
		}
		
		ContentResolver resolver = getContentResolver();
		if (requestCode == IMAGE_CODE) {
			try {
				Uri originalUri = data.getData(); //获得图片的uri
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); //显得到bitmap图片
				imageView1.setImageBitmap(bm);
			}catch (IOException e) {
			}
		}
	}
	public Bitmap getbm(){
		return bm;
	}
}
