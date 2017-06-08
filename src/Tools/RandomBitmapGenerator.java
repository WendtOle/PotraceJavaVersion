package Tools;

import java.awt.*;
import java.util.Random;

public class RandomBitmapGenerator {
    Random randomGenerator;
    int maxWidth;
    int maxHeight;
    double noiseRatio;

    public RandomBitmapGenerator(int maxWidth,int maxHeight, double noiseRatio) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.noiseRatio = noiseRatio;
    }

    public BetterBitmap getRandomBitmap() {
        randomGenerator = new Random();
        Dimension dimension = generateRandomDimensions();
        return createRandomBitmap(dimension);
    }

    private Dimension generateRandomDimensions() {
        int width = randomGenerator.nextInt(maxWidth)+1;
        int height = randomGenerator.nextInt(maxHeight)+1;
        return new Dimension(width,height);
    }

    private BetterBitmap createRandomBitmap(Dimension dimension) {
        BetterBitmap bitmap = new BetterBitmap(dimension.width, dimension.height);
        for (int y = 0; y < dimension.height; y++)
            for(int x = 0; x < dimension.width; x++) {
                if(isPixelFilled())
                    bitmap.addBlob(new Point(x,y),true);
            }
        return bitmap;
    }

    private boolean isPixelFilled() {
        double randomDouble = randomGenerator.nextDouble();
        if (randomDouble >= noiseRatio)
            return true;
        return false;
    }
}
