package com.example.vms_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String USER_TABLE = "USER_TABLE";
    public static final String ADMIN_TABLE = "ADMIN_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_SINGED_IN = "SIGNED_IN";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_STAFF_VISITOR = "STAFF_VISITOR";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "elm";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, " + COLUMN_STAFF_VISITOR + " TEXT, "
                + COLUMN_SINGED_IN + " BOOLEAN )";

        db.execSQL(createTableStatement);

        String createAdminTableStatement = "CREATE TABLE " + ADMIN_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, " + COLUMN_PASSWORD + " TEXT)";

        db.execSQL(createAdminTableStatement);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, DEFAULT_ADMIN_USERNAME);
        cv.put(COLUMN_PASSWORD, DEFAULT_ADMIN_PASSWORD);

        long insert = db.insert(ADMIN_TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userModel.getName());
        cv.put(COLUMN_STAFF_VISITOR, userModel.getStaff_visitor());
        cv.put(COLUMN_SINGED_IN, userModel.getOnSite());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addOneVisitor(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userModel.getName());
        cv.put(COLUMN_STAFF_VISITOR, "Visitor");
        cv.put(COLUMN_SINGED_IN, userModel.getOnSite());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<UserModel> getallUsers() {

        List<UserModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //Go through the results (loop). Insert them into a return list to display.
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String staffVisitor = cursor.getString(2);
                Boolean signedIn = cursor.getInt(3) == 1;

                UserModel newUser = new UserModel(userID, userName, staffVisitor, signedIn);
                returnList.add(newUser);

            } while (cursor.moveToNext());
        } else {
            //Fail - do not add to list.


        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteOne(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = " DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + userModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    public List<UserModel> getSpecificUserStaff(String initial) {

        List<UserModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " LIKE '" + initial + "%'" + " AND " + COLUMN_STAFF_VISITOR + '=' + "'Staff'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //Go through the results (loop). Insert them into a return list to display.
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String staffVisitor = cursor.getString(2);
                Boolean signedIn = cursor.getInt(3) == 1;

                UserModel newUser = new UserModel(userID, userName, staffVisitor, signedIn);
                returnList.add(newUser);

            } while (cursor.moveToNext());
        } else {
            //Fail - do not add to list.


        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<UserModel> getSpecificUserVisitor(String prefix) {
        return getSpecificUser(prefix, true);
    }

    public List<UserModel> getSpecificUser(String prefix, boolean onlyVisitor) {

        List<UserModel> returnList = new ArrayList<>();
        String queryString =
                "SELECT * FROM " + USER_TABLE +
                " WHERE " + COLUMN_USER_NAME + " LIKE '" + prefix + "%'";
        if(onlyVisitor) {
            queryString = queryString + " AND " + COLUMN_STAFF_VISITOR + '=' + "'Visitor'";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //Go through the results (loop). Insert them into a return list to display.
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String staffVisitor = cursor.getString(2);
                Boolean signedIn = cursor.getInt(3) == 1;

                UserModel newUser = new UserModel(userID, userName, staffVisitor, signedIn );
                returnList.add(newUser);

            } while (cursor.moveToNext());
        } else {
            //Fail - do not add to list.
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean signInOrOut(UserModel userModel) {

        String queryString =
            "SELECT " + COLUMN_SINGED_IN +
            " FROM " + USER_TABLE +
            " WHERE " + COLUMN_ID + " = " + userModel.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                Boolean signedIn = cursor.getInt(0) == 1;

                signInOrOutInternal(db, userModel.getId(), !signedIn);

            } while (cursor.moveToNext());
        } else {
            //Fail - do not add to list.
        }
        cursor.close();
        db.close();
        return true;
    }

    public boolean authenticate(String username, String password) {

        String queryString =
                "SELECT " + COLUMN_PASSWORD +
                        " FROM " + ADMIN_TABLE +
                        " WHERE " + COLUMN_USER_NAME + " = '" + username + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        String dbPassword = "";

        if (cursor.moveToFirst()) {
            dbPassword = cursor.getString(0);
        } else {
            //Fail - do not add to list.
        }

        cursor.close();
        db.close();
        return dbPassword != "" && dbPassword.equals(password);
    }

    public int getSignedInUsersCount() {

        String queryString =
                "SELECT COUNT(*) " +
                        " FROM " + USER_TABLE +
                        " WHERE " + COLUMN_SINGED_IN + " = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int signedInCount = 0;

        if (cursor.moveToFirst()) {
            signedInCount = cursor.getInt(0);
        } else {
            //Fail - do not add to list.
        }

        cursor.close();
        db.close();
        return signedInCount;
    }

    private void signInOrOutInternal(SQLiteDatabase db, int id, boolean shouldSignIn) {

        String queryString =
                "UPDATE " + USER_TABLE +
                " SET " + COLUMN_SINGED_IN + " = " + (shouldSignIn ? "1" : "0") +
                " WHERE " + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        cursor.close();
    }

}
