package com.foxcr.testeasydemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;

import com.foxcr.testeasydemo.R;

public class VipCoupons3DTranslateView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap originBitmap;
    private Bitmap translateBitmap;
    private Camera mCamera;//实现翻转的核心类
    private Matrix mMatrix;//存储变化后的矩阵
    private int currentDegree;//当前翻转角度
    private float axisY;//旋转轴的Y坐标
    private int viewWidth;//控件宽
    private int viewHeight;//控件高
    public VipCoupons3DTranslateView(Context context) {
        this(context,null);
    }


    public VipCoupons3DTranslateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VipCoupons3DTranslateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /**
     * 初始化
     */
    private void init(Context context) {
        mCamera = new Camera();
        mMatrix = new Matrix();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        View originView = LayoutInflater.from(context).inflate(R.layout.item_vip_coupons,null);
        View translateView = LayoutInflater.from(context).inflate(R.layout.item_vip_coupons,null);
        originBitmap = convertViewToBitmap(originView);
        translateBitmap = convertViewToBitmap(translateView);
        axisY = 0;
        currentDegree = 0;
    }





    @Override
    protected void onDraw(Canvas canvas) {
        //绘制下面那张图片
        mCamera.save();
        mCamera.rotateX(-currentDegree);//设置旋转角度
        mCamera.getMatrix(mMatrix);//获取到旋转后的矩阵
        mCamera.restore();
        mMatrix.preTranslate(-viewWidth / 2, 0);//绕自己的Top旋转
        mMatrix.postTranslate(viewWidth / 2, axisY);//旋转完后移动图片到轴线下面
        canvas.drawBitmap(originBitmap, mMatrix, mPaint);

        //绘制上面那张图片
        mCamera.save();
        mCamera.rotateX(90-currentDegree);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate(-viewWidth / 2, -viewHeight);//绕自己的bottom旋转
        mMatrix.postTranslate(viewWidth / 2, axisY);//旋转完后移动图片到轴线上面
        canvas.drawBitmap(translateBitmap, mMatrix, mPaint);

    }



    /**
     * 测量大小，根据控件大小按比例缩放图片，使得图片填充整个控件
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        if(viewWidth != 0 && viewHeight != 0) {
            originBitmap = scaleBitmap(originBitmap);
            translateBitmap = scaleBitmap(translateBitmap);
        }
    }

    /**
     * 按比例缩放图片
     * @param origin
     * @return
     */
    private Bitmap scaleBitmap(Bitmap origin) {
        if(origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) viewWidth) / width;
        float scaleHeight = ((float) viewHeight) / height;
        Matrix mt = new Matrix();
        mt.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(origin, 0, 0,
                width, height, mt, false);
        return newBitmap;
    }

    public Bitmap convertViewToBitmap(View view){
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();

    }
    /**
     * 设置当前翻转角度，并重新绘制
     * @param degree
     */
    public void setRotateDegree(int degree) {
        currentDegree = degree;
        axisY = degree / 90f * viewHeight;
        invalidate();
    }
}
