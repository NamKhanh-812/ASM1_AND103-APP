package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangKy extends AppCompatActivity {
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth=FirebaseAuth.getInstance();
        EditText user = findViewById(R.id.edtTkDK);
        EditText pass = findViewById(R.id.edtMkDK);

        ImageButton ibtnBack = findViewById(R.id.ibtnBack);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        //
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if(email.isEmpty()||password.isEmpty()){
                    Toast.makeText(DangKy.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();

                }else {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(DangKy.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(DangKy.this, "Đăng ký không thành công\nTài khoản đã tồn tại hoặc mật khẩu không đủ mạnh!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}