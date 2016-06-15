package com.translap.translatr;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;






import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.memetix.mst.detect.Detect;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class Home extends Fragment implements TextToSpeech.OnInitListener {

	ArrayList<String> result =new ArrayList<>();
	private TextView txtSpeechInput;
	private TextView txttranslated;
	private ImageButton btnSpeak;
	private final int REQ_CODE_SPEECH_INPUT = 100;
private Button convert;
	private Button listen;
	private TextToSpeech tts;
	static String mode;
	InterstitialAd mInterstitialAd;
	private String name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		// Retrieving the currently selected item number

		// Creating view correspoding to the fragment
		View v = inflater.inflate(R.layout.as, container, false);
//		setRetainInstance(true);
		Locale locale = new Locale("hi");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		v.getResources().updateConfiguration(config,
				v.getResources().getDisplayMetrics());
		tts = new TextToSpeech(getActivity(), this);
		mInterstitialAd = new InterstitialAd(getActivity());
		mInterstitialAd.setAdUnitId("ca-app-pub-1266294618278246/2323954017");

		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				requestNewInterstitial();
			}
		});

		requestNewInterstitial();


		txtSpeechInput = (TextView)v. findViewById(R.id.txtSpeechInput);
		txttranslated = (TextView)v. findViewById(R.id.translatedtext);

		btnSpeak = (ImageButton)v. findViewById(R.id.btnSpeak);
		convert = (Button)v. findViewById(R.id.convert);
		listen = (Button)v. findViewById(R.id.listen);

		// hide the action bar

		btnSpeak.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {
				result.clear();

				try{
					ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
					if(connectionDetector.isConnectingToInternet()==true) {
						promptSpeechInput();

					}
					else{
						Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
					}
				}catch (Exception ex){

					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
							"mailto","007saurabhgoyal@gmail.com", null));
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
					emailIntent.putExtra(Intent.EXTRA_TEXT,   "speak" + ex);
					startActivity(Intent.createChooser(emailIntent, "Send email..."));
				}

			}
		});
		convert.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

//				GoogleAPI.setHttpReferrer("https://www.googleapis.com/language/translate/v2?");
//				GoogleAPI.setKey("AIzaSyBVFP72GDR1MIZ2ZSnLbKAdlZe0-fcxv3A");
//
//				try {
//					String result = Translate.DEFAULT.execute("hello", Language.ENGLISH, Language.FRENCH);
//				} catch (GoogleAPIException e) {
//					e.printStackTrace();
//				}
//				if (mInterstitialAd.isLoaded()) {
//					mInterstitialAd.show();
//				}

				mode="convert2";
				try{
					ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
					if(connectionDetector.isConnectingToInternet()==true) {


							new bStuff().execute("https://www.googleapis.com/language/translate/v2?");


					}
					else{
						Toast.makeText(getActivity(), "Connect to the internet", Toast.LENGTH_LONG).show();
					}
				}catch (Exception ex){

					Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
							"mailto","007saurabhgoyal@gmail.com", null));
					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
					emailIntent.putExtra(Intent.EXTRA_TEXT,   "convert" + ex);
					startActivity(Intent.createChooser(emailIntent, "Send email..."));			}
			//	new bStuff().execute("https://www.googleapis.com/language/translate/v2?q=%E0%A4%AE%E0%A4%A8&target=eng&source=hi&key=AIzaSyBVFP72GDR1MIZ2ZSnLbKAdlZe0-fcxv3A");


			}







		});
		listen.setOnClickListener(new View.OnClickListener() {






			@Override
			public void onClick(View v) {
				mode="listen2";
//				if (mInterstitialAd.isLoaded()) {
//					mInterstitialAd.show();
//				}


					try {
						ConnectionDetector connectionDetector = new ConnectionDetector(getActivity());
						if (connectionDetector.isConnectingToInternet() == true) {
							if(txttranslated.getText().toString().equals(null) && result.get(0).equals(null)) {


								new bStuff().execute("https://www.googleapis.com/language/translate/v2?");
							}
							else{
								SpeakText(txttranslated.getText().toString());
							}

						} else {
							Toast.makeText(getActivity(), "Connect to the internet", Toast.LENGTH_LONG).show();
						}
					} catch (Exception ex) {





						Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
								"mailto","007saurabhgoyal@gmail.com", null));
						emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
						emailIntent.putExtra(Intent.EXTRA_TEXT,   "listen" + ex);
						startActivity(Intent.createChooser(emailIntent, "Send email..."));
					}

//				class bgStuff extends AsyncTask<Void, Void, Void> {
//
//					String translatedText = "";
//
//
//					@Override
//					protected void onPreExecute() {
//						// TODO Auto-generated method stub
//						super.onPreExecute();
//					}
//
//					@Override
//					protected Void doInBackground(Void... params) {
//						// TODO Auto-generated method stub
//						try {
//	//						translatedText = translateText();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							translatedText = "Unable to translate";
//						}
//						return null;
//					}
//
//					@Override
//					protected void onPostExecute(Void result) {
//						// TODO Auto-generated method stub
//
//
//						SpeakText(translatedText);
//
//
//
//
//
//
//						super.onPostExecute(result);
//					}
//
//				}
//
//				new bgStuff().execute();






			}
		});
