package com.youzh.machineselection;

import android.content.Context;
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
    private int mDuration = 0;
    private AnimationDrawable mAnimDrawable;
    private ArrayList<BallView> mBallViewList = new ArrayList<>();

    public BallViewLayout(Context context) {
        this(context, null, 0);
    }

    public BallViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            mDuration += mAnimDrawable.getDuration(i);
        }
        mAnimDrawable.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvOpen.setImageResource(R.drawable.anim_stop_rock);
                mAnimDrawable = (AnimationDrawable) mIvOpen.getDrawable();
                mAnimDrawable.start();
                mDuration = 0;
            }
        }, mDuration);
        int count = 0;
        for (final BallView ballView : mBallViewList) {
            count += 100;
            ballView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ballView.start();
                }
            }, count);
        }
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
