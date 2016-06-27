package com.softgarden.garden.view.feedback.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.feedback.utils.Bimp;

/**
 * Created by qiang-pc on 2016/6/23.
 */
public class GridAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    
    public GridAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Bimp.bmp.size()+1;
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
        if(convertView ==null){
            convertView = inflater.inflate(R.layout.item_grid_img,parent,false);
        }
        if(position == Bimp.bmp.size()){// 最后一项显示一个＋按钮
            ((ImageView)convertView).setImageBitmap(BitmapFactory.decodeResource(
                    context.getResources(), R.mipmap.tianjiaxiangpoan));
            if(Bimp.bmp.size() == 9){
                convertView.setVisibility(View.GONE);
            }
        }else{
            Bitmap bitmap = Bimp.bmp.get(position);
            ((ImageView)convertView).setImageBitmap(bitmap);
        }
        return convertView;
    }

}
