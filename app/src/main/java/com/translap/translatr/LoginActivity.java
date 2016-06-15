package com.translap.translatr;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;

/**
 * @author manish
 *
 */
public class LoginActivity extends Activity {
    Context mContext = LoginActivity.this;
    AccountManager mAccountManager;
    String token;
    int serverCode;
    ImageButton b1;
    public static String nm;
    private TextView textViewName;
    TextView textView;
    ProgressBar pb;

    private ProgressDialog pd;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Splash screen view
        setContentView(R.layout.activity_login2);
        ImageView imageView=(ImageView)findViewById(R.id.imagelogin);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        b1=(ImageButton)findViewById(R.id.login);
        pd = new ProgressDialog(LoginActivity.this);
        textViewName = (EditText) findViewById(R.id.editTextUsername);
        textView = (EditText) findViewById(R.id.editTextPassword);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pd.setMessage("Loading Please Wait");

                pd.show();
                //


                try {
                    ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
                    if (connectionDetector.isConnectingToInternet() == true) {

                        nm = textViewName.getText().toString();

                        Validation k = new Validation(getApplicationContext());
                        if (k.mobileNo(textView.getText().toString()) == true && nm != null) {

                            SharedPreferences.Editor editor = getSharedPreferences("Translap", MODE_PRIVATE).edit();
                            editor.putString("name", nm);
                            editor.commit();

                            syncGoogleAccount();

                        }


                    } else {
                        pd.hide();
                        Toast.makeText(getApplicationContext(), "Connect to the internet", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "007saurabhgoyal@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "convert" + ex);
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            }

    });
             }

             private String[] getAccountNames() {
                 mAccountManager = AccountManager.get(this);
                 Account[] accounts = mAccountManager
                         .getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                 String[] names = new String[accounts.length];
                 for (int i = 0; i < names.length; i++) {
                     names[i] = accounts[i].name;
                 }
                 return names;
             }

             private AbstractGetNameTask getTask(LoginActivity activity, String email,
                                                 String scope) {
                 return new GetNameInForeground(activity, email, scope);

             }

             public void syncGoogleAccount() {
                 if (isNetworkAvailable() == true) {

                     String[] accountarrs = getAccountNames();
                     if (accountarrs.length > 0) {
                         //you can set here account for login
                         getTask(LoginActivity.this, accountarrs[0], SCOPE).execute();


                     } else {
                         Toast.makeText(LoginActivity.this, "No Google Account Sync!",
                                 Toast.LENGTH_SHORT).show();
                     }
                 } else {
                     Toast.makeText(LoginActivity.this, "No Network Service!",
                             Toast.LENGTH_SHORT).show();
                 }
             }

             public boolean isNetworkAvailable() {

                 ConnectivityManager cm = (ConnectivityManager) mContext
                         .getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                 if (networkInfo != null && networkInfo.isConnected()) {
                     Log.e("Network Testing", "***Available***");
                     return true;
                 }
                 Log.e("Network Testing", "***Not Available***");
                 return false;
             }
         }