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
        Point startPosition = new Point(bitmapHandler.getBeginningIndexOfWordWithPixel(currentPixel),currentPixel.y);
        wasAFilledPixelFound = false;
        lookForFilledPixelInAllWords(startPosition);
    }

    private void lookForFilledPixelInAllWords(Point currentSearchPosition) {
        while (shouldContinueLooking(currentSearchPosition)) {
            wasAFilledPixelFound = isInCurrentWordAFilledPixel(currentSearchPosition);
            currentSearchPosition = goToNextSearchLocation(currentSearchPosition);
        }
    }

    private boolean shouldContinueLooking(Point pixel) {
        return isPixelStillInBitmap(pixel) && !wasAFilledPixelFound;
    }

    private boolean isPixelStillInBitmap(Point pixel) {
        return pixel.x<bitmapHandler.getWithOfBitmap() && pixel.x>=0 && pixel.y>=0;
    }

    private boolean isInCurrentWordAFilledPixel(Point pixel) {
        if (bitmapHandler.areThereFilledPixelInWord(pixel)) {
            currentPixel = getPositionOfNextFilledPixel(pixel);
            return true;
        }
        return false;
    }

    private Point goToNextSearchLocation(Point currentPosition) {
        currentPosition.x += Bitmap.PIXELINWORD;
        if (bitmapHandler.getWithOfBitmap() > currentPosition.x)
            return currentPosition;
        else
            return new Point(0,currentPosition.y -1);
    }

    private Point getPositionOfNextFilledPixel(Point startPosition) {
        Point currentPosition = new Point(startPosition.x,startPosition.y);
        while (!bitmapHandler.isPixelFilled(currentPosition)) {
            currentPosition.x++;
        }
        return currentPosition;
    }
}