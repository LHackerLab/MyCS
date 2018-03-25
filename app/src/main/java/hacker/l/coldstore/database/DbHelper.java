package hacker.l.coldstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;


/**
 * Created by user on 11/24/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = Contants.DATABASE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userData");
        db.execSQL("DROP TABLE IF EXISTS AdminData");
        db.execSQL("DROP TABLE IF EXISTS surakshacavach");
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_UserData_TABLE = "CREATE TABLE userData(loginId INTEGER,Username TEXT,UserPhone TEXT,EmailId TEXT,Password TEXT)";
        String CREATE_AdminData_TABLE = "CREATE TABLE AdminData(AdminId INTEGER,AdminName TEXT,AdminPhone TEXT,AdminEmail TEXT,AdminPassword TEXT,AdminProfile TEXT)";
        String CREATE_surakshacavach_TABLE = "CREATE TABLE surakshacavach(scid INTEGER,loginId INTEGER,Username TEXT,UserPhone TEXT,EmailId TEXT,Address TEXT,City TEXT ,PinCode TEXT, EmergencyOne TEXT, EmergencyTwo TEXT, EmergencyThree TEXT,barCode TEXT, socialUs TEXT)";
        db.execSQL(CREATE_AdminData_TABLE);
        db.execSQL(CREATE_UserData_TABLE);
        db.execSQL(CREATE_surakshacavach_TABLE);
    }

    //    // --------------------------Admin Data---------------
    public boolean upsertAdminData(Result ob) {
        boolean done = false;
        Result data = null;
        if (ob.getAdminId() != 0) {
            data = getAdminDataByAdminId(ob.getAdminId());
            if (data == null) {
                done = insertAdminData(ob);
            } else {
                done = updateAdminData(ob);
            }
        }
        return done;
    }


    //    // for Admin data..........
    private void populateAdminData(Cursor cursor, Result ob) {
        ob.setAdminId(cursor.getInt(0));
        ob.setAdminName(cursor.getString(1));
        ob.setAdminPhone(cursor.getString(2));
        ob.setAdminEmail(cursor.getString(3));
        ob.setAdminPassword(cursor.getString(4));
        ob.setAdminProfile(cursor.getString(5));
    }

    // insert Admin data.............
    public boolean insertAdminData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("AdminId", ob.getAdminId());
        values.put("AdminName", ob.getAdminName());
        values.put("AdminPhone", ob.getAdminPhone());
        values.put("AdminEmail", ob.getAdminEmail());
        values.put("AdminPassword", ob.getAdminPassword());
        values.put("AdminProfile", ob.getAdminProfile());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("AdminData", null, values);
        db.close();
        return i > 0;
    }

    //    Admin data
    public Result getAdminData() {

        String query = "Select * FROM AdminData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateAdminData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //
//    //show  Admin list data
    public List<Result> getAllAdminData() {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM AdminData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateAdminData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //  get Admin data
    public Result getAdminDataByAdminId(int id) {

        String query = "Select * FROM AdminData WHERE AdminId = " + id + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateAdminData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //    update  Admin data
    public boolean updateAdminData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("AdminId", ob.getAdminId());
        values.put("AdminName", ob.getAdminName());
        values.put("AdminPhone", ob.getAdminPhone());
        values.put("AdminEmail", ob.getAdminEmail());
        values.put("AdminPassword", ob.getAdminPassword());
        values.put("AdminProfile", ob.getAdminProfile());

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("AdminData", values, "AdminId = " + ob.getAdminId() + " ", null);

        db.close();
        return i > 0;
    }

    // delete Admin Data
    public boolean deleteAdminData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("AdminData", null, null);
        db.close();
        return result;
    }

//
//    //    // --------------------------user Data---------------
//    public boolean upsertUserData(Result ob) {
//        boolean done = false;
//        Result data = null;
//        if (ob.getLoginId() != 0) {
//            data = getUserDataByLoginId(ob.getLoginId());
//            if (data == null) {
//                done = insertUserData(ob);
//            } else {
//                done = updateUserData(ob);
//            }
//        }
//        return done;
//    }
//
//
//    //    // for user data..........
//    private void populateUserData(Cursor cursor, Result ob) {
//        ob.setLoginId(cursor.getInt(0));
//        ob.setUsername(cursor.getString(1));
//        ob.setUserPhone(cursor.getString(2));
//        ob.setEmailId(cursor.getString(3));
//        ob.setPassword(cursor.getString(4));
//    }
//
//    // insert userData data.............
//    public boolean insertUserData(Result ob) {
//        ContentValues values = new ContentValues();
//        values.put("loginId", ob.getLoginId());
//        values.put("Username", ob.getUsername());
//        values.put("UserPhone", ob.getUserPhone());
//        values.put("EmailId", ob.getEmailId());
//        values.put("Password", ob.getPassword());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        long i = db.insert("userData", null, values);
//        db.close();
//        return i > 0;
//    }
//
//    //    user data
//    public Result getUserData() {
//
//        String query = "Select * FROM userData";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Result data = new Result();
//
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            populateUserData(cursor, data);
//
//            cursor.close();
//        } else {
//            data = null;
//        }
//        db.close();
//        return data;
//    }
//
//    //
////    //show  user list data
//    public List<Result> getAllUserData() {
//        ArrayList list = new ArrayList<>();
//        String query = "Select * FROM userData";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//
//            while (cursor.isAfterLast() == false) {
//                Result ob = new Result();
//                populateUserData(cursor, ob);
//                list.add(ob);
//                cursor.moveToNext();
//            }
//        }
//        db.close();
//        return list;
//    }
//
//
//    //  get user data
//    public Result getUserDataByLoginId(int id) {
//
//        String query = "Select * FROM userData WHERE loginId = " + id + " ";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        Result data = new Result();
//
//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
//            populateUserData(cursor, data);
//
//            cursor.close();
//        } else {
//            data = null;
//        }
//        db.close();
//        return data;
//    }
//
//    //    update  data
//    public boolean updateUserData(Result ob) {
//        ContentValues values = new ContentValues();
//        values.put("loginId", ob.getLoginId());
//        values.put("Username", ob.getUsername());
//        values.put("UserPhone", ob.getUserPhone());
//        values.put("EmailId", ob.getEmailId());
//        values.put("Password", ob.getPassword());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        long i = 0;
//        i = db.update("userData", values, "loginId = " + ob.getLoginId() + " ", null);
//
//        db.close();
//        return i > 0;
//    }
//
//    // delete user Data
//    public boolean deleteUserData() {
//        boolean result = false;
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("userData", null, null);
//        db.close();
//        return result;
//    }

}
