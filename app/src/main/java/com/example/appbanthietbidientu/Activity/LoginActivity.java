package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.response.SignInResponse;
import com.example.appbanthietbidientu.ultil.ApiSp;
import com.google.firebase.auth.FirebaseAuth;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView titleLogin, registerAccount;
    EditText edtAccount, edtPassword;
    TextView login;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        titleLogin = findViewById(R.id.titleLogin);
        edtAccount = findViewById(R.id.edtAccount);
        edtPassword = findViewById(R.id.edtPassword);
        login = findViewById(R.id.login);
        registerAccount = findViewById(R.id.register_account);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtAccount.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strEmail.isEmpty() || strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), strEmail);
                    RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);

                    ApiSp.apiDevice.confirmLogin(requestBodyEmail, requestBodyPassword)
                            .enqueue(new Callback<SignInResponse>() {
                                @Override
                                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                                    if (response.body() != null) {
                                        switch (response.body().statusCode) {
                                            case 200:
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("email", strEmail);
                                                startActivity(intent);
                                                finish();
                                                break;
                                            case 401:
                                                Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<SignInResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        registerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}