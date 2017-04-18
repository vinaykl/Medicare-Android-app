package com.example.vinaykl.bs2;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nagarchith Balaji on 10/24/2016.
 */

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    String[] data;
    int [] flag;
    String[] country;
    String[] population;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] rank, int[] flag) {
        this.context = context;
        this.data = rank;
        this.flag=flag;
        //this.country = country;
        //this.population = population;
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView txttip;
        //TextView txtcountry;
        //TextView txtpopulation;
        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        txttip = (TextView) itemView.findViewById(R.id.rank);
        //txtcountry = (TextView) itemView.findViewById(R.id.country);
        //txtpopulation = (TextView) itemView.findViewById(R.id.population);

        // Capture position and set to the TextViews
        txttip.setText(data[position]);
        //txtcountry.setText(country[position]);
        //txtpopulation.setText(population[position]);

        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.flag);
        // Capture position and set to the ImageView
        imgflag.setImageResource(flag[position]);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}
