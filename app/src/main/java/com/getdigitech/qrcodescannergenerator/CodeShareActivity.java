package com.getdigitech.qrcodescannergenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CodeShareActivity extends AppCompatActivity {
    ImageView imageView;
    Button qrShareBtn;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AdView mAdView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_share);


        imageView = findViewById(R.id.qr_image);
        qrShareBtn = findViewById(R.id.qr_share_btn);

        //        Mobile Admob Ads Initilization
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        qrShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(CodeShareActivity.this, "Sharing", Toast.LENGTH_SHORT).show();
            }
        });

    }
}