package com.veve.typeone.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ThreeColumnRecord extends DiaryRecord {


    @PrimaryKey(autoGenerate = true)
    long _id;
    long time;
    float glucoseLevel;
    float insulinShot;
    long mealId;

    public ThreeColumnRecord() {

    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(float glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    public float getInsulinShot() {
        return insulinShot;
    }

    public void setInsulinShot(float insulinShot) {
        this.insulinShot = insulinShot;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }
}
