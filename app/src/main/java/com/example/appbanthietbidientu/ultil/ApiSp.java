package com.example.appbanthietbidientu.ultil;

import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.model.Sanphammoi;
import com.example.appbanthietbidientu.model.User;
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
import retrofit2.http.Query;

public interface ApiSp {
    Gson gson=new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiSp apiSp=new Retrofit.Builder()
            .baseUrl("http://10.90.119.32/server/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiSp.class);

    @GET("getspmoinhat.php")
    Call<List<Sanpham>> getListsp();

    @GET("getsp.php")
    Call<List<Sanpham>> getlistDienThoai(@Query("page") int page);

    @GET("getlaptop.php")
    Call<List<Sanpham>> getlistLapTop();

    @Multipart
    @POST("thongtinkhachhang.php")
    Call<List<User>> getThongTinKhachHang(@Part(Const.KEY_USERNAME) RequestBody tenkhachhang,
                                          @Part(Const.KEY_SDT) RequestBody sodienthoai,
                                          @Part(Const.KEY_EMAIL) RequestBody email);
}
