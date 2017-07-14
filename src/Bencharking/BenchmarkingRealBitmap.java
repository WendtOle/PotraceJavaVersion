package Bencharking;

import AdditionalCode.FileInputOutput.BitmapImporter;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Param;
import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;


public class BenchmarkingRealBitmap {

    @State(Scope.Thread)
    public static class MySate {

        Bitmap[] bitmaps;
        Param params;

        @Setup
        public void setBitmap(){
            bitmaps = loadTestPictures();
        }

        @Setup
        public void setUpParams() {
            params = new Param();
        }

        private Bitmap[] loadTestPictures() {
            Bitmap[] testPictures = new Bitmap[4];
            BitmapImporter importer = new BitmapImporter("lion.png","benchmarkingPictures");
            testPictures[0] = importer.getBitmap();
            importer = new BitmapImporter("guyWithGun.png","benchmarkingPictures");
            testPictures[1] = importer.getBitmap();
            importer = new BitmapImporter("fox.png","benchmarkingPictures");
            testPictures[2] = importer.getBitmap();
            importer = new BitmapImporter("shark.png","benchmarkingPictures");
            testPictures[3] = importer.getBitmap();

            return testPictures;
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(4)
    @Threads(1)
    public void mesureRefactored(MySate state) throws InterruptedException {
        for(Bitmap currentBitmap : state.bitmaps) {
            DecompositionInterface decomposer = new Potrace.refactored.Decompose();
            decomposer.getPathList(currentBitmap,state.params);
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(4)
    @Threads(1)
    public void mesureOriginal(MySate state) throws InterruptedException {
        for(Bitmap currentBitmap : state.bitmaps) {
            DecompositionInterface decomposer = new Potrace.original.Decompose();
            decomposer.getPathList(currentBitmap,state.params);
        }
    }
}