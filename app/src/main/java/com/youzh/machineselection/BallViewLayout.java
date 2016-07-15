package com.youzh.machineselection;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by youzehong on 16/4/25.
 */
public class BallViewLayout extends LinearLayout {

    @Bind(R.id.iv_open)
    ImageView mIvOpen;
    private int mAdDuration = 0;
    private AnimationDrawable mAnimDrawable;
    private ArrayList<BallView> mBallViewList = new ArrayList<>();
    private ArrayList<Integer> mBallList = new ArrayList<>();
    private int mDuration;

    public BallViewLayout(Context context) {
        this(context, null, 0);
    }

    public BallViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BallViewLayout);
        mDuration = a.getInteger(R.styleable.BallViewLayout_duration, 2000);
        a.recycle();
        View view = View.inflate(context, R.layout.layout_item, this);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.iv_open)
    public void openClick(View v) {
        if (mAnimDrawable != null && mAnimDrawable.isRunning()) {
            mAnimDrawable.stop();
        }
        mIvOpen.setImageResource(R.drawable.anim_open_rock);
        mAnimDrawable = (AnimationDrawable) mIvOpen.getDrawable();

        for (int i = 0; i < mAnimDrawable.getNumberOfFrames(); i++) {
            mAdDuration += mAnimDrawable.getDuration(i);
        }
        mAnimDrawable.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvOpen.setImageResource(R.drawable.anim_stop_rock);
                mAnimDrawable = (AnimationDrawable) mIvOpen.getDrawable();
                mAnimDrawable.start();
                mAdDuration = 0;
            }
        }, mAdDuration);

        // 开始
        int count = 0;
        for (final BallView ballView : mBallViewList) {
            count += 100;
            ballView.start(count);
        }

        // 结束
        BallView.randomBall(mBallList, 6, 33); // 双色球红球33机选6个
        postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                for (int i = 0; i < mBallViewList.size(); i++) {
                    final BallView ballView = mBallViewList.get(i);
                    count += 150;
                    final int finalI = i;
                    ballView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!ballView.isBuleBall()){
                                String format = ballView.format.format(mBallList.get(finalI) + 1);
                                ballView.stop(format);
                            } else {
                                ballView.stop(ballView.getRandomNum(ballView.isBuleBall()));
                            }
                        }
                    }, count);
                }
            }
        }, mDuration);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewGroup view = (ViewGroup) this.getChildAt(i);
            ViewGroup childView = (ViewGroup) view.getChildAt(0);
            int childCount1 = childView.getChildCount();
            for (int a = 0; a < childCount1; a++) {
                BallView ballView = (BallView) childView.getChildAt(a);
                mBallViewList.add(ballView);
            }
        }
    }
}
