package com.foxcr.testeasydemo.shareElementAnim;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.foxcr.testeasydemo.R;

public class ActivityB extends AppCompatActivity {
    public static final String PROPNAME_SCREENLOCATION_LEFT = "PROPNAME_SCREENLOCATION_LEFT";
    public static final String PROPNAME_SCREENLOCATION_TOP = "PROPNAME_SCREENLOCATION_TOP";
    public static final String PROPNAME_WIDTH = "PROPNAME_WIDTH";
    public static final String PROPNAME_HEIGHT = "PROPNAME_HEIGHT";

    public static final String IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA";
    public static final String VIEW_INFO_EXTRA = "VIEW_INFO_EXTRA";
    private CountDownTimer countDownTimer;
    private Button button;
    private String imageUrl = "http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg";
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        button = findViewById(R.id.mCountDownBtn);
        imageView = findViewById(R.id.mFitXYIV);
        RequestOptions options = new RequestOptions().centerCrop().dontAnimate();
        Glide.with(this).load(imageUrl).apply(options).into(imageView);
        startCountDownTimer();
    }

    private void startCountDownTimer(){
        countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText(millisUntilFinished/1000+"s");
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFinish() {
                Intent intent = new Intent(ActivityB.this, ActivityA.class);
                intent.putExtra(IMAGE_URL_EXTRA, imageUrl);
                intent.putExtra(VIEW_INFO_EXTRA, /* start values */ captureValues(imageView));
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        };

        countDownTimer.start();
    }

    private Bundle captureValues(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt(PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
        b.putInt(PROPNAME_SCREENLOCATION_TOP, screenLocation[1]);
        b.putInt(PROPNAME_WIDTH, view.getWidth());
        b.putInt(PROPNAME_HEIGHT, view.getHeight());
        return b;
    }
}
