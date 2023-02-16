package com.example.appbanthietbidientu.ultil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanthietbidientu.R;

public class BaseFunctionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        overridePendingTransition(R.anim.animation_scale_enter_right,R.anim.animation_scale_exit_left);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.animation_scale_enter_left,R.anim.animation_scale_exit_right);
    }
}
