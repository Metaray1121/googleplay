package tw.sgft12.m0600;


import android.accounts.Account;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class M0600 extends AppCompatActivity implements View.OnClickListener {
    String[] abc;
    //==============================================================================
    String[] taipei = {"中正區", "大同區", "中山區", "萬華區", "信義區", "松山區", "大安區", "南港區", "北投區", "內湖區", "士林區", "文山區"};
    String[] NewTaipei = {"板橋區", "新莊區", "泰山區", "林口區", "淡水區", "金山區", "八里區", "萬里區", "石門區", "三芝區", "瑞芳區", "汐止區", "平溪區", "貢寮區", "雙溪區", "深坑區", "石碇區", "新店區", "坪林區", "烏來區", "中和區", "永和區", "土城區", "三峽區", "樹林區", "鶯歌區", "三重區", "蘆洲區", "五股區"};
    String[] Keelung = {"仁愛區", "中正區", "信義區", "中山區", "安樂區", "暖暖區", "七堵區"};
    String[] Taoyuan = {"桃園區", "中壢區", "平鎮區", "八德區", "楊梅區", "蘆竹區", "龜山區", "龍潭區", "大溪區", "大園區", "觀音區", "新屋區", "復興區"};
    String[] Hsinchu = {"竹北市", "竹東鎮", "新埔鎮", "關西鎮", "峨眉鄉", "寶山鄉", "北埔鄉", "橫山鄉", "芎林鄉", "湖口鄉", "新豐鄉", "尖石鄉", "五峰鄉"};
    String[] Hsinchu2 = {"東區", "北區", "香山區"};
    String[] Miaoli = {"苗栗市", "通霄鎮", "苑裡鎮", "竹南鎮", "頭份鎮", "後龍鎮", "卓蘭鎮", "西湖鄉", "頭屋鄉", "公館鄉", "銅鑼鄉", "三義鄉", "造橋鄉", "三灣鄉", "南庄鄉", "大湖鄉", "獅潭鄉", "泰安鄉"};
    String[] Taichung = {"中區", "東區", "南區", "西區", "北區", "北屯區", "西屯區", "南屯區", "太平區", "大里區", "霧峰區", "烏日區", "豐原區", "后里區", "東勢區", "石岡區", "新社區", "和平區", "神岡區", "潭子區", "大雅區", "大肚區", "龍井區", "沙鹿區", "梧棲區", "清水區", "大甲區", "外埔區", "大安區"};
    String[] Nantou = {"南投市", "埔里鎮", "草屯鎮", "竹山鎮", "集集鎮", "名間鄉", "鹿谷鄉", "中寮鄉", "魚池鄉", "國姓鄉", "水里鄉", "信義鄉", "仁愛鄉"};
    String[] Changhua = {"彰化市", "員林鎮", "和美鎮", "鹿港鎮", "溪湖鎮", "二林鎮", "田中鎮",
            "北斗鎮", "花壇鄉", "芬園鄉", "大村鄉", "永靖鄉", "伸港鄉", "線西鄉", "福興鄉", "秀水鄉",
            "埔心鄉", "埔鹽鄉", "大城鄉", "芳苑鄉", "竹塘鄉", "社頭鄉", "二水鄉", "田尾鄉", "埤頭鄉", "溪州鄉"};
    String[] Yunlin = {"斗六市", "斗南鎮", "虎尾鎮", "西螺鎮", "土庫鎮", "北港鎮", "莿桐鄉", "林內鄉", "古坑鄉", "大埤鄉", "崙背鄉", "二崙鄉", "麥寮鄉", "臺西鄉", "東勢鄉", "褒忠鄉", "四湖鄉", "口湖鄉", "水林鄉", "元長鄉"};
    String[] Chiayi = {"太保市", "朴子市", "布袋鎮", "大林鎮", "民雄鄉", "溪口鄉", "新港鄉", "六腳鄉", "東石鄉", "義竹鄉", "鹿草鄉", "水上鄉", "中埔鄉", "竹崎鄉", "梅山鄉", "番路鄉", "大埔鄉", "阿里山鄉"};
    String[] Chiayi2 = {"東區", "西區"};
    String[] Tainan = {"中西區", "東區", "南區", "北區", "安平區", "安南區", "永康區", "歸仁區", "新化區", "左鎮區", "玉井區", "楠西區", "南化區", "仁德區", "關廟區", "龍崎區", "官田區", "麻豆區", "佳里區", "西港區", "七股區", "將軍區", "學甲區", "北門區", "新營區", "後壁區", "白河區", "東山區", "六甲區", "下營區", "柳營區", "鹽水區", "善化區", "大內區", "山上區", "新市區", "安定區"};
    String[] Kaohsiung = {"楠梓區", "左營區", "鼓山區", "三民區", "鹽埕區", "前金區", "新興區", "苓雅區", "前鎮區", "小港區", "旗津區", "鳳山區", "大寮區", "鳥松區", "林園區", "仁武區", "大樹區", "大社區", "岡山區", "路竹區", "橋頭區", "梓官區", "彌陀區", "永安區", "燕巢區", "田寮區", "阿蓮區", "茄萣區", "湖內區", "旗山區", "美濃區", "內門區", "杉林區", "甲仙區", "六龜區", "茂林區", "桃源區", "那瑪夏區"};
    String[] Pingtung = {"屏東市", "潮州鎮", "東港鎮", "恆春鎮", "萬丹鄉", "長治鄉", "麟洛鄉", "九如鄉", "里港鄉", "鹽埔鄉", "高樹鄉", "萬巒鄉", "內埔鄉", "竹田鄉", "新埤鄉", "枋寮鄉", "新園鄉", "崁頂鄉", "林邊鄉", "南州鄉", "佳冬鄉", "琉球鄉", "車城鄉", "滿州鄉", "枋山鄉", "霧台鄉", "瑪家鄉", "泰武鄉", "來義鄉", "春日鄉", "獅子鄉", "牡丹鄉", "三地門鄉"};
    String[] Yilan = {"宜蘭市", "羅東鎮", "蘇澳鎮", "頭城鎮", "礁溪鄉", "壯圍鄉", "員山鄉", "冬山鄉", "五結鄉", "三星鄉", "大同鄉", "南澳鄉"};
    String[] Hualien = {"花蓮市", "鳳林鎮", "玉里鎮", "新城鄉", "吉安鄉", "壽豐鄉", "秀林鄉", "光復鄉", "豐濱鄉", "瑞穗鄉", "萬榮鄉", "富里鄉", "卓溪鄉"};
    String[] Taitung = {"臺東市", "成功鎮", "關山鎮", "長濱鄉", "海端鄉", "池上鄉", "東河鄉", "鹿野鄉", "延平鄉", "卑南鄉", "金峰鄉", "大武鄉", "達仁鄉", "綠島鄉", "蘭嶼鄉", "太麻里鄉"};
    String[] Penghu = {"馬公市", "湖西鄉", "白沙鄉", "西嶼鄉", "望安鄉", "七美鄉"};
    String[] Kinmen = {"金城鎮", "金湖鎮", "金沙鎮", "金寧鄉", "烈嶼鄉", "烏坵鄉"};
    String[] Lienchiang = {"南竿鄉", "北竿鄉", "莒光鄉", "東引鄉"};
    //===============================================================================

    private M0600FriendDbHelper dbHper;
    private static final String DB_FILE = "sgft.db";
    private static final String DB_TABLE = "m0600_member";
    private static final int DBversion = 1;
    private RecyclerView recyclerView;
    private TextView mTxtResult;
    private TextView mDesc;
    private ArrayList<HashMap<String, String>> arrayList;
    private List<Map<String, Object>> mList;
    private SwipeRefreshLayout laySwipe;
    private int total;
    private TextView t_count;
    private TextView u_loading;
    private ConstraintLayout li01;
    private int nowposition = 0;
    private int t_total = 0;
    //    private String ul = "https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json";
//    private String ul = "https://tcnr122021.000webhostapp.com/OpenData/m0600project_opendata.json";
    private String ul = "https://gis.taiwan.net.tw/XMLReleaseALL_public/restaurant_C_f.json";

    private Button mapclick;
    private TextView mLat;
    private TextView mLng;
    private TextView address;
    private Uri uri;
    private Intent intent;
    private Spinner sp001, sp002;
    private ConstraintLayout L1;
    private ArrayAdapter<CharSequence> spls1;
    public RecyclerAdapter0600 adapter;
    private Button mCollect;
    private String addlocation2;
    private TextView Tel;
    private String tel2;
    private ArrayAdapter<String> spls2;
    private String des2;
    private String px2;
    private String py2;
    private String start2;
    private String end12;
    private String zipcode2;
    private String region2;
    private String town2;
    private String level2;
    private String id2;
    private String picture;

    private TextView Cstart;
    private TextView Cend1;
    private TextView Czipcode;
    private TextView Cregion;
    private TextView Ctown;
    private TextView Clevel;
    private TextView CPx;
    private TextView CPy;
    private TextView Cdes;
    private TextView Cpicture;

    private TextView Cid2;
    private TextView mRecommend;
    private BottomNavigationView mbottom_menu;
    private String memberid;
    private String sqlctl;
    private String TAG = "1111";
    private ArrayList<String> recSet;
    private String mDisplayName;
    private String mPhotoUrl;
    private String mEmail;
    private String mGivenname;
    private String mId;
    private Intent intent01 = new Intent();
//    private String g_Account; //無使用googlesignin


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableStrictMode(this);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);

        GoogleSignInAccount account = new Intent(this.getIntent()).getParcelableExtra("googleAccount");
        mDisplayName = account.getDisplayName();
        if(account.getPhotoUrl() == null){
            mPhotoUrl = "NoPhotoUrl";
        }else{
            mPhotoUrl = account.getPhotoUrl().toString();
        }
        mEmail = account.getEmail();
        mGivenname = account.getGivenName();
        mId = account.getId();

