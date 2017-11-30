package com.example.winter.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovelChapterListActivity extends AppCompatActivity {
    private ListView listView;
    private ImageView imageView;
    private SimpleAdapter sim_adapter;
    private List<Map<String, Object>> data_list;
    private List<String> list = new ArrayList<String>();

    private int[] icon = { R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book ,R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book, R.drawable.book, R.drawable.book,
            R.drawable.book };
    private String[] iconName = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_chapter_list);
        listView = (ListView)findViewById(R.id.chaptrList);
        list = getData();
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(myArrayAdapter);
        imageView = (ImageView)findViewById(R.id.img);
        imageView.setImageResource(R.drawable.book);
//        String [] from ={"image","text"};
////        int [] to = {R.id.image,R.id.text};
//        int [] to = {R.id.image,R.id.text};
//        sim_adapter = new SimpleAdapter(this, data_list, R.layout.one_novel, from, to);
//
//        listView.setAdapter(sim_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(list.get(arg2).equals("第1章"))
                {
                    Intent intent =new Intent(NovelChapterListActivity.this,ChapterActivity.class);
                    intent.putExtra("no", "1");
                    startActivity(intent);
                }
                if(list.get(arg2).equals("第2章"))
                {
                    Intent intent =new Intent(NovelChapterListActivity.this,ChapterActivity.class);
                    intent.putExtra("no", "2");
                    startActivity(intent);
                }
                else {
                    Intent intent =new Intent(NovelChapterListActivity.this,ChapterActivity.class);
                    intent.putExtra("no", arg2+"");
                    startActivity(intent);
                }
            }

        });
    }

    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        String d = "第";
        String z = "章";
        for(int i = 0; i < 100; i++) {
            data.add(d + i + z);
        }
        return data;
    }

    public List<Map<String, Object>> getMapData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        list = getData();
        for(int i = 0; i < list.size(); i++){
            iconName[i] = list.get(i);
        }

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
        Intent intent =new Intent(NovelChapterListActivity.this,ChapterActivity.class);
        startActivity(intent);
    }
}
