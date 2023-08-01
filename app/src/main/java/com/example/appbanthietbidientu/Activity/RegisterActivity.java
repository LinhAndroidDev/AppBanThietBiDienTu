package com.example.appbanthietbidientu.Activity;

import android.annotation.SuppressLint;
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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView register;
    EditText account, password, passwordRepeat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        register = findViewById(R.id.register);
        account = findViewById(R.id.textAccountRegister);
        password = findViewById(R.id.textPasswordRegister);
        passwordRepeat = findViewById(R.id.textRepeatPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strAccount = account.getText().toString();
                String strPassword = password.getText().toString();
                String strPasswordRepeat = passwordRepeat.getText().toString();

                if (strAccount.isEmpty() || strPassword.isEmpty() || strPasswordRepeat.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(strAccount).matches()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                } else if (strPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                } else if (!strPassword.equals(strPasswordRepeat)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), strAccount);
                    RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);

                    ApiSp.apiDevice.confirmRegister(requestBodyEmail, requestBodyPassword)
                            .enqueue(new Callback<SignInResponse>() {
                                @Override
                                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                                    if (response.body() != null) {
                                        switch (response.body().statusCode) {
                                            case 200:
                                                Toast.makeText(getApplicationContext(), "Đăng kí tài khoản thành công", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                                break;
                                            case 400:
                                                Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                                break;
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
    }
}