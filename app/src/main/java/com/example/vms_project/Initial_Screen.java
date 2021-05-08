package com.example.vms_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Initial_Screen extends AppCompatActivity {

    Button btn_staff, btn_visitor, btn_admin;
    TextView txt_signinlabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.initial_screen);

        btn_staff = findViewById(R.id.Staff_btn);
        btn_visitor = findViewById(R.id.Visitor_btn);
        btn_admin = findViewById(R.id.btn_Admin);
        txt_signinlabel = findViewById(R.id.txt_signinlabel);

        DatabaseHelper databaseHelper = new DatabaseHelper(Initial_Screen.this);
        int signedInUsersCount = databaseHelper.getSignedInUsersCount();
        txt_signinlabel.setText(String.valueOf(signedInUsersCount));

        btn_staff.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityStaff();
            }

         });
        btn_visitor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityVisitor();
            }

        });

        btn_admin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAdminLogin();
            }

        });
    }

    public void openActivityStaff() {

        Intent intent = new Intent(this, Staff_Selection.class);
        startActivity(intent);
    }

    public void openActivityVisitor() {

        Intent intent = new Intent(this, Visitor_Option_Selection.class);
        startActivity(intent);
    }

    public void openActivityAdminLogin() {

        Intent intent = new Intent(this, Admin_Login.class);
        startActivity(intent);
    }

}