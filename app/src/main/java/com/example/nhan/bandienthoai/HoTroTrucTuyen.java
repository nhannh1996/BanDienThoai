package com.example.nhan.bandienthoai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HoTroTrucTuyen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference firebase;
    private String taikhoan;
    private EditText edt_TinNhan;
    private Button btn_Gui;
    private ListView lvTinNhan;
    private ArrayList<String> listChat;
    private ArrayAdapter<String> adapterChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_tro_truc_tuyen);
        setTitle("Hỗ trợ trực tuyến");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebase = database.getReference("HoTroTrucTuyen");
        taikhoan = mAuth.getCurrentUser().getUid().toString();
        edt_TinNhan = (EditText) findViewById(R.id.editText_TinNhan);
        btn_Gui = (Button) findViewById(R.id.button_Gui);
        lvTinNhan = (ListView) findViewById(R.id.listView_HoTro);
        listChat = new ArrayList<>();
        adapterChat = new ArrayAdapter<String>(HoTroTrucTuyen.this,android.R.layout.simple_list_item_1,listChat);
        lvTinNhan.setAdapter(adapterChat);
        firebase.child("1lDdxDVFHGP1wyptX8sA19je8vv1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listChat.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    listChat.add(data.getValue().toString());
                    adapterChat.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btn_Gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtn_Gui();
            }
        });
        edt_TinNhan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                setBtn_Gui();
                return true;
            }
        });
    }
    public void setBtn_Gui(){
        String tinnhan = edt_TinNhan.getText().toString();
        if(tinnhan.length() == 0){
            Toast.makeText(this, "Bạn phải nhập tin nhắn!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(taikhoan.equals("GYHe2BPEyaa0L3fPCnPXg0EJfBj2")) {
            firebase.child("1lDdxDVFHGP1wyptX8sA19je8vv1").push().setValue("Admin: " + tinnhan);
            edt_TinNhan.setText("");
        }else{
            firebase.child("1lDdxDVFHGP1wyptX8sA19je8vv1").push().setValue("User: " + tinnhan);
            edt_TinNhan.setText("");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        if (item.getItemId() == R.id.action_XoaDuLieu) {
            if(taikhoan.equals("GYHe2BPEyaa0L3fPCnPXg0EJfBj2")){
                firebase.child("1lDdxDVFHGP1wyptX8sA19je8vv1").removeValue();
                Toast.makeText(this, "Xóa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                firebase.child("1lDdxDVFHGP1wyptX8sA19je8vv1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listChat.clear();
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            listChat.add(data.getValue().toString());
                            adapterChat.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hotrotructuyen, menu);
        return true;
    }
}
