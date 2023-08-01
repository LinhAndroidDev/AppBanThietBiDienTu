package com.example.appbanthietbidientu.ultil;

import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.response.SignInResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiSp {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiSp apiDevice = new Retrofit.Builder()
            .baseUrl("http://192.168.1.5/appbanthietbi/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiSp.class);

    @Multipart
    @POST("register.php")
    Call<SignInResponse> confirmRegister(@Part(Const.KEY_EMAIL) RequestBody email,
                                         @Part(Const.KEY_PASSWORD) RequestBody password);

    @Multipart
    @POST("login.php")
    Call<SignInResponse> confirmLogin(@Part(Const.KEY_EMAIL) RequestBody email,
                                         @Part(Const.KEY_PASSWORD) RequestBody password);

    @GET("getspmoinhat.php")
    Call<List<Sanpham>> getListsp();

    @GET("getdienthoai.php")
    Call<List<Sanpham>> getlistDienThoai();

    @GET("getlaptop.php")
    Call<List<Sanpham>> getlistLapTop();

    @Multipart
    @POST("thongtinkhachhang.php")
    Call<SignInResponse> getThongTinKhachHang(@Part(Const.KEY_USERNAME) RequestBody tenkhachhang,
                                          @Part(Const.KEY_SDT) RequestBody sodienthoai,
                                          @Part(Const.KEY_EMAIL) RequestBody email);
}
