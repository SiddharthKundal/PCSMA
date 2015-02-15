package com.pcsma.requestmaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomePage extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Button getPage = (Button) findViewById(R.id.buttonGET);
        getPage.setOnClickListener(this);
        Button putPage = (Button) findViewById(R.id.buttonPUT);
        putPage.setOnClickListener(this);
        Button postPage = (Button) findViewById(R.id.buttonPOST);
        postPage.setOnClickListener(this);
        Button deletePage = (Button) findViewById(R.id.buttonDELETE);
        deletePage.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonGET:
			Intent openGetPageIntent = new Intent(this, GetActivity.class);
			startActivity(openGetPageIntent);
			break;
		case R.id.buttonPOST:
			Intent openPostPageIntent = new Intent(this, PostActivity.class);
			startActivity(openPostPageIntent);
			break;
		case R.id.buttonPUT:
			Intent openPutPageIntent = new Intent(this, PutActivity.class);
			startActivity(openPutPageIntent);
			break;
		case R.id.buttonDELETE:
			Intent openDeletePageIntent = new Intent(this, DeleteActivity.class);
			startActivity(openDeletePageIntent);
			break;
		}
	}
}
