package com.example.sampleapplication.utils;

import org.tensorflow.lite.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class SqautHandler {
    private Interpreter squatsModel;
    private boolean doneOneSquat;
    private int squatCount;
    private List<Integer> scoreList;
    private HintPlayer hintPlayer;

    // settings
    private final float OUTPUT_OF_ONE_SQAUT = 0.7f;


    public SqautHandler(Interpreter squatsModel, HintPlayer hintPlayer) {
        this.squatsModel = squatsModel;
        this.doneOneSquat = false;
        this.squatCount = 0;
        this.scoreList = new ArrayList<>();
        this.hintPlayer = hintPlayer;
    }

    public void run(float[] input, float[][] output) {
        squatsModel.run(input,output);
        
    }


    public int getAvgScore() {
        int sum = 0;
        for (int score : scoreList) {
            sum += score;
        }
        return sum / scoreList.size();
    }

    public int getSquatCount() {
        return squatCount;
    }



}
