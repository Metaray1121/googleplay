package tw.sgft12.m0600;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements View.OnClickListener {


    private TextView mStatusTextView, mIdTokenTextView, mAuthCodeTextView;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CircleImgView img;
    private String TAG = "tcnr12=>";
    private static final int RC_GET_TOKEN = 9002;
    private static final int RC_GET_AUTH_CODE = 9003;
    private Uri User_IMAGE;
    private String[] params;
    private Button mRefreshButton;
    private Intent intent01 = new Intent();
    private String g_DisplayName;
    //    ========================================
    //所需要申請的權限數組
    private static final String[] permissionsArray = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private List<String> permissionsList = new ArrayList<String>();
    //申請權限後的返回碼
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private String g_Email;
    private String g_PhotoUrl;
    private String g_Id;
    private GoogleSignInAccount g_Account;
    private String g_Givenname;
    private String g_photo;
    //    ========================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        checkRequiredPermission(this);     //  檢查SDK版本, 確認是否獲得權限.
        setupViewComponent();
    }

    private void checkRequiredPermission(final Activity activity) { //
        for (String permission : permissionsArray) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        if (permissionsList.size() != 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new
                    String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), permissions[i] + "權限申請成功!", Toast.LENGTH_LONG).show();
//                        p_ok=1;
                    } else {
                        Toast.makeText(getApplicationContext(), "權限被拒絕： " + permissions[i], Toast.LENGTH_LONG).show();
//                        p_ok=0;
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setupViewComponent() {
        //View
        mAuthCodeTextView = findViewById(R.id.detail);
        mRefreshButton = findViewById(R.id.button_optional_action);
        mRefreshButton.setText(R.string.refresh_token);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
        findViewById(R.id.data_button).setOnClickListener(this);

        // For sample only: make sure there is a valid server client ID.
        validateServerClientID();

// [START configure_signin]
        // Configure sign-in to request offline access to the user's ID, basic
        // profile, and Google Drive. The first time you request a code you will
        // be able to exchange it for an access token and refresh token, which
        // you should store. In subsequent calls, the code will only result in
        // an access token. By asking for profile access (through
        // DEFAULT_SIGN_IN) you will also get an ID Token as a result of the
        // code exchange.
        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                //.requestServerAuthCode(serverClientId)
                .requestIdToken(serverClientId)
                .requestEmail()
                .build();
        // [END configure_signin]

        //START build_client
        // Build a GoogleSignInClient with the options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //END build_client

        // --START customize_button--
        // Set the dimensions of the sign-in button
        //SignInButton signInButton = findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        //signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        // END customize_button
    }

    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in google_sign.xml, must end with " + suffix;

            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sign_in_button:
                getAuthCode();
                break;

            case R.id.sign_out_button:
                signOut();
                break;

//            case R.id.disconnect_button:
//                revokeAccess();
//                break;

            case R.id.data_button:
                intent01.putExtra("googlename", g_DisplayName);
                intent01.putExtra("googleemail", g_Email);
                intent01.putExtra("googleid", g_Id);
                intent01.putExtra("googleAccount", g_Account);
                intent01.putExtra("googlePhotoUrl", g_PhotoUrl);
                intent01.putExtra("googleGivenname", g_Givenname);
                intent01.setClass(Main.this, M0600.class);

                startActivity(intent01);
                break;
//            case R.id.button_optional_action:
//                refreshidToken();
//                break;
        }
    }

    private void getAuthCode() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

    private void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //--START_EXCLUDE--
                        updateUI(null);
                        // [END_EXCLUDE]
                        // img.setImageResource(R.drawable.googleg_color); //還原圖示
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // --START_EXCLUDE--
                        updateUI(null);
                        // --END_EXCLUDE--
                        img.setImageResource(R.drawable.googleg_color); //還原圖示
                    }
                });
    }

    private void refreshidToken() {
//        // Attempt to silently refresh the GoogleSignInAccount. If the GoogleSignInAccount
//        // already has a valid token this method may complete immediately.
//        //
//        // If the user has not previously signed in on this device or the sign-in has expired,
//        // this asynchronous branch will attempt to sign in the user silently and get a valid
//        // ID token. Cross-device single sign on will occur in this branch.
//        mGoogleSignInClient.silentSignIn()
//                .addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
//                    @Override
//                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
//                        handleSignInResult(task);
//                    }
//                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // --START on_start_sign_in--
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null && GoogleSignIn.hasPermissions(account, new Scope(Scopes.DRIVE_APPFOLDER))) {
            updateUI(account);
        } else {
            updateUI(null);
        }
        //--END on_start_sign_in--
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_AUTH_CODE) {
            // [START get_auth_code]
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String authCode = account.getServerAuthCode();//找Code
                // Show signed-un UI
                updateUI(account);

            } catch (ApiException e) {
                Log.w(TAG, "Sign-in failed", e);
                updateUI(null);
            }
            // [END get_auth_code]
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();


            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "handleSignInResult:error", e);
            updateUI(null);
        }
    }

    //==============================
