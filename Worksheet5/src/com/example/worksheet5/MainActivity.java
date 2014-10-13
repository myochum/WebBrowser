package com.example.worksheet5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText userURL;
	WebView webpage;
	Button goButton;
	Thread loadPage;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 userURL = (EditText) findViewById(R.id.userURL);
		 webpage = (WebView) findViewById(R.id.webpage);
		 goButton = (Button) findViewById(R.id.goButton);
		 
		 webpage.getSettings().setJavaScriptEnabled(true);
		 
		 
		goButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				loadPage.start();
				
			}
		});
		
		
		final Handler websiteInfo = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
		
			webpage.loadData(msg.obj.toString(), "text/html", "UTF-8");
			
			return false;
		}
	});
		
		loadPage = new Thread(){
		
			@Override
			public void run(){
				
				String fullURL = userURL.getText().toString();
				String protocol = "https://";
				String webContent;
				
				if(!(fullURL.contains(protocol))){
					fullURL = protocol + fullURL;
				}
				
				try {
					StringBuilder inputBuilder = new StringBuilder();
					String input;
					
					URL userInput = new URL(fullURL);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(userInput.openStream()));
					
					while((input = in.readLine()) != null){
						inputBuilder.append(input);
					}
					
					webContent = inputBuilder.toString();
					in.close();	
					Message msg = Message.obtain();
				msg.obj = webContent;
				
				websiteInfo.sendMessage(msg);
					
				} catch (MalformedURLException e) {
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				

			}
		};
		
		
	
	
	
	}
}
