package com.example.appbanthietbidientu.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanthietbidientu.Activity.GioHangActivity;
import com.example.appbanthietbidientu.Activity.MainActivity;
import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @Override
    public int getCount() {
        return gioHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return gioHangList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView txtTenGioHang,txtGiaGioHang,btnvalue;
        ImageView imgGioHang;
        Button btnminus,btnmaximum;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view == null){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_giohang,null);

            viewHolder.txtTenGioHang=view.findViewById(R.id.textviewTenGioHang);
            viewHolder.txtGiaGioHang=view.findViewById(R.id.textviewGiaMonHang);
            viewHolder.imgGioHang=view.findViewById(R.id.imageviewGioHang);
            viewHolder.btnminus=view.findViewById(R.id.buttonminius);
            viewHolder.btnvalue=view.findViewById(R.id.buttonvalue);
            viewHolder.btnmaximum=view.findViewById(R.id.buttonmaximum);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        GioHang gioHang=gioHangList.get(i);
        viewHolder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(gioHang.getGiasp())+"₫");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.errorimage)
                .into(viewHolder.imgGioHang);
        viewHolder.btnvalue.setText(gioHang.getSoluongsp()+"");
        int sl= Integer.parseInt(viewHolder.btnvalue.getText().toString());
        if(sl >= 10){
            viewHolder.btnmaximum.setVisibility(View.INVISIBLE);
        }else if(sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.btnmaximum.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnmaximum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoinhat=Integer.parseInt(finalViewHolder.btnvalue.getText().toString())+1;
                int slHt = MainActivity.gioHangArrayList.get(i).getSoluongsp();
                long giaHt=MainActivity.gioHangArrayList.get(i).getGiasp();
                MainActivity.gioHangArrayList.get(i).setSoluongsp(slMoinhat);
                long giaMoinhat=(slMoinhat * giaHt)/slHt;
                MainActivity.gioHangArrayList.get(i).setGiasp(giaMoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoinhat)+"₫");
                GioHangActivity.EvenUltil();
                if(slMoinhat > 9){
                    finalViewHolder.btnmaximum.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slMoinhat));
                }else {
                    finalViewHolder.btnmaximum.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slMoinhat));
                }
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoinhat=Integer.parseInt(finalViewHolder.btnvalue.getText().toString())-1;
                int slHt = MainActivity.gioHangArrayList.get(i).getSoluongsp();
                long giaHt=MainActivity.gioHangArrayList.get(i).getGiasp();
                MainActivity.gioHangArrayList.get(i).setSoluongsp(slMoinhat);
                long giaMoinhat=(slMoinhat * giaHt)/slHt;
                MainActivity.gioHangArrayList.get(i).setGiasp(giaMoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoinhat)+"₫");
                GioHangActivity.EvenUltil();
                if(slMoinhat < 2){
                    finalViewHolder.btnmaximum.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slMoinhat));
                }else {
                    finalViewHolder.btnmaximum.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slMoinhat));
                }
            }
        });

        return view;
    }
}
