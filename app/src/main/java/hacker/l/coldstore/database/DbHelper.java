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
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = Contants.DATABASE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userData");
        db.execSQL("DROP TABLE IF EXISTS AdminData");
        db.execSQL("DROP TABLE IF EXISTS rackData");
        db.execSQL("DROP TABLE IF EXISTS inwardData");
        db.execSQL("DROP TABLE IF EXISTS inwardData");
        db.execSQL("DROP TABLE IF EXISTS paymentData");
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);

    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_UserData_TABLE = "CREATE TABLE userData(loginId INTEGER,Username TEXT,UserPhone TEXT,EmailId TEXT,Password TEXT)";
        String CREATE_AdminData_TABLE = "CREATE TABLE AdminData(AdminId INTEGER,AdminName TEXT,AdminPhone TEXT,AdminEmail TEXT,AdminPassword TEXT,AdminProfile TEXT)";
        String CREATE_surakshacavach_TABLE = "CREATE TABLE rackData(rackId INTEGER,floor TEXT,rack TEXT,capacity TEXT)";
        String CREATE_inwardData_TABLE = "CREATE TABLE inwardData(inwardId INTEGER,userName TEXT,fatherName TEXT,userPhone TEXT,address TEXT,quantity TEXT,rent TEXT,variety TEXT,floor INTEGER,rack TEXT,advanced TEXT,caseType TEXT,grandTotal TEXT,byUser TEXT,time TEXT,date TEXT)";
        String CREATE_PaymentData_TABLE = "CREATE TABLE paymentData(paymentId INTEGER,inwardId INTEGER,userName TEXT,fatherName TEXT,userPhone TEXT,address TEXT,quantity TEXT,rent TEXT,variety TEXT,floor INTEGER,rack TEXT,advanced TEXT,caseType TEXT,grandTotal TEXT,byUser TEXT,time TEXT,date TEXT)";
        db.execSQL(CREATE_AdminData_TABLE);
        db.execSQL(CREATE_inwardData_TABLE);
        db.execSQL(CREATE_PaymentData_TABLE);
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


    //    // --------------------------user Data---------------
    public boolean upsertUserData(Result ob) {
        boolean done = false;
        Result data = null;
        if (ob.getUserId() != 0) {
            data = getUserDataByLoginId(ob.getUserId());
            if (data == null) {
                done = insertUserData(ob);
            } else {
                done = updateUserData(ob);
            }
        }
        return done;
    }


    //    // for user data..........
    private void populateUserData(Cursor cursor, Result ob) {
        ob.setUserId(cursor.getInt(0));
        ob.setUserName(cursor.getString(1));
        ob.setUserPhone(cursor.getString(2));
        ob.setEmpPassword(cursor.getString(4));
    }

    // insert userData data.............
    public boolean insertUserData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("loginId", ob.getUserId());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("userData", null, values);
        db.close();
        return i > 0;
    }

    //    user data
    public Result getUserData() {

        String query = "Select * FROM userData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateUserData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //
//    //show  user list data
    public List<Result> getAllUserData() {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM userData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateUserData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //  get user data
    public Result getUserDataByLoginId(int id) {

        String query = "Select * FROM userData WHERE loginId = " + id + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateUserData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //    update  data
    public boolean updateUserData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("loginId", ob.getUserId());

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("userData", values, "loginId = " + ob.getUserId() + " ", null);

        db.close();
        return i > 0;
    }

    // delete user Data
    public boolean deleteUserData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("userData", null, null);
        db.close();
        return result;
    }

    //    // --------------------------rack Data---------------
    public boolean upsertRackData(Result ob) {
        boolean done = false;
        Result data = null;
        if (ob.getRackId() != 0) {
            data = getRackDataByRackId(ob.getRackId());
            if (data == null) {
                done = insertRackData(ob);
            } else {
                done = updateRackData(ob);
            }
        }
        return done;
    }


    //    // for rack data..........
    private void populateRackData(Cursor cursor, Result ob) {
        ob.setRackId(cursor.getInt(0));
        ob.setFloor(cursor.getInt(1));
        ob.setRack(cursor.getString(2));
        ob.setCapacity(cursor.getString(3));
    }

    // insert rack data.............
    public boolean insertRackData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("rackId", ob.getRackId());
        values.put("floor", ob.getFloor());
        values.put("rack", ob.getRack());
        values.put("capacity", ob.getCapacity());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("rackData", null, values);
        db.close();
        return i > 0;
    }

    //    rack data
    public Result getRackData() {

        String query = "Select * FROM rackData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateRackData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //    ..........show  rack list data
    public List<Result> getAllRackData() {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM rackData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateRackData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //  get rack data
    public Result getRackDataByRackId(int id) {

        String query = "Select * FROM rackData WHERE rackId = " + id + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateRackData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //  get rack data
    public Result getRackDataByRackFloor(String rack, int floor) {

        String query = "Select * FROM rackData WHERE rack ='" + rack + "' AND floor= " + floor + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateRackData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //    ..........show  rack list data
    public List<Result> getRackDataByFloor(int floor) {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM rackData WHERE floor = " + floor + " ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateRackData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //    update  rack data
    public boolean updateRackData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("rackId", ob.getRackId());
        values.put("floor", ob.getFloor());
        values.put("rack", ob.getRack());
        values.put("capacity", ob.getCapacity());

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("rackData", values, "rackId = " + ob.getRackId() + " ", null);

        db.close();
        return i > 0;
    }

    // delete rack Data
    public boolean deleteRackData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("rackData", null, null);
        db.close();
        return result;
    }

    // delete rack Data
    public boolean deleteRackData(int rackId) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("rackData", "rackId" + "=" + rackId, null);
        db.close();
        return result;
    }


    // --------------------------inward Data-------------------------------------------------------
    public boolean upsertInwardData(Result ob) {
        boolean done = false;
        Result data = null;
        if (ob.getInwardId() != 0) {
            data = getInwardDataByInwardId(ob.getInwardId());
            if (data == null) {
                done = insertInwardData(ob);
            } else {
                done = updateInwardData(ob);
            }
        }
        return done;
    }


    //    // for Inward data..........
    private void populateInwardData(Cursor cursor, Result ob) {
        ob.setInwardId(cursor.getInt(0));
        ob.setUserName(cursor.getString(1));
        ob.setFatherName(cursor.getString(2));
        ob.setUserPhone(cursor.getString(3));
        ob.setAddress(cursor.getString(4));
        ob.setQuantity(cursor.getString(5));
        ob.setRent(cursor.getString(6));
        ob.setVarietyName(cursor.getString(7));
        ob.setFloor(cursor.getInt(8));
        ob.setRack(cursor.getString(9));
        ob.setAdvanced(cursor.getString(10));
        ob.setCaseType(cursor.getString(11));
        ob.setGrandTotal(cursor.getString(12));
        ob.setByUser(cursor.getString(13));
        ob.setTime(cursor.getString(14));
        ob.setDay(cursor.getString(15));
    }

    // insert Inward data.............
    public boolean insertInwardData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("inwardId", ob.getInwardId());
        values.put("userName", ob.getUserName());
        values.put("fatherName", ob.getFatherName());
        values.put("userPhone", ob.getUserPhone());
        values.put("address", ob.getAddress());
        values.put("quantity", ob.getQuantity());
        values.put("rent", ob.getRent());
        values.put("variety", ob.getVarietyName());
        values.put("floor", ob.getFloor());
        values.put("rack", ob.getRack());
        values.put("advanced", ob.getAdvanced());
        values.put("caseType", ob.getCaseType());
        values.put("grandTotal", ob.getGrandTotal());
        values.put("byUser", ob.getByUser());
        values.put("time", ob.getTime());
        values.put("date", ob.getDay());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("inwardData", null, values);
        db.close();
        return i > 0;
    }

    //    Inward data
    public Result getInwardData() {

        String query = "Select * FROM inwardData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateInwardData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //
//    //show  Inward list data
    public List<Result> getAllInwardData() {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM inwardData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateInwardData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //  get Inward data
    public Result getInwardDataByInwardId(int id) {

        String query = "Select * FROM inwardData WHERE inwardId = " + id + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateInwardData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //  get Inward data
    public Result getInwardDataByRackFloor(String rack, int floor) {

        String query = "Select * FROM inwardData WHERE rack ='" + rack + "' AND floor= " + floor + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateInwardData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //  get Inward data
    public List<Result> getAllInwardDataByRackFloor(String rack, int floor) {
        String query = "Select * FROM inwardData WHERE rack ='" + rack + "' AND floor= " + floor + "";
        ArrayList list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populateInwardData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //    update  Inward data
    public boolean updateInwardData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("inwardId", ob.getInwardId());
        values.put("userName", ob.getUserName());
        values.put("fatherName", ob.getFatherName());
        values.put("userPhone", ob.getUserPhone());
        values.put("address", ob.getAddress());
        values.put("quantity", ob.getQuantity());
        values.put("rent", ob.getRent());
        values.put("variety", ob.getVarietyName());
        values.put("floor", ob.getFloor());
        values.put("rack", ob.getRack());
        values.put("advanced", ob.getAdvanced());
        values.put("caseType", ob.getCaseType());
        values.put("grandTotal", ob.getGrandTotal());
        values.put("byUser", ob.getByUser());
        values.put("time", ob.getTime());
        values.put("date", ob.getDay());

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("inwardData", values, "inwardId = " + ob.getInwardId() + " ", null);

        db.close();
        return i > 0;
    }

    // delete Inward Data
    public boolean deleteInwardData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("inwardData", null, null);
        db.close();
        return result;
    }

    // delete Inward Data
    public boolean deleteInwardData(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("inwardData", "inwardId" + "=" + id, null);
        db.close();
        return result;
    }


    // --------------------------payment Data-------------------------------------------------------
    public boolean upsertPaymentData(Result ob) {
        boolean done = false;
        Result data = null;
        if (ob.getPaymentId() != 0) {
            data = getPaymentDataByPaymentId(ob.getPaymentId());
            if (data == null) {
                done = insertPaymentData(ob);
            } else {
                done = updatePaymentData(ob);
            }
        }
        return done;
    }


    //    // for Payment data..........
    private void populatePaymentData(Cursor cursor, Result ob) {
        ob.setPaymentId(cursor.getInt(0));
        ob.setInwardId(cursor.getInt(1));
        ob.setUserName(cursor.getString(2));
        ob.setFatherName(cursor.getString(3));
        ob.setUserPhone(cursor.getString(4));
        ob.setAddress(cursor.getString(5));
        ob.setQuantity(cursor.getString(6));
        ob.setRent(cursor.getString(7));
        ob.setVarietyName(cursor.getString(8));
        ob.setFloor(cursor.getInt(9));
        ob.setRack(cursor.getString(10));
        ob.setAdvanced(cursor.getString(11));
        ob.setCaseType(cursor.getString(12));
        ob.setGrandTotal(cursor.getString(13));
        ob.setByUser(cursor.getString(14));
        ob.setTime(cursor.getString(15));
        ob.setDay(cursor.getString(16));
    }

    // insert Payment data.............
    public boolean insertPaymentData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("paymentId", ob.getPaymentId());
        values.put("inwardId", ob.getInwardId());
        values.put("userName", ob.getUserName());
        values.put("fatherName", ob.getFatherName());
        values.put("userPhone", ob.getUserPhone());
        values.put("address", ob.getAddress());
        values.put("quantity", ob.getQuantity());
        values.put("rent", ob.getRent());
        values.put("variety", ob.getVarietyName());
        values.put("floor", ob.getFloor());
        values.put("rack", ob.getRack());
        values.put("advanced", ob.getAdvanced());
        values.put("caseType", ob.getCaseType());
        values.put("grandTotal", ob.getGrandTotal());
        values.put("byUser", ob.getByUser());
        values.put("time", ob.getTime());
        values.put("date", ob.getDay());

        SQLiteDatabase db = this.getWritableDatabase();

        long i = db.insert("paymentData", null, values);
        db.close();
        return i > 0;
    }

    //    Payment data
    public Result getPaymentData() {

        String query = "Select * FROM paymentData";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populatePaymentData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //
//    //show  Payment list data
    public List<Result> getAllPaymentData() {
        ArrayList list = new ArrayList<>();
        String query = "Select * FROM paymentData";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                Result ob = new Result();
                populatePaymentData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }


    //  get Payment data
    public Result getPaymentDataByPaymentId(int id) {

        String query = "Select * FROM paymentData WHERE paymentId = " + id + " ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populatePaymentData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }

    //  get Payment data
    public Result getPaymentDataByRackFloor(String rack, int floor) {

        String query = "Select * FROM paymentData WHERE rack ='" + rack + "' AND floor= " + floor + "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Result data = new Result();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populatePaymentData(cursor, data);

            cursor.close();
        } else {
            data = null;
        }
        db.close();
        return data;
    }


    //    update  Payment data
    public boolean updatePaymentData(Result ob) {
        ContentValues values = new ContentValues();
        values.put("paymentId", ob.getPaymentId());
        values.put("inwardId", ob.getInwardId());
        values.put("userName", ob.getUserName());
        values.put("fatherName", ob.getFatherName());
        values.put("userPhone", ob.getUserPhone());
        values.put("address", ob.getAddress());
        values.put("quantity", ob.getQuantity());
        values.put("rent", ob.getRent());
        values.put("variety", ob.getVarietyName());
        values.put("floor", ob.getFloor());
        values.put("rack", ob.getRack());
        values.put("advanced", ob.getAdvanced());
        values.put("caseType", ob.getCaseType());
        values.put("grandTotal", ob.getGrandTotal());
        values.put("byUser", ob.getByUser());
        values.put("time", ob.getTime());
        values.put("date", ob.getDay());

        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("paymentData", values, "paymentId = " + ob.getPaymentId() + " ", null);

        db.close();
        return i > 0;
    }

    // delete Payment Data
    public boolean deletePaymentData() {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("paymentData", null, null);
        db.close();
        return result;
    }

    // delete Payment Data
    public boolean deletePaymentData(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("paymentData", "paymentId" + "=" + id, null);
        db.close();
        return result;
    }
}
