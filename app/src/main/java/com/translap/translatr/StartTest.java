package com.translap.translatr;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.translap.translatr.R;
import com.translap.translatr.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StartTest extends AppCompatActivity {
Button b1;
    String que;
    String anss1;
    String anss2;
    String anss3;
    String anss4;
    TextView t1;
    private ProgressDialog pd;
    private RadioGroup radioGroup;
    private RadioButton ans1, ans2, ans3,ans4;
    Toolbar toolbar;
    String cat;
    String id;
    String td;
 static List<Question> listSuperHeroes;
   public static ArrayList<Question>ques=new ArrayList<>();
    Database database;
    TextView category;
    Button rough;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starttest);
        database=new Database(this);


        QuestionFragment.Count=0;
        QuestionFragment.countt=0;

        Bundle bundle = getIntent().getExtras();
        cat=bundle.getString("category");
        td=bundle.getString("testdetail");
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1266294618278246/2323954017");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();


            }
        });

        requestNewInterstitial();
     //   category.setText(cat);
        rough=(Button)findViewById(R.id.rough);
        rough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent k=new Intent(getApplicationContext(),Drawing.class);
                startActivity(k);
            }
        });
        database.getWritableDatabase();
        toolbar= (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listSuperHeroes = new ArrayList<>();
        listSuperHeroes=database.fetchQus(cat,td);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getApplicationContext(),Drawing.class);
//                startActivity(i);
//            }
//        });
        navigateques();



    }
    public void navigateques(){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        QuestionFragment rFragment = new QuestionFragment();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    private void getData1() {






     /*   pd = new ProgressDialog(StartTest.this) {
            @Override
            public void onBackPressed() {
                pd.dismiss();
            }};
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();


            //Showing a progress dialog
            //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

            //Creating a json array request
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL1,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Dismissing progress dialog
                            pd.dismiss();

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
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Adding request to the queue
            requestQueue.add(jsonArrayRequest);*/
        }



    @Override
    public void onBackPressed() {



        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        QuestionFragment.Count=0;
        QuestionFragment.countt=0;
//        Intent k=new Intent(getApplicationContext(),Category.class);
//        startActivity(k);
        Intent  i=new Intent(getApplicationContext(),TestList.class);
        i.putExtra("category", cat);
        startActivity(i);
    }


}
