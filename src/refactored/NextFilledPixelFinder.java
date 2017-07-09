package refactored;

import General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class NextFilledPixelFinder {
    Point currentPixel;
    boolean wasAFilledPixelFound = false;
    BitmapHandlerInterface bitmapHandler;
    Bitmap bitmap;
    private Point currentSearchPosition;

    public NextFilledPixelFinder(Bitmap bitmap){
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.bitmap = bitmap;
        currentPixel = new Point(0,bitmap.h-1);
    }

    public boolean isThereAFilledPixel() {
        lookingForFilledPixel();
        return wasAFilledPixelFound;
    }

    public Point getPositionOfNextFilledPixel(){
        throwNoPixelFoundExceptionIfNecessary();
        return currentPixel;
    }

    private void throwNoPixelFoundExceptionIfNecessary() {
        if(!wasAFilledPixelFound) {
            throw new RuntimeException("There are no more filled Pixels");
        }
    }

    private void lookingForFilledPixel() {
        currentSearchPosition = new Point(bitmapHandler.getBeginningIndexOfWordWithPixel(currentPixel),currentPixel.y);
        wasAFilledPixelFound = false;
        lookForFilledPixelInAllWords();
    }

    private void lookForFilledPixelInAllWords() {
        while (shouldContinueLooking()) {
            whenThereIsAFilledPixelGoToIt();
            goToNextSearchLocation();
        }
    }

    private boolean shouldContinueLooking() {
        return isPixelStillInBitmap(currentSearchPosition) && !wasAFilledPixelFound;
    }

    private boolean isPixelStillInBitmap(Point pixel) {
        return pixel.x<bitmapHandler.getWithOfBitmap() && pixel.x>=0 && pixel.y>=0;
    }

    private void whenThereIsAFilledPixelGoToIt() {
        if (bitmapHandler.areThereFilledPixelInWord(currentSearchPosition)) {
            wasAFilledPixelFound = true;
            getPositionOfNextFilledPixelInWord();
        } else {
            wasAFilledPixelFound = false;
        }
    }

    private void goToNextSearchLocation() {
        currentSearchPosition.x += Bitmap.PIXELINWORD;
        if (bitmapHandler.getWithOfBitmap() <= currentSearchPosition.x)
            currentSearchPosition = new Point(0,currentSearchPosition.y -1);
    }

    private void getPositionOfNextFilledPixelInWord() {
        Point currentPosition = (Point)currentSearchPosition.clone();
        while (!bitmapHandler.isPixelFilled(currentPosition)) {
            currentPosition.x++;
        }
        currentPixel = currentPosition;
    }
}