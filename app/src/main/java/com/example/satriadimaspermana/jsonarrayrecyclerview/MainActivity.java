package com.example.satriadimaspermana.jsonarrayrecyclerview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {

    private String TAG = MainActivity.class.getSimpleName();
    private Button  btnJsonArray;
    private TextView msgResponse;
    private ProgressDialog pDialog;

    private  String jsonRespons;
    private String tag_json_arry = "jarray_req" ;

    private List<RootObject> data = new ArrayList<>();
    private MyAdapter adapter = new MyAdapter();
    private RecyclerView rec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rec  = (RecyclerView) findViewById(R.id.rec);
        rec.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rec.setAdapter(adapter);

        btnJsonArray = (Button) findViewById(R.id.btnJsonArray);
//        msgResponse = (TextView) findViewById(R.id.msgResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnJsonArray.setOnClickListener(this);
    }

    private void makeJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            jsonRespons = "" ;


                            for (int i = 0; i <response.length(); i++){
                                JSONObject person = (JSONObject) response
                                        .get(i);

                                RootObject obj = new Gson().fromJson(person.toString(),RootObject.class);
//                                String name = person.getString("name");
//                                String email = person.getString("email");
//                                JSONObject phone = person
//                                        .getJSONObject("phone");
//                                String home = phone.getString("home");
//                                String mobile = phone.getString("mobile");

                                jsonRespons = obj.cover_url+"\n\n" ;
                                jsonRespons += "Name : " +obj.title+ "\n\n";
                                jsonRespons += "Email : " +obj.description+ "\n\n";

                                data.add(obj);

                            }
//                            msgResponse.setText(jsonRespons);
                            adapter.addAll(data);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(req,tag_json_arry);
    }

    @Override
    public void onClick(View view) {
        makeJsonArryReq();
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

}
