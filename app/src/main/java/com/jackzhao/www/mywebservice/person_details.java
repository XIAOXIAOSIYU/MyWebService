package com.jackzhao.www.mywebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class person_details extends AppCompatActivity {

    TextView label_person_id, lb_person_special, lb_person_description, lb_person_title;
    ImageView img_person_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        img_person_photo = (ImageView) findViewById(R.id.img_person_photo);
        label_person_id = (TextView) findViewById(R.id.lb_person_name);
        lb_person_special = (TextView) findViewById(R.id.lb_person_special);
        lb_person_description = (TextView) findViewById(R.id.lb_person_description);
        lb_person_title = (TextView) findViewById(R.id.lb_person_title);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            if (data.containsKey("PERSON_ID")) {
                int person_id = Integer.parseInt(data.getString("PERSON_ID"));
                new AsyncCallPersonDetails().execute(person_id);
            }
        }
    }

    public void doGoPersonList(View view) {
        Intent intent = new Intent(person_details.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public class AsyncCallPersonDetails extends AsyncTask<Integer, Void, Person> {

        private final ProgressDialog dialog = new ProgressDialog(person_details.this);

        @Override
        protected Person doInBackground(Integer... integers) {
            CallSoap soap = new CallSoap();
            Person person = soap.GetPersonDetails(integers[0]);
            return person;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("數據加載中......");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Person person) {
            super.onPostExecute(person);

            new GetImageAsyncTaskHelper(img_person_photo).execute(person.getPerson_Photo1());
            label_person_id.setText(person.getPerson_Name_Cn());
            lb_person_special.setText(person.getPerson_Special_Description());
            lb_person_description.setText(Html.fromHtml(person.getPerson_Description()));
            lb_person_title.setText(person.getPerson_Title());

            dialog.dismiss();
        }
    }
}
