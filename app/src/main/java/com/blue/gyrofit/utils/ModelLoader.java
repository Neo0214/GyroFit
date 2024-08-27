package com.blue.gyrofit.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ModelLoader {
    private String modelPath;
    private Context context;

    public ModelLoader(String modelPath,Context context) {
        this.modelPath = modelPath;
        this.context = context;
    }

    public Interpreter loadModel() {
        return new Interpreter(loadModelFile());
    }

    private MappedByteBuffer loadModelFile() {
        try {
            AssetFileDescriptor fileDescriptor = this.context.getAssets().openFd(modelPath);
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
