package com.example.appbanthietbidientu.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.User;
import com.example.appbanthietbidientu.ultil.ApiSp;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinKhachHang extends AppCompatActivity {
    Toolbar toolbarThongTinKhachHang;
    EditText NhapTenKhachHang,NhapSoDienThoai,NhapEmail;
    Button XacNhan,Huy;
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
                progressDialog.show();
                CountDownTimer countDownTimer=new CountDownTimer(3000,3000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                        Intent intent = new Intent(ThongTinKhachHang.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"Cảm ơn bạn đã mua hàng",Toast.LENGTH_LONG).show();
                    }
                }.start();

                String strTen=NhapTenKhachHang.getText().toString().trim();
                String strSDT=NhapSoDienThoai.getText().toString().trim();
                String strEmail=NhapEmail.getText().toString().trim();

                RequestBody requestBodyTenKhachHang=RequestBody.create(MediaType.parse("multipart/form-data"),strTen);
                RequestBody requestBodySDT=RequestBody.create(MediaType.parse("multipart/form-data"),strSDT);
                RequestBody requestBodyEmail=RequestBody.create(MediaType.parse("multipart/form-data"),strEmail);

                ApiSp.apiSp.getThongTinKhachHang(requestBodyTenKhachHang,requestBodySDT,requestBodyEmail).enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        User user= (User) response.body();

                        if(user != null){
                            NhapTenKhachHang.setText(user.getTenkhachhang());
                            NhapSoDienThoai.setText(user.getSodienthoai()+"");
                            NhapEmail.setText(user.getEmail());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
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
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.animation_scale_enter_left,R.anim.animation_scale_exit_right);
    }
}