////-----------------無使用googlesignin---------
//        Intent intent01 = this.getIntent();
//        mEmail = intent01.getStringExtra("googleemail");
//        mGivenname = intent01.getStringExtra("googleGivenname");
//        mId = intent01.getStringExtra("googleid");
//        mPhotoUrl = intent01.getStringExtra("g_PhotoUrl");
//        mDisplayName = intent01.getStringExtra("googlename");
//        g_Account = intent01.getStringExtra("googleAccount");
////-------------------------------------------------

        int bb = 0;

        setContentView(R.layout.m0600);
        setupViewComponent();
        MySQL_Select(mEmail);
    }

    private void enableStrictMode(Context context) {
        //-------------抓取遠端資料庫設定執行續------------------------------
        StrictMode.setThreadPolicy(new
                StrictMode.
                        ThreadPolicy.Builder().
                detectDiskReads().
                detectDiskWrites().
                detectNetwork().
                penaltyLog().
                build());
//        StrictMode.setVmPolicy(
//                new
//                        StrictMode.
//                                VmPolicy.
//                                Builder().
//                        detectLeakedSqlLiteObjects().
//                        penaltyLog().
//                        penaltyDeath().
//                        build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .penaltyLog()
                        .build());
    }

    private void setupViewComponent() {
        //--------------------
        mRecommend = (TextView) findViewById(R.id.m0600_recommend);
        mbottom_menu = findViewById(R.id.m0600_bottom_menu);
        mbottom_menu.setOnItemSelectedListener(bottomOn);
        //-------------------
        mCollect = (Button) findViewById(R.id.m0600_collect);
        mCollect.setOnClickListener(this);
        L1 = (ConstraintLayout) findViewById(R.id.L1);
        sp001 = (Spinner) findViewById(R.id.m0600_sp001);
        sp002 = (Spinner) findViewById(R.id.m0600_sp002);
        li01 = (ConstraintLayout) findViewById(R.id.li01);
        li01.setVisibility(View.GONE);
        mTxtResult = findViewById(R.id.m0600_name);
        mDesc = findViewById(R.id.m0600_descr);
        mDesc.setMovementMethod(ScrollingMovementMethod.getInstance());
        mDesc.scrollTo(0, 0);//textview 回頂端

        mapclick = (Button) findViewById(R.id.m0600_map);
        mapclick.setOnClickListener(this);
        mLat = (TextView) findViewById(R.id.m0600_Lat);
        mLng = (TextView) findViewById(R.id.m0600_Lng);
        address = (TextView) findViewById(R.id.m0600_address);
        Tel = (TextView) findViewById(R.id.m0600_Tel);
        Cstart = (TextView) findViewById(R.id.m0600_start);
        Cend1 = (TextView) findViewById(R.id.m0600_end1);
        Czipcode = (TextView) findViewById(R.id.m0600_zipcode);
        Cregion = (TextView) findViewById(R.id.m0600_region);
        Ctown = (TextView) findViewById(R.id.m0600_town);
        Clevel = (TextView) findViewById(R.id.m0600_level);
        Cid2 = (TextView) findViewById(R.id.m0600_id2);
        CPx = (TextView) findViewById(R.id.m0600_Px);
        CPy = (TextView) findViewById(R.id.m0600_Py);
        Cdes = (TextView) findViewById(R.id.m0600_des);
        Cpicture = (TextView) findViewById(R.id.m0600_picture);

        recyclerView = findViewById(R.id.recyclerView);
        t_count = findViewById(R.id.count);
        //--------------------RecyclerView設定 上下滑動------------------
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                li01.setVisibility(View.GONE);
//                L1.setVisibility(View.VISIBLE);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                mbottom_menu.setVisibility(View.INVISIBLE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //--------------監聽sp001,sp002--------
        sp001.setOnItemSelectedListener(sp001on);
        spls1 = ArrayAdapter.createFromResource(
                this,
                R.array.descr, //選擇"縣市"
                R.layout.m0600spinner_item);
        sp001.setAdapter(spls1);


        //--------------設定下載中-----------
        u_loading = findViewById(R.id.u_loading);
        u_loading.setVisibility(View.GONE);
        //-------------------------------------
        laySwipe = findViewById(R.id.m0600_laySwipe);
        laySwipe.setOnRefreshListener(onSwipeToRefresh);
        laySwipe.setSize(SwipeRefreshLayout.LARGE);
        // 設置下拉多少距離之後開始刷新數據
        laySwipe.setDistanceToTriggerSync(300);
        // 設置進度條背景顏色
        laySwipe.setProgressBackgroundColorSchemeColor(getColor(android.R.color.background_light));
        // 設置刷新動畫的顏色，可以設置1或者更多
        laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_purple,
                android.R.color.holo_orange_dark);

