package com.example.assignment1.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CircleImageView extends AppCompatImageView {
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs, Paint paint) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr, Paint paint) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint=new Paint();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //若没有图片则返回
        if(getDrawable()==null){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        //若对应wrap_content,则宽度取图片的宽度，否则取viewgroup传过来的值
        if(widthMode==MeasureSpec.AT_MOST){
            //取图片和viewgroupk分配给子view的最大值之中的最小值
            width=Math.min(getDrawable().getIntrinsicWidth(), width);
        }

        //同宽度处理
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode==MeasureSpec.AT_MOST){
            height=Math.min(getDrawable().getIntrinsicHeight(), height);
        }

        //最后取宽度和高度两者的最小，使view的宽高相等
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub

        Drawable drawable=getDrawable();
        if(drawable==null) return;

        //拉伸图片，使宽高相等，以适应view的大小
        drawable.setBounds(0, 0, getWidth(), getHeight());

        //创建图层，将图层压入栈，canvas的各种绘图操作将在该图层实现
        int layer=canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //先将图片画到图层
        drawable.draw(canvas);

        //创建一个bitmap,并在bitmap上绘制一个圆形，其实不止圆形，你可以在上面画各种图形都可以，反正和原始图片有交集就可以
        Bitmap bitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas bitmapCanvas=new Canvas(bitmap);
        bitmapCanvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, paint);

        //设置xfermode,该模式是取两次画图的交集部分，即圆形部分，且显示第一次画图的内容，即原始图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        //将带有圆形的bitmap覆盖上去，实际上相当于面具
        canvas.drawBitmap(bitmap, 0, 0, paint);

        //将图层弹出栈，并将图层的内容提交到最终的canvas上
        canvas.restoreToCount(layer);

    }
}
