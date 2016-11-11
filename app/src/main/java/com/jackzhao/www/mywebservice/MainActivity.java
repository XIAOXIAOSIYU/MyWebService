package com.jackzhao.www.mywebservice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    Button button;
    ListView lv_persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_submit);
        lv_persons = (ListView) findViewById(R.id.lv_persons);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncCallSoap().execute();
            }
        });
    }

    public class AsyncCallSoap extends AsyncTask<String, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("數據加載中......");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            CallSoap call = new CallSoap();
            String response = call.GetPersons();
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            try {
                JSONArray jsonArray = new JSONArray(response);
                final PersonAdapter adapter = new PersonAdapter(MainActivity.this, jsonArray);
                lv_persons.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            dialog.dismiss();
        }
    }
}
