package com.example.vms_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin_Portal extends AppCompatActivity {

    Button btn_add, btn_viewAll, btn_remove, btn_home, btn_search;
    ListView lv_userList;
    EditText et_name;
    Spinner et_staff_visitor;
    EditText sw_signedin;
    Boolean isDeleteUser;

    ArrayAdapter userAA;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_screen);
        isDeleteUser = false;

        btn_add = findViewById(R.id.btn_Add);
        btn_viewAll = findViewById(R.id.btn_view_all);
        btn_home = findViewById(R.id.home_6);
        btn_search = findViewById(R.id.btn_search);
        btn_remove = findViewById(R.id.btn_remove);
        et_name = findViewById(R.id.et_name);
        et_staff_visitor = findViewById(R.id.et_staff_visitor);
        lv_userList = findViewById(R.id.lv_userList_display);
        sw_signedin= findViewById(R.id.sw_onSite);

        String[] listOfItems = {"Staff", "Visitor"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfItems);
        et_staff_visitor.setAdapter(spinnerAdapter);

        databaseHelper = new DatabaseHelper(Admin_Portal.this);
        ShowUsersonListView(databaseHelper.getallUsers());

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserModel userModel;

                try {
                    userModel = new UserModel(-1, et_name.getText().toString(), et_staff_visitor.getSelectedItem().toString(), sw_signedin.getText().toString() == "true");
                    Toast.makeText(Admin_Portal.this, "User succesfully added", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Admin_Portal.this, "Error adding user", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1, "error", "", false );
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(Admin_Portal.this);

                boolean success = databaseHelper.addOne(userModel);
                Toast.makeText(Admin_Portal.this, "Success " + success, Toast.LENGTH_SHORT).show();
                ShowUsersonListView(databaseHelper.getallUsers());

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DatabaseHelper databaseHelper = new DatabaseHelper(Admin_Portal.this);
            List<UserModel> allUsers = databaseHelper.getallUsers();

                ShowUsersonListView(databaseHelper.getallUsers());

                //Toast.makeText(Admin_Portal.this, allUsers.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        lv_userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isDeleteUser) {
                    UserModel chosenUser = (UserModel) parent.getItemAtPosition(position);
                    databaseHelper.deleteOne(chosenUser);
                    ShowUsersonListView(databaseHelper.getallUsers());
                    Toast.makeText(Admin_Portal.this, "Deleted" + chosenUser.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prefix = et_name.getText().toString();
                List<UserModel> users = databaseHelper.getSpecificUser(prefix, false);

                ShowUsersonListView(users);
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeleteUser = !isDeleteUser;
                btn_remove.setBackgroundColor(isDeleteUser ? Color.RED : getResources().getColor((R.color.purple_500)));
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

    private void ShowUsersonListView(List<UserModel> users) {
        ArrayAdapter userSL = new ArrayAdapter<UserModel>(Admin_Portal.this, android.R.layout.simple_list_item_1, users);
        lv_userList.setAdapter(userSL);
    }

    public void openActivityHome() {

        Intent intent = new Intent(this, Initial_Screen.class);
        startActivity(intent);
    }

}
