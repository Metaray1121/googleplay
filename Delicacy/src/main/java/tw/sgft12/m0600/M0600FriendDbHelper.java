package tw.sgft12.m0600;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class M0600FriendDbHelper extends SQLiteOpenHelper {

    String TAG = "tcnr12=>";
    public String sCreateTableCommand;
    // 資料庫名稱
    private static final String DB_FILE = "sgft.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    // 資料表名稱
    private static final String DB_TABLE = "m0600_member";    // 資料庫物件，固定的欄位變數

    private static final String crTBsql = "CREATE     TABLE   " + DB_TABLE + "   ( " //依照自己的欄位更改
            + "id    INTEGER   PRIMARY KEY," + "name TEXT NOT NULL," + "tel TEXT,"
            + "address TEXT," + "des TEXT," +  "picture1 TEXT," +"  px  TEXT  ," + "py    TEXT," + " start  TEXT," + " end1  TEXT,"
             + " zipcode TEXT," + " region TEXT," + " town TEXT," + " level TEXT," + " id2  TEXT ," + " member_id  INTEGER , " + " creat_at TIMESTAMP );";

    private static SQLiteDatabase database;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crTBsql);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //        Log.d(TAG, "onUpgrade()");
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE);
        onCreate(db);
    }

    //----------------------------------------------
    //----------------------------------------------
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new M0600FriendDbHelper(context, DB_FILE, null, VERSION)
                    .getWritableDatabase();
        }
        return database;
    }

    public M0600FriendDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
        super(context, DB_FILE, null, VERSION);
        sCreateTableCommand = "";
    }

    public long insertRec(String b_name, String b_address, String b_tel, String b_des2, String b_px,
                          String b_py, String start, String end1, String zipcode, String region, String town, String level, String id2, String picture) {

        if (check(id2) != 0) {
            return -1;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();
        rec.put("name", b_name);
        rec.put("address", b_address);
        rec.put("tel", b_tel);
        rec.put("des", b_des2);
        rec.put("px", b_py);
        rec.put("py", b_px);
        rec.put("start", start);
        rec.put("end1", end1);
        rec.put("zipcode", zipcode);
        rec.put("region", region);
        rec.put("town", town);
        rec.put("level", level);
        rec.put("id2", id2);
        rec.put("picture", picture);
        long rowID = db.insert(DB_TABLE, null, rec);  //SQLite 新增語法
        db.close();
        return rowID;
    }

    public int RecCount() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT    *   FROM   " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null); //select
        return recSet.getCount();
    }

    public String FindRec(String tname) {
        SQLiteDatabase db = getReadableDatabase();
        String fldSet = "ans=";
        String sql = "SELECT  * FROM  " + DB_TABLE + " WHERE name LIKE ? ORDER BY id ASC";
        String[] args = {"%" + tname + "%"};
        Cursor recSet = db.rawQuery(sql, args);
        int ColumnCount = recSet.getColumnCount();
//=====================================================
        if (recSet.getCount() != 0) {
            recSet.moveToFirst();
            fldSet = recSet.getString(0) + " "
                    + recSet.getString(1) + " "
                    + recSet.getString(2) + " "
                    + recSet.getString(3) + "\n";

            while (recSet.moveToNext()) {
                for (int i = 0; i < ColumnCount; i++) {
                    fldSet += recSet.getString(i) + " ";
                }
                fldSet += "\n";
            }
        }
        recSet.close();
        db.close();
        return fldSet;
    }

    public void createTB() {
        // 批次新增
        int maxrec = 20;
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0; i <= maxrec; i++) {
            ContentValues newRow = new ContentValues();
            newRow.put("name", "路人" + u_chinayear(i));
            newRow.put("grp", "第" + u_chinano((int) (Math.random() * 4 + 1)) + "組");
            newRow.put("address", "台中市西區工業一路" + (100 + i) + "號");
            db.insert(DB_TABLE, null, newRow);  //insert db
        }

        db.close();
    }

    private String u_chinayear(int input_i) {
        String c_number = "";
        String china_no[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        c_number = china_no[input_i % 10];

        return c_number;
    }

    private String u_chinano(int input_i) {
        String c_number = "";
        String china_no[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        c_number = china_no[input_i % 10];
        return c_number;
    }

    //1405browse
    public ArrayList<String> getRecSet() {
        SQLiteDatabase db = getReadableDatabase(); //瀏覽所以用Read
        String sql = "SELECT * FROM " + DB_TABLE;
//        String sql = "SELECT * FROM " + " DB_TABLE WHERE email LIKE ? ";
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        //----------------------------
        int columnCount = recSet.getColumnCount(); //每一個table有幾個欄位
//        recSet.moveToFirst();
//        String fldSet = "";

//        for (int i = 0; i < columnCount; i++)
//            fldSet += recSet.getString(i) + "#"; //每一個欄位之間加#
//        recAry.add(fldSet);
        while (recSet.moveToNext()) {
            String fldSet = "";
            for (int i = 0; i < columnCount; i++) {
                fldSet += recSet.getString(i) + "#"; //
            }
            recAry.add(fldSet);
        }
        //------------------------
        recSet.close();
        db.close();
        return recAry;
    }

    public int clearRec() { //清除sqlite資料
        SQLiteDatabase db = getWritableDatabase();
        String sql = "   SELECT *              FROM     " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null); //選擇全部

        if (recSet.getCount() != 0) {
            int rowsAffected = db.delete(DB_TABLE, "1", null);
            // From the documentation of SQLiteDatabase delete method:
            // To remove all rows and get a count pass "1" as the whereClause.
            db.close();
            return rowsAffected;
        } else {
            db.close();
            return -1;
        }

    }

    public int check(String c) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE + " WHERE id2 = '" + c + "'";
        Cursor recSet = db.rawQuery(sql, null);
        return recSet.getCount();
    }

    public int deleteRec(String b_id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            String whereClause = "id = '" + b_id + "'";  //
            int rowsAffected = db.delete(DB_TABLE, whereClause, null);  //delete one record
            recSet.close();
            db.close();
            return rowsAffected; //傳回總共刪除幾筆
        } else {
            recSet.close();
            db.close();
            return -1;
        }
    }

    public int updateRec(String b_tid, String b_tname, String b_tgrp, String b_taddr, String b_tdes) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            ContentValues rec = new ContentValues();
//			rec.put("id", b_id);
            rec.put("name", b_tname);
            rec.put("grp", b_tgrp);
            rec.put("address", b_taddr);
            rec.put("des", b_tdes);
            String whereClause = "id ='" + b_tid + "'";

            int rowsAffected = db.update(DB_TABLE, rec, whereClause, null);

            recSet.close();
            db.close();
            return rowsAffected; //傳回被變更幾筆
        } else {
            recSet.close();
            db.close();
            return -1;
        }

    }

    public long insertRec_m(ContentValues rec) {
        SQLiteDatabase db = getWritableDatabase();
        long rowID = db.insert(DB_TABLE, null, rec);
        db.close();
        return rowID;
    }
}
//    private String u_chinagrp(int input_i) {
//        String c_number = "";
//        String china_no[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
//        c_number = china_no[input_i % 10];
//
//        return c_number;
//    }
//
//    private String u_chinayear(int input_i) {
//        String c_number = "";
//        String china_no[] = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉"};
//        c_number = china_no[input_i % 10];
//        return c_number;
//    }
