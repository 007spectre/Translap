package com.translap.translatr;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewFragment;
import android.widget.ImageView;
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
import com.pushbots.push.Pushbots;
import com.squareup.picasso.Picasso;
import com.translap.translatr.Utilities.Utility;
import com.translap.translatr.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private RecyclerView recyclerView;
    private VivzAdapter vivzAdapter;
    private RecyclerView recyclerView2;
    private VivzAdapter vivzAdapter2;
    TextView pr;
    private  boolean mUserLearnedDrawer;
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private  boolean mSavedInstanceState;
    InterstitialAd mInterstitialAd;
    SQLiteHandler db;
    private ProgressDialog pd;
    Database database;
    private SessionManager session;
    String strr;
    String textEmail;
    String userImageUrl;
    ImageView imageProfile;
    ImageView imageProfile1;
    public static final String KEY_EMAIL = "email";
    private static final String REGISTER_URL = "http://www.creativecolors.in/service/gmaillogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageProfile=(ImageView)findViewById(R.id.image);
        database=new Database(this);
        database.getWritableDatabase();
        db = new SQLiteHandler(getApplicationContext());
        db.getWritableDatabase();
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            imageProfile1=(ImageView)findViewById(R.id.image1);
            imageProfile1.setVisibility(View.VISIBLE);


            String strr=  db.getUserDetails1();
            if(strr.equals(null)) {
                imageProfile1.setImageDrawable(getResources().getDrawable(R.drawable.icon));


            }else{
                ConnectionDetector k=new ConnectionDetector(getApplicationContext());
                if(k.isConnectingToInternet()==true) {
                    Picasso.with(this)
                            .load(strr)
                            .into(imageProfile1);
                }else{
                    imageProfile1.setImageDrawable(getResources().getDrawable(R.drawable.icon));

                }

            }


        }

        else {
            imageProfile.setVisibility(View.VISIBLE);

            Intent intent = getIntent();
            textEmail = intent.getStringExtra("email_id");
            System.out.println(textEmail);

            try {
                System.out.println("On Home Page***"
                        + AbstractGetNameTask.GOOGLE_USER_DATA);
                JSONObject profileData = new JSONObject(
                        AbstractGetNameTask.GOOGLE_USER_DATA);

                if (profileData.has("picture")) {
                    userImageUrl = profileData.getString("picture");
                    new MainActivity.GetImageFromUrl().execute(userImageUrl);
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            db.addUser(textEmail);
            db.addUser1(userImageUrl);

            session.setLogin(true);
            insertemail();


        }


        containerView=findViewById(R.id.drawer);
        recyclerView= (RecyclerView) findViewById(R.id.drawerlist);
        recyclerView2= (RecyclerView) findViewById(R.id.drawerlist2);
        db = new SQLiteHandler(getApplicationContext());
        String str=  db.getUserDetails();


        TextView name=(TextView)findViewById(R.id.name);
        TextView email =(TextView)findViewById(R.id.profileid);
        email.setText(str);
        SharedPreferences prefs = getSharedPreferences("Translap", MODE_PRIVATE);

            name.setText(prefs.getString("name", null));




        //    Picasso.with(this)
        //         .load(strr)
        //        .into(imageProfile);

        vivzAdapter=new VivzAdapter(getApplicationContext(),getData());
        vivzAdapter2=new VivzAdapter(getApplicationContext(),getData2());
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().setPushEnabled(true);
        recyclerView.setAdapter(vivzAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView2.setAdapter(vivzAdapter2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        vivzAdapter.setClickListener(new VivzAdapter.ClickListener() {
            @Override
            public void itemclicked(View view, int position) {

                if (position == 0) {
                    mDrawerLayout.closeDrawers();
                    Intent t = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(t);
                }
                if (position == 1) {
                    mDrawerLayout.closeDrawers();

                    alertBox();

                }
//                if (position == 2) {
//                    mDrawerLayout.closeDrawers();
//
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.MOBILE_NO)));
//                    startActivity(callIntent);
//
//                }

            }
        });
        vivzAdapter2.setClickListener(new VivzAdapter.ClickListener() {
            @Override
            public void itemclicked(View view, int position) {

                if (position == 1) {
                    mDrawerLayout.closeDrawers();

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.APPLINK));
                    startActivity(Intent.createChooser(i, getResources().getString(R.string.LBL_SHARE_VIA)));

                }
                if (position == 0) {
                    try {
                        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
                        if (connectionDetector.isConnectingToInternet() == true) {
                            navigateTp("https://play.google.com/store/apps/details?id=com.gilo.com.translap.translatr");

                        } else {
                            Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/html");
                        intent.putExtra(Intent.EXTRA_EMAIL, "pranjal@houssup.com");
                        intent.putExtra(Intent.EXTRA_CC, "007saurabhgoyal@gmail.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Exception ");
                        intent.putExtra(Intent.EXTRA_TEXT, "btnspeak" + ex);

                        startActivity(Intent.createChooser(intent, "Send Email"));
                    }


                }
                if (position == 2) {
                    Intent k = new Intent(getApplicationContext(), Feedback.class);
                    startActivity(k);
                }


            }
        });
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1266294618278246/2323954017");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();


        Utility utility=new Utility();
        mUserLearnedDrawer= Boolean.valueOf(utility.readFromSharedPref(this,KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mSavedInstanceState=true;
        }

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){

                    mUserLearnedDrawer=true;
                    Utility utility=new Utility();
                    utility.writeToSharedPref(getApplicationContext(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                invalidateOptionsMenu();
            }


        };

        if(!mUserLearnedDrawer && !mSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {

                mDrawerToggle.syncState();

            }
        });
        navigateHome();


        //  getData1();
        //  getsub();
        //  getqus();
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
    void navigateTp(String url) {
        com.translap.translatr.WebViewFragment webViewFragment=new com.translap.translatr.WebViewFragment();
        webViewFragment.url=url;
        Intent k=new Intent(this, com.translap.translatr.WebViewFragment.class);
        startActivity(k);


    }











    public static List<Information> getData(){
        List<Information> data=new ArrayList<>();
        int icon[]={R.drawable.homeicon,R.drawable.aboutus};
        String []titles={"Home","About Us"};
        for(int i=0;i<titles.length && i<icon.length;i++){

            Information current =new Information();
            current.itemId=icon[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;

    }
    public static List<Information> getData2(){
        List<Information> data=new ArrayList<>();
        int icon[]={R.drawable.star,R.drawable.share,R.drawable.feedback};
        String []titles={"Rate Us 5 Star","Share","Feedback"};
        for(int i=0;i<titles.length && i<icon.length;i++){

            Information current =new Information();
            current.itemId=icon[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
//          .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    private void navigateHome() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        TabLayout rFragment = new TabLayout();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(R.id.content_frame, rFragment);
//        ft.commit();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Home rFragment = new Home();
//       FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(R.id.content_frame, rFragment);
//        ft.commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        TabLayout rFragment = new TabLayout();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();







    }

    @Override
    public void onBackPressed() {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure You want to exit from the Translap?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // Do nothing but close the dialog
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());

            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

//    @Override
//    public void itemclicked(View view, int position) {
////        if(position ==0){
////            mDrawerLayout.closeDrawers();
////            navigateHome();
////        }
//
//        if (position == 0) {
//            mDrawerLayout.closeDrawers();
//
//            alertBox();
//
//        } if (position == 1) {
//            mDrawerLayout.closeDrawers();
//
//            Intent callIntent = new Intent(Intent.ACTION_CALL);
//            callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.MOBILE_NO)));
//            startActivity(callIntent);
//
//        } if (position == 2) {
//            mDrawerLayout.closeDrawers();
//
//            Intent i = new Intent(Intent.ACTION_SEND);
//            i.setType("text/plain");
//            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
//            i.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.APPLINK));
//            startActivity(Intent.createChooser(i, getResources().getString(R.string.LBL_SHARE_VIA)));
//
//        }
//        if(position ==3){
//            try{
//                ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
//                if(connectionDetector.isConnectingToInternet()==true) {
//                    navigateTp("https://play.google.com/store/apps/details?id=com.gilo.com.translap.translatr");
//
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();
//                }
//            }catch (Exception ex){
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/html");
//                intent.putExtra(Intent.EXTRA_EMAIL, "pranjal@houssup.com");
//                intent.putExtra(Intent.EXTRA_CC, "007saurabhgoyal@gmail.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Exception ");
//                intent.putExtra(Intent.EXTRA_TEXT, "btnspeak" + ex);
//
//                startActivity(Intent.createChooser(intent, "Send Email"));
//            }
//
//
//        }
//        if (position == 4) {
//            Intent k=new Intent(this,Feedback.class);
//            startActivity(k);
//        }
//
//    }



    protected void alertBox() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        // builder.setMessage("We deliver food when you think you might not get it."+"\n"+"\nDeveloped By:CoreApps Lab"+"\nWe build powerful,feature intensive,client oriented,user friendly Web & Smartphone applications"+"\n\nReach us at:\n"+getResources().getString(R.string.companyAddress)+"\n"+getResources().getString(R.string.companyContactPerson)+"\n"+getResources().getString(R.string.companyEmailAddress)+"\n"+getResources().getString(R.string.companyMobNo)+"\n")
//        builder.setMessage("Translap is basically a translator which translate one language to another by just listening to the audio of the user and converts it into another audio format."+ "\n" + "\nDeveloped By:Translap"  + getResources().getString(R.string.companyContactPerson) + "\n" + getResources().getString(R.string.companyEmailAddress) + "\n" + getResources().getString(R.string.companyMobNo) + "\n")
//                .setCancelable(true)
//                .setTitle(getString(R.string.app_name));
//        AlertDialog alert = builder.create();
//        alert.show();
        Intent l=new Intent(this,About.class);
        startActivity(l);
    }


    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {


            //   Picasso.with(this)
            //      .load(strr)
            //   .into(imageView);

            if(result==null){

                imageProfile1.setImageDrawable(getResources().getDrawable(R.drawable.icon));



            }else {
                imageProfile.setImageBitmap(result);
            }
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }




    private void insertemail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(KEY_EMAIL, textEmail);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }



}

