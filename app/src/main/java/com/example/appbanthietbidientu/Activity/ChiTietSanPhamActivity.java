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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.example.appbanthietbidientu.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarChitietsp;
    ImageView anhChitietsp;
    TextView tenChiTiet,giaChiTiet,motaChiTiet;
    Spinner spinner;
    TextView themVaoGioHang;
    Sanpham sanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        overridePendingTransition(R.anim.animation_enter_right,R.anim.animation_exit_left);

        Khaibao();
        Actionbar();
        getInforsp();
        CatchEvenSpinner();
        EvenClickThemGioHang();
    }

    private void EvenClickThemGioHang() {
        themVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.gioHangArrayList.size() > 0){
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exits=false;

                    for (int i=0;i < MainActivity.gioHangArrayList.size();i++) {
                        if (MainActivity.gioHangArrayList.get(i).getIdsp() == sanpham.getId()) {
                            MainActivity.gioHangArrayList.get(i).setSoluongsp(MainActivity.gioHangArrayList.get(i).getSoluongsp() + sl);
                            if(MainActivity.gioHangArrayList.get(i).getSoluongsp() >= 10){
                                MainActivity.gioHangArrayList.get(i).setSoluongsp(10);
                            }
                            MainActivity.gioHangArrayList.get(i).setGiasp((long) sanpham.getGiasanpham() * MainActivity.gioHangArrayList.get(i).getSoluongsp());
                            exits=true;
                        }
                    }
                    if(!exits){
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi= (long) soluong * sanpham.getGiasanpham();
                        MainActivity.gioHangArrayList.add(new GioHang(sanpham.getId(), sanpham.getTensanpham(), giaMoi, sanpham.getHinhanhsanpham(), soluong));
                    }
                }else{
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi= (long) soluong * sanpham.getGiasanpham();
                    MainActivity.gioHangArrayList.add(new GioHang(sanpham.getId(), sanpham.getTensanpham(), giaMoi, sanpham.getHinhanhsanpham(), soluong));
                }
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
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
        if (item.getItemId() == R.id.iconGioHang) {
            Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchEvenSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> integerArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(integerArrayAdapter);
    }

    private void getInforsp() {
        sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");

        tenChiTiet.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        giaChiTiet.setText(String.format("Giá: %s₫", decimalFormat.format(sanpham.getGiasanpham())));
        motaChiTiet.setText(sanpham.getMotasanpham());
        Typeface regular = ResourcesCompat.getFont(ChiTietSanPhamActivity.this,R.font.svn_gilroy_regular);
        motaChiTiet.setTypeface(regular);
        Picasso.with(getApplicationContext()).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.errorimage)
                .into(anhChitietsp);
    }

    private void Actionbar() {
        setSupportActionBar(toolbarChitietsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitietsp.setNavigationIcon(R.drawable.ic_action_back);
        toolbarChitietsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Khaibao() {
        toolbarChitietsp=findViewById(R.id.ToolbarChitietSanPham);
        anhChitietsp=findViewById(R.id.imageviewChiTietSanPham);
        tenChiTiet=findViewById(R.id.textviewTenChiTietSanPham);
        giaChiTiet=findViewById(R.id.textviewGiaChiTietSanPham);
        motaChiTiet=findViewById(R.id.textviewMoTaChiTietSanPham);
        spinner=findViewById(R.id.spinnerChiTietSanPham);
        themVaoGioHang=findViewById(R.id.ThemGioHangChiTietSanPham);
    }

    @Override
    public void finish() {
        super.finish();
    }
}