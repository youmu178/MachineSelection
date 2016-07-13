package com.youzh.machineselection;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by youzehong on 16/4/22.
 */
public class BallView extends LinearLayout {
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
        setGravity(Gravity.CENTER);
        setBackgroundResource(bg);
        setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText("10");
        textView.setBackgroundResource(bgBall);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(textView);
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