//    private void updateUI(GoogleSignInAccount account) {
//
//        if (account != null) {
//            g_DisplayName= account.getDisplayName();//暱稱
//            g_Account = account.getAccount();    // 取得帳號
//            int a=0;
//            // 取得頭照
//            if( account.getPhotoUrl()==null){
//                g_PhotoUrl="000";
//            }else {
//                g_PhotoUrl = account.getPhotoUrl().toString();
//            }
//            int c=0;
//
//            g_Email = account.getEmail();
//            g_Id = account.getId();
//            g_Givenname = account.getGivenName();
//
//
////            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);
//            ((TextView) findViewById(R.id.status)).setText(getString(R.string.signed_in_fmt,account.getDisplayName())+"\nEmail:"+
//                    account.getEmail()+"\nFirst name:"+
//                    account.getGivenName()+"\nLast name:"+
//                    account.getFamilyName()
//            );
//
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
//            String authCode = account.getServerAuthCode();
//            mAuthCodeTextView.setText(getString(R.string.auth_code_fmt, authCode));
//            //-------改變圖像--------------
//            User_IMAGE = account.getPhotoUrl();
//            if(User_IMAGE==null){
//                return;
//            }
//            img = (CircleImgView) findViewById(R.id.google_icon);
//            new AsyncTask<String,Void,Bitmap>() {
//                @Override
//                protected Bitmap doInBackground(String... params) {
//                    String url = params[0];
//                    return getBitmapFromURL(url);
//                }
//                @Override
//                protected void onPostExecute(Bitmap result) {
//                    img.setImageBitmap(result);
//                    super.onPostExecute(result);
//                }
//            }.execute(User_IMAGE.toString().trim());
//            //-------------------------
//        } else {
//            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
//            mAuthCodeTextView.setText(getString(R.string.auth_code_fmt, "null"));
//            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
//        }
//    }
    //============================
    private void updateUI(GoogleSignInAccount account) {

        if (account != null) {
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);
            g_DisplayName = account.getDisplayName();    //丟資料
            g_Email = account.getEmail();
            g_Id = account.getId();
            g_Givenname = account.getGivenName();
            g_Account = account;

////-----------------無使用googlesignin---------
//            g_DisplayName = "ray";    //丟資料
//            g_Email = "email";
//            g_Id = "001";
//            g_Givenname = "jimmy";
//            g_Account = account;
////---------------------------------------------------

            //g_PhotoUrl = account.getPhotoUrl().toString(); //公有變數需告為Uri,後面需要加toString
            ((TextView) findViewById(R.id.status)).setText( "\nEmail:" +
                    account.getEmail() + "\nFirst name:" +
                    account.getGivenName() + "\nLast name:" +
                    account.getFamilyName()
            );

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            //-------改變圖像--------------

            if (account.getPhotoUrl() == null) {
                img = (CircleImgView) findViewById(R.id.google_icon);
            } else {
                g_PhotoUrl = account.getPhotoUrl().toString();
                User_IMAGE = account.getPhotoUrl();
                img = (CircleImgView) findViewById(R.id.google_icon);
                new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... params) {
                        String url = params[0];
                        return getBitmapFromURL(url);
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {
                        img.setImageBitmap(result);
                        super.onPostExecute(result);
                    }
                }.execute(User_IMAGE.toString().trim());

            }
            //-------------------------
        } else {
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            img = (CircleImgView) findViewById(R.id.google_icon);
            img.setImageResource(R.drawable.googleg_color);
        }
    }
    //===================================================

    private Bitmap getBitmapFromURL(String imageurl) {//數位檔轉成圖檔
        try {
            URL url = new URL(imageurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //====================================================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_finish:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) { //進用返回鍵
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}

//============================
