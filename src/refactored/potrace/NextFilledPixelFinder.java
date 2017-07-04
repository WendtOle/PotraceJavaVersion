package refactored.potrace;

import java.awt.*;

/**
 * Created by andreydelany on 02.07.17.
 */
public class NextFilledPixelFinder {

        Point currentPixel;
        BitmapHandlerInterface bitmapHandler;
        Bitmap bitmap;

        public NextFilledPixelFinder(Bitmap bitmap){
            this.bitmapHandler = new BitmapHandler(bitmap);
            currentPixel = new Point(0,bitmap.height-1);
        }

        public boolean isThereAFilledPixel() {
            currentPixel = findNextFilledPixel();
            return currentPixel != null;
        }

        public Point getPositionOfNextFilledPixel() {
            return currentPixel;
        }

        private Point findNextFilledPixel() {
            int x0 = bitmapHandler.getBeginningIndexOfWordWithPixel(currentPixel);

            for (int y=currentPixel.y; y>=0; y--) {
                for (int x = x0; x<bitmapHandler.getWithOfBitmap() && x>=0; x+=Bitmap.PIXELINWORD) {

                    if (bitmapHandler.areThereFilledPixelInWord(new Point(x,y))) {
                        while (!bitmapHandler.isPixelFilled(new Point(x,y))) {
                            x++;
                        }
                        return new Point(x,y);
                    }
                }
                x0 = 0;
            }
            return null;
        }
}