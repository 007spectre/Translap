package com.translap.translatr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.translap.translatr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CountDetail extends AppCompatActivity {
TextView t1;
    String str;
    Button b1;
    String cat;
    String catr;
    String str1;
    String tstt;
    Toolbar toolbar;
    InterstitialAd mInterstitialAd;
    ProgressDialog PD;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_count_detail);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        cat=bundle.getString("value");
        catr=bundle.getString("category");
        str1=bundle.getString("str1");
        tstt=bundle.getString("test");
        t1=(TextView)findViewById(R.id.test1);
        t1.setText(cat);
        b1=(Button)findViewById(R.id.another);
        PD = new ProgressDialog(CountDetail.this);

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1266294618278246/2323954017");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();


            }
        });

        requestNewInterstitial();

        url = "http://www.creativecolors.in/appzster/sselectcount.php";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PD.setMessage("Loading Please Wait");

                PD.show();


                StringRequest stringRequest = new StringRequest ( Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                PD.dismiss();
                                if (isInternetOn()) {


                                    Intent  i=new Intent(getApplicationContext(),TestList.class);
                                    i.putExtra("category", catr);
                                    startActivity(i);
                                    //  Intent i = new Intent(getApplicationContext(), CountDetail.class);
                                    //   i.putExtra("value", str);
                                    //   i.putExtra("test", tst);
                                    //   i.putExtra("category",catr);
                                    //   i.putExtra("str1",str1);
                                    //   startActivity(i);
                                }

                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();

                                }


                                //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("test",tstt);
                        params.put("user",str1);
                        params.put("count",cat);



                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);



                //    Intent  i=new Intent(getApplicationContext(),TestList.class);
                //  i.putExtra("category", catr);
                //  startActivity(i);
            }
        });

   /*     JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog


                        //calling method to parse json array
                    //    parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);*/

    }
    /*  private void parseData(JSONArray array){
          for(int i = 0; i<array.length(); i++) {

              JSONObject json = null;
              try {
                  json = array.getJSONObject(i);
                  //     superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
             str=      json.getString("count");





                  //    superHero.setRank(json.getInt(Config.TAG_RANK));
                  //    superHero.setRealName(json.getString(Config.TAG_REAL_NAME));
                  //    superHero.setCreatedBy(json.getString(Config.TAG_CREATED_BY));
                  //    superHero.setFirstAppearance(json.getString(Config.TAG_FIRST_APPEARANCE));






              } catch (JSONException e) {
                  e.printStackTrace();
              }

          }
          t1.setText(str);

          //Finally initializing our adapter


          //Adding adapter to recyclerview




      }*/
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //    Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        }
        return false;
    }
  private void requestNewInterstitial() {
      AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
              .build();

      mInterstitialAd.loadAd(adRequest);
  }
    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
Intent k=new Intent(getApplicationContext(),Category.class);
        startActivity(k);

//


    }


}
