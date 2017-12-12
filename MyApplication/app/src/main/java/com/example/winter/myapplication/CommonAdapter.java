package com.example.winter.myapplication;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonHolder> {
    protected Context mContext;//定义成protected,用来给子类访问
    protected List<T> mList = new ArrayList<T>();

    public CommonAdapter(List<T> mList) {
        this.mList = mList;
    }

    protected View.OnClickListener onClickListener;//定义成protected，让子类重写

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 添加数据，
     *
     * @param aList
     */
    public void addData(List<T> aList) {
        if (aList != null && aList.size() > 0) {
            mList.addAll(aList);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getData() {
        return mList;
    }

    /**
     * 设置数据，
     *
     * @param sList
     */
    public void setData(List<T> sList) {
        mList.clear();
        addData(sList);
    }


}