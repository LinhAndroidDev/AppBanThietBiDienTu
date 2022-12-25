package com.example.appbanthietbidientu.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanthietbidientu.R;
import com.example.appbanthietbidientu.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanphamAdapter extends BaseAdapter implements Filterable {
    private List<Sanpham> dienThoaiList,dienThoaioldList;
    Context context;

    public SanphamAdapter(List<Sanpham> dienThoaiList, Context context) {
        this.dienThoaiList = dienThoaiList;
        this.dienThoaioldList = dienThoaiList;
        this.context = context;
    }

    @Override
    public int getCount() {
            return dienThoaiList.size();
    }

    @Override
    public Object getItem(int i) {
        return dienThoaiList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString().toLowerCase();
                if(strSearch.isEmpty()){
                    dienThoaiList = dienThoaioldList;
                }else {
                    List<Sanpham> list = new ArrayList<>();
                    for (Sanpham sanpham : dienThoaioldList){
                        if(sanpham.getTensanpham().toLowerCase().contains(strSearch)){
                            list.add(sanpham);
                        }
                    }
                    dienThoaiList = list;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values = dienThoaiList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dienThoaiList = (List<Sanpham>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder{
        TextView tenDienThoai,giaDienThoai,motaDienThoai;
        ImageView hinhAnhDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=new ViewHolder();
        if(view == null){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_sanpham,null);
            viewHolder.tenDienThoai=view.findViewById(R.id.tenDienThoai);
            viewHolder.giaDienThoai=view.findViewById(R.id.giaDienThoai);
            viewHolder.hinhAnhDienThoai=view.findViewById(R.id.imageviewDienThoai);
            viewHolder.motaDienThoai=view.findViewById(R.id.motaDienThoai);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Sanpham dienThoai=dienThoaiList.get(i);
        viewHolder.tenDienThoai.setText(dienThoai.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.giaDienThoai.setText("Giá: "+decimalFormat.format(dienThoai.getGiasanpham())+"₫");
        viewHolder.motaDienThoai.setMaxLines(2);
        viewHolder.motaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.motaDienThoai.setText(dienThoai.getMotasanpham());
        Picasso.with(context).load(dienThoai.getHinhanhsanpham())
                .placeholder(R.drawable.loadimage)
                .error(R.drawable.errorimage)
                .into(viewHolder.hinhAnhDienThoai);
        return view;
    }
}
