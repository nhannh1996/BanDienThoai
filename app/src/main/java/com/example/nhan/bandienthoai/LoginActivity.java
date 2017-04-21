package com.example.nhan.bandienthoai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btn_DangNhap,btn_DangKy;
    private EditText edt_TaiKhoan,edt_MatKhau;
    private TextView tv_QuenMatKhau;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ánh xạ, khai báo
        anhxa();
        //onclick button đăng nhập trên keyboard
        edt_MatKhau.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                setBtn_DangNhap();
                return true;
            }
        });
        //đăng nhập
       btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtn_DangNhap();
            }
        });
       //đăng ký
        btn_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtn_DangKy();
            }
        });
        tv_QuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTv_QuenMatKhau();
            }
        });
        //tự động login
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    if(user.getUid().toString().equals("GYHe2BPEyaa0L3fPCnPXg0EJfBj2")){
                        Toast.makeText(LoginActivity.this,"ADMIN " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(LoginActivity.this,AdminManager.class);
                        startActivity(intent1);
                        LoginActivity.this.finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"USER " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                } else {
                    // User is signed out
                   // Toast.makeText(LoginActivity.this,"Thoát tài khoản thành công", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };
    }

    public void setBtn_DangNhap(){
        mProgress.setTitle("Đang trong quá trình đăng nhập ...");
        mProgress.setCancelable(false);
        mProgress.setMessage("Vui lòng chờ trong giây lát!");
        String taikhoan = edt_TaiKhoan.getText().toString();
        String matkhau = edt_MatKhau.getText().toString();
        if(taikhoan.length() == 0 ){
            Toast.makeText(this, "Vui lòng nhập tài khoản !", Toast.LENGTH_SHORT).show();
            edt_TaiKhoan.requestFocus();
            return;
        }else if(matkhau.length() == 0){
            Toast.makeText(this, "Vui lòng nhập mật khẩu !", Toast.LENGTH_SHORT).show();
            edt_MatKhau.requestFocus();
            return;
        }
        mProgress.show();
        //kết nối tài khoản từ firebase
        mAuth.signInWithEmailAndPassword(taikhoan,matkhau).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }else{
                     mProgress.dismiss();
                }
            }
        });
    }
    public void setBtn_DangKy() {
        String taikhoan = edt_TaiKhoan.getText().toString();
        String matkhau = edt_MatKhau.getText().toString();
        if(taikhoan.length() == 0 ){
            Toast.makeText(this, "Vui lòng nhập tài khoản !", Toast.LENGTH_SHORT).show();
            edt_TaiKhoan.requestFocus();
            return;
        }else if(matkhau.length() == 0){
            Toast.makeText(this, "Vui lòng nhập mật khẩu !", Toast.LENGTH_SHORT).show();
            edt_MatKhau.requestFocus();
            return;
        }
        mProgress.setTitle("Đang trong quá trình đăng ký ...");
        mProgress.setCancelable(false);
        mProgress.setMessage("Vui lòng chờ trong giây lát!");
        mProgress.show();
        mAuth.createUserWithEmailAndPassword(taikhoan,matkhau).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }else{
                    Toast.makeText(LoginActivity.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }
            }
        });
    }
    public void setTv_QuenMatKhau(){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edt_TaiKhoan.getText().toString();
        if(emailAddress.length() == 0) {
            Toast.makeText(this, "Hãy nhập Email ở phía trên!", Toast.LENGTH_SHORT).show();
        }else {
            mProgress.setTitle("Đang trong quá kiểm tra Email ...");
            mProgress.setCancelable(false);
            mProgress.setMessage("Vui lòng chờ trong giây lát!");
            mProgress.show();
            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Hãy đăng nhập vào mail và thiết lập lại mật khẩu", Toast.LENGTH_LONG).show();
                        mProgress.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Không tồn tại Email trong hệ thống", Toast.LENGTH_LONG).show();
                        mProgress.dismiss();
                    }
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void anhxa(){
        setTitle("FPT POLYTECHNIC - NHAN");
        mAuth = FirebaseAuth.getInstance();
        btn_DangNhap = (Button) findViewById(R.id.button_DangNhap);
        btn_DangKy = (Button) findViewById(R.id.button_DangKy);
        edt_TaiKhoan = (EditText) findViewById(R.id.editText_TaiKhoan);
        edt_MatKhau = (EditText) findViewById(R.id.editText_MatKhau);
        tv_QuenMatKhau = (TextView) findViewById(R.id.textView_QuenMatKhau);
        mProgress = new ProgressDialog(this);


    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
