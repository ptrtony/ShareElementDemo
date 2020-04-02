package com.foxcr.testeasydemo.shareElementAnim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.foxcr.testeasydemo.R;
import static com.foxcr.testeasydemo.shareElementAnim.ActivityB.IMAGE_URL_EXTRA;
import static com.foxcr.testeasydemo.shareElementAnim.ActivityB.PROPNAME_HEIGHT;
import static com.foxcr.testeasydemo.shareElementAnim.ActivityB.PROPNAME_SCREENLOCATION_LEFT;
import static com.foxcr.testeasydemo.shareElementAnim.ActivityB.PROPNAME_SCREENLOCATION_TOP;
import static com.foxcr.testeasydemo.shareElementAnim.ActivityB.PROPNAME_WIDTH;
import static com.foxcr.testeasydemo.shareElementAnim.ActivityB.VIEW_INFO_EXTRA;

public class CFragment  extends Fragment implements OnActivityFinishListener {
        private static final long DEFAULT_DURATION = 800;
        private LinearInterpolator DEFAULT_INTERPOLATOR = new LinearInterpolator();
        private ImageView mDestinationView;
        private String imageUrl;
        private int mStartXValues,mEndXValues,mStartYValues,mEndYValues;
        private int mStartWidth,mEndWidth,mStartHeight,mEndHeight;
        private Activity mActivity;
        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            mActivity = (Activity) context;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_c,container,false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mDestinationView = view.findViewById(R.id.mImageView);
            extractViewInfoFromBundle(mActivity.getIntent());

            ((ActivityA)mActivity).setOnActivityFinishListener(this);
            Glide.with(this).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    mDestinationView.setImageBitmap(resource);
                    onUiReady();
                }
            });
        }


        private void onUiReady() {
            mDestinationView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // remove previous listener
                    mDestinationView.getViewTreeObserver().removeOnPreDrawListener(this);
                    // prep the scene
                    prepareScene();
                    // run the animation
                    runEnterAnimation();
                    return true;
                }
            });
        }

        private void extractViewInfoFromBundle(Intent intent) {
            if (intent!=null){
                Bundle bundle = intent.getBundleExtra(VIEW_INFO_EXTRA);
                imageUrl = intent.getStringExtra(IMAGE_URL_EXTRA);
                if (bundle!=null){
                    mStartXValues = bundle.getInt(PROPNAME_SCREENLOCATION_LEFT,0);
                    mStartYValues = bundle.getInt(PROPNAME_SCREENLOCATION_TOP,0);
                    mStartWidth =   bundle.getInt(PROPNAME_WIDTH,0);
                    mStartHeight = bundle.getInt(PROPNAME_HEIGHT,0);
                }

            }

        }


        private void prepareScene() {

            int[] locations = new int[2];
            mDestinationView.getLocationOnScreen(locations);
            mEndXValues = locations[0];
            mEndYValues = locations[1];
            mEndWidth = mDestinationView.getWidth();
            mEndHeight = mDestinationView.getHeight();
            mDestinationView.setScaleX(getScaleX());
            mDestinationView.setScaleY(getScaleY());
            mDestinationView.setTranslationX(getDeltaX());
            mDestinationView.setTranslationY(getDeltaY());
        }


        private float getScaleX(){
            return scaleDelta(mStartWidth, mEndWidth);
        }

        private float getScaleY(){
            return scaleDelta(mStartHeight, mEndHeight);
        }

        private int getDeltaX(){
            return translationDelta(mStartXValues, mEndXValues);
        }

        private int getDeltaY(){
            return translationDelta(mStartYValues, mEndYValues);
        }




        private int translationDelta(int startPosition,int endPosition){
            return startPosition - endPosition;
        }

        private float scaleDelta(int startValue,int endValue){
            if (endValue == 0)return 1;
            return startValue/(endValue * 1.0f);
        }


        private void runEnterAnimation() {
            // We can now make it visible
            mDestinationView.setVisibility(View.VISIBLE);
            // finally, run the animation
            mDestinationView.animate()
                    .setDuration(DEFAULT_DURATION)
                    .setInterpolator(DEFAULT_INTERPOLATOR)
                    .scaleX(1f)
                    .scaleY(1f)
                    .translationX(0)
                    .translationY(0)
                    .start();
        }

        private void runExitAnimation() {
            mDestinationView.animate()
                    .setDuration(DEFAULT_DURATION)
                    .setInterpolator(DEFAULT_INTERPOLATOR)
                    .scaleX(getScaleX())
                    .scaleY(getScaleY())
                    .translationX(getDeltaX())
                    .translationY(getDeltaY())
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.finish();
                            mActivity.overridePendingTransition(0, 0);
                        }
                    }).start();
        }


        @Override
        public void onActivityFinish() {
            runExitAnimation();
        }
}
