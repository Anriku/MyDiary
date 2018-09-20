package com.anrikuwen.mydiary.diaryfragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.anrikuwen.mydiary.utils.DensityUtil;

/**
 * Created by 10393 on 2017/2/9.
 */

/**
 * 简单的自定义显示日期的控件
 */
public class MyCalendarView extends View {

    private Paint paint;
    private Rect bounds;

    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String month = DiaryTime.getMonth();
        String day = DiaryTime.getDay();
        String weekDay = DiaryTime.getWeekDay();

        paint.setColor(Color.parseColor("#99ccff"));
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        paint.getTextBounds(month,0,month.length(),bounds);
        float monthWidth = bounds.width();
        float monthHeight = bounds.height();
        canvas.drawText(month,getWidth()/2 - monthWidth/2,monthHeight*2,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(DensityUtil.dip2px(getContext(),40));
        paint.getTextBounds(day,0,day.length(),bounds);
        float dayWidth = bounds.width();
        float dayHeight = bounds.height();
        canvas.drawText(day,getWidth()/2 - dayWidth/2,getHeight()/2 + dayHeight/2,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(DensityUtil.dip2px(getContext(), 15));
        paint.getTextBounds(weekDay,0,weekDay.length(),bounds);
        float weekDayWidth = bounds.width();
        float weekDayHeight = bounds.height();
        canvas.drawText(weekDay,getWidth()/2 - weekDayWidth/2,getHeight() - weekDayHeight,paint);
    }
}
