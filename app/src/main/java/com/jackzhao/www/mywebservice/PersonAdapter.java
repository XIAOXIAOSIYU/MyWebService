package com.jackzhao.www.mywebservice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class PersonAdapter extends BaseAdapter {

    JSONArray jsonArray;
    Context context;

    public PersonAdapter(Context _context, JSONArray _json) {
        super();
        jsonArray = _json;
        context = _context;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public Object getItem(int arg0) {
        try {
            return jsonArray.getJSONObject(arg0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView = inflater.inflate(R.layout.activity_person_item, parent, false);

        try {

            JSONArray person = (JSONArray) jsonArray.get(position);
            ImageView image = (ImageView) listView.findViewById(R.id.img_person);
            TextView lb_person_name = (TextView) listView.findViewById(R.id.lb_person_name);
            TextView lb_person_title = (TextView) listView.findViewById(R.id.lb_person_title);
            Button button = (Button) listView.findViewById(R.id.btn_details);

            final String person_id = person.getString(0);
            String person_name = person.getString(1);
            String person_title = person.getString(3);
            lb_person_name.setText(person_name);
            lb_person_title.setText((person_title == "null" || person_title.length() == 0) ? "" : person_title);
            new GetImageAsyncTaskHelper(image).execute(person.getString(2));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, person_details.class);
                    intent.putExtra("PERSON_ID", person_id);
                    context.startActivity(intent);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listView;
    }
}
