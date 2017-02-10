package com.anrikuwen.mydiary.database;

import org.litepal.crud.DataSupport;

/**
 * Created by 10393 on 2017/2/9.
 */

public class DiaryData extends DataSupport {

    private String diaryTitle;
    private String diaryContent;
    private String weather;
    private String mood;
    private String diaryMonth;
    private String diaryDay;
    private String diaryWeekDay;
    private String diaryYear;
    private String diaryTime;

    public String getDiaryTime() {
        return diaryTime;
    }

    public void setDiaryTime(String diaryTime) {
        this.diaryTime = diaryTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

    public String getDiaryMonth() {
        return diaryMonth;
    }

    public void setDiaryMonth(String diaryMonth) {
        this.diaryMonth = diaryMonth;
    }

    public String getDiaryDay() {
        return diaryDay;
    }

    public void setDiaryDay(String diaryDay) {
        this.diaryDay = diaryDay;
    }

    public String getDiaryWeekDay() {
        return diaryWeekDay;
    }

    public void setDiaryWeekDay(String diaryWeekDay) {
        this.diaryWeekDay = diaryWeekDay;
    }

    public String getDiaryYear() {
        return diaryYear;
    }

    public void setDiaryYear(String diaryYear) {
        this.diaryYear = diaryYear;
    }
}
