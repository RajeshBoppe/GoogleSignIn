package com.sample.googlesign;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dumadu on 2/13/2018.
 */

public class ContactsActivity extends Activity {
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> arraylist;

    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        lv = (ListView) findViewById(R.id.text_list_view);

        new GetContacts().execute();

    }

    class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ContactsActivity.this, "pre Execute", Toast.LENGTH_SHORT).show();
            pDialog = new ProgressDialog(ContactsActivity.this);
            pDialog.setMessage("Loading..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("test", "doInBackground: ");
            arraylist = new ArrayList<HashMap<String, String>>();
            HttpHandler sh = new HttpHandler();
            String jsonData = sh.makeServiceCall("https://api.androidhive.info/contacts/");
            if (jsonData != null) {
                try {
                    JSONObject contactsData = new JSONObject(jsonData);
                    JSONArray jsonContactsArray = contactsData.getJSONArray("contacts");
                    for (int i = 0; i < jsonContactsArray.length(); i++) {
//                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject jsonContactObject = jsonContactsArray.getJSONObject(i);
//                        Log.e("test", "Id: " + jsonContactObject.getString("id"));
                        Log.e("test", "Name: " + jsonContactObject.getString("name"));
//                        Log.e("test", "Email: " + jsonContactObject.getString("email"));
//                        Log.e("test", "Address: " + jsonContactObject.getString("address"));
//                        Log.e("test", "Gender: " + jsonContactObject.getString("gender"));
//                        JSONObject jsonPhoneObj = jsonContactObject.getJSONObject("phone");
//                        Log.e("test", "MOBILE: "+jsonPhoneObj.getString("mobile"));
//                        Log.e("test", "HOME: "+jsonPhoneObj.getString("home"));
//                        Log.e("test", "OFFICE: "+jsonPhoneObj.getString("office"));
                        Log.e("test", "-----------------------------------------------------------");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ContactsActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(ContactsActivity.this, "onProgressupdate", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ContactsActivity.this, "post Execution", Toast.LENGTH_SHORT).show();
            pDialog.cancel();

            ListAdapter adapter = new SimpleAdapter(
                    ContactsActivity.this, contacts,
                    R.layout.activity_listview2, new String[]{"name"});

            lv.setAdapter(adapter);
        }
    }
}
