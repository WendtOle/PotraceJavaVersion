package Bencharking;

import AdditionalCode.FileInputOutput.JsonEncoder;
import Potrace.General.Bitmap;
import Potrace.General.DecompositionInterface;
import Potrace.General.Param;
import org.openjdk.jmh.annotations.*;

import java.io.File;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;


public class Benchmarking{

    @State(Scope.Thread)
    public static class MySate {

        Bitmap[] bitmaps;
        Potrace.General.Param params;

        @Setup
        public void setBitmap(){
            bitmaps = loadTestPictures();
        }

        @Setup
        public void setUpParams() {
            params = new Param();
        }

        private Bitmap[] loadTestPictures() {
            File[] bitmapFiles = getAllBitmapFilesForTesting("testPictures");
            Bitmap[] testPictures = new Bitmap[bitmapFiles.length];
            for (int i = 0; i < bitmapFiles.length; i++)
                testPictures[i] = loadBitmap(bitmapFiles[i]);
            return testPictures;
        }

        private Bitmap loadBitmap(File bitmapFile){
            JsonEncoder encoder = new JsonEncoder(bitmapFile);
            return encoder.getBitmap();
        }

        private File[] getAllBitmapFilesForTesting(String folderNameOfTestPictures) {
            return new File(folderNameOfTestPictures).listFiles((dir, name) -> {
                        return name.toLowerCase().endsWith(".json");
                    }
            );
        }
    }

    @Benchmark
    @Warmup(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @Measurement(iterations = 10, time = 500, timeUnit = MILLISECONDS)
    @OutputTimeUnit(NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @Fork(2)
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
    @Fork(2)
    @Threads(1)
    public void mesureOriginal(MySate state) throws InterruptedException {
        for(Bitmap currentBitmap : state.bitmaps) {
            DecompositionInterface decomposer = new Potrace.original.Decompose();
            decomposer.getPathList(currentBitmap,state.params);
        }
    }
}