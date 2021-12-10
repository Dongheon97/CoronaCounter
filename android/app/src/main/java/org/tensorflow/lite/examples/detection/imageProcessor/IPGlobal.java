package org.tensorflow.lite.examples.detection.imageProcessor;

import android.app.Application;

public class IPGlobal extends Application {
    private boolean isBack;
    private boolean isRight;
    private int maximum;
    private int current;
    private int limited;

    public boolean getIsBack(){
        return this.isBack;
    }
    public void setIsBack(boolean givenBool){
        this.isBack = givenBool;
    }

    public boolean getIsRight(){
        return isRight;
    }
    public void setIsRight(boolean givenBool){
        this.isRight = givenBool;
    }

    public int getMaximum(){
        return this.maximum;
    }
    public void setMaximum(int givenMax){
        this.maximum = givenMax;
    }

    public int getCurrent(){
        return this.current;
    }
    public void setCurrent(int givenCounted){
        this.current = givenCounted;
    }

    public int getLimited(){
        return this.limited;
    }
    public void setLimited(int givenLimited){
        this.limited = givenLimited;
    }



}
