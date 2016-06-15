package com.translap.translatr;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Category extends AppCompatActivity  {
    TextView tvMessage;
    //Creating a List of superheroes
    private List<EnCategory> listSuperHeroes;
    TextView btn;
    DownloadManager dManager;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    long did;
    InterstitialAd mInterstitialAd;

    private Spinner spinner;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    //An ArrayList for Spinner Items
    private ArrayList<String> students;

    //JSON Array
    private JSONArray result;
    private List<TestDetail> movieList = new ArrayList<>();
    //TextViews to display details
    private TextView textViewName;
    private TextView textViewCourse;
    private TextView textViewSession;
    Button b1;
    private ProgressDialog pd;
    private Toolbar toolbar;
    Database database;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);


        database=new Database(this);

        database.getWritableDatabase();

        //    setTitle(null);
        //   Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        //  setSupportActionBar(topToolBar);
        //  topToolBar.setLogo(R.drawable.);
        //   topToolBar.setLogoDescription(getResources().getString(R.string.action_settings));
        students = new ArrayList<String>();


        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Initializing Views
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1266294618278246/2323954017");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();


            }
        });

        requestNewInterstitial();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listSuperHeroes = new ArrayList<>();

        //Calling method to get data
        getData1();

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    //This method will get data from the web api
    private void getData1() {
     //   EnCategory jdetail = new EnCategory();
        listSuperHeroes=database.fetchCat();

        adapter = new CategoryAdapter(getApplicationContext(),listSuperHeroes);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    /*    pd = new ProgressDialog(Category.this) {
            @Override
            public void onBackPressed() {
                pd.dismiss();
            }};
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        if (isInternetOn()) {
            //Showing a progress dialog
            //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

            //Creating a json array request
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL3,
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
            requestQueue.add(jsonArrayRequest);
        }

        else{


            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }*/


    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

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

    //This method will parse json data
//    private void parseData(List<EnCategory> array){
//        for(int i = 0; i<array.length(); i++) {
//         //   db = new SQLiteHandler(getApplicationContext());
//            EnCategory jdetail = new EnCategory();
//            JSONObject json = null;
//            String id;
//            String category;
//            try {
//                json = array.getJSONObject(i);
//                //     superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL))
//                // ;
//                    id=json.getString("id").toString();
//                category = json.getString("category").toString();
//               jdetail.setId(json.getString(Config.ID));
//                jdetail.setCategory(json.getString("category"));
//          //      db.addCategory(id,category);
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            listSuperHeroes.add(jdetail);
//
//        }
//   //     arrayList=db.fetchData();
//        //Finally initializing our adapter
//      //  adapter = new CategoryAdapter(listSuperHeroes);
//
//        //Adding adapter to recyclerview
//        recyclerView.setAdapter(adapter);
//
//
//
//    }




    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        Intent k=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(k);

//


    }







}