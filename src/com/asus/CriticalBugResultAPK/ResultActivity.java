package com.asus.CriticalBugResultAPK;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResultActivity extends Activity {
	private SimpleAdapter adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Collection for data of Listview
		ArrayList<HashMap<String, Object>> listview_data = new ArrayList<HashMap<String, Object>>();

		// get all Critical Bug List
		DatabaseHandler db = new DatabaseHandler(this);
		List<CriticalBug> Tests = db.getAllCriticalBug();

		for (CriticalBug cb : Tests) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", cb.ID);
			map.put("domain", cb.Domain);
			map.put("TCID", cb.TCID);
			map.put("TCName", cb.TCName);
			map.put("AutoType", cb.AutoType);
			map.put("ScreenShotURI", cb.ScreenShotURI);
			map.put("Result", cb.Result);
			listview_data.add(map);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_listview);

		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.ListView_Main);
		adapter = new SimpleAdapter(this, listview_data, R.layout.result_listview_item, new String[] { "id", "domain", "TCID", "TCName", "AutoType", "Result" },
				new int[] { R.id.number, R.id.domain, R.id.tcid, R.id.tcname, R.id.autotype, R.id.result });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Intent intent = new Intent();
				// intent.setType("image/*");// 圖片類型

				//Intent i = new Intent();
				//i.setAction(Intent.ACTION_VIEW);
				//i.setDataAndType(Uri.fromFile(new File("//mnt/sdcard/ATST/ScreenShot/1/")), MimeTypeMap.getSingleton().getMimeTypeFromExtension("png"));
				// i.setDataAndType(Uri.parse("/sdcard/ATST/ScreenShot/1"),
				// "image/*");

				//startActivity(i);
				Log.d("X",Environment.DIRECTORY_DOCUMENTS);
				Log.d("X",Environment.DIRECTORY_PICTURES);
				Log.d("X",Environment.DIRECTORY_DCIM);
				
				Intent intent = new Intent();  
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setDataAndType(Uri.fromFile(new File("//mnt/sdcard/ATST/ScreenShot/1/test.png")), MimeTypeMap.getSingleton().getMimeTypeFromExtension("png"));
				startActivity(intent);
			}
		});

	}
}
