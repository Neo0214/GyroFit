package com.example.sampleapplication.utils;

import java.util.ArrayList;
import java.util.List;

public class SqautDataHandler {
    private List<Float> data;
    private final int windowSize=45;
    private final int oneSize=6;

    public SqautDataHandler() {
        this.data=new ArrayList<>();
    }

    public void AddData(String str){
        List<Float> newData=parseData(str);
        if (data.size()==windowSize*oneSize){
            // remove the first window
            data.subList(0, oneSize).clear();
            // add new window at tail
            data.addAll(newData);
        }
        else{
            data.addAll(newData);
        }
    }
    private List<Float> parseData(String str){
        return null;
    }

    public boolean isEnough(){
        return data.size()==windowSize*oneSize;
    }
}
