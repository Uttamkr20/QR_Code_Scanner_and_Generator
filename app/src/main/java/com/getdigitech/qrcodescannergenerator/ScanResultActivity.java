package com.getdigitech.qrcodescannergenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ScanResultActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    TextView resultTv;
    Button copyBtn, shareBtn, scanAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        getSupportActionBar().setTitle("Scan Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        resultTv = findViewById(R.id.scan_result_tv);
        copyBtn = findViewById(R.id.copy_btn);
        shareBtn = findViewById(R.id.share_btn);
        scanAgainBtn = findViewById(R.id.again_scanBtn);


        //        Mobile Admob Ads Initilization
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Initilise for Interestial Ads
        mInterstitialAd = new InterstitialAd(this);
        String interstitial_Ad = getString(R.string.interstitial_full_screen);
        mInterstitialAd.setAdUnitId(interstitial_Ad);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        Intent intent = getIntent();
        final String res = intent.getStringExtra("res");
        resultTv.setText(res);

        scanAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                assert res != null;
                ClipData data = ClipData.newPlainText("result", res);
                manager.setPrimaryClip(data);
                Toast.makeText(getApplicationContext(), "Copied Text", Toast.LENGTH_SHORT).show();

                //Calling Interstitial Ads
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, res);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

                Toast.makeText(ScanResultActivity.this, "Sharing", Toast.LENGTH_SHORT).show();

            }




        });


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
    }
}