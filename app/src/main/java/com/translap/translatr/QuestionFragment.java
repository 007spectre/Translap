package com.translap.translatr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saurabh goyal on 3/2/2016.
 */
public class QuestionFragment extends Fragment {
    TextView t1;
    TextView t2;
    private RadioGroup radioGroup;
    private RadioButton ans1, ans2, ans3, ans4;
    Button b1;
    Button qs;
    String selection;
    String qid;
    String ans;
    Database database;
    String cans;
    TextView c;
    static int countt = 0;
    Button que;
 String str;
    public static int Count = 0;
    ProgressDialog PD;
TextView dm;
    TextView correct;
    SQLiteHandler db;
    String str1;
    String tst;
    Context context;
    String  catr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = new Database(getActivity());
        database.getWritableDatabase();
        db = new SQLiteHandler(getActivity());
        // Retrieving the currently selected item number
        PD = new ProgressDialog(getActivity());
        PD.setMessage("Loading.....");
        // Creating view correspoding to the fragment
        View v = inflater.inflate(R.layout.activity_start_test, container, false);
        t1 = (TextView) v.findViewById(R.id.text1);
        correct = (TextView) v.findViewById(R.id.correctAnswer);



        c = (TextView) v.findViewById(R.id.cans);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioSex);
        ans1 = (RadioButton) v.findViewById(R.id.radio1);
        ans2 = (RadioButton) v.findViewById(R.id.radio2);
        ans3 = (RadioButton) v.findViewById(R.id.radio3);
        ans4 = (RadioButton) v.findViewById(R.id.radio4);
        b1 = (Button) v.findViewById(R.id.textView10);
        t1.setText(StartTest.listSuperHeroes.get(Count).getQuestion());

        ans1.setText(StartTest.listSuperHeroes.get(Count).getAns1());
        ans2.setText(StartTest.listSuperHeroes.get(Count).getAns2());
        ans3.setText(StartTest.listSuperHeroes.get(Count).getAns3());
        ans4.setText(StartTest.listSuperHeroes.get(Count).getAns4());
        catr=StartTest.listSuperHeroes.get(Count).getCategory();

        c.setText(StartTest.listSuperHeroes.get(Count).getCans());
     ///   dm.setText(StartTest.listSuperHeroes.get(Count).getTest());
        tst=        StartTest.listSuperHeroes.get(Count).getTest();
       // dm.setText(StartTest.);
        cans = StartTest.listSuperHeroes.get(Count).getCans();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int id = radioGroup.getCheckedRadioButtonId();

                String str1 = cans.toString().trim();
                if(id==-1){
                    Toast.makeText(getActivity(),"Please Choose a Option",Toast.LENGTH_SHORT).show();

                }else {
                    View radioButton = radioGroup.findViewById(id);
                    int radioId = radioGroup.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
//                qid=StartTest.listSuperHeroes.get(StartTest.Count).getId();
                    selection = (String) btn.getText();
                    String str = selection.toString().trim();

                    if (str.equals(str1)) {

                        countt = countt + 1;
                        que.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.GONE);


                    } else {
                        correct.setVisibility(View.VISIBLE);
                        correct.setText("Correct Answer is " + str1);

                        que.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.GONE);


                    }
                }


            }
        });
        que = (Button) v.findViewById(R.id.next);
        que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Count = Count + 1;
                if (Count < (StartTest.listSuperHeroes.size())) {
                    navigateques();

                    //     listSuperHeroes=database.fetchQus(cat);
                } else {
//                  Count = 0;
                    str1=  db.getUserDetails();
                    str = String.valueOf(countt);
                    String url = "http://www.creativecolors.in/appzster/sselectcount.php";

                    Intent i = new Intent(getActivity(), CountDetail.class);
                    i.putExtra("value", str);
                    i.putExtra("test", tst);
                    i.putExtra("category", catr);
                    i.putExtra("str1",str1);
                    startActivity(i);
                   // PD = new ProgressDialog(getActivity());

                 //   PD.setMessage("Loading Please Wait");


//                    str1 = db.getUserDetails();
//                    str = String.valueOf(countt);
//                    String url = "http://www.creativecolors.in/appzster/sselectcount.php";
//
//
//                    try {
//
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                                new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//
//                                        PD.dismiss();
//                                        if (isInternetOn()) {
//                                            Intent i = new Intent(getActivity(), CountDetail.class);
//                                            i.putExtra("value", str);
//                                            startActivity(i);
//                                        } else {
//                                            Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();
//
//                                        }
//
//
//                                        //  Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_LONG).show();
//                                    }
//                                }) {
//                            @Override
//                            protected Map<String, String> getParams() {
//                                Map<String, String> params = new HashMap<String, String>();
//                                params.put("test", tst);
//                                params.put("user", str1);
//                                params.put("count", str);
//
//
//                                return params;
//                            }
//
//                        };
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//                        requestQueue.add(stringRequest);
//
//
//                    }catch (Exception ex) {
//
//
//
//
//
//                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                                "mailto", "007saurabhgoyal@gmail.com", null));
//                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
//                        emailIntent.putExtra(Intent.EXTRA_TEXT,   "listen" + ex);
//                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
//                    }


                }
                }



        });





        return v;
    }



    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection

        ConnectivityManager connec = (ConnectivityManager) getActivity()
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



    private void insertcount() {

    }

    //   Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();




    public void navigateques(){
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        QuestionFragment rFragment = new QuestionFragment();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, rFragment);
        ft.commit();

    }

}
