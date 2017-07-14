package AdditionalCode.TestDataGeneration;

import AdditionalCode.BitmapManipulator;
import Potrace.General.Bitmap;

import java.awt.*;
import java.util.Random;

public class RandomBitmapGenerator {

    Random randomGenerator = new Random();
    Dimension maxDimensions;

    public RandomBitmapGenerator(Dimension maxDimension) {
        this.maxDimensions = maxDimension;
    }

    public Bitmap generate(double noiseRatio){
        Dimension randomDimensions = generateRandomDimensions(maxDimensions);
        Bitmap bitmap = new Bitmap(randomDimensions.width,randomDimensions.height);
        fillBitmapWithRandomValues(bitmap,noiseRatio);
        return bitmap;
    }

    private Dimension generateRandomDimensions(Dimension maxDimension) {
        int randomWidth = randomGenerator.nextInt(maxDimension.width)+1;
        int randomHeight = randomGenerator.nextInt(maxDimension.height)+1;
        return new Dimension(randomWidth,randomHeight);
    }

    private void fillBitmapWithRandomValues(Bitmap bitmap, double noiseRatio) {
        for (int y = 0; y < bitmap.h; y++)
            for(int x = 0; x < bitmap.w; x++) {
                if(isPixelFilled(noiseRatio))
                    BitmapManipulator.BM_PUT(bitmap,x,y,true);
            }
    }

    private boolean isPixelFilled(double noiseRatio) {
        return  randomGenerator.nextDouble() >= noiseRatio;
    }
}


