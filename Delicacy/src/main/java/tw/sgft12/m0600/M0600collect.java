package tw.sgft12.m0600;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class M0600collect extends AppCompatActivity implements View.OnClickListener {
    private TextView count_t;
    private Button b001, b002, b003, b004;
    private EditText e001, e002, e003;
    private TextView e004;

    private M0600FriendDbHelper dbHper;
    private static final String DB_FILE = "sgft.db";
    private static final String DB_TABLE = "m0600_member";
    private static final int DBversion = 1;

    private TextView t001;
    private TextView tvTitle;
    private Button btNext;
    private Button btPrev;
    private Button btTop;
    private Button btEnd;
    private int index = 0; //宣告公有變數,沒有值時為object 要改為int!!!
    private ArrayList<String> recSet = null;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double range = 250; //設定touchevent移動距離
    private double ran = 60; //
    private Vibrator myVibrator;
    private Button btDelete;
    private Button btUpdate;
    private TextView id;
    private String tid;

    protected static final int BUTTON_POSITIVE = -1;
    protected static final int BUTTON_NEGATIVE = -2;
    private String msg;
    private String tname;
    private String tgrp;
    private String taddr;
    private int rowsAffected;
    private String tdes;
    private String TAG = "111";
    private String sqlctl;
    private ImageView mCoIMG;
    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        GoogleSignInAccount account = new Intent(this.getIntent()).getParcelableExtra("googleAccount");
//        mEmail = account.getEmail();
        Intent intent01 = this.getIntent();
        mEmail = intent01.getStringExtra("googleemail");

        setContentView(R.layout.m0600_collect);
        setupViewComponent();


//        initDB();
//        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");

    }


    private void setupViewComponent() {
        tvTitle = (TextView) findViewById(R.id.tvIdTitle);
        e001 = (EditText) findViewById(R.id.edtName2);
        e002 = (EditText) findViewById(R.id.edtGrp);
        e003 = (EditText) findViewById(R.id.edtAddr);
        id = (TextView) findViewById(R.id.edtName1);
        id.setKeyListener(null);
        e004 = (TextView) findViewById(R.id.m0600_collect_descr);
        e004.setMovementMethod(ScrollingMovementMethod.getInstance());
        e004.scrollTo(0, 0);//textview 回頂端

        mCoIMG = (ImageView) findViewById(R.id.m0600_collectIMG);

        count_t = (TextView) findViewById(R.id.count_t);


        btNext = (Button) findViewById(R.id.btIdNext);
        btPrev = (Button) findViewById(R.id.btIdPrev);
        btTop = (Button) findViewById(R.id.btIdtop);
        btEnd = (Button) findViewById(R.id.btIdend);

        btDelete = (Button) findViewById(R.id.btIdDelete);

        btNext.setOnClickListener(this);
        btPrev.setOnClickListener(this);
        btTop.setOnClickListener(this);
        btEnd.setOnClickListener(this);

        btDelete.setOnClickListener(this);

        initDB();
        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
        showRec(index);

        // 宣告震動
        myVibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
//        collectlist = (Button)findViewById(R.id.m_mysql);
//        collectlist.performClick();
        //----------------把MySQL匯入到SQLite--------------
        dbmysql();
        recSet = dbHper.getRecSet();
        index = 0;
        showRec(index);
        //----------------把MySQL匯入到SQLite,結束----------
    }


    private void initDB() {
        if (dbHper == null) {
            dbHper = new M0600FriendDbHelper(this, DB_FILE, null, DBversion);
        }

        recSet = dbHper.getRecSet();
        int a = 0;
    }

    private void showRec(int index) {
        if (index >= recSet.size()) {
            index = recSet.size() - 1;
        }

        if (recSet.size() >= 1) { //陣列用size ,json用length
            String stHead = "顯示資料：第 " + (index + 1) + " 筆 / 共 " + recSet.size() + " 筆";
            tvTitle.setTextColor(ContextCompat.getColor(this, R.color.Blue));
            tvTitle.setText(stHead);
//-----------------------------------------------------------------
            String[] fld = recSet.get(index).split("#"); //自動產生一維
            //id.setTextColor(ContextCompat.getColor(this, R.color.Red));
            //id.setBackgroundColor(ContextCompat.getColor(this, R.color.Yellow));
            id.setText(fld[0]);
            e001.setText(fld[1]);
            e002.setText(fld[2]);
            e003.setText(fld[3]);
            e004.setText(fld[4]);

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (fld[5].contains("http")) {
                        Glide.with(M0600collect.this).load(fld[5]).into(mCoIMG);
                    } else {
                        Glide.with(M0600collect.this).load("https://tcnr2021-15.000webhostapp.com/post_img/t008.jpg").into(mCoIMG);
                    }

                }
            });

            e004.scrollTo(0, 0); //textview 回頂端
