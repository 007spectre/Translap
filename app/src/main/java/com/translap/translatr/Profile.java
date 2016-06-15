package com.translap.translatr;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ccs7pc on 2/29/2016.
 */
public class Profile extends Fragment {
    TextView t1;
    String email;
    private SQLiteHandler db;
    TextView t2;
    private    String    countrr;
    String[] ids = new String[20];
    String count;
    String testnet;
    TextView tt;
    String ttst;
    ImageView imageProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile, container, false);
  //      setRetainInstance(true);
        t1 = (TextView) v.findViewById(R.id.email);
        t2 = (TextView) v.findViewById(R.id.rank);
        tt = (TextView) v.findViewById(R.id.test);
        db = new SQLiteHandler(getActivity());
        String str = db.getUserDetails();
        imageProfile=(ImageView)v.findViewById(R.id.image1);

        t1.setText("Email id: "+str);
        String strr=  db.getUserDetails1();
        if(strr.equals(null)) {
            imageProfile.setImageDrawable(getResources().getDrawable(R.drawable.icon));


        }else{
            ConnectionDetector k=new ConnectionDetector(getActivity());
            if(k.isConnectingToInternet()==true) {
                Picasso.with(getActivity())
                        .load(strr)
                        .into(imageProfile);
            }else{
                imageProfile.setImageDrawable(getResources().getDrawable(R.drawable.icon));

            }

        }


        String str1 = "http://www.creativecolors.in/appzster/selectcountuser.php?id=" + str;
//Showing a progress dialog

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(str1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog

                        //calling method to parse json array
                            parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);

        return v;


    }


    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                //     superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                count=      json.getString("count");






                //    superHero.setRank(json.getInt(Config.TAG_RANK));
                //    superHero.setRealName(json.getString(Config.TAG_REAL_NAME));
                //    superHero.setCreatedBy(json.getString(Config.TAG_CREATED_BY));
                //    superHero.setFirstAppearance(json.getString(Config.TAG_FIRST_APPEARANCE));






            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if(count.equals(null)){
            t2.setText(0);
        }
        else {
            t2.setText(count);
        }

        //Finally initializing our adapter


        //Adding adapter to recyclerview





    }
}