/*        setProgressViewOffset : 設置進度圓圈的偏移量。
        第一個參數表示進度圈是否縮放，
        第二個參數表示進度圈開始出現時距頂端的偏移，
        第三個參數表示進度圈拉到最大時距頂端的偏移。*/
        laySwipe.setProgressViewOffset(true, 0, 50);
//====================
        onSwipeToRefresh.onRefresh();  //開始轉圈下載資料
        //-------------------------
        initDB();
    }
//-----------------------

    //--------------------
    private String tname;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m0600_collect:
                // 檢查name跟在e001上打得是否有有此筆資料
                picture = Cpicture.getText().toString().trim();
                tname = mTxtResult.getText().toString().trim();
                addlocation2 = address.getText().toString();
                tel2 = Tel.getText().toString();
                des2 = Cdes.getText().toString();
                px2 = CPx.getText().toString().trim();
                py2 = CPy.getText().toString().trim();
                start2 = Cstart.getText().toString().trim();
                end12 = Cend1.getText().toString().trim();
                zipcode2 = Czipcode.getText().toString().trim();
                region2 = Cregion.getText().toString().trim();
                town2 = Ctown.getText().toString().trim();
                level2 = Clevel.getText().toString().trim();
                id2 = Cid2.getText().toString().trim();
                Toast.makeText(getApplicationContext(), "已新增", Toast.LENGTH_SHORT).show();

