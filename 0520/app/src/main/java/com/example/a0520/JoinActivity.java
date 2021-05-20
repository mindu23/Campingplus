package com.example.a0520;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JoinActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "117.16.46.95";
    private static String TAG = "test";
    private static String Insert = "/insert.php";

    private EditText mEditTextName;
    private EditText mEditTextId;
    private EditText mEditTextPassword;
    private EditText mEditTextEmail;
    private TextView mTextViewResult;

    private TextView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("회원가입");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);

        mEditTextName = (EditText)findViewById(R.id.editText_join_name);
        mEditTextId = (EditText)findViewById(R.id.editText_join_id);
        mEditTextPassword = (EditText)findViewById(R.id.editText_join_password);
        mEditTextEmail = (EditText)findViewById(R.id.editText_join_email);

        //mTextViewResult = (TextView)findViewById(R.id.textView_join_result);

//        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        Button buttonDelete = (Button)findViewById(R.id.button_join_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button buttonInsert = (Button)findViewById(R.id.button_join_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String id = mEditTextId.getText().toString();
                String password = mEditTextPassword.getText().toString();
                String email = mEditTextEmail.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + Insert, name,id,password,email);

                mEditTextName.setText("");
                mEditTextId.setText("");
                mEditTextPassword.setText("");
                mEditTextEmail.setText("");
            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(JoinActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[1];
            String id= (String)params[2];
            String password= (String)params[3];
            String email= (String)params[4];

            String serverURL = (String)params[0];

            String postParameters = "name=" + name + "&id=" + id + "&password=" + password + "&email=" + email;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //reponseStatusCode == 200
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}