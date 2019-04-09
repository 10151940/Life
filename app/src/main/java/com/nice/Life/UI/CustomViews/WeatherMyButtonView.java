package com.nice.Life.UI.CustomViews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class WeatherMyButtonView extends ViewGroup {
    //正常情况下的颜色
    private int normal_color;
    //选中时候的颜色
    private int pressed_color;

    public WeatherMyButtonView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有的子控件，这会触发每个子view的onmeasure函数
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {//如果没有子view，当前viewgroup没有存在的意义，不用占用控件
            setMeasuredDimension(0 ,0);
        } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
            //宽度设置为viewgroup自己的测量宽度，高度设置为所有子view的高度中和
            setMeasuredDimension(widthSize, getTotleHeight());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMaxChildWidth(), heightSize);
        }
    }

    //获取view的宽度最大值
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i=0; i<childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    //将所有子view的高度相加
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i=0; i<childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = t;
        //将子view逐个摆放
        for (int i=0; i<count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子view，参数分别是子view矩形区域的左，上，右，下
            child.layout(l, curHeight, l + width, curHeight + height);
            curHeight += height;
        }
    }
}
