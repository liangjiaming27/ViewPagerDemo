package cn.edsmall.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * @author 梁家明
 * @time 2017/2/6  15:43
 * @desc ${TODD}
 */

public class ImagerAdapter extends PagerAdapter {

    ArrayList<ImageView> mArrayList;

    public ImagerAdapter(ArrayList<ImageView> arrayList) {
        this.mArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = position%mArrayList.size();
        ImageView imageView = mArrayList.get(realPosition);
        container.removeView(imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position%mArrayList.size();
        ImageView view = mArrayList.get(realPosition);
        container.addView(view);
        return view;
    }
}
