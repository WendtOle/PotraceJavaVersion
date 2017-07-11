package AdditionalCode.Input;

import AdditionalCode.BitmapManipulator;
import Potrace.General.Bitmap;

import java.awt.*;
import java.util.Random;

/**
 * Created by andreydelany on 22.06.17.
 */

public class RandomGeneratedBitmap extends Bitmap {

    Random randomGenerator = new Random();

    public RandomGeneratedBitmap(int maxWidth, int maxHeight, double noiseRatio) {
        super();
        Dimension randomDimensions = generateRandomDimensions(maxWidth,maxHeight);
        this.w = randomDimensions.width;
        this.h = randomDimensions.height;
        fillBitmapWithRandomValues(noiseRatio);
    }

    private Dimension generateRandomDimensions(int maxWidth, int maxHeight) {
        int width = randomGenerator.nextInt(maxWidth)+1;
        int height = randomGenerator.nextInt(maxHeight)+1;
        return new Dimension(width,height);
    }

    private void fillBitmapWithRandomValues(double noiseRatio) {
        for (int y = 0; y < h; y++)
            for(int x = 0; x < w; x++) {
                if(isPixelFilled(noiseRatio))
                    BitmapManipulator.BM_PUT(this,x,y,true);
            }
    }

    private boolean isPixelFilled(double noiseRatio) {
        double randomDouble = randomGenerator.nextDouble();
        if (randomDouble >= noiseRatio)
            return true;
        return false;
    }
}


