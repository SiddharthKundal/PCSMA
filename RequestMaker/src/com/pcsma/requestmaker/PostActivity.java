package com.pcsma.requestmaker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

public class PostActivity extends Activity implements OnClickListener{

	EditText etName, etLink, etLength, etAuthor, etRating;
	Button executePost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_page);
		etName = (EditText)findViewById(R.id.etName);
		etLength = (EditText)findViewById(R.id.etLength);
		etAuthor = (EditText)findViewById(R.id.etAuthor);
		etLink = (EditText)findViewById(R.id.etLink);
		etRating = (EditText)findViewById(R.id.etRating);
		executePost = (Button) findViewById(R.id.postPageButton);
		executePost.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.postPageButton:
			String name = etName.getText().toString();
			String length = etLength.getText().toString();
			String author = etAuthor.getText().toString();
			String rating = etRating.getText().toString();
			String link = etLink.getText().toString();
			if(!name.equals("") && !length.equals("") && !author.equals("") && !link.equals("") && !rating.equals("")){
				CL_Video myVid = (new CL_Video(name, length, author, link, Integer.parseInt(rating)));
				PostAsyncTask postTask = new PostAsyncTask(myVid);
				postTask.execute("");
				executePost.setClickable(false);
			}else{
				Toast.makeText(getApplicationContext(), "Enter proper parameters", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}


	private class PostAsyncTask extends AsyncTask<String, Void, String>{

		CL_Video vid;

		public PostAsyncTask(CL_Video vid){
			this.vid = vid;
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
				HttpPost postRequest= new HttpPost(Utils.URL);

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("name",vid.getVideoName()));
				nameValuePairs.add(new BasicNameValuePair("author", vid.getVideoAuthor()));
				nameValuePairs.add(new BasicNameValuePair("link", vid.getVideoLink()));
				nameValuePairs.add(new BasicNameValuePair("length", vid.getVideoLength()));
				nameValuePairs.add(new BasicNameValuePair("rating", "" + vid.getVideoRating()));
				postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(postRequest);
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
			executePost.setClickable(true);
			Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_LONG).show();
		}
	}

}
