package com.foxcr.testeasydemo.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TouchView extends View {
    private boolean isTouchView = false;
    private static final String TAG = "TouchView";
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                isTouchView = true;
                Log.d(TAG,"action_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG,"action_move");
                break;
            case MotionEvent.ACTION_UP:
                if (isTouchView){
                    isTouchView = false;
                    Log.d(TAG,"action_up");
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!isTouchView){
                    Log.d(TAG,"action_cancel");
                }else{
                    Log.d(TAG,"action_cancel_isTouchView");
                }
                break;
        }
        return true;
    }
}
