package com.example.nhan.bandienthoai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TestShow extends AppCompatActivity {
    private ListView listView_SanPham;
   /* private FirebaseDatabase database;
    private DatabaseReference firebase;
    private adapterSP_Iphone adapterSP_iphone;
    private ArrayList<SanPham> listSP;*/
   private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_show);
        //database = FirebaseDatabase.getInstance();
        //firebase = database.getReference("SanPham");
        listView_SanPham = (ListView) findViewById(R.id.listView_SanPham);
        list = new ArrayList<>();
        list.add("1");
        for(int i = 0 ; i < 50 ; i++){
            list.add(String.valueOf(i));
        }
        ArrayAdapter adapter = new ArrayAdapter(TestShow.this,android.R.layout.simple_list_item_1,list);
        listView_SanPham.setAdapter(adapter);
        /*listSP = new ArrayList<>();
        firebase.child("Apple").child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSP.clear();

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String anhsp = data.child("anhSP").getValue().toString();
                    String ten = data.child("tenSP").getValue().toString();
                    String sotien = data.child("sotienSP").getValue().toString();
                    String mieuta = data.child("mieutaSP").getValue().toString();
                    SanPham sanPham = new SanPham(anhsp,ten,sotien,mieuta);
                    listSP.add(sanPham);
                }
                adapterSP_iphone = new adapterSP_Iphone(listSP,TestShow.this);
                listView_SanPham.setAdapter(adapterSP_iphone);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TestShow.this, "Error" + listSP.size(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
