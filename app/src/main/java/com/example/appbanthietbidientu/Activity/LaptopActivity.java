package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanthietbidientu.Adapter.SanphamAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.ultil.ApiSp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLapTop;
    ListView listViewLapTop;
    ArrayList<Sanpham> laptopArrayList;
    SanphamAdapter laptopAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        Khaibao();
        ActionBar();
        getData();

        listViewLapTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",laptopArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        ApiSp.apiSp.getlistLapTop().enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                laptopArrayList= (ArrayList<Sanpham>) response.body();
                laptopAdapter=new SanphamAdapter(laptopArrayList,getApplicationContext());
                listViewLapTop.setAdapter(laptopAdapter);
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(toolbarLapTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLapTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        toolbarLapTop=findViewById(R.id.ToolbarLapTop);
        listViewLapTop=findViewById(R.id.listviewLaptop);

        laptopArrayList=new ArrayList<>();
    }
}