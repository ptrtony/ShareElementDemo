package com.foxcr.testeasydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.foxcr.testeasydemo.view.Rotate3d;

public class MainActivity extends Activity {
    private FrameLayout containerView;
    private ConstraintLayout mVipCouponsContainerCl1;
    private ConstraintLayout mVipCouponsContainerCl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerView = findViewById(R.id.container_view);
        mVipCouponsContainerCl1 = findViewById(R.id.mVipCouponsContainerCl1);
        mVipCouponsContainerCl = findViewById(R.id.mVipCouponsContainerCl);
        findViewById(R.id.mReverseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首页页面以0~90度翻转,并加载数据
                applyRotation(1, 0, 90);
            }
        });

        findViewById(R.id.mTranslateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //音乐页面以0~-90度翻转
                applyRotation(0, 0, -90);
            }
        });

    }

    /**
     * 执行翻转第一个视图动画
     * @param tag view索引
     * @param start 起始角度
     * @param end 结束角度
     */
    private void applyRotation(int tag, float start, float end) {
        // 得到中心点(以中心翻转)
        final float centerX = containerView.getWidth() / 2.0f;
        final float centerY = containerView.getHeight() / 2.0f;
        // 根据参数创建一个新的三维动画,并且监听触发下一个动画
        final Rotate3d rotation = new Rotate3d(start, end, centerX, centerY,310.0f, true);
        rotation.setDuration(300);//设置动画持续时间
        rotation.setInterpolator(new AccelerateInterpolator());//设置动画变化速度
        rotation.setAnimationListener(new DisplayNextView(tag));//显示下一个视图
        containerView.startAnimation(rotation);
    }

    /**
     * 显示下一个视图
     * @author qiulong
     *
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int tag;

        private DisplayNextView(int tag) {
            this.tag = tag;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            containerView.post(new SwapViews(tag));
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * 执行翻转第二个视图动画子线程
     * @author qiulong
     *
     */
    private final class SwapViews implements Runnable {
        private final int tag;

        public SwapViews(int position) {
            tag = position;
        }

        public void run() {
            if (tag == 0) {
                showView(tag,mVipCouponsContainerCl, mVipCouponsContainerCl1, 90, 0);
            } else if (tag == 1) {
                showView(tag,mVipCouponsContainerCl1, mVipCouponsContainerCl, -90, 0);
            }
        }
    }

    /**
     * 显示第二个视图动画
     * @param showView 要显示的视图
     * @param hiddenView 要隐藏的视图
     * @param start_jd 开始角度
     * @param end_jd 目标角度
     */
    private void showView(int tag, ConstraintLayout showView, ConstraintLayout hiddenView, int start_jd, int end_jd) {
        //同样以中心点进行翻转
        float centerX = showView.getWidth() / 2.0f;
        float centerY = showView.getHeight() / 2.0f;
        if (centerX == 0 || centerY == 0) {
            //调用该方法getMeasuredWidth()，必须先执行measure()方法，否则会出异常。
            showView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //获取该view在父容器里面占的大小
            centerX = showView.getMeasuredWidth() / 2.0f;
            centerY = showView.getMeasuredHeight() / 2.0f;
        }
        hiddenView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
        Rotate3d rotation = new Rotate3d(start_jd, end_jd, centerX, centerY, 310.0f, false);
        rotation.setDuration(300);//设置动画持续时间
        rotation.setInterpolator(new DecelerateInterpolator());//设置动画变化速度
        containerView.startAnimation(rotation);
    }


}