return v;
	}
	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
				.build();

		mInterstitialAd.loadAd(adRequest);
	}
	public void SpeakText(String text){
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi");
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			getParentFragment().startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getActivity(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

//	@Override
//	public void onBackPressed() {
//
//		if (mInterstitialAd.isLoaded()) {
//			mInterstitialAd.show();
//		}
//
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//		builder.setTitle("Confirm");
//		builder.setMessage("Are you sure You want to exit from the Translapp?");
//
//		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//			public void onClick(DialogInterface dialog, int which) {
//				// Do nothing but close the dialog
//
//				System.exit(0);
//			}
//
//		});
//
//		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// Do nothing
//				dialog.dismiss();
//			}
//		});
//
//		AlertDialog alert = builder.create();
//		alert.show();
//
//
//	}

	/**
	 * Receiving speech input
	 *
	 * */

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {

				//speakOut("Ich");
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case REQ_CODE_SPEECH_INPUT: {

			if (resultCode == -1 && null != data) {

			result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				txtSpeechInput.setText(result.get(0));
				mode="speak2";

				try{
					ConnectionDetector connectionDetector=new ConnectionDetector(getActivity());
					if(connectionDetector.isConnectingToInternet()==true) {
						new bStuff().execute("https://www.googleapis.com/language/translate/v2?");

					}
					else{
						Toast.makeText(getActivity(),"Connect to the internet",Toast.LENGTH_LONG).show();
					}
				}catch (Exception ex){
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/html");
					intent.putExtra(Intent.EXTRA_EMAIL, "pranjal@houssup.com");
					intent.putExtra(Intent.EXTRA_CC, "007saurabhgoyal@gmail.com");
					intent.putExtra(Intent.EXTRA_SUBJECT, "Exception ");
					intent.putExtra(Intent.EXTRA_TEXT, "btnconvert"+ex);

					startActivity(Intent.createChooser(intent, "Send Email"));				}


				Log.v("translated", "text" + result.get(0));
			}








			break;
		}

		}
	}
	//public String translateText() throws Exception{
		// Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
//		Translate.setClientId("TranslateSaurabh");//CHANGE THIS
//		Translate.setClientSecret("YV3BSMkVKBD+8URCzidsdvaNn00AfncJRSXUJoloUKY="); //CHANGE THIS
//
//		String translatedText = Translate.execute(txtSpeechInput.getText().toString(), Language.HINDI,Language.ENGLISH);



//		return translatedText;
	//}
	class bStuff extends AsyncTask<String, Void, Void> {

		String translatedText = "";
		private final HttpClient Client = new DefaultHttpClient();
		private String Content;


	private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(getActivity());
		String data = "";




		@Override
		protected void onPreExecute() {


				Dialog.setMessage("Please wait..");
				Dialog.show();

			try {
				data +=URLEncoder.encode("q", "UTF-8") + "=" +URLEncoder.encode(txtSpeechInput.getText().toString(), "UTF-8");

				//data ="q="+txtSpeechInput.getText().toString();
				data+="&target=en&source=hi&key=AIzaSyBVFP72GDR1MIZ2ZSnLbKAdlZe0-fcxv3A";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(String... urls) {
			// TODO Auto-generated method stub
			try {
				//	translatedText = translateText();
				BufferedReader reader = null;

				// Send data
				try {

					// Defined URL  where to send data
					URL url = new URL(urls[0]+data );

					// Send POST data request

					URLConnection conn = url.openConnection();
					conn.setDoOutput(false);

//					OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//					wr.write(data);
//					wr.flush();
//
					// Get the server response
					conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
					conn.setRequestProperty("Accept","*/*");

					reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = null;

					// Read Server Response
					while ((line = reader.readLine()) != null) {
						// Append server response in string
						sb.append(line + "");
					}

					// Append Server Response To Content String
					Content = sb.toString();
					JSONObject movie = new JSONObject(Content);
					JSONObject  jsonRootObject = movie.getJSONObject("data");
					JSONArray jsonArray = jsonRootObject.optJSONArray("translations");
					for (int k=0;k<jsonArray.length();k++){
						JSONObject jsonObject = jsonArray.getJSONObject(k);
					 name = jsonObject.optString("translatedText").toString();
					}

				} catch (Exception ex) {
					Error = ex.getMessage();
				} finally {
					try {

						reader.close();
					} catch (Exception ex) {
					}
				}
				return null;



			} catch (Exception e) {
				// TODO Auto-generated catch block
				translatedText = "Unable to translate";
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Dialog.dismiss();
			if (Error != null) {
//							Utilities.toast(getActivity(),Error);
			} else {
				if(mode.equals("listen2")){
					name=name.replace("&#39;","'");

					SpeakText(name);
				}
			 if(mode.equals("convert2")){
				 name=name.replace("&#39;","'");
				 txttranslated.setText(name);

			 }
				if(mode.equals("speak2")){
					name=name.replace("&#39;", "'");
					txttranslated.setText(name);

				}

//							Intent navigationIntent=new Intent(getActivity(),MainActivity.class);
//							startActivity(navigationIntent);


			}

		}

	}
	
}
