package com.example.appbanthietbidientu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaispAdapter extends BaseAdapter {
        List<Loaisp> loaispList;
        Context context;

    public LoaispAdapter(List<Loaisp> loaispList, Context context) {
        this.loaispList = loaispList;
        this.context = context;
    }

        @Override
        public int getCount() {
            return loaispList.size();
        }

        @Override
        public Object getItem(int i) {
            return loaispList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class ViewHolder{
            TextView tenloaisp;
            ImageView hinhanhloaisp;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder=null;
            if(view == null){
                viewHolder=new ViewHolder();
                LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=layoutInflater.inflate(R.layout.dong_layout_loaisp,null);
                viewHolder.tenloaisp=view.findViewById(R.id.textviewloaisp);
                viewHolder.hinhanhloaisp=view.findViewById(R.id.imageloaisp);
                view.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            Loaisp loaisp= loaispList.get(i);
            viewHolder.tenloaisp.setText(loaisp.getTenloaisp());
            Picasso.with(context).load(loaisp.getHinhanhloaisp()).into(viewHolder.hinhanhloaisp);

            return view;
        }
    }