package com.pcsma.requestmaker;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends Activity implements OnClickListener{

	EditText etIndex;
	Button executeDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_page);
		etIndex = (EditText)findViewById(R.id.etIndex);
		executeDelete = (Button) findViewById(R.id.deletePageButton);
		executeDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.deletePageButton:
			String index = etIndex.getText().toString();
			if(!index.equals("")){
				DeleteAsyncTask postTask = new DeleteAsyncTask(index);
				postTask.execute("");
				executeDelete.setClickable(false);
			}else{
				Toast.makeText(getApplicationContext(), "Enter proper parameters", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}


	private class DeleteAsyncTask extends AsyncTask<String, Void, String>{

		String index;

		public DeleteAsyncTask(String index){
			this.index = index;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				HttpParams httpParameters = new BasicHttpParams();
				int timeoutConnection = 10000;
				HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
				int timeoutSocket = 10000;
				HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

				DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
				String deleteURL = Utils.URL + "?" + "index=" + URLEncoder.encode(index, "utf-8");
				HttpDelete deleteRequest= new HttpDelete(deleteURL);

				HttpResponse response = httpclient.execute(deleteRequest);
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
			executeDelete.setClickable(true);
			Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_LONG).show();
		}
	}

}
