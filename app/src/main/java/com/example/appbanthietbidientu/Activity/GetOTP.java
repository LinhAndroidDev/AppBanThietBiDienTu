package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.appbanthietbidientu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class GetOTP extends AppCompatActivity {
    String id = "";
    String phoneNumber = "";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ToggleButton nhapOtp;
    EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        nhapOtp = findViewById(R.id.NhapOTP);
        otp = findViewById(R.id.otp);

//        GetDataIntent();


        nhapOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String strNhapOtp = otp.getText().toString().trim();
//                onClickNhapOtp(strNhapOtp);
                Intent intent = new Intent(GetOTP.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GetDataIntent() {
        id = getIntent().getStringExtra("ID").toString();
        phoneNumber = getIntent().getStringExtra("phone number").toString();
    }

    private void onClickNhapOtp(String strNhapOtp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, strNhapOtp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = task.getResult().getUser();
                            if(user != null){
                                goToMainActivity(user.getPhoneNumber());
                            }
                        }else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(GetOTP.this,"Error Sign",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToMainActivity(String phoneNumber) {
        Intent intent = new Intent(GetOTP.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}