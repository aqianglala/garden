package com.softgarden.garden.view.feedback.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softgarden.garden.jiadun_android.R;

import java.util.ArrayList;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class GridAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private ArrayList<String> fileList;

    public GridAdapter(Context context, ArrayList<String> fileList) {
        this.fileList = fileList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_grid_img,parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        if(position == fileList.size()){// 最后一项显示一个＋按钮
            holder.image.setBackgroundResource(R.mipmap.tianjiaxiangpoan);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 进入选择的页面
                    if (pickPictureInterface!=null){
                        pickPictureInterface.pick();
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }

    private PickPictureInterface pickPictureInterface;
    public void setPickPictureInterface(PickPictureInterface pickPictureInterface){
        this.pickPictureInterface = pickPictureInterface;
    }
    public interface PickPictureInterface{
        void pick();
    }
}
