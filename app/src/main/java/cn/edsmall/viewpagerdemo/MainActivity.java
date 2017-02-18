package cn.edsmall.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.startX;
import static cn.edsmall.viewpagerdemo.R.drawable.c;
import static cn.edsmall.viewpagerdemo.R.drawable.d;

public class MainActivity extends AppCompatActivity implements onClickImage{
    private ImagerAdapter mAdapter;
    private LinearLayout mLayout;
    private ViewPager mViewPager;

    private ArrayList<ImageView> dots;
    private ResHandler handler;
    private float currentX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] imager = {R.drawable.a,R.drawable.b, c, d};

        mViewPager = (ViewPager) findViewById(R.id.viewpager_);
        ArrayList<ImageView> imageViewArrayList = new ArrayList<>();
        mLayout = (LinearLayout) findViewById(R.id.line);

        dots = new ArrayList<>();
        for (int i = 0; i<imager.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imager[i]);
            imageViewArrayList.add(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,0,0);
            dot.setImageResource(R.drawable.dot_selector);
            mLayout.addView(dot,params);
            dots.add(dot);
        }
        mAdapter = new ImagerAdapter(imageViewArrayList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("测试结果: ", "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position%dots.size();
                setDot(realPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDot(0);
        int  half =Integer.MAX_VALUE/2;
        mViewPager.setCurrentItem(half-half%dots.size());

        handler = new ResHandler();
        handler.postDelayed(reshRunable,2*1000);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.removeCallbacks(reshRunable);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currentX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.postDelayed(reshRunable,2*1000);
                        int realPosition =mViewPager.getCurrentItem()%dots.size();
                        clickImage(realPosition);
                        break;

                }
                if (mViewPager.getCurrentItem() == 1 && currentX < startX) {
                    return false;
                } else if (mViewPager.getCurrentItem() == 2) {
                    return false;
                } else {
                    return MainActivity.super.onTouchEvent(motionEvent);
                }
//                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(reshRunable);
    }

    Runnable reshRunable = new Runnable() {
        @Override
        public void run() {
            Log.i("it520","run");
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            handler.postDelayed(reshRunable, 2 * 1000);
        }
    };

    private void setDot(int position) {
        for (int i = 0; i < dots.size(); i++) {
            ImageView dot = dots.get(i);
            if (i == position) {
                dot.setSelected(true);
            } else {
                dot.setSelected(false);
            }
        }
    }

    @Override
    public void clickImage(int position) {
        Toast.makeText(this,"点击了"+position,Toast.LENGTH_SHORT).show();
    }

    class ResHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
