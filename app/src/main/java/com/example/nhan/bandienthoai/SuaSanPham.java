package com.example.nhan.bandienthoai;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nhan.bandienthoai.fragment.fragment_iphone;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class SuaSanPham extends AppCompatActivity {
    private ImageButton imgButton_AnhSanPham;
    private EditText edt_TenSanPham,edt_SoTienSanPham, edt_MieuTaSanPham;
    private Spinner sp_Loai,sp_HangSanPham;
    private Button btn_SuaSanPham,btn_XoaSanPham;
    private FirebaseDatabase database;
    private DatabaseReference firebase;
    private String hang,loai;
    //error key
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        anhxa();

        btn_SuaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] hinh = ImageView_To_Byte(imgButton_AnhSanPham);
                final String chuoiHinh = Base64.encodeToString(hinh, Base64.DEFAULT);
                String tenSP = edt_TenSanPham.getText().toString();
                String hangSua = sp_HangSanPham.getSelectedItem().toString();
                String loaiSua = sp_Loai.getSelectedItem().toString();
                String sotienSP = edt_SoTienSanPham.getText().toString();
                String mieutaSP = edt_MieuTaSanPham.getText().toString();
                SanPham sanPham = new SanPham(chuoiHinh,tenSP,sotienSP,mieutaSP);
                if(!hangSua.equals(hang)){
                    firebase.child(hang).child(loai).child(fragment_iphone.key).removeValue();
                    firebase.child(hangSua).child(loaiSua).child(fragment_iphone.key).setValue(sanPham);
                    Toast.makeText(SuaSanPham.this, "Sửa thành công sản phẩm: " + hangSua + " - " + this + " - " + tenSP, Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    firebase.child(hangSua).child(loaiSua).child(fragment_iphone.key).setValue(sanPham);
                    Toast.makeText(SuaSanPham.this, "Sửa thành công sản phẩm: " + hangSua + " - " + this + " - " + tenSP, Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });
        btn_XoaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SuaSanPham.this);
                builder.setMessage("Bạn chắc chắn muốn xóa chứ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String tenSP = edt_TenSanPham.getText().toString();

                                firebase.child(hang).child(loai).child(fragment_iphone.key).removeValue();
                                Toast.makeText(SuaSanPham.this, "Xóa thành công sản phẩm: " + hang + " - " + loai + " - " + tenSP, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
    public byte[] ImageView_To_Byte(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void anhxa(){
        database = FirebaseDatabase.getInstance();
        firebase = database.getReference("SanPham");
        imgButton_AnhSanPham = (ImageButton) findViewById(R.id.imageButton_AnhSanPham);
        edt_TenSanPham = (EditText) findViewById(R.id.editText_TenSanPham);
        edt_SoTienSanPham = (EditText) findViewById(R.id.editText_SoTien);
        edt_MieuTaSanPham = (EditText) findViewById(R.id.editText_MieuTa);
        sp_Loai = (Spinner) findViewById(R.id.spinner_Loai);
        sp_HangSanPham = (Spinner) findViewById(R.id.spinner_HangSP);
        btn_SuaSanPham = (Button) findViewById(R.id.button_SuaSanPham);
        btn_XoaSanPham = (Button) findViewById(R.id.button_XoaSanPham);
        ArrayAdapter<CharSequence> adapterLoai = ArrayAdapter.createFromResource(this,R.array.listLoai,android.R.layout.simple_spinner_item);
        adapterLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Loai.setAdapter(adapterLoai);

        ArrayAdapter<CharSequence> adapterHang = ArrayAdapter.createFromResource(this,R.array.listHang,android.R.layout.simple_spinner_item);
        adapterHang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_HangSanPham.setAdapter(adapterHang);

        Intent intent = this.getIntent();
        String anhSP = intent.getStringExtra("anhSP");
        String tenSP  = intent.getStringExtra("tenSP");
        hang = intent.getStringExtra("hangSX").toString();
        loai = intent.getStringExtra("loaiSP").toString();
        String sotienSP = intent.getStringExtra("sotienSP");
        String mieutaSP = intent.getStringExtra("mieutaSP");
        byte[] mangHinh = Base64.decode(anhSP, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(mangHinh,0,mangHinh.length);
        imgButton_AnhSanPham.setImageBitmap(bmp);
        edt_TenSanPham.setText(tenSP);
        edt_SoTienSanPham.setText(sotienSP);
        edt_MieuTaSanPham.setText(mieutaSP);
        if(loai.equals("Dien Thoai")){
            sp_Loai.setSelection(0);
        }else if(loai.equals("LapTop")){
            sp_Loai.setSelection(1);
        }else if(loai.equals("May Tinh Ban")){
            sp_Loai.setSelection(2);
        }else if(loai.equals("Phu Kien")){
            sp_Loai.setSelection(3);
        }
        if(hang.equals("Apple")){
            sp_HangSanPham.setSelection(0);
        }else if(hang.equals("SamSung")){
            sp_HangSanPham.setSelection(1);
        }else if(hang.equals("Nokia")){
            sp_HangSanPham.setSelection(2);
        }else if(hang.equals("Oppo")){
            sp_HangSanPham.setSelection(3);
        }
    }
}
