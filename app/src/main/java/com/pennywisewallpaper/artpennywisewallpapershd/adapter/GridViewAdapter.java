package com.pennywisewallpaper.artpennywisewallpapershd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pennywisewallpaper.artpennywisewallpapershd.R;
import com.pennywisewallpaper.artpennywisewallpapershd.object.ObjectImage;

import java.util.ArrayList;

import static com.pennywisewallpaper.artpennywisewallpapershd.Utils.CommonVL.ASSEST_URI;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private int layout;
    private ArrayList<ObjectImage> arrImg;

    public GridViewAdapter(Context mContext, int layout, ArrayList<ObjectImage> arrImg) {
        this.mContext = mContext;
        this.layout = layout;
        this.arrImg = arrImg;
    }

    @Override
    public int getCount() {
        return arrImg.size();
    }

    @Override
    public Object getItem(int i) {
        return arrImg.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.gvRow = convertView.findViewById(R.id.gvRow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Glide.with(mContext).load(ASSEST_URI + arrImg.get(i).getImgThumb()).centerCrop().into(viewHolder.gvRow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

class ViewHolder {
    ImageView gvRow;
}