//            long rowID = dbHper.insertRec(tname, addlocation2, tel2, des2, px2, py2, start2, end12,
//                    zipcode2, region2, town2, level2, id2, picture);
//================MySQL==================
                mysql_insert();
//==================================
                break;

            case R.id.m0600_map:
                //----------------------------使用經緯度
                String latlocation = mLat.getText().toString(); //緯度
                String lnglocation = mLng.getText().toString(); //經度
                String labellocation = mTxtResult.getText().toString();
                String addlocation = address.getText().toString();
//
//        String urlAdress = "http://maps.google.com/maps?q=" + lnglocation + "," + latlocation + "(" +
//                labellocation+ ")&iwloc=A&hl=hl=zh-TW";
//        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAdress));
//        startActivity(it);

//--------------------------使用住址
//        String serch_address = labellocation;//用名稱找
                String serch_address = addlocation;//用住址找
                String location = "geo:0,0?q=" + serch_address;
                Uri uri = Uri.parse(location);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);

                //Toast.makeText(getApplicationContext(),"ANS="+ mLat.getText().toString() + mLng.getText().toString(),Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"ANS" + address.getText().toString(),Toast.LENGTH_SHORT).show();

                break;
        }
    }

    //------------------檢查帳號是否存在-------------
    private void MySQL_Select(String email) {
        String selectMYSQL = "";
        String result = "";
        try {
            sqlctl = "SELECT  * FROM m6_member WHERE email = '" + email + "' ORDER BY id";
            ArrayList<String> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(sqlctl);
            result = M06DBConnector.executeQuery(nameValuePairs);
//            Log.d(TAG, "result=" + result + "result.length()=" + result.length());
            int a = 0;
            if (result.length() <= 16) {

                mysql_create_member();

            }
        } catch (Exception e) {
            Log.d("Error=>", e.toString());
        }
    }

    //------------------創建
    private void mysql_create_member() {
        //        sqlctl = "SELECT * FROM member ORDER BY id ASC";
        ArrayList<String> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(sqlctl);

        nameValuePairs.add(mDisplayName);
        nameValuePairs.add(mEmail);
        nameValuePairs.add(mPhotoUrl);

        try {
            Thread.sleep(500); //  延遲Thread 睡眠0.5秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//-----------------------------------------------
        String result = M06DBConnector.executeCreateMember(nameValuePairs);  //真正執行新增
//-----------------------------------------------
    }

    private void mysql_insert() {
        //        sqlctl = "SELECT * FROM member ORDER BY id ASC";
        ArrayList<String> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(sqlctl);
        nameValuePairs.add(tname);
        nameValuePairs.add(tel2);
        nameValuePairs.add(addlocation2);
        nameValuePairs.add(des2);
        nameValuePairs.add(picture);
        nameValuePairs.add(zipcode2);
        nameValuePairs.add(px2);
        nameValuePairs.add(py2);
        start2 = "NoStartTime";
        nameValuePairs.add(start2);
        end12 = "NoEndTime";
        nameValuePairs.add(end12);
        nameValuePairs.add(region2);
        nameValuePairs.add(town2);
        nameValuePairs.add(level2);
        nameValuePairs.add(id2);
        memberid = mEmail;
        nameValuePairs.add(memberid);


        try {
            Thread.sleep(500); //  延遲Thread 睡眠0.5秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//-----------------------------------------------
        String result = M06DBConnector.executeInsert(nameValuePairs);  //真正執行新增
//-----------------------------------------------
    }

    private void initDB() {
        if (dbHper == null) {
            dbHper = new M0600FriendDbHelper(this, DB_FILE, null, DBversion);
        }
        recSet = dbHper.getRecSet(); //
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

    public String City = "新北市";
    AdapterView.OnItemSelectedListener sp001on = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String[] CityArray = getResources().getStringArray(R.array.descr);
            City = CityArray[position];

            if (position == 0) {
                City = "新北市";
                abc = NewTaipei;
                return;
            }
            switch (position) {
                case 1:
                    abc = Keelung;
                    break;

                case 2:
                    abc = taipei;
                    break;

                case 3:
                    abc = NewTaipei;
                    break;

                case 4:
                    abc = Taoyuan;
                    break;

                case 5:
                    abc = Hsinchu2;
                    break;

                case 6:
                    abc = Hsinchu;
                    break;

                case 7:
                    abc = Miaoli;
                    break;

                case 8:
                    abc = Taichung;
                    break;

                case 9:
                    abc = Changhua;
                    break;

                case 10:
                    abc = Nantou;
                    break;

                case 11:
                    abc = Yunlin;
                    break;

                case 12:
                    abc = Chiayi2;
                    break;

                case 13:
                    abc = Chiayi;
                    break;

                case 14:
                    abc = Tainan;
                    break;

                case 15:
                    abc = Kaohsiung;
                    break;

                case 16:
                    abc = Pingtung;
                    break;

                case 17:
                    abc = Yilan;
                    break;

                case 18:
                    abc = Hualien;
                    break;

                case 19:
                    abc = Taitung;
                    break;

                case 20:
                    abc = Lienchiang;
                    break;

                case 21:
                    abc = Penghu;
                    break;

                case 22:
                    abc = Kinmen;
                    break;
            }
            //City = abc[position];

//                    onSwipeToRefresh.onRefresh();
            setDatatolist();
            adapter.notifyDataSetChanged();
            sp002.setVisibility(View.VISIBLE);
            spls2 = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.m0600spinner_item,
                    abc
            );
            sp002.setAdapter(spls2);
            //
            sp002.setOnItemSelectedListener(sp002on);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private String Spinnertown = "板橋區";
    AdapterView.OnItemSelectedListener sp002on = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Spinnertown = abc[position];
            Toast.makeText(getApplicationContext(), Spinnertown, Toast.LENGTH_SHORT).show();
//            onSwipeToRefresh.onRefresh();
            setDatatolist();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    //===========================
    private final SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

//-------------------------------------
                    mTxtResult.setText("");
                    MyAlertDialog0600 myAltDlg = new MyAlertDialog0600(M0600.this);
                    myAltDlg.setTitle(getString(R.string.m0600_dialog_title));
                    myAltDlg.setMessage(getString(R.string.m0600_dialog_t001) + getString(R.string.m0600_dialog_b001));
                    myAltDlg.setIcon(android.R.drawable.ic_menu_rotate);
                    myAltDlg.setCancelable(false);
                    myAltDlg.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.m0600_dialog_positive), altDlgOnClkPosiBtnLis);
                    myAltDlg.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.m0600_dialog_neutral), altDlgOnClkNeutBtnLis);
                    myAltDlg.show();
