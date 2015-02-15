package com.pcsma.requestmaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GetActivity extends Activity implements OnClickListener{

	TextView resultView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_page);
		resultView = (TextView) findViewById(R.id.tvResult);
		Button getButton = (Button) findViewById(R.id.getPageButton);
		getButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.getPageButton:
			GetAsyncTask getTask = new GetAsyncTask();
			getTask.execute("");
			break;
		}
	}

	private class GetAsyncTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			try {

				HttpParams httpParameters = new BasicHttpParams();
				int timeoutConnection = 10000;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
				int timeoutSocket = 10000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpGet getRequest= new HttpGet(Utils.URL);
				HttpResponse response;
				response = httpclient.execute(getRequest);
				Log.wtf(Utils.TAG, "Status code ::" + response.getStatusLine().toString());
				if(response.getStatusLine().toString().contains("" + HttpStatus.SC_BAD_REQUEST)){
					return "HTTP/1.1 400 Bad Request error encountered";
				}else{
					String responseString = EntityUtils.toString(response.getEntity());
					return responseString;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ArrayList<CL_Video> myList = new ArrayList<CL_Video>();
			if(!result.equals("")){
				String[] resultVideos = result.split(Pattern.quote(Utils.ITEM_SEPARATOR));
				for(String s: resultVideos){
					if(s.contains(Utils.PROPERTY_SEPARATOR)){
						String[] videoPropsArr = s.split(Utils.PROPERTY_SEPARATOR);
						myList.add(new CL_Video(videoPropsArr[0], videoPropsArr[1], videoPropsArr[2], videoPropsArr[3], Integer.parseInt(videoPropsArr[4])));
					}
				}
			}
			String textToSet ="";
			for(CL_Video vid : myList){
				textToSet = textToSet + "Name: " + vid.getVideoName() + "\n"
						+ "Author: " + vid.getVideoAuthor() + "\n"
						+ "Link: " + vid.getVideoLink() + "\n"
						+ "Duration: " + vid.getVideoLength() + "\n"
						+ "Rating: " + vid.getVideoRating() + "\n"
						+ "\n";
			}
			resultView.setText(textToSet);
		}
	}
}