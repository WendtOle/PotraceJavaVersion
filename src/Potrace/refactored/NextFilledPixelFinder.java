package Potrace.refactored;

import Potrace.General.Bitmap;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class NextFilledPixelFinder {
    Point currentPixel;
    boolean noPixelWasFound = false;
    BitmapHandlerInterface bitmapHandler;
    Bitmap bitmap;
    private Point currentSearchPosition;

    public NextFilledPixelFinder(Bitmap bitmap){
        this.bitmapHandler = new BitmapHandler(bitmap);
        this.bitmap = bitmap;
        currentPixel = new Point(0,bitmap.h-1);
    }

    public Point getPositionOfNextFilledPixel(){
        goThroughWholePictureUntilAFilledPixelWasFound();
        return currentPixel;
    }

    private void goThroughWholePictureUntilAFilledPixelWasFound() {
        setInitialSearchPosition();
        noPixelWasFound = true;
        lookForFilledPixelInAllWords();
    }

    private void setInitialSearchPosition() {
        int xComponent = bitmapHandler.getBeginningIndexOfWordWithPixel(currentPixel);
        int yComponent = currentPixel.y;
        currentSearchPosition = new Point(xComponent,yComponent);
        currentPixel = new NoPointFound();
    }

    private void lookForFilledPixelInAllWords() {
        while (shouldContinueSearch()) {
            lookInCurrentWordForFilledPixel();
            goToNextWord();
        }
    }

    private boolean shouldContinueSearch() {
        return isSearchPositionStillInBitmap(currentSearchPosition) && noPixelWasFound;
    }

    private boolean isSearchPositionStillInBitmap(Point pixel) {
        return pixel.x<bitmapHandler.getWithOfBitmap() && pixel.x>=0 && pixel.y>=0; //ToDO extract to BitmapHandler inRange()
    }

    private void lookInCurrentWordForFilledPixel() {
        if (isAFilledPixelInCurrentWord())
            goToPositionOfFirstFilledPixelInWord();
    }

    private boolean isAFilledPixelInCurrentWord() {
        return bitmapHandler.areThereFilledPixelInWord(currentSearchPosition);
    }

    private void goToPositionOfFirstFilledPixelInWord() {
        currentPixel = goThroughWordToFirstFilledPixel();
        noPixelWasFound = false;
    }

    private Point goThroughWordToFirstFilledPixel() {
        Point currentPosition = (Point)currentSearchPosition.clone();
        while (isPixelEmpty(currentPosition))
            currentPosition.x++;
        return currentPosition;
    }

    private boolean isPixelEmpty(Point currentPosition) {
        return !bitmapHandler.isPixelFilled(currentPosition);
    }

    private void goToNextWord() {
        goToNextWordInLine();
        if (isEndOfLineReached())
            goToStartOfNextLine();
    }

    private void goToNextWordInLine() {
        currentSearchPosition.x += Bitmap.PIXELINWORD;
    }

    private boolean isEndOfLineReached() {
        return bitmapHandler.getWithOfBitmap() <= currentSearchPosition.x;
    }

    private void goToStartOfNextLine() {
        currentSearchPosition = new Point(0,currentSearchPosition.y -1);
    }
}