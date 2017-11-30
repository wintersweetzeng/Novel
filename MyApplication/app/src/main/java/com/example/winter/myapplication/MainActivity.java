package com.example.winter.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenNew(View v){
        //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
        Intent intent =new Intent(MainActivity.this,NovelsListActivity.class);
        startActivity(intent);
    }
}


//public class LoginActivity extends Activity implements View.OnClickListener{
//    private static final String TAG = "login";
//    Button loginBtn = null;
//    EditText useridEt = null;
//    EditText passEt = null;
//    TextView promptText = null;
//    @Override
//        super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_login);
//    loginBtn = (Button) findViewById(R.id.loginBtn);
//        loginBtn.setOnClickListener(this);
//    useridEt = (EditText) findViewById(R.id.userId);
//    passEt = (EditText) findViewById(R.id.pass);
//    promptText = (TextView) findViewById(R.id.promptText);
//    OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//            .readTimeout(10000L, TimeUnit.MILLISECONDS)
//            .build();
//        OkHttpUtils.initClient(okHttpClient);
//
//    @Override
//    public void onClick(View v) {
//        String userid =  useridEt.getText().toString().trim();
//        String pass =  passEt.getText().toString().trim();
//        if(userid.equals("")){
//            promptText.setText(R.string.userIdError);
//            return ;
//        }
//        if(pass.equals("")){
//            promptText.setText(R.string.passError);
//            return ;
//        }
//        WebConstant.digest = ("Basic " + new String(Base64.encode((userid + ':' + pass).getBytes(), Base64.DEFAULT))).replace("\n", "");
//
//        String url = WebConstant.REQUESTPATH+"/users/" + userid+"?getAll=true";
//        OkHttpUtils.get()
//                .url(url).addHeader("Authorization",  WebConstant.digest).addHeader("Accept-Language","zh-CN")
//                .build().execute(new Callback()
//        {
//            @Override
//            public String parseNetworkResponse(Response response, int id) throws Exception {
//                String string = response.body().string();
//                JSONObject jsonObj = new JSONObject(string);
//                if(jsonObj.get("userName")!=null){
//                    WebConstant.userId = (String)jsonObj.get("userId");
//                    WebConstant.userName = (String)jsonObj.get("userName");
//                    return (String) jsonObj.get("userName");
//                }
//                return null;
//            }
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                WebConstant.digest = null;
//                promptText.setText(R.string.loginError);
//                Log.i(TAG,e.getMessage());
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Object response, int id) {
//                promptText.setText(R.string.loginSuccess+" "+response);
//                Intent intent = new Intent();
//                LoginActivity.this.setResult(WebConstant.RESULT_OK, intent);
//                LoginActivity.this.finish();
//            }
//        });
//
//    }
//}