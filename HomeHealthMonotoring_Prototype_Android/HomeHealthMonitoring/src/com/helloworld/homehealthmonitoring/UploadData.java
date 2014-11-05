package com.helloworld.homehealthmonitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
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
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udel.homehealthmonitoring.MainActivity;

public class UploadData extends ActionBarActivity {
	private Socket client;
	private PrintWriter printwriter;
	private String serverIP = "127.0.0.1";// hard coded server IP or try 10.0.2.2 as stated in the android docs 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_data);
		final EditText HR = (EditText)findViewById(R.id.HeartRate);
		final EditText BP = (EditText)findViewById(R.id.BloodPressure);
		final EditText ECG = (EditText)findViewById(R.id.Electrocardiography);
        Button b2 = (Button)findViewById(R.id.button2);
        Intent intent = getIntent();
        final String username = intent.getStringExtra(MainActivity.USERNAME);
        final String pass = intent.getStringExtra(MainActivity.PASS);
        final String upload_page = "uploadData"; 
        b2.setOnClickListener(
        		new OnClickListener() {
        			public void onClick(View v) {
        				String hr= HR.getText().toString();
        				String bp= BP.getText().toString();
        				String ecg= ECG.getText().toString();
        				
        				//Toast.makeText(getBaseContext(), "Welcome "+username+"!", Toast.LENGTH_SHORT).show();
        				//Toast.makeText(getBaseContext(), "Uploading Data", Toast.LENGTH_SHORT).show();
        				try {
        					HttpResponse response;
        					HttpClient httpclient = new DefaultHttpClient();
        					HttpPost request = new HttpPost();
        					URI website1 = new URI("http://128.4.213.180:8080/api/uploadData");
        					URI website = new URI("http://128.4.213.180:8080/api/authenticate");
        					
        					
        					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        			        nameValuePairs.add(new BasicNameValuePair("username", username));
        			        nameValuePairs.add(new BasicNameValuePair("password", pass));
        			        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        			        request.setURI(website);
        			        response = httpclient.execute(request);
        					
        					List<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>(2);
        			        nameValuePairs1.add(new BasicNameValuePair("dataName", "Heart Rate"));
        			        nameValuePairs1.add(new BasicNameValuePair("dataValue", hr));
        			        request.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
        			        
        			        
        			        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        			        String line = in.readLine();
        			        //Toast.makeText(getBaseContext(), "Server Says: "+line, Toast.LENGTH_LONG).show();
        			        if(line.contains("true"))
    						{
        			        	HR.setText("");
        						BP.setText("");
        						ECG.setText("");
            			        request.setURI(website1);
            			        response = httpclient.execute(request);
            			        Toast.makeText(getBaseContext(), "Uploading!", Toast.LENGTH_SHORT).show();
            			        BufferedReader in2 = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            			        line = in2.readLine();
            			        if(line.contains("true"))
            			        {
            			        	Toast.makeText(getBaseContext(), "Upload Successful!", Toast.LENGTH_LONG).show();
            			        }
        			        }
        			        //else send an email
    						
        					/*
        					client = new Socket(serverIP,8088);
        					printwriter = new PrintWriter(client.getOutputStream(),true);
        					printwriter.write(hr);
        					printwriter.flush();
        					printwriter.close();
        					client.close();
        					Toast.makeText(getBaseContext(), "Upload Successfully!", Toast.LENGTH_LONG).show();
        					*/
        				}
        				catch(IOException e){
        					e.printStackTrace();
        					Toast.makeText(getBaseContext(), "Connection Error! (IOException)", Toast.LENGTH_LONG).show();
        				} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(getBaseContext(), "Connection Error! (URISyntaxException)", Toast.LENGTH_LONG).show();
						}        					
        	}
		});
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_upload_data,
					container, false);
			return rootView;
		}
	}

}
