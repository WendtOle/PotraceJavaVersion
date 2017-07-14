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
        setInitialSearchParameters();
        lookForFilledPixelInAllWords();
        return currentPixel;
    }

    private void setInitialSearchParameters() {
        setInitialSearchLocation();
        currentPixel = new NoFilledPixelFound();
        noPixelWasFound = true;
    }

    private void setInitialSearchLocation() {
        int xComponent = bitmapHandler.getBeginningIndexOfWordWithPixel(currentPixel);
        int yComponent = currentPixel.y;
        currentSearchPosition = new Point(xComponent,yComponent);
    }

    private void lookForFilledPixelInAllWords() {
        while (shouldContinueSearch()) {
            lookInCurrentWordForFilledPixel();
            goToNextWord();
        }
    }

    private boolean shouldContinueSearch() {
        return isSearchPositionStillInBitmap() && noPixelWasFound;
    }

    private boolean isSearchPositionStillInBitmap() {
        return bitmapHandler.isPixelInBitmap(currentSearchPosition);
    }

    private void lookInCurrentWordForFilledPixel() {
        if (isAFilledPixelInCurrentWord())
            goToPositionOfFirstFilledPixelInWord();
    }

    private boolean isAFilledPixelInCurrentWord() {
        return bitmapHandler.areThereFilledPixelInWord(currentSearchPosition);
    }

    private void goToPositionOfFirstFilledPixelInWord() {
        currentPixel = getFirstFilledPixelInWord();
        noPixelWasFound = false;
    }

    private Point getFirstFilledPixelInWord() {
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
        return !isSearchPositionStillInBitmap();
    }

    private void goToStartOfNextLine() {
        currentSearchPosition = new Point(0,currentSearchPosition.y -1);
    }
}