//------------------------------------------------------------------
        } else {
            id.setText("");
            e001.setText("");
            e002.setText("");
            e003.setText("");
            e004.setText("");
            Glide.with(M0600collect.this).load("https://tcnr2021-15.000webhostapp.com/post_img/t008.jpg").into(mCoIMG);
            count_t.setText("共計"+recSet.size()+"筆");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btIdNext:
                ctrNext();
                break;

            case R.id.btIdPrev:
                ctrPrev();
                break;

            case R.id.btIdtop:
                ctrFirst();
                break;

            case R.id.btIdend:
                ctrEnd();
                break;

            case R.id.btIdDelete:
//                MyAlertDialog aldDial = new MyAlertDialog(M0600collect.this);
//                aldDial.setTitle(getString(R.string.m0600_clear));
//                aldDial.setMessage(getString(R.string.m0600_check));
//                aldDial.setIcon(android.R.drawable.star_big_on);
//                aldDial.setCancelable(false);
//                aldDial.setButton(BUTTON_POSITIVE, getString(R.string.m0600_Yes), aldBtnListener);
//                aldDial.setButton(BUTTON_NEGATIVE, getString(R.string.m0600_No), aldBtnListener);
//                aldDial.show();
                ArrayList<String> sqlList = new ArrayList<>();
                sqlList.add(id.getText().toString());
                M06DBConnector.executeDelet(sqlList);
                refreshActivity();

                break;
        }
    }

    private void refreshActivity() {
        dbmysql();
        showRec(index);
    }


    private DialogInterface.OnClickListener aldBtnListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    tid = id.getText().toString().trim();
                    rowsAffected = dbHper.deleteRec(tid);  // delete record
                    if (rowsAffected == -1) {
                        msg = "資料表已空, 無法刪除 !";
                    } else if (rowsAffected == 0) {
                        msg = "找不到欲刪除的記錄, 無法刪除 !";
                    } else {
                        msg = "第 " + (index + 1) + " 筆記錄  已刪除 ! \n" + "共 " + rowsAffected + " 筆記錄   被刪除 !";
                        recSet = dbHper.getRecSet();

                        if (index == dbHper.RecCount()) {
                            index--; //
                        }
                        showRec(index); //重構
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    break;
                case BUTTON_NEGATIVE:
                    msg = getString(R.string.m0600_msg2);
                    break;
            }
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private void ctrFirst() {
        index = 0;
        showRec(index);
    }

    private void ctrPrev() {
        if (index == 0) {
            index = recSet.size() - 1;
        } else {
            index--;
        }
        showRec(index);
    }

    private void ctrNext() {
        if (index == (recSet.size() - 1)) {
            index = 0;
        } else {
            index++;
        }
        showRec(index);
    }

    private void ctrEnd() {
        index = recSet.size() - 1;
        showRec(index);
    }
