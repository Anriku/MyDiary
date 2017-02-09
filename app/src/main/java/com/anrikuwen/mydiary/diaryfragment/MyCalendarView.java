package com.anrikuwen.mydiary.diaryfragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 10393 on 2017/2/9.
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if("1".equals(month)){
            month = "一月";
        }else if ("2".equals(month)){
            month = "二月";
        }else if ("3".equals(month)){
            month = "三月";
        }else if ("4".equals(month)){
            month = "四月";
        }else if ("5".equals(month)){
            month = "五月";
        }else if ("6".equals(month)){
            month = "六月";
        }else if ("7".equals(month)){
            month = "七月";
        }else if ("8".equals(month)){
            month = "八月";
        }else if ("9".equals(month)){
            month = "九月";
        }else if ("10".equals(month)){
            month = "十月";
        }else if ("11".equals(month)){
            month = "十一月";
        }else if ("12".equals(month)){
            month = "十二月";
        }
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String weekDay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        if("1".equals(weekDay)){
            weekDay = "星期天";
        }else if("2".equals(weekDay)){
            weekDay = "星期一";
        }else if("3".equals(weekDay)){
            weekDay = "星期二";
        }else if ("4".equals(weekDay)){
            weekDay = "星期三";
        }else if ("5".equals(weekDay)){
            weekDay = "星期四";
        }else if ("6".equals(weekDay)){
            weekDay = "星期五";
        }else if ("7".equals(weekDay)){
            weekDay = "星期六";
        }

        paint.setColor(Color.parseColor("#99ccff"));
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        paint.getTextBounds(month,0,month.length(),bounds);
        float monthWidth = bounds.width();
        float monthHeight = bounds.height();
        canvas.drawText(month,getWidth()/2 - monthWidth/2,monthHeight*2,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(250);
        paint.getTextBounds(day,0,day.length(),bounds);
        float dayWidth = bounds.width();
        float dayHeight = bounds.height();
        canvas.drawText(day,getWidth()/2 - dayWidth/2,getHeight()/2 + dayHeight/2,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(80);
        paint.getTextBounds(weekDay,0,weekDay.length(),bounds);
        float weekDayWidth = bounds.width();
        float weekDayHeight = bounds.height();
        canvas.drawText(weekDay,getWidth()/2 - weekDayWidth/2,getHeight() - weekDayHeight,paint);
    }
}
