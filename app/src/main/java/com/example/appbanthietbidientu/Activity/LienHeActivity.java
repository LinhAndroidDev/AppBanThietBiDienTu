package com.example.appbanthietbidientu.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.ultil.BaseFunctionActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class LienHeActivity extends BaseFunctionActivity {
    Toolbar toolbarLienHe;
    TextView txtNameUser,txtPhoneUser,txtEmailUser;
    LinearLayout contact;
    RelativeLayout layoutContact;
    LinearLayout callNow,messNow;
    CardView contactHuy;
    int PERMISSION_CODE = 100;
    RelativeLayout bgCoverLienHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        Khaibao();
        Actionbar();

        if(ContextCompat.checkSelfPermission(LienHeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(LienHeActivity.this,new String[]{ Manifest.permission.CALL_PHONE },PERMISSION_CODE);
        }

        contact();

        Typeface berkSwa = ResourcesCompat.getFont(this,R.font.svn_androgyne);
        txtNameUser.setTypeface(berkSwa);
        txtPhoneUser.setTypeface(berkSwa);
        txtEmailUser.setTypeface(berkSwa);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void contact() {
        BottomSheetBehavior<RelativeLayout> bottomContact =BottomSheetBehavior.from(layoutContact);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomContact.setState(BottomSheetBehavior.STATE_EXPANDED);
                bgCoverLienHe.setVisibility(View.VISIBLE);
            }
        });

        contactHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomContact.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bgCoverLienHe.setVisibility(View.INVISIBLE);
            }
        });

        callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0969601767"));
                startActivity(intent);
            }
        });

        messNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:0969601767"));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.iconGioHang:
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Actionbar() {
        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationIcon(R.drawable.ic_action_back);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Khaibao() {
        toolbarLienHe=findViewById(R.id.ToolbarLienHe);
        txtNameUser = findViewById(R.id.txtNameUser);
        txtPhoneUser = findViewById(R.id.txtPhoneUser);
        txtEmailUser = findViewById(R.id.txtEmailUser);
        contact = findViewById(R.id.contact);
        layoutContact = findViewById(R.id.layoutContact);
        callNow = findViewById(R.id.callNow);
        messNow = findViewById(R.id.messNow);
        contactHuy = findViewById(R.id.contactHuy);
        bgCoverLienHe = findViewById(R.id.bgCoverLienHe);
    }
}