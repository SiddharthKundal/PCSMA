package com.pcsma.requestmaker;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
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

public class PutActivity extends Activity implements OnClickListener{

	EditText etName, etLink, etLength, etAuthor, etRating, etIndex;
	Button executePut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.put_page);
		etIndex = (EditText)findViewById(R.id.etIndex);
		etName = (EditText)findViewById(R.id.etName);
		etLength = (EditText)findViewById(R.id.etLength);
		etAuthor = (EditText)findViewById(R.id.etAuthor);
		etLink = (EditText)findViewById(R.id.etLink);
		etRating = (EditText)findViewById(R.id.etRating);
		executePut = (Button) findViewById(R.id.putPageButton);
		executePut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.putPageButton:
			String index = etIndex.getText().toString();
			String name = etName.getText().toString();
			String length = etLength.getText().toString();
			String author = etAuthor.getText().toString();
			String rating = etRating.getText().toString();
			String link = etLink.getText().toString();
			if(!index.equals("") && !name.equals("") && !length.equals("") && !author.equals("") && !link.equals("") && !rating.equals("")){
				CL_Video myVid = (new CL_Video(name, length, author, link, Integer.parseInt(rating)));
				PutAsyncTask postTask = new PutAsyncTask(myVid, index);
				postTask.execute("");
				executePut.setClickable(false);
			}else{
				Toast.makeText(getApplicationContext(), "Enter proper parameters", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}


	private class PutAsyncTask extends AsyncTask<String, Void, String>{

		CL_Video vid;
		String index;

		public PutAsyncTask(CL_Video vid, String index){
			this.vid = vid;
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

				String putURL = Utils.URL + "?" + "name=" + URLEncoder.encode(vid.getVideoName(), "utf-8")
						+ "&" + "index=" + URLEncoder.encode(index, "utf-8")
						+ "&" + "link=" + URLEncoder.encode(vid.getVideoLink(), "utf-8")
						+ "&" + "length=" + URLEncoder.encode(vid.getVideoLength(), "utf-8")
						+ "&" + "author=" + URLEncoder.encode(vid.getVideoAuthor(), "utf-8")
						+ "&" + "rating=" + URLEncoder.encode(vid.getVideoRating() + "", "utf-8");
				HttpPut putRequest= new HttpPut(putURL);

				HttpResponse response = httpclient.execute(putRequest);
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
			executePut.setClickable(true);
			Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_LONG).show();
		}
	}

}
