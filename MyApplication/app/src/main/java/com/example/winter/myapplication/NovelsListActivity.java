package com.example.winter.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.winter.myapplication.entity.Novel;
import com.example.winter.myapplication.utils.CodeConvertUtils;
import com.example.winter.myapplication.utils.HttpCallbackListener;
import com.example.winter.myapplication.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NovelsListActivity extends AppCompatActivity {

    private static final String TAG = "NovelsListActivity";
    private static final int UPDATE_UI = 1;

    private List<Novel> novelList = new ArrayList<>();

    private GridView gview;
    private RecyclerView novelListView;
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    updateUI();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novels_list);
//        gview = (GridView) findViewById(R.id.gview);

        initNovels();
        novelListView = (RecyclerView) findViewById(R.id.book_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        novelListView.setLayoutManager(layoutManager);
        BookAdapter bookAdapter = new BookAdapter(novelList);
        novelListView.setAdapter(bookAdapter);


        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
//        sim_adapter = new SimpleAdapter(this, data_list, R.layout.one_novel, from, to);
        //配置适配器
//        gview.setAdapter(sim_adapter);
    }

    public void initNovels() {
        String json = "{\"count\":10}";

        RequestBody body = RequestBody.create(HttpUtils.MEDIA_TYPE_JSON, json);
        HttpUtils.sendOkHttpPost(HttpUtils.CommonUrl + "getNovels", body, new okhttp3.Callback() {
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
                novelList = gson.fromJson(reader, new TypeToken<List<Novel>>(){}.getType());
                Message message = new Message();
                message.what = UPDATE_UI;
                handler.sendMessage(message);
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView novelImage;
        TextView novelName;
        View novelView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.e(TAG, "createViewHolder");
            novelView = itemView;
            novelImage = (ImageView) itemView.findViewById(R.id.book_image);
            novelName = (TextView) itemView.findViewById(R.id.book_name);

        }

        public void bindView(final Novel novel) {
            Log.e(TAG, novel.getName());
            novelName.setText(novel.getName());
            Log.e(TAG, novel.getImageurl());
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(5, TimeUnit.SECONDS)
//                    .readTimeout(5, TimeUnit.SECONDS)
//                    .writeTimeout(5, TimeUnit.SECONDS)
//                    .build();
//            Picasso picasso = new Picasso.Builder(NovelsListActivity.this)
//                    .build();
//            picasso.with(NovelsListActivity.this)
//                    .load(novel.getImageurl())
//                    .fit()
////                    .networkPolicy()
//                    .placeholder(R.drawable.book)
//                    .into(novelImage);
            Glide.with(NovelsListActivity.this)
                    .load(novel.getImageurl())
                    .fitCenter()
                    .placeholder(R.drawable.book)
                    .crossFade()
                    .into(novelImage);

            novelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new NovelChapterListActivity().newIntent(NovelsListActivity.this,
                            novel.getNo(), novel.getName(), novel.getImageurl());
                    startActivity(intent);
                }
            });

        }
    }

    public class BookAdapter extends RecyclerView.Adapter<ViewHolder> {
        private  List<Novel> mNovelLists;


        public BookAdapter(List<Novel> novelList) {
            super();
            mNovelLists = novelList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, "onCreateViewHolder");
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.novel_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Log.e(TAG, "onBindViewHolder");
            Novel novel = mNovelLists.get(position);
            holder.bindView(novel);
        }

        @Override
        public int getItemCount() {
            Log.e(TAG, "getItemCount" + mNovelLists.size());
            return mNovelLists.size();
        }
    }

    public void updateUI() {
        Log.e(TAG, "updateUI");
        BookAdapter mAdapter = new BookAdapter(novelList);
        novelListView.setAdapter(mAdapter);
    }

}
