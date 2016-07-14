package com.youzh.machineselection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by youzehong on 16/4/22.
 */
public class BallView extends LinearLayout {

    private TextView mTvBall;
    private ArrayList<Integer> mBallList = new ArrayList<>();
    private int mWidth;
    private int mHeight;
    private Random random = new Random();
    private DecimalFormat format = new DecimalFormat("00");
    private boolean isBule;

    public BallView(Context context) {
        this(context, null, 0);
    }

    public BallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BallView);
        int bgBall = a.getResourceId(R.styleable.BallView_ball_bg, R.mipmap.ball_bg_red);
        int bg = a.getResourceId(R.styleable.BallView_bg, R.mipmap.wheel_ball_red_bg);
        isBule = a.getBoolean(R.styleable.BallView_isBule, false);
        setGravity(Gravity.CENTER);
        setBackgroundResource(bg);
        setOrientation(LinearLayout.HORIZONTAL);
        mTvBall = new TextView(context);
        mTvBall.setGravity(Gravity.CENTER);
        mTvBall.setText("01");
        mTvBall.setBackgroundResource(bgBall);
        mTvBall.setTextColor(getResources().getColor(android.R.color.white));
        mTvBall.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mTvBall);
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("youzh", "width: " + getWidth() + " height: " + getHeight() +
                " X: " + mTvBall.getPivotX() + " Y: " + mTvBall.getY());
        mWidth = w;
        mHeight = h;

    }

    public void start() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTvBall, "translationY", 0, mHeight);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTvBall, "translationY", -mHeight, mHeight);
                objectAnimator.setDuration(250);
                objectAnimator.setRepeatMode(ValueAnimator.RESTART);
                objectAnimator.setRepeatCount(-1);
                objectAnimator.start();
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setRandomNum(mTvBall, isBule);
                    }
                });

            }
        });
    }

    public void stop () {

        
    }
    /**
     * 机选号码
     *
     * @param list
     * @param num     机选几个
     * @param ballNum 在多少个数里选
     */
    private void randomBall(ArrayList<Integer> list, int num, int ballNum) {
        list.clear();
        Random random = new Random();
        boolean[] bool = new boolean[ballNum];
        int randInt = 0;
        for (int j = 0; j < num; j++) {
            do {
                randInt = random.nextInt(ballNum);
            } while (bool[randInt]);
            bool[randInt] = true;
            list.add(randInt);
        }
        Collections.sort(list);
    }

    private void setRandomNum(TextView tv, boolean isBule) {
        int ballNum = 33;
        if (isBule) {
            ballNum = 16;
        }
        int i = random.nextInt(ballNum);
        String format = this.format.format(i);
        mTvBall.setText(format);
    }

}
