package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class NextFilledPixelFinder {

        Bitmap bitmap;
        Point foundPixel;

        public NextFilledPixelFinder(Bitmap bitmap){
            this.bitmap = bitmap;
        }

        public boolean isThereAFilledPixel(Point startPointForSearch) {
            foundPixel = findNextFilledPixel(startPointForSearch);
            return foundPixel != null;
        }

        public Point getPositionofNextFilledPixel() {
            return foundPixel;
        }

        private Point findNextFilledPixel(Point startPointforSearch) {
            int x0 = getBeginningIndexOfCurrentWord(startPointforSearch.x);

            for (int y=startPointforSearch.y; y>=0; y--) {
                for (int x = x0; x<bitmap.width && x>=0; x+=Bitmap.PIXELINWORD) {

                    if (getWordWherePixelIsContained(new Point(x, y)) != 0) {
                        while (!bitmap.getPixelValue(new Point(x, y))) {
                            x++;
                        }
                        return new Point(x,y);
                    }
                }
                x0 = 0;
            }
            return null;
        }

        private int getBeginningIndexOfCurrentWord(int index) {
            return (index) & ~(Bitmap.PIXELINWORD-1);
        }

        private long getWordWherePixelIsContained(Point pixel) {
            return getLineWherePixelIsContained(pixel.y)[pixel.x/Bitmap.PIXELINWORD];
        }

        private long[] getLineWherePixelIsContained(int y) {
            long[] scanLine = new long[bitmap.wordsPerScanLine];
            for (int i = 0; i < bitmap.wordsPerScanLine; i ++) {
                scanLine[i] = bitmap.words[(y * bitmap.wordsPerScanLine) + i];
            }
            return scanLine;
        }
}
