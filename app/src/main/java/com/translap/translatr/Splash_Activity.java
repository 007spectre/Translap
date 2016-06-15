package com.translap.translatr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Splash_Activity extends Activity {
    ImageView imageView;
    private SessionManager session;
    Database database;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=new Database(this);
        database.getWritableDatabase();


        session = new SessionManager(getApplicationContext());


            setContentView(R.layout.activity_splash_);
            imageView = (ImageView) findViewById(R.id.splash_image);




    if(isInternetOn()) {
        try {
        getData1();
        getsub();
        getqus();}
        catch (Exception ex) {





            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "007saurabhgoyal@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            emailIntent.putExtra(Intent.EXTRA_TEXT,   "listen" + ex);
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }

    }


    else{

        if (session.isLoggedIn()) {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);


        }



        Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
    }







        }











    private void getqus() {



        //Showing a progress dialog
        //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL1,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog

                        if (isInternetOn()) {

                            //calling method to parse json array
                            parseData4(response);

                            if (session.isLoggedIn()) {

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);


                            } else {
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                            }

                        }

                        else
                        {

                            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();

                        }
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


    private void parseData4(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            Question jdetail2 = new Question();
            ArrayList<Question> productList2 =new ArrayList<Question>();
            JSONObject json = null;

            try {
                json = array.getJSONObject(i);
//                que=json.getString("question");
//                anss1=json.getString("ans1");
//                anss2=json.getString("ans2");
//                anss3=json.getString("ans3");
//                anss4=json.getString("ans4");
                // superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                jdetail2.setId(json.getString(Config.ID1));
                jdetail2.setCategory(json.getString(Config.QCUESTION1));
                jdetail2.setTest(json.getString(Config.TEST1));
                jdetail2.setQuestion(json.getString(Config.QUESTION1));
                jdetail2.setAns1(json.getString(Config.ANS1));
                jdetail2.setAns2(json.getString(Config.ANS2));
                jdetail2.setAns3(json.getString(Config.ANS3));
                jdetail2.setAns4(json.getString(Config.ANS4));
                jdetail2.setCans(json.getString(Config.CANS));


                //    superHero.setRank(json.getInt(Config.TAG_RANK));
                //    superHero.setRealName(json.getString(Config.TAG_REAL_NAME));
                //    superHero.setCreatedBy(json.getString(Config.TAG_CREATED_BY));
                //    superHero.setFirstAppearance(json.getString(Config.TAG_FIRST_APPEARANCE));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            productList2.add(jdetail2);
            database.insertQus(productList2);
            //  listSuperHeroes.add(jdetail);

        }




    }
    private void getsub() {

        String testurl="http://www.creativecolors.in/appzster/testlist.php";
        if (isInternetOn()) {
            //Showing a progress dialog
            //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

            //Creating a json array request
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(testurl,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Dismissing progress dialog
                            //  pd.dismiss();

                            //calling method to parse json array
                            parseData1(response);
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
        }


    }



    private void parseData1(JSONArray array){
        String id;
        String category;
        String test;
        String question;
        String coin;
        ArrayList<TestDetail> productList1 =new ArrayList<TestDetail>();

        for(int i = 0; i<array.length(); i++) {
            TestDetail jdetail1 = new TestDetail();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                //     superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL));
                jdetail1.setId(json.getString(Config.ID));
                jdetail1.setCategory(json.getString(Config.TCAT));
                jdetail1.setTest(json.getString(Config.TEST));
                jdetail1.setQuestion(json.getString(Config.QUESTION));
                jdetail1.setCoin(json.getString(Config.COIN));
                id=json.getString(Config.ID);
                category=json.getString(Config.ID);
                test=json.getString(Config.TEST);
                question=json.getString(Config.QUESTION);
                coin=json.getString(Config.COIN);
                productList1.add(jdetail1);

                database.insertSub(productList1);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Finally initializing our adapter


        //Adding adapter to recyclerview




    }


    private void getData1() {

        pd = new ProgressDialog(Splash_Activity.this) {
            @Override
            public void onBackPressed() {
                pd.dismiss();
            }};

        if (isInternetOn()) {
            //Showing a progress dialog
            //  final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);

            //Creating a json array request
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL3,
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Adding request to the queue
            requestQueue.add(jsonArrayRequest);
        }

        else{


            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }


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
    private void parseData(JSONArray array){

        ArrayList<EnCategory> productList =new ArrayList<EnCategory>();

        JSONObject json = null;
        String id;
        String category;

        for(int i = 0; i<array.length(); i++) {

            EnCategory jdetail = new EnCategory();

            try {
                json = array.getJSONObject(i);
                //     superHero.setImageUrl(json.getString(Config.TAG_IMAGE_URL))
                // ;

                jdetail.setId(json.getString(Config.ID));
                jdetail.setCategory(json.getString("category"));
                productList.add(jdetail);
                database.insertCat(productList);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        //     arrayList=db.fetchData();
        //Finally initializing our adapter




    }


}