//------------------------------------

                }
            };
    private DialogInterface.OnClickListener altDlgOnClkPosiBtnLis = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
//-----------------開始執行下載----------------
            laySwipe.setRefreshing(true);
            u_loading.setVisibility(View.VISIBLE);

            mTxtResult.setText(getString(R.string.m0600_name) + "");
//            mTxtResult.setText("");
            mDesc.setText("");
            mDesc.scrollTo(0, 0);//textview 回頂端


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//=================================
                    setDatatolist();
//=================================
//----------SwipeLayout 結束 --------
//可改放到最終位置 u_importopendata()
                    u_loading.setVisibility(View.GONE);
                    laySwipe.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), getString(R.string.m0600_loadover), Toast.LENGTH_SHORT).show();
                }
            }, 1000);  //10秒
        }
    };

    private DialogInterface.OnClickListener altDlgOnClkNeutBtnLis = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            u_loading.setVisibility(View.GONE);
            laySwipe.setRefreshing(false);
        }
    };

    private void setDatatolist() { //放JSON 到 RecyclerView
        u_importopendata();  //(1)下載Opendata
        //設定Adapter
        final ArrayList<Post0600> mData = new ArrayList<>();
        for (Map<String, Object> m : mList) {
            if (m != null) {
                String Name = m.get("Name").toString().trim(); //名稱
                String Add = m.get("Add").toString().trim(); //住址
                String Picture1 = m.get("Picture1").toString().trim(); //圖片
                String Description = m.get("Description").toString().trim();//描述
                String Px = m.get("Px").toString().trim();
                String Py = m.get("Py").toString().trim();
                String Region = m.get("Region").toString().trim();
                String Tel = m.get("Tel").toString().trim();
                String Town = m.get("Town").toString().trim();
                String id2 = m.get("Id").toString().trim();
                if (Picture1.isEmpty() || Picture1.length() < 1) {
                    Picture1 = "https://tcnr122021.000webhostapp.com/OpenData/nopic1.jpg";
                }
                if (Description.isEmpty() || Description.length() < 1) {
                    Description = "必住飯店!!!";
                }
                // String Description = m.get("Description").toString().trim(); //描述
                String Zipcode = m.get("Zipcode").toString().trim(); //描述
//************************************************************
                mData.add(new Post0600(Name, Picture1, Add, Description, Zipcode, Px, Py, Region, Tel, Town, id2));
//************************************************************
            } else {
                return;
            }
        }

        adapter = new RecyclerAdapter0600(this, mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
// ************************************
        adapter.setOnItemClickListener(new RecyclerAdapter0600.OnItemClickListener() { //點擊監聽
            @Override
            public void onItemClick(View view, int position) {
                L1.setVisibility(View.GONE);
                li01.setVisibility(View.VISIBLE);
//                Toast.makeText(M2205.this, "onclick" + mData.get(position).hotelName.toString(), Toast.LENGTH_SHORT).show();
//                mTxtResult.setText(getString(R.string.m0600_name) + mData.get(position).Name); //mData.get(position).Name 抓opendata的關鍵字
                mTxtResult.setText(mData.get(position).Name);

                mDesc.setText(mData.get(position).Content);
                mDesc.scrollTo(0, 0); //textview 回頂端

                nowposition = position;
                t_count.setText(getString(R.string.m0600_ncount) + "   (" + (nowposition + 1) + ")" + total + "/" + t_total);

                //--------------------------------------------
                mLat.setText(mData.get(position).Px);
                mLng.setText(mData.get(position).Py);
                address.setText(mData.get(position).Add);
                Tel.setText("0" + mData.get(position).Tel.substring(4));
                //------------
                Cpicture.setText(mData.get(position).posterThumbnailUrl);
                Cdes.setText(mData.get(position).Content);
                CPx.setText(mData.get(position).Px);
                CPy.setText(mData.get(position).Py);
                Czipcode.setText(mData.get(position).Zipcode);
                Cregion.setText(mData.get(position).Region);
                Ctown.setText(mData.get(position).Town);
                Cid2.setText(mData.get(position).id2);
                Cstart.setText("");
                Cend1.setText("");
                Clevel.setText("6");
//--------------------------------
            }
        });
//*************************************
        recyclerView.setAdapter(adapter);
    }

    //-------------------------------------------------
////    String url = "https://gis.taiwan.net.tw/XMLReleaseALL_public/hotel_C_f.json";  //旅館民宿 - 觀光資訊資料庫
////    Id、Name、Grade、Add、Zipcode、Region、Town、Tel、Fax、Gov、Picture1、Picture2、Picture3、Px、Py、Class、Map、TotalN_oms、Lowest_ice、Ceilin_ice、TaiwanHost、Indust_ail、TotalN_ple、Access_oms、Public_ets、Liftin_ent、Parkin_ace
////    String url = "https://datacenter.taichung.gov.tw/swagger/OpenData/c60986c5-03fb-49b9-b24f-6656e1de02aa";//台中市景點資料
///*# 本例「旅館民宿-觀光資訊資料庫」之片段結構如下：
//# 1.真正的旅館民宿資料位於"Info"這個屬性內，且其結構就是一個list[],
//# 2."Info"又位於"Infos"上層結構內，而"Infos"又位於最外圍上層之"XML_Head"
//#   內，因此存取第一筆旅館民宿資料語法為：
//#   hotelData["XML_Head"]["Infos"]["Info"][0]
//# 3.註：hotelData為透過json.load方法取得之旅館民宿json資料集   */
//==========================================================
    private void u_importopendata() { //下載Opendata
        try {
            String Task_opendata
                    = new TransTask().execute(ul).get();   //旅館民宿 - 觀光資訊資料庫
//-------解析 json   帶有多層結構-------------
            mList = new ArrayList<Map<String, Object>>();
            JSONObject json_obj1 = new JSONObject(Task_opendata);
            JSONObject json_obj2 = json_obj1.getJSONObject("XML_Head");
            JSONObject infos = json_obj2.getJSONObject("Infos");
            JSONArray info = infos.getJSONArray("Info");
            total = 0;
            t_total = info.length(); //總筆數
//------JSON 排序----------------------------------------
            info = sortJsonArray(info);
            total = info.length(); //有效筆數
            t_count.setText(getString(R.string.m0600_ncount) + total + "/" + t_total);
//----------------------------------------------------------
            //-----開始逐筆轉換-----
            total = info.length();
            t_count.setText(getString(R.string.m0600_ncount) + total);
            for (int i = 0; i < info.length(); i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                String Name = info.getJSONObject(i).getString("Name");
                String Description = info.getJSONObject(i).getString("Description");
                String Add = info.getJSONObject(i).getString("Add");
                String Picture1 = info.getJSONObject(i).getString("Picture1");
                String Zipcode = info.getJSONObject(i).getString("Zipcode"); //郵遞區號,
                String Px = info.getJSONObject(i).getString("Px"); //經度
                String Py = info.getJSONObject(i).getString("Py"); //緯度
                String Region = info.getJSONObject(i).getString("Region");
                String Tel = info.getJSONObject(i).getString("Tel");
                String Town = info.getJSONObject(i).getString("Town");
                String Id = info.getJSONObject(i).getString("Id");


                item.put("Name", Name);
                item.put("Description", Description);
                item.put("Add", Add);
                item.put("Picture1", Picture1);
                item.put("Zipcode", Zipcode);
                item.put("Px", Px);
                item.put("Py", Py);
                item.put("Region", Region);
                item.put("Tel", Tel);
                item.put("Town", Town);
                item.put("Id", Id);
                mList.add(item);
//-------------------
            }
            t_count.setText(getString(R.string.m0600_ncount) + total + "/" + t_total);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//----------SwipeLayout 結束 --------
    }

    //-----------------------------
    private JSONArray sortJsonArray(JSONArray jsonArray) {//自定義的排序Method
        final ArrayList<JSONObject> json = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {  //將資料存入ArrayList json中
            try {
                if (
                        jsonArray.getJSONObject(i).getString("Zipcode").trim().length() > 0 //郵遞區號
//                                && jsonArray.getJSONObject(i).getString("Picture1").trim().length() > 0  //照片
//                                && !jsonArray.getJSONObject(i).getString("Picture1").trim().equals("null") //照片
                                && jsonArray.getJSONObject(i).getString("Region").trim().substring(0, 3).equals(City)
                                && jsonArray.getJSONObject(i).getString("Town").trim().equals(Spinnertown)

//                        && jsonArray.getJSONObject(i).getString("Zipcode").trim().substring(0,1).equals(Zip)
                    //substring(0,1) 從第"0"個字取"1"個字
                ) {
                    json.add(jsonArray.getJSONObject(i));

                }

            } catch (JSONException jsone) {
                jsone.printStackTrace();
            }
        }
        //---------------------------------------------------------------
        Collections.sort(json, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject jsonOb1, JSONObject jsonOb2) {
                        String lidZipcode = "", ridZipcode = "";
                        try {
                            lidZipcode = jsonOb1.getString("Zipcode");
                            ridZipcode = jsonOb2.getString("Zipcode");
                        } catch (JSONException jsone) {
                            jsone.printStackTrace();
                        }
                        return lidZipcode.compareTo(ridZipcode);
                    }
                }
        );

        return new JSONArray(json);//回傳排序縣市後的array
    }

