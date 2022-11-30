package com.example.appbanthietbidientu.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbanthietbidientu.Adapter.SanphamAdapter;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.example.appbanthietbidientu.ultil.ApiSp;
import com.example.appbanthietbidientu.ultil.CheckConnect;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDienThoai;
    ListView listViewDienThoai;
    ArrayList<Sanpham> dienThoaiArrayList;
    SanphamAdapter dienThoaiAdapter;
    View footerView;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;
    mHander mHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        if(CheckConnect.haveNetworkConnected(getApplicationContext())){
            Khaibao();
            ActionBar();
            getData(page);
            LoadmoreData();
        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Error Connect Internet");
        }

        listViewDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",dienThoaiArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void LoadmoreData() {
        listViewDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if(FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitData == false){
                    isLoading =true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int page) {
        ApiSp.apiSp.getlistDienThoai(page).enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                if(response.body() != null && response.body().size() > 2){
                    listViewDienThoai.removeFooterView(footerView);
                    dienThoaiArrayList.addAll(response.body());
                    dienThoaiAdapter = new SanphamAdapter(dienThoaiArrayList, getApplicationContext());
                    listViewDienThoai.setAdapter(dienThoaiAdapter);
                    dienThoaiAdapter.notifyDataSetChanged();
                }else {
                    limitData = true;
                    listViewDienThoai.removeFooterView(footerView);
                    CheckConnect.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(toolbarDienThoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
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
        toolbarDienThoai = findViewById(R.id.ToolbarDienThoai);
        listViewDienThoai = findViewById(R.id.listviewDienThoai);

        dienThoaiArrayList = new ArrayList<>();
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.loading_layout,null);
        mHander=new mHander();
    }

    public class mHander extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewDienThoai.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHander.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message =mHander.obtainMessage(1);
            mHander.sendMessage(message);
            super.run();
        }
    }
}