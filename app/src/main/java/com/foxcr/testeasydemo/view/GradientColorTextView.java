package com.foxcr.testeasydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

/**
 * Name     youcai
 * Anthor   cjq
 * Time     2020/3/16 0016
 * Describe
 */
public class GradientColorTextView extends androidx.appcompat.widget.AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();

    public GradientColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{0xFFDFB981, 0xFFF1DCB0},
                null, Shader.TileMode.REPEAT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2f - mTextBound.width() / 2f, getMeasuredHeight() / 2f + mTextBound.height() / 2f, mPaint);
    }

    public void setGradientColorList(int[] colors){
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                colors,
                null, Shader.TileMode.REPEAT);
        invalidate();
    }
}