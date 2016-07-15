package com.youzh.machineselection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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
    private int mHeight;
    public static Random random = new Random();
    public static DecimalFormat format = new DecimalFormat("00");
    private boolean isBule;
    private ObjectAnimator loopAnimator;

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

    public boolean isBuleBall() {
        return isBule;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
    }

    public void start(long delay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTvBall, "translationY", 0, mHeight);
        objectAnimator.setDuration(300);
        objectAnimator.setStartDelay(delay);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                loopAnimator = ObjectAnimator.ofFloat(mTvBall, "translationY", -mHeight, mHeight);
                loopAnimator.setDuration(300);
                loopAnimator.setRepeatMode(ValueAnimator.RESTART);
                loopAnimator.setRepeatCount(-1);
                loopAnimator.start();
                loopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mTvBall.setText(getRandomNum(isBule));
                    }
                });
            }
        });
    }

    public void stop(final String numStr) {
        if (loopAnimator.isRunning()) {
            loopAnimator.cancel();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTvBall, "translationY", -mHeight, 0);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mTvBall.setText(numStr);
                }
            });
        }
    }

    /**
     * 机选号码
     *
     * @param list
     * @param num     机选几个
     * @param ballNum 在多少个数里选
     */
    public static void randomBall(ArrayList<Integer> list, int num, int ballNum) {
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

    public static String getRandomNum(boolean isBule) {
        // 红球33个
        int ballNum = 33;
        // 蓝球16个
        if (isBule) {
            ballNum = 16;
        }
        int i = random.nextInt(ballNum);
        String str = format.format(i + 1);
        return str;
    }

}
