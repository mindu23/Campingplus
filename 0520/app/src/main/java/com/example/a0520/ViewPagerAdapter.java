package com.example.a0520;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Integer> imageList;

    public ViewPagerAdapter(Context context, ArrayList<Integer> imageList){
        this.mContext = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager, null);

        ImageView imageView = view.findViewById(R.id.imageView_pager);
        imageView.setImageResource(imageList.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}
