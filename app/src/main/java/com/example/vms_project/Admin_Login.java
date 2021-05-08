package com.example.vms_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Admin_Login extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_login, btn_home;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        databaseHelper = new DatabaseHelper(Admin_Login.this);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_home = findViewById(R.id.home_7);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Boolean authenticated = authenticate(username, password);

                if(authenticated) {
                    openActivityAdmin();
                } else {
                    Toast.makeText(Admin_Login.this, "Invalid details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityHome();
                finish();
            }
        });
    }

    private Boolean authenticate(String username, String password) {
        return databaseHelper.authenticate(username, password);
    }

    private void openActivityAdmin() {

        Intent intent = new Intent(this, Admin_Portal.class);
        startActivity(intent);
    }

    private void openActivityHome() {

        Intent intent = new Intent(this, Initial_Screen.class);
        startActivity(intent);
    }
}
