package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appbanthietbidientu.Adapter.LoaispAdapter;
import com.example.appbanthietbidientu.Adapter.SanphammoiAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Loaisp;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.model.Sanphammoi;
import com.example.appbanthietbidientu.ultil.ApiSp;
import com.example.appbanthietbidientu.ultil.CheckConnect;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ViewFlipper viewFlipper;
    ProgressBar loadSpMoi,loadMain;
    RecyclerView listSanPhamMoi;
    ListView listManHinhChinh;
    ArrayList<Loaisp> loaispArrayList;
    ArrayList<Sanpham> sanphamArrayList;
    public static ArrayList<GioHang> gioHangArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.animation_scale_enter_left,R.anim.animation_scale_exit_right);

        Khaibao();

        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            ActionBar();
            GetDuLieusp();
            ActionViewFlip();
            ClickItemManHinhChinh();
        }else{
            Toast.makeText(getApplicationContext(),"Error connect Internet",Toast.LENGTH_LONG).show();
            finish();
        }

        Animation anim_in_right= AnimationUtils.loadAnimation(this,R.anim.anim_in_right);
        Animation anim_out_right=AnimationUtils.loadAnimation(this,R.anim.anim_out_right);

        viewFlipper.setInAnimation(anim_in_right);
        viewFlipper.setOutAnimation(anim_out_right);
    }

    private void ClickItemManHinhChinh() {
        listManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.close();
                        break;
                    case 1:
                        Intent dienthoai = new Intent(MainActivity.this, DienThoaiActivity.class);
                        dienthoai.putExtra("idloaisanpham", loaispArrayList.get(i).getId());
                        startActivity(dienthoai);
                        break;
                    case 2:
                        Intent laptop = new Intent(MainActivity.this, LaptopActivity.class);
                        laptop.putExtra("idloaisanpham", loaispArrayList.get(i).getId());
                        startActivity(laptop);
                        break;
                    case 3:
                        Intent lienhe = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(lienhe);
                        break;
                    case 4:
                        Intent thongtin=new Intent(MainActivity.this,ThongTinActivity.class);
                        startActivity(thongtin);
                        break;
                }
            }
        });
    }

    private void GetDuLieusp() {
        loadMain.setVisibility(View.VISIBLE);
        ApiSp.apiSp.getListsp("media","04505275-acd8-47cb-9bdb-5885d1fbaeff").enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                sanphamArrayList= (ArrayList<Sanpham>) response.body();
                SanphammoiAdapter sanphamAdapter=new SanphammoiAdapter(sanphamArrayList, getApplicationContext());
                listSanPhamMoi.setAdapter(sanphamAdapter);

                //Set sản phẩm mới
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                listSanPhamMoi.setLayoutManager(gridLayoutManager);
                loadMain.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                loadMain.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void ActionViewFlip() {
        ArrayList<String> mangQuangCao=new ArrayList<>();
        mangQuangCao.add("https://image-us.24h.com.vn/upload/3-2020/images/2020-07-26/Top-dien-thoai-co-camera-sau-hinh-chu-L-chup-anh-sieu-dinh-1-1595762816-463-width660height440.jpg");
        mangQuangCao.add("https://tapchicongthuong.vn/images/19/9/20/toc-tien-oppo.jpg");
        mangQuangCao.add("https://cdn.sforum.vn/sforum/wp-content/uploads/2020/08/OPPO-F17-1.jpg");
        mangQuangCao.add("https://quanlykho.vn/wp-content/uploads/2022/08/img_63083bb914d24.png");

        for(int i=0;i<mangQuangCao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(MainActivity.this).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
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

    private void Khaibao() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.Navigation);
        loadSpMoi = findViewById(R.id.loadSpMoi);
        loadMain = findViewById(R.id.loadMain);
        viewFlipper = findViewById(R.id.viewflipper);
        listManHinhChinh = findViewById(R.id.listManhinhchinh);
        listSanPhamMoi = findViewById(R.id.listSanphammoi);
        sanphamArrayList = new ArrayList<>();

        loaispArrayList=new ArrayList<>();
        loaispArrayList.add(0,new Loaisp(0,"Trang Chính","https://icons.iconarchive.com/icons/custom-icon-design/pretty-office-4/256/home-icon.png"));
        loaispArrayList.add(1,new Loaisp(0,"Điện Thoại","https://cdn01.dienmaycholon.vn/filewebdmclnew/public//userupload/images/dien-thoai-Samsung-Galaxy-S10-Plus-1.jpg"));
        loaispArrayList.add(2,new Loaisp(0,"Laptop","https://cdn.tgdd.vn/Files/2017/01/19/939425/cach-cai-hinh-nen-may-tinh-khong-bi-mo_1280x720-800-resize.jpg"));
        loaispArrayList.add(3,new Loaisp(0,"Liên Hệ","https://invoice.fast.com.vn/images/contact-us-icon.png"));
        loaispArrayList.add(4,new Loaisp(0,"Thông Tin","https://e7.pngegg.com/pngimages/330/43/png-clipart-computer-icons-businessperson-3d-character-icon-material-cartoon-character-camera-icon.png"));

        LoaispAdapter loaispAdapter=new LoaispAdapter(loaispArrayList,MainActivity.this);
        listManHinhChinh.setAdapter(loaispAdapter);

        if(gioHangArrayList != null){

        }else {
            gioHangArrayList=new ArrayList<>();
        }
    }
}