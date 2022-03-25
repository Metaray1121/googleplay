package tw.sgft12.m0600;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class M06DBConnector {
    public static int httpstate = 0;
    //--------------------------------------------------------
    private static String postUrl;
    private static String myResponse;
    static String result = null;
    private static OkHttpClient client = new OkHttpClient();
//---------------------------------------------------------
//    static String connect_ip = "https://tigercloud2022.com/15/android_mysql_connect/android_connect_db.php";    //15號json

//    static String connect_ip = "https://tigercloud2022.com/12/m06_android_ALL_db.php";    //12號json
    //第二組
     static String connect_ip="https://tigercloud2022.com/sgft/android_mysql_connect/m06_sgft06_android_ALL_db.php";
    //第一組
//    static String connect_ip = "https://tigercloud2022.com/100/android_mysql_connect/android_connect_db_all.php";


    //----------------------------------------------------------------------------------------
    public static String executeQuery(ArrayList<String> query_string) {  //查詢
//        OkHttpClient client = new OkHttpClient();
        postUrl=connect_ip ;
        //--------------
        String query_0 = query_string.get(0);
//        String query_0 = "SELECT * FROM XXXX where name = 'test' and ;
        FormBody body = new FormBody.Builder()
                .add("selefunc_string","query")
                .add("query_string", query_0)       //表單變數名稱
                .build();
//        FormBody body = new FormBody.Builder()
//               .add("selefunc_string",query_string.get(0))      //跑去DB黨連接的方法跟變數..綠色的
//                .add("query_string", query_string.get(1))       //表單變數名稱
//                .build();
//--------------
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        // ======++++++++++++++++++++====================
        // 使用httpResponse的方法取得http 狀態碼設定給httpstate變數
        httpstate = 0;   //設 httpcode初始值
        // ======++++++++++++++++++++====================
        try (Response response = client.newCall(request).execute()) {
            httpstate = response.code();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String executeCreateMember(ArrayList<String> query_string) { //新增
        //        OkHttpClient client = new OkHttpClient();
        postUrl=connect_ip ;
        //--------------
        String query_0 = query_string.get(0);
        String query_1 = query_string.get(1);
        String query_2 = query_string.get(2);

        FormBody body = new FormBody.Builder()
                .add("selefunc_string","create_member")
                .add("name", query_0)
                .add("email", query_1)
                .add("photoUrl", query_2)
                .build();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        // ======++++++++++++++++++++====================
        // 使用httpResponse的方法取得http 狀態碼設定給httpstate變數
        httpstate = 0;   //設 httpcode初始值
        // ======++++++++++++++++++++====================
        try (Response response = client.newCall(request).execute()) {
            httpstate = response.code();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String executeInsert(ArrayList<String> query_string) { //新增
        //        OkHttpClient client = new OkHttpClient();
        postUrl=connect_ip ;
        //--------------
        String query_0 = query_string.get(0);
        String query_1 = query_string.get(1);
        String query_2 = query_string.get(2);
        String query_3 = query_string.get(3);
        String query_4 = query_string.get(4);
        String query_5 = query_string.get(5);
        String query_6 = query_string.get(6);
        String query_7 = query_string.get(7);;
        String query_8 = query_string.get(8);;
        String query_9 = query_string.get(9);
        String query_10 = query_string.get(10);
        String query_11 = query_string.get(11);
        String query_12 = query_string.get(12);
        String query_13 = query_string.get(13);
        String query_14 = query_string.get(14);


        FormBody body = new FormBody.Builder()
                .add("selefunc_string","insert")
                .add("name", query_0)
                .add("tel", query_1)
                .add("address", query_2)
                .add("des", query_3)
                .add("picture1", query_4)
                .add("zipcode", query_5)
                .add("px", query_6)
                .add("py", query_7)
                .add("start", query_8)
                .add("end1", query_9)
                .add("region", query_10)
                .add("town", query_11)
                .add("level", query_12)
                .add("id2", query_13)
                .add("member_id", query_14)
                .build();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        // ======++++++++++++++++++++====================
        // 使用httpResponse的方法取得http 狀態碼設定給httpstate變數
        httpstate = 0;   //設 httpcode初始值
        // ======++++++++++++++++++++====================
        try (Response response = client.newCall(request).execute()) {
            httpstate = response.code();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String executeUpdate(ArrayList<String> query_string) {
        //        OkHttpClient client = new OkHttpClient();
        postUrl=connect_ip ;
        //--------------
        String query_0 = query_string.get(0);
        String query_1 = query_string.get(1);
        String query_2 = query_string.get(2);
        String query_3 = query_string.get(3);

        FormBody body = new FormBody.Builder()
                .add("selefunc_string","update")
                .add("id", query_0)
                .add("name", query_1)
                .add("grp", query_2)
                .add("address", query_3)
                .build();
//--------------
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        // ======++++++++++++++++++++====================
        // 使用httpResponse的方法取得http 狀態碼設定給httpstate變數
        httpstate = 0;   //設 httpcode初始值
        // ======++++++++++++++++++++====================
        try (Response response = client.newCall(request).execute()) {
            httpstate = response.code();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String executeDelet(ArrayList<String> query_string) {
        //        OkHttpClient client = new OkHttpClient();
        postUrl=connect_ip ;
        //--------------
        String query_0 = query_string.get(0);

        FormBody body = new FormBody.Builder()
                .add("selefunc_string","delete")
                .add("id", query_0)
                .build();
//--------------
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        // ======++++++++++++++++++++====================
        // 使用httpResponse的方法取得http 狀態碼設定給httpstate變數
        httpstate = 0;   //設 httpcode初始值
        // ======++++++++++++++++++++====================
        try (Response response = client.newCall(request).execute()) {
            httpstate = response.code();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
//==========================
}
//==========================

