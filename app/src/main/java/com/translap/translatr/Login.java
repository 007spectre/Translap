package com.translap.translatr;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.translap.translatr.MainActivity;
import com.translap.translatr.R;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {
    private ImageButton signInButton;
    //Signing Options

    private GoogleSignInOptions gso;
    private String email;
    private static final int PROFILE_PIC_SIZE = 400;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    public static final String KEY_EMAIL = "email";
    private static final String REGISTER_URL = "http://www.creativecolors.in/service/gmaillogin.php";
    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    private TextView textViewName;
    private TextView textViewEmail;
    private EditText name,phone;
    private SQLiteHandler db;
    public static String nm;
    TextView textView;
    private SessionManager session;
    String profilelink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_login);
        ImageView imageView=(ImageView)findViewById(R.id.imagelogin);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon));


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        textViewName = (EditText) findViewById(R.id.editTextUsername);
        textView = (EditText) findViewById(R.id.editTextPassword);

        signInButton = (ImageButton) findViewById(R.id.buttonLogin);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        // SQLite database handler

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //Setting onclick listener to signing button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
                    if(connectionDetector.isConnectingToInternet()==true) {



                        nm=textViewName.getText().toString();
                        Validation k=new Validation(getApplicationContext());
                        if(k.mobileNo(textView.getText().toString())==true  && nm!=null  ) {

                            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

                            //Starting intent for result
                            startActivityForResult(signInIntent, RC_SIGN_IN);
                        }


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "007saurabhgoyal@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT,   "convert" + ex);
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));			}
                //	new bStuff().execute("https://www.googleapis.com/language/translate/v2?q=%E0%A4%AE%E0%A4%A8&target=eng&source=hi&key=AIzaSyBVFP72GDR1MIZ2ZSnLbKAdlZe0-fcxv3A");




            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }






    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            email = acct.getEmail();
            profilelink=acct.getPhotoUrl().toString();

            //Displaying name and email
            //     textViewName.setText(acct.getDisplayName());
            //   textViewEmail.setText(acct.getEmail());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                                 db.addUser(email);
                            db.addUser1(profilelink);
                            session.setLogin(true);



                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();

                    params.put(KEY_EMAIL, email);


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);













        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }




    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
