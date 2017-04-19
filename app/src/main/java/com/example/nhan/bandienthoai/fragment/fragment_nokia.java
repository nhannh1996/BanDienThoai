package com.example.nhan.bandienthoai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.nhan.bandienthoai.R;
import com.example.nhan.bandienthoai.SanPham;
import com.example.nhan.bandienthoai.SuaSanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Nhan on 4/3/2017.
 */

public class fragment_nokia extends Fragment {
    public fragment_nokia(){};

    private GridView listView_SanPham;
    private FirebaseDatabase database;
    private DatabaseReference firebase;
    private adapterSP_Iphone adapterSP_iphone;
    private ArrayList<SanPham> listSP;
    private FirebaseAuth mAuth;
    public static String key;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nokia, container, false);
        database = FirebaseDatabase.getInstance();
        firebase = database.getReference("SanPham");
        mAuth = FirebaseAuth.getInstance();
        listView_SanPham = (GridView) v.findViewById(R.id.listView_SanPham);

        listSP = new ArrayList<>();
        firebase.child("Nokia").child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSP.clear();

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String anhsp = data.child("anhSP").getValue().toString();
                    String ten = data.child("tenSP").getValue().toString();
                    String sotien = data.child("sotienSP").getValue().toString();
                    String mieuta = data.child("mieutaSP").getValue().toString();
                    String danhgia = data.child("danhgia").getValue().toString();
                    SanPham sanPham = new SanPham(anhsp,ten,sotien,mieuta,danhgia);
                    listSP.add(sanPham);
                }
                adapterSP_iphone = new adapterSP_Iphone(getActivity(),listSP);
                listView_SanPham.setAdapter(adapterSP_iphone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        listView_SanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mAuth.getCurrentUser().getUid().equals("GYHe2BPEyaa0L3fPCnPXg0EJfBj2")) {
                    String anh = listSP.get(i).getAnhSP().toString();
                    final String ten = listSP.get(i).getTenSP().toString();
                    String sotien = listSP.get(i).getSotienSP();
                    String mieuta = listSP.get(i).getMieutaSP().toString();
                    Intent intent = new Intent(getContext(), SuaSanPham.class);
                    String danhgia = listSP.get(i).getDanhgia().toString();
                    firebase.child("Nokia").child("DienThoai").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data:dataSnapshot.getChildren()){
                                if(data.child("tenSP").getValue().toString().equals(ten)){
                                    key = data.getKey().toString();
                                    return;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    intent.putExtra("anhSP", anh);
                    intent.putExtra("hangSX","Nokia");
                    intent.putExtra("loaiSP", "DienThoai");
                    intent.putExtra("tenSP", ten);
                    intent.putExtra("sotienSP", sotien);
                    intent.putExtra("mieutaSP", mieuta);
                    intent.putExtra("danhgia", danhgia);
                    startActivityForResult(intent,99);
                }
            }
        });
        return v;
    }

}