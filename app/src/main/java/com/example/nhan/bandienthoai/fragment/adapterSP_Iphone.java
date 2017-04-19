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
        ImageView imgDanhGia1,imgDanhGia2,imgDanhGia3,imgDanhGia4,imgDanhGia5;
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
           // cell.tvMieuTa = (TextView) view.findViewById(R.id.textView_MieuTa);
            cell.imgDanhGia1 = (ImageView) view.findViewById(R.id.imageView_Star1);
            cell.imgDanhGia2 = (ImageView) view.findViewById(R.id.imageView_Star2);
            cell.imgDanhGia3 = (ImageView) view.findViewById(R.id.imageView_Star3);
            cell.imgDanhGia4 = (ImageView) view.findViewById(R.id.imageView_Star4);
            cell.imgDanhGia5 = (ImageView) view.findViewById(R.id.imageView_Star5);
            view.setTag(cell);
        }else{
            cell = (View_Cell) view.getTag();
        }
        String AnhSanPham = list.get(i).getAnhSP().toString();
        byte[] mangHinh = Base64.decode(AnhSanPham, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(mangHinh,0,mangHinh.length);
        cell.imgAnhSP.setImageBitmap(bmp);
        cell.tvTen.setText(list.get(i).getTenSP().toString());
        cell.tvSoTien.setText(list.get(i).getSotienSP().toString() + " VND");
      //  cell.tvMieuTa.setText(list.get(i).getMieutaSP().toString());
        String danhgia = list.get(i).getDanhgia().toString();
        if(danhgia.equals("1")){
            cell.imgDanhGia1.setImageResource(R.drawable.star1);
            cell.imgDanhGia2.setImageResource(R.drawable.star0);
            cell.imgDanhGia3.setImageResource(R.drawable.star0);
            cell.imgDanhGia4.setImageResource(R.drawable.star0);
            cell.imgDanhGia5.setImageResource(R.drawable.star0);
        }else if(danhgia.equals("2")){
            cell.imgDanhGia1.setImageResource(R.drawable.star1);
            cell.imgDanhGia2.setImageResource(R.drawable.star1);
            cell.imgDanhGia3.setImageResource(R.drawable.star0);
            cell.imgDanhGia4.setImageResource(R.drawable.star0);
            cell.imgDanhGia5.setImageResource(R.drawable.star0);
        }else if(danhgia.equals("3")){
            cell.imgDanhGia1.setImageResource(R.drawable.star1);
            cell.imgDanhGia2.setImageResource(R.drawable.star1);
            cell.imgDanhGia3.setImageResource(R.drawable.star1);
            cell.imgDanhGia4.setImageResource(R.drawable.star0);
            cell.imgDanhGia5.setImageResource(R.drawable.star0);
        }else if(danhgia.equals("4")){
            cell.imgDanhGia1.setImageResource(R.drawable.star1);
            cell.imgDanhGia2.setImageResource(R.drawable.star1);
            cell.imgDanhGia3.setImageResource(R.drawable.star1);
            cell.imgDanhGia4.setImageResource(R.drawable.star1);
            cell.imgDanhGia5.setImageResource(R.drawable.star0);
        }else if(danhgia.equals("5")){
            cell.imgDanhGia1.setImageResource(R.drawable.star1);
            cell.imgDanhGia2.setImageResource(R.drawable.star1);
            cell.imgDanhGia3.setImageResource(R.drawable.star1);
            cell.imgDanhGia4.setImageResource(R.drawable.star1);
            cell.imgDanhGia5.setImageResource(R.drawable.star1);
        }
        return view;
    }
}
