package com.example.appbanthietbidientu.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.response.SignInResponse;
import com.example.appbanthietbidientu.ultil.ApiSp;
import com.example.appbanthietbidientu.ultil.CheckConnect;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinKhachHang extends AppCompatActivity {
    Toolbar toolbarThongTinKhachHang;
    EditText NhapTenKhachHang,NhapSoDienThoai,NhapEmail;
    TextView XacNhan,Huy,txtThanhToanTien;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        overridePendingTransition(R.anim.animation_scale_enter_right,R.anim.animation_scale_exit_left);

        //set thông báo xác nhận
        progressDialog=new ProgressDialog(ThongTinKhachHang.this);
        progressDialog.setMessage("Please wait ...");

        Khaibao();
        Actionbar();
        ClickXacNhan();
        ClickHuy();
    }

    private void ClickHuy() {
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ClickXacNhan() {
        XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckConnect.haveNetworkConnected(ThongTinKhachHang.this)){
                    if(NhapTenKhachHang.getText().toString().isEmpty() || NhapSoDienThoai.getText().toString().isEmpty() || NhapEmail.getText().toString().isEmpty()){
                        Toast.makeText(ThongTinKhachHang.this,"Bạn chưa nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.show();

                        String strTen=NhapTenKhachHang.getText().toString().trim();
                        String strSDT=NhapSoDienThoai.getText().toString().trim();
                        String strEmail=NhapEmail.getText().toString().trim();

                        RequestBody requestBodyTenKhachHang=RequestBody.create(MediaType.parse("multipart/form-data"),strTen);
                        RequestBody requestBodySDT=RequestBody.create(MediaType.parse("multipart/form-data"),strSDT);
                        RequestBody requestBodyEmail=RequestBody.create(MediaType.parse("multipart/form-data"),strEmail);

                        CountDownTimer cd = new CountDownTimer(3000,3000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                ApiSp.apiDevice.getThongTinKhachHang(requestBodyTenKhachHang,requestBodySDT,requestBodyEmail)
                                        .enqueue(new Callback<SignInResponse>() {
                                            @Override
                                            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                                                if(response.body() != null){
                                                    switch (response.body().statusCode){
                                                        case 200:
                                                            progressDialog.dismiss();
                                                            MainActivity.gioHangArrayList.clear();
                                                            Intent intent = new Intent(ThongTinKhachHang.this,MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            Toast.makeText(getApplicationContext(),"Cảm ơn bạn đã mua hàng",Toast.LENGTH_LONG).show();
                                                            break;
                                                        case 400:
                                                            Toast.makeText(getApplicationContext(), response.body().message, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<SignInResponse> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }.start();

                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error connect Internet",Toast.LENGTH_LONG).show();
                }

              }
        });
    }

    private void Actionbar() {
        setSupportActionBar(toolbarThongTinKhachHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTinKhachHang.setNavigationIcon(R.drawable.ic_action_back);
        toolbarThongTinKhachHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Khaibao() {
        toolbarThongTinKhachHang=findViewById(R.id.ToolbarThongTinKhachHang);
        NhapTenKhachHang = findViewById(R.id.TenKhachHang);
        NhapSoDienThoai = findViewById(R.id.SoDienThoai);
        NhapEmail = findViewById(R.id.EmailKhachHang);
        XacNhan = findViewById(R.id.XacNhan);
        Huy = findViewById(R.id.Huy);
        txtThanhToanTien = findViewById(R.id.txtThanhToanTien);

        String nd = getIntent().getStringExtra("TotalMoney");

        txtThanhToanTien.setText("Thanh toán số tiền: "+nd);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.animation_scale_enter_left,R.anim.animation_scale_exit_right);
    }
}