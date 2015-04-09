package com.pcsma.assignment.uploadclient;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button send = (Button) findViewById(R.id.buttonPOST);
		Button show = (Button) findViewById(R.id.buttonSHOW);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SendFile sendMyFile = new SendFile();
				sendMyFile.execute("");
			}
		});
	}

	class SendFile extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(Utils.URL_POST);

				File myFile = new File(Environment.getExternalStorageDirectory() + "/Scan.pdf");
				FileBody myFileBody = new FileBody(myFile);
				httppost.setHeader("Content-type", "multipart/form-data");

				HttpEntity entity = MultipartEntityBuilder.create()
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addPart("image", myFileBody)
						.build();

				httppost.setEntity(entity);

				HttpResponse response;
				response = httpclient.execute(httppost);
				return "successs";
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return "";
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result.equals("successs")){
				Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
			}
		}
	}
}