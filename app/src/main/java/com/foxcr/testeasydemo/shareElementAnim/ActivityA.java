package com.foxcr.testeasydemo.shareElementAnim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.foxcr.testeasydemo.R;

public class ActivityA extends AppCompatActivity {
    private static final long DEFAULT_DURATION = 300;
    private LinearInterpolator DEFAULT_INTERPOLATOR = new LinearInterpolator();
    private ImageView mDestinationView;
    private String imageUrl;
    private int mStartXValues,mEndXValues,mStartYValues,mEndYValues;
    private int mStartWidth,mEndWidth,mStartHeight,mEndHeight;
    private OnActivityFinishListener onActivityFinishListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onActivityFinishListener!=null) {
            onActivityFinishListener.onActivityFinish();
        }
    }


    public void setOnActivityFinishListener(OnActivityFinishListener onActivityFinishListener){
        this.onActivityFinishListener = onActivityFinishListener;
    }
}
