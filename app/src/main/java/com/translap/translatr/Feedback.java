package com.translap.translatr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by saurabh goyal on 2/7/2016.
 */
public class Feedback extends ActionBarActivity {

    Toolbar toolbar;
    InterstitialAd mInterstitialAd;
    EditText t;
    public static final String UPLOAD_URL = "http://www.creativecolors.in/PhotoUpload/image.php";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    private Button buttonChoose;
    private Bitmap bitmap;

    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   toolbar= (Toolbar) findViewById(R.id.app_bar);
    //    setSupportActionBar(toolbar);
         t=(EditText)findViewById(R.id.edittext);
        Button subButton =(Button)findViewById(R.id.submit);
        buttonChoose = (Button) findViewById(R.id.upload);
        buttonChoose.setOnClickListener(this);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class UploadImage extends AsyncTask<Bitmap,Void,String>{

                    ProgressDialog loading;
                    RequestHandler rh = new RequestHandler();

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(MainActivity.this, "Uploading...", null,true,true);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Bitmap... params) {
                        Bitmap bitmap = params[0];
                        String uploadImage = getStringImage(bitmap);

                        HashMap<String,String> data = new HashMap<>();

                        data.put(UPLOAD_KEY, uploadImage);
                        String result = rh.sendPostRequest(UPLOAD_URL,data);

                        return result;
                    }
                }

                UploadImage ui = new UploadImage();
                ui.execute(bitmap);














//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//
//
//                intent.setData(Uri.parse("mailto:"));
//                intent.setType("text/html");
//                intent.putExtra(Intent.EXTRA_EMAIL, "007surabhgoyal@gmail.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback ");
//                intent.putExtra(Intent.EXTRA_TEXT,  t.getText().toString());
//
//                startActivity(Intent.createChooser(intent, "Send Email"));
               // Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                 //       "mailto","appzstertech2016@gmail.com", null));
               // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
               // emailIntent.putExtra(Intent.EXTRA_TEXT,  t.getText().toString());
               // startActivity(Intent.createChooser(emailIntent, "Send email..."));

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
        super.onBackPressed();


//


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }




    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            //    imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     //   Intent k=new Intent(getApplicationContext(),MainActivity.class);
       // startActivity(k);
    }
}
