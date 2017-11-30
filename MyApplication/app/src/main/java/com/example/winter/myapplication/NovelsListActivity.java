package com.example.winter.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovelsListActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_novels_list);
//    }

    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book ,R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book };
    private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频","通讯录1", "日历1", "照相机1", "时钟1", "游戏1", "短信1", "铃声1",
            "设置1", "语音1", "天气1", "浏览器1", "视频1" ,"通讯录2", "日历2", "照相机2", "时钟2", "游戏2", "短信2", "铃声2",
            "设置3", "语音4", "天气5", "浏览器6", "视频7" ,"通讯录7", "日历8", "照相机8", "时钟8", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频8"  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novels_list);
        gview = (GridView) findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.one_novel, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
    }



    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }


    public void OpenNew(View v){
        //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
        Intent intent =new Intent(NovelsListActivity.this,NovelChapterListActivity.class);
        startActivity(intent);
    }
}