//-------------------------------------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: //拖移
                Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(1000); // 震動五秒
                break;

            case MotionEvent.ACTION_DOWN: //按下
                x1 = event.getX(); //按下的X軸位置
                y1 = event.getY(); //按下的Y軸位置
                break;

            case MotionEvent.ACTION_UP: //放開
                x2 = event.getX(); //觸控放開
                y2 = event.getY(); //
                // ========================================
                // 判斷左右的方法，因為屏幕的左上角是：0，0 點右下角是max,max
                // 並且移動距離需大於 > range
                double xbar = Math.abs(x2 - x1);
                double ybar = Math.abs(y2 - y1);
                double z = Math.sqrt(xbar * xbar + ybar * ybar); //z等於X,Y的斜邊
                int angle = Math.round((float) (Math.asin(ybar / z) / Math.PI * 180));// 角度
                if (xbar != 0 && ybar != 0) {
                    if (x1 - x2 > range) { // 向左滑動
                        ctrPrev();
                    }
                    if (x1 - x2 < range) { // 向右滑動
                        ctrNext();
                        // t001.setText("向右滑動\n" + "滑動參值x1=" + x1 + " x2=" + x2 + "
                        // r=" + (x2 - x1)+"\n"+"ang="+angle);
                    }
                    if (y2 - y1 > range && angle > ran) { // 向下滑動
                        // 往下角度需大於50
                        // 最後一筆
                        ctrEnd();
                    }
                    if (y1 - y2 > range && angle > ran) { // 向上滑動
                        // 往上角度需大於50
                        ctrFirst();// 第一筆
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void dbmysql() {
//        sqlctl = "SELECT  *  FROM  m6_collect  ORDER  BY  id  ASC";
//        sqlctl = "SELECT  *  FROM  m6_collect  WHERE member_id = $email ORDER  BY  id  ASC";
        sqlctl = "SELECT  *  FROM  m6_collect WHERE member_id = '" + mEmail + "' ORDER BY id";
//        sqlctl = "SELECT  * FROM m6_member WHERE email = '" + email + "' ORDER BY id";
        ArrayList<String> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add("query");
        nameValuePairs.add(sqlctl);
//        query
        try {
            String result = M06DBConnector.executeQuery(nameValuePairs);
            //==========================================
//            chk_httpstate();  //檢查 連結狀態
            //==========================================
            int a = 0;
            /**************************************************************************
             * SQL 結果有多筆資料時使用JSONArray
             * 只有一筆資料時直接建立JSONObject物件 JSONObject
             * jsonData = new JSONObject(result);
             **************************************************************************/

            // -------------------------------------------------------
            if (!result.equals("0")) { // MySQL 連結成功有資料
                JSONArray jsonArray = new JSONArray(result);
                int rowsAffected = dbHper.clearRec();                 // 匯入前,刪除所有SQLite資料
                // 處理JASON 傳回來的每筆資料
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    ContentValues newRow = new ContentValues();
                    // --(1) 自動取的欄位 --取出 jsonObject 每個欄位("key","value")-----------------------
                    Iterator itt = jsonData.keys();
                    while (itt.hasNext()) {
                        String key = itt.next().toString();
                        String value = jsonData.getString(key); // 取出欄位的值
                        if (value == null) {
                            continue;
                        } else if ("".equals(value.trim())) {
                            continue;
                        } else {
                            jsonData.put(key, value.trim());
                        }
                        // ------------------------------------------------------------------
                        newRow.put(key, value.toString()); // 動態找出有幾個欄位
                        // -------------------------------------------------------------------
                    }
                    // ---(2) 使用固定已知欄位---------------------------
                    // newRow.put("id", jsonData.getString("id").toString());
                    // newRow.put("name",
                    // jsonData.getString("name").toString());
                    // newRow.put("grp", jsonData.getString("grp").toString());
                    // newRow.put("address", jsonData.getString("address")
                    // -------------------加入SQLite---------------------------------------
                    long rowID = dbHper.insertRec_m(newRow);
                    Toast.makeText(getApplicationContext(), "共匯入 " + Integer.toString(jsonArray.length()) + " 筆資料", Toast.LENGTH_SHORT).show();
                }
                // ---------------------------
            } else {
                int rowsAffected = dbHper.clearRec();
                Toast.makeText(getApplicationContext(), "主機資料庫無資料", Toast.LENGTH_LONG).show();
            }
            recSet = dbHper.getRecSet();  //重新載入SQLite
//            setupViewComponent();
//            u_setspinner();
            // --------------------------------------------------------
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    //--------------------------------------------------------------
    @Override
    protected void onPause() {
        super.onPause();
        if (dbHper != null) {
            dbHper.close();
            dbHper = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHper == null)
            dbHper = new M0600FriendDbHelper(this, DB_FILE, null, DBversion);
    }

    @Override
    protected void onStop() {
        if (myVibrator != null) //震動
            myVibrator.cancel();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.m0600_sub, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.m_return:
                finish();
                break;

        }
        return true;
    }
}


