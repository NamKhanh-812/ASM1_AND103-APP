package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class LoginTkMk extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tk_mk);
        mAuth=FirebaseAuth.getInstance();
        EditText user = findViewById(R.id.edtTkDN);
        EditText pass = findViewById(R.id.edtMkDN);
        Button btnLogIn = findViewById(R.id.btnLogin);
        TextView txtQuenMK = findViewById(R.id.txtQuenMK);
        TextView txtDangKy = findViewById(R.id.txtDangKy);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if(email.isEmpty()||password.isEmpty()){
                    Toast.makeText(LoginTkMk.this, "Tài khoản, mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();

                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(LoginTkMk.this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();

                }else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginTkMk.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginTkMk.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginTkMk.this, DanhSach.class));
                            }
                            else {
                                Toast.makeText(LoginTkMk.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTkMk.this, DangKy.class));
            }
        });
        txtQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(LoginTkMk.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginTkMk.this, "Vui lòng kiểm tra email", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginTkMk.this, "Lỗi gửi email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}