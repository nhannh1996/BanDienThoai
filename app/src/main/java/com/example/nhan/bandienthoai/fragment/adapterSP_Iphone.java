package com.example.nhan.bandienthoai.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhan.bandienthoai.R;
import com.example.nhan.bandienthoai.SanPham;

import java.util.ArrayList;

/**
 * Created by Nhan on 4/3/2017.
 */

public class adapterSP_Iphone extends BaseAdapter {
    ArrayList<SanPham> list;

    Context context;
    public adapterSP_Iphone(Context c,ArrayList<SanPham>list){
        this.list = list;
        this.context = c;
    };
    class View_Cell{
        ImageView imgAnhSP;
        TextView tvTen,tvSoTien,tvMieuTa;
    };
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View_Cell cell;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        if(view == null) {
            cell = new View_Cell();
            view = inflater.inflate(R.layout.layout_dienthoai,null);
            cell.imgAnhSP = (ImageView) view.findViewById(R.id.imageView_AnhSanPham);
            cell.tvTen = (TextView) view.findViewById(R.id.textView_TenSanPham);
            cell.tvSoTien = (TextView) view.findViewById(R.id.textView_GiaTienSP);
            cell.tvMieuTa = (TextView) view.findViewById(R.id.textView_MieuTa);
            view.setTag(cell);
        }else{
            cell = (View_Cell) view.getTag();
        }
        String AnhSanPham = list.get(i).getAnhSP().toString();
        byte[] mangHinh = Base64.decode(AnhSanPham, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(mangHinh,0,mangHinh.length);
        cell.imgAnhSP.setImageBitmap(bmp);
        cell.tvTen.setText(list.get(i).getTenSP().toString());
        cell.tvSoTien.setText(list.get(i).getSotienSP().toString());
        cell.tvMieuTa.setText(list.get(i).getMieutaSP().toString());
        return view;
    }
}
