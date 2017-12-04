package com.example.winter.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.winter.myapplication.entity.Chapter;
import com.example.winter.myapplication.utils.CodeConvertUtils;
import com.example.winter.myapplication.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChapterActivity extends AppCompatActivity {

    private static final String TAG = "ChapterActivity";
    public static final String CHAPTER_NO = "CHAPTER_NO";
    public static final String NOVEL_NO = "NOVEL_NO";
    public static final String CHAPTER_TITLE = "CHAPTER_TITLE";

    private String getContentJson = "";
    private String content = "";
    private static final int UPDATE_UI = 1;
    private PopupWindow mPopWindow;


    public static Intent newIntent(Context context, String novelNo, String chapterNO, String chapterTitle) {
        Log.e(TAG, novelNo + chapterNO + chapterTitle);
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra(NOVEL_NO, novelNo);
        intent.putExtra(CHAPTER_NO, chapterNO);
        intent.putExtra(CHAPTER_TITLE, chapterTitle);
        return intent;
    }

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        String novelNo = (String) getIntent().getSerializableExtra(NOVEL_NO);
        String chapterNo = (String) getIntent().getSerializableExtra(CHAPTER_NO);
        String chapterTitle = (String) getIntent().getSerializableExtra(CHAPTER_TITLE);
        getContentJson = "{\"novelNo\":\""+novelNo+"\",\"chapterNo\":\""+chapterNo+"\", \"chapterTitle\":\""+chapterTitle+"\"}";
//        setContentView(R.layout.activity_novel_chapter_list);
        textView = (TextView) findViewById(R.id.chapterContent);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
//        String no = getIntent().getStringExtra("no");
        getData();
        textView.setText(content);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    updateUI();
            }
        }
    };

    public void updateUI() {
        textView.setText(Html.fromHtml(content));
    }

    public void OpenNew(View v){
        Intent intent =new Intent(ChapterActivity.this,NovelChapterListActivity.class);
        startActivity(intent);
    }

    private void getData(){

        Log.e(TAG, getContentJson);
        RequestBody body = RequestBody.create(HttpUtils.MEDIA_TYPE_JSON, getContentJson);
        HttpUtils.sendOkHttpPost(HttpUtils.CommonUrl + "getChapter", body, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.e(TAG, responseData);
                responseData = CodeConvertUtils.convert(responseData);
                Log.e(TAG, responseData);
                JsonReader reader = new JsonReader(new StringReader(responseData));
                reader.setLenient(true);
                Log.e(TAG, responseData);
                try{
                    JSONObject obj = new JSONObject(responseData);
                    content = (String) obj.get("content");
                }catch (Exception e){
                    e.printStackTrace();
                }

//                Gson gson = new Gson();
//                content = gson.fromJson(reader, new TypeToken<List<Chapter>>(){}.getType());
                Message message = new Message();
                message.what = UPDATE_UI;
                handler.sendMessage(message);
            }
        });

    }

    private void showPopupWindow() {
        //设置contentView
        final View contentView = LayoutInflater.from(ChapterActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
//        mPopWindow.showAsDropDown(mMenuTv);
        //设置各个控件的点击响应
//        TextView tv1 = (TextView)contentView.findViewById(R.id.pop_computer);
//        TextView tv2 = (TextView)contentView.findViewById(R.id.pop_financial);
//        TextView tv3 = (TextView)contentView.findViewById(R.id.pop_manage);
//
//        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(ChapterActivity.this).inflate(R.layout.activity_chapter, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 100, 100);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

    }
}
