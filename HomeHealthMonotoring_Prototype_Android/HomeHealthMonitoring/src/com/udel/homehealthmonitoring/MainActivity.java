package com.udel.homehealthmonitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.helloworld.homehealthmonitoring.R;
import com.helloworld.homehealthmonitoring.UploadData;

public class MainActivity extends ActionBarActivity {
	public final static String USERNAME = "com.udel.homehealthmonitoring.username";
	public final static String PASS = "com.udel.homehealthmonitoring.password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        Button b1 = (Button)findViewById(R.id.button1);
        final Intent intent = new Intent(this, UploadData.class);
        

        b1.setOnClickListener(
        		new OnClickListener() {
        			public void onClick(View v) {
        				String login = username.getText().toString();
        				String pass = password.getText().toString();
        				
        				username.setText("");//set text field Null
        				password.setText("");//set text field Null
        				try {
        					HttpClient httpclient = new DefaultHttpClient();
        					HttpPost request = new HttpPost("http://128.4.213.180:8080/api/authenticate");
        					        					
        					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        			        nameValuePairs.add(new BasicNameValuePair("username", login));
        			        nameValuePairs.add(new BasicNameValuePair("password", pass));
        			        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        			        
        					HttpResponse response = httpclient.execute(request);
        			        
        			        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        			        String line = in.readLine();        			        
        			        if(line.contains("true"))
    						{
        			        	//Toast.makeText(getBaseContext(), "Server says: "+line, Toast.LENGTH_SHORT).show();
        			        	Toast.makeText(getBaseContext(), "Welcome "+login+"!", Toast.LENGTH_SHORT).show();
        			        	intent.putExtra(USERNAME, login);
        			        	intent.putExtra(PASS, pass);
        						startActivity(intent);
        			        }
        			        //Toast.makeText(getBaseContext(), "Welcome "+login+"!", Toast.LENGTH_SHORT).show();
        			        //Toast.makeText(getBaseContext(), "Server says: "+line+" !", Toast.LENGTH_LONG).show();
    						
        			        /*
        					if(login.compareToIgnoreCase("test") == 0 && pass.compareTo("test") == 0){
        						Toast.makeText(getBaseContext(), "Welcome "+login+"!", Toast.LENGTH_LONG).show();
        						intent.putExtra(USERNAME, login);
        						startActivity(intent);
        					}
        					else{
        						Toast.makeText(getBaseContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show();
        					}*/
        				}
        				
        				catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(getBaseContext(), "IllegalStateException", Toast.LENGTH_SHORT).show();
						} 
        				catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(getBaseContext(), "IOException", Toast.LENGTH_SHORT).show();
						}   					
        	}
		});
        
    }
}