//    private void dbmysql() {
//        sqlctl = "SELECT  *  FROM  member  ORDER  BY  id  ASC";
//        ArrayList<String> nameValuePairs = new ArrayList<>();
////        nameValuePairs.add("query");
//        nameValuePairs.add(sqlctl);
////        query
//        try {
//            String result = M06DBConnector.executeQuery(nameValuePairs);
//            //==========================================
////            chk_httpstate();  //檢查 連結狀態
//            //==========================================
//            int a = 0;
//            /**************************************************************************
//             * SQL 結果有多筆資料時使用JSONArray
//             * 只有一筆資料時直接建立JSONObject物件 JSONObject
//             * jsonData = new JSONObject(result);
//             **************************************************************************/
//            JSONArray jsonArray = new JSONArray(result);
//            // -------------------------------------------------------
//            if (jsonArray.length() > 0) { // MySQL 連結成功有資料
//                int rowsAffected = dbHper.clearRec();                 // 匯入前,刪除所有SQLite資料
//                // 處理JASON 傳回來的每筆資料
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonData = jsonArray.getJSONObject(i);
//                    ContentValues newRow = new ContentValues();
//                    // --(1) 自動取的欄位 --取出 jsonObject 每個欄位("key","value")-----------------------
//                    Iterator itt = jsonData.keys();
//                    while (itt.hasNext()) {
//                        String key = itt.next().toString();
//                        String value = jsonData.getString(key); // 取出欄位的值
//                        if (value == null) {
//                            continue;
//                        } else if ("".equals(value.trim())) {
//                            continue;
//                        } else {
//                            jsonData.put(key, value.trim());
//                        }
//                        // ------------------------------------------------------------------
//                        newRow.put(key, value.toString()); // 動態找出有幾個欄位
//                        // -------------------------------------------------------------------
//                    }
//                    // ---(2) 使用固定已知欄位---------------------------
//                    // newRow.put("id", jsonData.getString("id").toString());
//                    // newRow.put("name",
//                    // jsonData.getString("name").toString());
//                    // newRow.put("grp", jsonData.getString("grp").toString());
//                    // newRow.put("address", jsonData.getString("address")
//                    // -------------------加入SQLite---------------------------------------
//                    long rowID = dbHper.insertRec_m(newRow);
//                    Toast.makeText(getApplicationContext(), "共匯入 " + Integer.toString(jsonArray.length()) + " 筆資料", Toast.LENGTH_SHORT).show();
//                }
//                // ---------------------------
//            } else {
//                Toast.makeText(getApplicationContext(), "主機資料庫無資料", Toast.LENGTH_LONG).show();
//            }
//            recSet = dbHper.getRecSet();  //重新載入SQLite
////            setupViewComponent();
////            u_setspinner();
//            // --------------------------------------------------------
//        } catch (Exception e) {
//            Log.d(TAG, e.toString());
//        }
//    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();  禁用返回鍵
    }

    //==========================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m0600_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        li01.setVisibility(View.GONE);
        Intent it = new Intent();
        switch (item.getItemId()) {
            case R.id.action_settings:
                finish();
                break;
            case R.id.menu_top:
                nowposition = 0;
                recyclerView.scrollToPosition(nowposition);
                t_count.setText(getString(R.string.m0600_ncount) + "   (" + (nowposition + 1) + ")" + total + "/" + t_total);
                break;

            case R.id.menu_next:
                nowposition = nowposition + 100;
                if (nowposition > total - 1) {
                    nowposition = total - 1;
                }
                recyclerView.scrollToPosition(nowposition);
                t_count.setText(getString(R.string.m0600_ncount) + "   (" + (nowposition + 1) + ")" + total + "/" + t_total);
                break;

            case R.id.menu_back:
                nowposition = nowposition - 100;
                if (nowposition < 0) {
                    nowposition = 0;
                }
                recyclerView.scrollToPosition(nowposition);
                t_count.setText(getString(R.string.m0600_ncount) + "   (" + (nowposition + 1) + ")" + total + "/" + t_total);
                break;

            case R.id.menu_end:
                nowposition = total - 1;
                recyclerView.scrollToPosition(nowposition);
                t_count.setText(getString(R.string.m0600_ncount) + "   (" + (nowposition + 1) + ")" + total + "/" + t_total);
                break;

            case R.id.menu_load:
                onSwipeToRefresh.onRefresh();  //開始轉圈下載資料
                break;

            case R.id.m0600_update:
//                it.setClass(M0600.this, M0600collect.class);
//                startActivity(it);
                intent01.putExtra("googleemail", mEmail);
                intent01.setClass(M0600.this, M0600collect.class);
                startActivity(intent01);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    NavigationBarView.OnItemSelectedListener bottomOn = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.m0600_bottom_recommend:
                    mRecommend.setVisibility(View.VISIBLE);
                    L1.setVisibility(View.INVISIBLE);
                    li01.setVisibility(View.GONE);


                    break;
                case R.id.m0600_bottom_search:
                    mRecommend.setVisibility(View.INVISIBLE);
                    L1.setVisibility(View.VISIBLE);
                    li01.setVisibility(View.GONE);
//                    intent.putExtra("class_title",getString(R.string.m0600));
//                    intent.setClass(M0600.this,M0600.class);
//                    startActivity(intent);
                    break;
            }
            return true;
        }
    };


    //*********************************************************************
    private class TransTask extends AsyncTask<String, Void, String> {
        String ans;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while (line != null) {
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ans = sb.toString();
            //------------
            return ans;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parseJson(s);
        }

        private void parseJson(String s) {
        }
    }
    //-------------老師版本
//    String latitude = mLat.getText().toString();
//    String longitude = mLng.getText().toString();
//    String address = myaddress.getText().toString();
//    //                String labelLocation = "中區職訓";
//    String labelLocation = mTxtResult.getText().toString();
//    //                Toast.makeText(getApplicationContext(), latitude+","+longitude, Toast.LENGTH_SHORT).show();
//    //---------- 使用經緯度 ---------------------
////                String urlAddress = "http://maps.google.com/maps?q=" + latitude + "," + longitude
////                        + "(" + labelLocation + ")&iwloc=A&hl=h&l=zh-TW";
////                Intent intent01 = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));
////                startActivity(intent01);
////------------使用住址-------------
////                address="台中市工業區一路100號";
////                String serch_address = labelLocation; //用名稱找
//    String serch_address = address; //用住址找
//    String location = "geo:0,0?q="+serch_address;
//    Uri uri = Uri.parse(location+ "(" + labelLocation + ")&iwloc=A&hl=h&l=zh-TW");
//    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//    startActivity(it);
////----------使用 zoom -------
//                String location = "geo:" + latitude + "," + longitude+"?z=17";
}
