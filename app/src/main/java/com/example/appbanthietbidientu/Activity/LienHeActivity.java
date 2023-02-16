package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.ultil.BaseFunctionActivity;

public class LienHeActivity extends BaseFunctionActivity {
    Toolbar toolbarLienHe;
    TextView txtNameUser,txtPhoneUser,txtEmailUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        Khaibao();
        Actionbar();

        Typeface berkSwa = ResourcesCompat.getFont(this,R.font.svn_androgyne);
        txtNameUser.setTypeface(berkSwa);
        txtPhoneUser.setTypeface(berkSwa);
        txtEmailUser.setTypeface(berkSwa);
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
    }
}