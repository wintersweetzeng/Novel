package com.example.winter.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.winter.myapplication.entity.Chapter;
import com.example.winter.myapplication.entity.Novel;
import com.example.winter.myapplication.utils.CodeConvertUtils;
import com.example.winter.myapplication.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NovelChapterListActivity extends AppCompatActivity {

    private static final String TAG = "NovelChapterList";
    private static final String NOVEL_CHAPTERS = "NOVEL_CHAPTERS";
    private static final int UPDATE_UI = 1;
    private String novelNo;
    private List<Chapter> chapterList = new ArrayList<>();
    private RecyclerView listView;
    private ImageView imageView;
    private SimpleAdapter sim_adapter;
    private List<Map<String, Object>> data_list;
    private List<String> list = new ArrayList<String>();
    private HashMap<String, List<Chapter>> novelChapters = new HashMap<>();

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

    public static final String NOVEL_NO = "NOVEL_NO";

    public static Intent newIntent(Context context, String novelNo) {
        Intent intent = new Intent(context, NovelChapterListActivity.class);
        intent.putExtra(NOVEL_NO, novelNo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_chapter_list);
        listView = (RecyclerView) findViewById(R.id.chaptrList);
        novelNo = (String) getIntent().getSerializableExtra(NOVEL_NO);
        initDate();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        ChapterAdapter adapter = new ChapterAdapter(chapterList);
        listView.setAdapter(adapter);
//        list = getData();
//        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>
//                (this,android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(myArrayAdapter);
        imageView = (ImageView)findViewById(R.id.img);
        imageView.setImageResource(R.drawable.book);
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

    public void updateUI(){
        Log.e(TAG, "updateUI");
        ChapterAdapter adapter = new ChapterAdapter(chapterList);
        listView.setAdapter(adapter);
    }

    private void initDate(){

        String json = "{\"novelNo\":\""+ novelNo + "\"}";
        Log.e(TAG, json);
        RequestBody body = RequestBody.create(HttpUtils.MEDIA_TYPE_JSON, json);
        HttpUtils.sendOkHttpPost(HttpUtils.CommonUrl + "getChapterList", body, new okhttp3.Callback() {
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
                Gson gson = new Gson();
                chapterList = gson.fromJson(reader, new TypeToken<List<Chapter>>(){}.getType());
                novelChapters.put(novelNo, chapterList);
                Message message = new Message();
                message.what = UPDATE_UI;
                handler.sendMessage(message);
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

    private class ChapterHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ChapterHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void bindView (final Chapter chapter,final int position) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new ChapterActivity().newIntent(NovelChapterListActivity.this,
                            novelNo, chapter.getChapterNo(), chapter.getChapterTitle());
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(NOVEL_CHAPTERS, novelChapters);
                    intent.putExtra(NOVEL_CHAPTERS, novelChapters);
                    intent.putExtra("POSITION", position);
                    startActivity(intent);
                }
            });
        }
    }

    public class ChapterAdapter extends RecyclerView.Adapter<ChapterHolder> {
        private List<Chapter> mChapterLists;

        public ChapterAdapter(List<Chapter> chapterList) {
            super();
            mChapterLists = chapterList;
        }

        @Override
        public ChapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, "onCreateViewHolder");
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            ChapterHolder holder = new ChapterHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ChapterHolder holder, int position) {
            Log.e(TAG, "onBindViewHolder");
            Chapter chapter = mChapterLists.get(position);
            holder.textView.setText(chapter.getChapterNo() + "  " + chapter.getChapterTitle());
            holder.bindView(chapter, position);
        }

        @Override
        public int getItemCount() {
            if (mChapterLists == null) {
                return 0;
            }
            Log.e(TAG, "getItemCount: " + mChapterLists.size());
            return mChapterLists.size();
        }
    }
}
