package com.example.nhan.bandienthoai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AdminManager extends AppCompatActivity {
    private ImageButton imgButton_AnhSanPham;
    private EditText edt_TenSanPham,edt_SoTienSanPham, edt_MieuTaSanPham;
    private Spinner sp_Loai,sp_HangSanPham,sp_DanhGia;
    private Button btn_ThemSanPham;
    private FirebaseDatabase database;
    private DatabaseReference firebase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);
        anhxa();


        edt_MieuTaSanPham.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                themSanPham();

                return true;
            }
        });
        btn_ThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themSanPham();
            }
        });
        imgButton_AnhSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chosser = Intent.createChooser(pick, " Chọn Ảnh");
                chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{photo});
                startActivityForResult(chosser, 999);
            }
        });
    }
    public void themSanPham(){
        String tenSP = edt_TenSanPham.getText().toString();
        String sotienSP = edt_SoTienSanPham.getText().toString();
        String mieutaSp = edt_MieuTaSanPham.getText().toString();
        String danhgia = sp_DanhGia.getSelectedItem().toString();
        byte[] hinh = ImageView_To_Byte(imgButton_AnhSanPham);
        final String chuoiHinh = Base64.encodeToString(hinh, Base64.DEFAULT);
        SanPham sanPham = new SanPham(chuoiHinh,tenSP,sotienSP,mieutaSp,danhgia);
        String sphang = sp_HangSanPham.getSelectedItem().toString();

        String loai = sp_Loai.getSelectedItem().toString();
        firebase.child(sphang).child(loai).push().setValue(sanPham);
        Toast.makeText(AdminManager.this,"Thêm thành công: " + tenSP,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_DangXuat) {

            mAuth.signOut();
            Toast.makeText(AdminManager.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminManager.this,LoginActivity.class);
            startActivity(intent);
            finish();

            return true;
        }
        if (id == R.id.action_SanPham) {
            Intent intent = new Intent(AdminManager.this,MainActivity.class);
            startActivity(intent);
           // finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public byte[] ImageView_To_Byte(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK) {

            if (data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgButton_AnhSanPham.setImageBitmap(imageBitmap);
            } else {
                Uri uri = data.getData();
                imgButton_AnhSanPham.setImageURI(uri);
            }

        }
    }
    public void anhxa(){

        setTitle("Quản lý");
        database = FirebaseDatabase.getInstance();
        firebase = database.getReference("SanPham");
        mAuth = FirebaseAuth.getInstance();
        imgButton_AnhSanPham = (ImageButton) findViewById(R.id.imageButton_AnhSanPham);
        edt_TenSanPham = (EditText) findViewById(R.id.editText_TenSanPham);
        edt_SoTienSanPham = (EditText) findViewById(R.id.editText_SoTien);
        edt_MieuTaSanPham = (EditText) findViewById(R.id.editText_MieuTa);
        sp_Loai = (Spinner) findViewById(R.id.spinner_Loai);
        sp_HangSanPham = (Spinner) findViewById(R.id.spinner_HangSP);
        sp_DanhGia = (Spinner) findViewById(R.id.spinner_DanhGia);
        btn_ThemSanPham = (Button) findViewById(R.id.button_ThemSanPham);

        ArrayAdapter<CharSequence> adapterLoai = ArrayAdapter.createFromResource(this,R.array.listLoai,android.R.layout.simple_spinner_item);
        adapterLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Loai.setAdapter(adapterLoai);

        ArrayAdapter<CharSequence> adapterHang = ArrayAdapter.createFromResource(this,R.array.listHang,android.R.layout.simple_spinner_item);
        adapterHang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_HangSanPham.setAdapter(adapterHang);

        ArrayAdapter<CharSequence> adapterDanhGia = ArrayAdapter.createFromResource(this,R.array.listDanhGia,android.R.layout.simple_spinner_item);
        adapterDanhGia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_DanhGia.setAdapter(adapterDanhGia);

    }
}
